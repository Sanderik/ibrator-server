package com.sanderik.models.viewmodels;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ControlDeviceViewModel {

    @NotBlank
    private Long deviceId;

    // Duration in seconds.
    @NotNull
    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="Please use a number above 0")
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
