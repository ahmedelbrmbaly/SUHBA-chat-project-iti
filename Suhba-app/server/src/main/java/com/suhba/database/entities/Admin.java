package com.suhba.database.entities;

public class Admin {
    private Long adminId;
    private String adminName;
    private String adminEmail;
    private String password;
    private boolean isActive;


    public Admin() {
        this.adminName = null;
        this.adminEmail = null;
        this.password = null;
        this.isActive = false;

    }

    public Admin(String adminName, String adminEmail, String password, boolean isActive) {
        this.adminName = adminName;
        this.adminEmail = adminEmail;
        this.password = password;
        this.isActive = isActive;
    }

    // Getters
    public Long getAdminId() { return adminId; }
    public String getAdminName() { return adminName; }
    public String getAdminEmail() { return adminEmail; }
    public String getPassword() { return password; }

    public boolean isActive() { return isActive; } // Boolean getter

    // Setters
    public void setAdminId(Long adminId) { this.adminId = adminId; }
    public void setAdminName(String adminName) { this.adminName = adminName; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }
    public void setPassword(String password) { this.password = password; }
    public void setActive(boolean active) { isActive = active; }


    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", adminName='" + adminName + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}