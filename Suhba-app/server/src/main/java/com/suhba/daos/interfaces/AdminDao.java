package com.suhba.daos.interfaces;

import com.suhba.database.entities.Admin;
import java.util.List;
import java.util.Optional;

public interface AdminDao {

    public boolean isAdminCradentialsValid(String email, String password);
    public  boolean addAdmin(Admin admin);
    public Admin getAdminById(Long adminId);
    public  Admin getAdminByEmail(String adminEmail);
    public    List<Admin> getAllAdmins();
    public    boolean updateAdmin(Admin admin);
    public    boolean deleteAdmin(Long adminId);

}
