public class Admin {
    private int adminId;
    private String adminName;
    private String adminEmail;
    private String password;
    private boolean isActive;

    // Getters
    public int getAdminId() { return adminId; }
    public String getAdminName() { return adminName; }
    public String getAdminEmail() { return adminEmail; }
    public String getPassword() { return password; }
    public boolean isActive() { return isActive; } // Boolean getter

    // Setters
    public void setAdminId(int adminId) { this.adminId = adminId; }
    public void setAdminName(String adminName) { this.adminName = adminName; }
    public void setAdminEmail(String adminEmail) { this.adminEmail = adminEmail; }
    public void setPassword(String password) { this.password = password; }
    public void setActive(boolean active) { isActive = active; }
}