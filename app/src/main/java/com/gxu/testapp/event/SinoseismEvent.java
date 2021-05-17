package com.gxu.testapp.event;

import com.gxu.testapp.entity.SinoseismEventEntity;

public class SinoseismEvent {

    private String eventJson;

    public SinoseismEvent(String eventJson) {
        this.eventJson = eventJson;
    }

    public String getEventJson() {
        return eventJson;
    }

    public void setEventJson(String  eventJson) {
        this.eventJson = eventJson;
    }
}
