public class Admin {
    private long adminId;
    private String adminName;
    private String adminEmail;
    private String password;
    private boolean isActive;

    // Getters and Setters
    public long getAdminId() { return adminId; }
    public void setAdminId(long adminId) { this.adminId = adminId; }
    public String getAdminName() { return adminName; }
    public void setAdminName(String adminName) { this.adminName = adminName; }
    public String getAdminEmail() { return adminEmail; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
}