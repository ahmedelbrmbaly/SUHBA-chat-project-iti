package com.suhba.database.entities;

import java.time.LocalDate;

public class UserSession {
    private long userId;
    private String macAddress;
    private boolean isActive;
    private LocalDate startDate;
    private LocalDate lastLoginDate;
    private LocalDate endDate;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDate lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userId=" + userId +
                ", macAddress='" + macAddress + '\'' +
                ", isActive=" + isActive +
                ", startDate=" + startDate +
                ", lastLoginDate=" + lastLoginDate +
                ", endDate=" + endDate +
                '}';
    }
}
