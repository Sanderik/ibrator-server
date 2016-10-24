package com.sanderik.models.viewmodels;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

public class ControlDeviceViewModel {

    @NotBlank
    private Long deviceId;

    // Duration in seconds.
    @NotNull
    private int duration;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
