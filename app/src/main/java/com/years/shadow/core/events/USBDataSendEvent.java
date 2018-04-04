package com.years.shadow.core.events;

public class USBDataSendEvent {
    private final String data;

    public USBDataSendEvent(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

}