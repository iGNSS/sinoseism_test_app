package com.gxu.testapp.event;

public class AlertEvent {
    int flag;

    public AlertEvent(int  flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
