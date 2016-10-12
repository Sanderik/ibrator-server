package com.sanderik.models.viewmodels;

import javax.validation.constraints.NotNull;

public class AddDeviceModel {

    @NotNull
    private String name;

    @NotNull
    private String connectionToken;

    public String getConnectionToken() {
        return connectionToken;
    }

    public void setConnectionToken(String connectionToken) {
        this.connectionToken = connectionToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
