package com.zystems.plantdex.models;

public class ReportedComplaintSubmission {
    private String userId;
    private String appVersion;
    private String phoneVersion;
    private String remarks;

    public ReportedComplaintSubmission() {
    }

    public ReportedComplaintSubmission(String userId, String appVersion, String phoneVersion, String remarks) {
        this.userId = userId;
        this.appVersion = appVersion;
        this.phoneVersion = phoneVersion;
        this.remarks = remarks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getPhoneVersion() {
        return phoneVersion;
    }

    public void setPhoneVersion(String phoneVersion) {
        this.phoneVersion = phoneVersion;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
