package com.sanderik.models.viewmodels;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;

public class ControlDeviceViewModel {

    @NotBlank
    private Long deviceId;

    // Duration in seconds.
    @NotNull
    @Size(min = 0, max = 100)
    private int intensity;

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }
}
