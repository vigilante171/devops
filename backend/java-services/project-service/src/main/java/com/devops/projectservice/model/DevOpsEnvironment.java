package com.devops.projectservice.model;

public class DevOpsEnvironment {

    private String name;
    private EnvironmentType type;
    private String baseUrl;
    private boolean protectedEnvironment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnvironmentType getType() {
        return type;
    }

    public void setType(EnvironmentType type) {
        this.type = type;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public boolean isProtectedEnvironment() {
        return protectedEnvironment;
    }

    public void setProtectedEnvironment(boolean protectedEnvironment) {
        this.protectedEnvironment = protectedEnvironment;
    }
}
