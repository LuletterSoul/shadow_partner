package com.years.ground_station.core.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.years.ground_station.core.Consts;
import com.years.ground_station.core.events.PrepareDevicesListEvent;
import com.years.ground_station.core.events.SelectDeviceEvent;
import com.years.ground_station.core.events.ShowDevicesListEvent;
import com.years.ground_station.track.Node;
import com.years.ground_station.track.Track;

import java.util.LinkedList;
import java.util.List;

import de.greenrobot.event.EventBus;

public abstract class AbstractUSBHIDService extends Service {

	private static final String TAG = AbstractUSBHIDService.class.getCanonicalName();

	private USBThreadDataReceiver usbThreadDataReceiver;

	private final Handler uiHandler = new Handler();

	private UsbManager mUsbManager;
	private UsbInterface intf;
	private UsbEndpoint endPointRead;
	private UsbEndpoint endPointWrite;
	private UsbDeviceConnection connection;
	private UsbDevice device;

	private IntentFilter filter;
	private PendingIntent mPermissionIntent;

	private int packetSize;
	private boolean sendedDataType;
	private byte[] bytes=new byte[6];

	private double a,b,c;

	public static double x=0,y=0;
	public static int left=0,right=0;

	private Node node=new Node();

	protected EventBus eventBus = EventBus.getDefault();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(Consts.ACTION_USB_PERMISSION), 0);
		filter = new IntentFilter(Consts.ACTION_USB_PERMISSION);
		filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
		filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
		filter.addAction(Consts.ACTION_USB_SHOW_DEVICES_LIST);
		filter.addAction(Consts.ACTION_USB_DATA_TYPE);
		filter.addAction("startreceive");// control receive and stop receive
		filter.addAction("stopreceive");
		registerReceiver(mUsbReceiver, filter);
		eventBus.register(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		String action = intent.getAction();
		if (Consts.ACTION_USB_DATA_TYPE.equals(action)) {
			sendedDataType = intent.getBooleanExtra(Consts.ACTION_USB_DATA_TYPE, false);
		}
		onCommand(intent, action, flags, startId);
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onDestroy() {
		eventBus.unregister(this);
		super.onDestroy();
		if (usbThreadDataReceiver != null) {
			usbThreadDataReceiver.stopThis();
		}
		unregisterReceiver(mUsbReceiver);
	}

	private class USBThreadDataReceiver extends Thread {

		private volatile boolean isStopped;

		public USBThreadDataReceiver() {
		}

		@Override
		public void run() {
			try {
				if (connection != null && endPointRead != null) {
					while (!isStopped) {
						Thread.sleep(300);
						final byte[] buffer = new byte[packetSize];
						final int status = connection.bulkTransfer(endPointRead, buffer, packetSize, 100);
						for (int i = 1; i < buffer.length - 10; i++)
						{
							if (buffer[i] == (byte) 0xAA
									&& buffer[i + 1] == (byte) 0xAA
									&& buffer[i + 2] == (byte) 0x30
									&& buffer[i + 3] == (byte) 0xD
									&& buffer[i + 4] == (byte) 0x00)
							{
								for (int j = 0; j < 6; j++)
								{
									bytes[j] = buffer[i + j + 5];
								}
							}
						}

						a= ((bytes[0] & 0xff)*256+(bytes[1] & 0xff))/100.0;
						b = ((bytes[2] & 0xff)*256+(bytes[3] & 0xff))/100.0;
						c = ((bytes[4] & 0xff)*256+(bytes[5] & 0xff))/100.0;

//						Track.set_speed(a,b,c,2.86,2.99,4.35,x,y);
//
//						node=Track.set_speed(a,b,c,1.60,1.57,1.21);
//						x=node.x;
//						y=node.y;

						node=Track.get_speed(a,b,c,0,0);
						x=node.x;
						y=node.y;
						left=node.left;
						right=node.right;


						if (status > 0) {
							uiHandler.post(new Runnable() {
								@Override
								public void run()
								{
									onUSBDataReceive(bytes);
								}
							});
						}
					}
				}
			} catch (Exception e) {
				Log.e(TAG, "Error in receive thread", e);
			}
		}


		public void stopThis() {
			isStopped = true;
		}
	}

//	public void onEventMainThread(USBDataSendEvent event){
//		sendData(event.getData(), sendedDataType);
//	}

	public void onEvent(SelectDeviceEvent event) {
		device = (UsbDevice) mUsbManager.getDeviceList().values().toArray()[event.getDevice()];
		mUsbManager.requestPermission(device, mPermissionIntent);
	}

    public void onEventMainThread(PrepareDevicesListEvent event) {
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<CharSequence> list = new LinkedList<CharSequence>();
        for (UsbDevice usbDevice : mUsbManager.getDeviceList().values()) {
            list.add(onBuildingDevicesList(usbDevice));
        }
        final CharSequence devicesName[] = new CharSequence[mUsbManager.getDeviceList().size()];
        list.toArray(devicesName);
        eventBus.post(new ShowDevicesListEvent(devicesName));
    }

//	private void sendData(String data, boolean sendAsString) {
//		if (device != null && endPointWrite != null && mUsbManager.hasPermission(device) && !data.isEmpty()) {
//			// mLog(connection +"\n"+ device +"\n"+ request +"\n"+
//			// packetSize);
//			byte[] out = data.getBytes();// UTF-16LE
//											// Charset.forName("UTF-16")
//			onUSBDataSending(data);
//			if (sendAsString) {
//				try {
//					String str[] = data.split("[\\s]");
//					out = new byte[str.length];
//					for (int i = 0; i < str.length; i++) {
//						out[i] = USBUtils.toByte(Integer.decode(str[i]));
//					}
//				} catch (Exception e) {
//					onSendingError(e);
//				}
//			}
//			int status = connection.bulkTransfer(endPointWrite, out, out.length, 250);
//			onUSBDataSended(status, out);
//		}
//	}

	/**
	 * receives the permission request to connect usb devices
	 */
	private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (Consts.ACTION_USB_PERMISSION.equals(action)) {
				setDevice(intent);
			}
			if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
				setDevice(intent);
				if (device == null) {
					onDeviceConnected(device);
				}
			}
			if (action.equals("startreceive")){
				startreceive();
			}
			if (action.equals("stopreceive")){
				stopreceive();
			}
			if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
				if (device != null) {
					device = null;
					if (usbThreadDataReceiver != null) {
						usbThreadDataReceiver.stopThis();
					}
//					eventBus.post(new DeviceDetachedEvent());
					onDeviceDisconnected(device);
				}
			}
		}

		private void setDevice(Intent intent) {
			device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
			if (device != null && intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
				onDeviceSelected(device);
				connection = mUsbManager.openDevice(device);
				intf = device.getInterface(0);
				if (null == connection) {
					// mLog("(unable to establish connection)\n");
				} else {
					connection.claimInterface(intf, true);
				}
				try {
					if (UsbConstants.USB_DIR_OUT == intf.getEndpoint(1).getDirection()) {
						endPointWrite = intf.getEndpoint(1);
					}
				} catch (Exception e) {
					Log.e("endPointWrite", "Device have no endPointWrite", e);
				}
				try {
					if (UsbConstants.USB_DIR_IN == intf.getEndpoint(0).getDirection()) {
						endPointRead = intf.getEndpoint(0);
						packetSize = endPointRead.getMaxPacketSize();
					}
				} catch (Exception e) {
					Log.e("endPointWrite", "Device have no endPointRead", e);
				}
//				usbThreadDataReceiver = new USBThreadDataReceiver();
//				usbThreadDataReceiver.start();
//				eventBus.post(new DeviceAttachedEvent());
			}
		}
	};

	public void startreceive(){
		usbThreadDataReceiver=new USBThreadDataReceiver();
		Log.i(TAG, "startreceive: ");
		usbThreadDataReceiver.start();
		//eventBus.post(new DeviceAttachedEvent());
	}
	public void stopreceive(){
		usbThreadDataReceiver.interrupt();
		Log.i(TAG, "stopreceive: ");
	}

	public void onCommand(Intent intent, String action, int flags, int startId) {
	}

	public void onUSBDataReceive(byte[] buffer) {
	}

	public void onDeviceConnected(UsbDevice device) {
	}

	public void onDeviceDisconnected(UsbDevice device) {
	}

	public void onDeviceSelected(UsbDevice device) {
	}

	public CharSequence onBuildingDevicesList(UsbDevice usbDevice) {
		return null;
	}
//
//	public void onUSBDataSending(String data) {
//	}
//
//	public void onUSBDataSended(int status, byte[] out) {
//	}
//
//	public void onSendingError(Exception e) {
//	}

}