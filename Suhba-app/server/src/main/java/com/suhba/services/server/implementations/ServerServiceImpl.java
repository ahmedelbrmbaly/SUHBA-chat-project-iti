package com.suhba.services.server.implementations;



import com.suhba.daos.implementation.AdminDAOImpl;
import com.suhba.daos.implementation.UserDAOImpl;
import com.suhba.database.entities.Admin;
import com.suhba.database.entities.User;
import com.suhba.database.enums.Country;
import com.suhba.database.enums.Gender;
import com.suhba.database.enums.UserStatus;
import com.suhba.services.server.interfaces.ServerService;

import java.util.List;
import java.util.Map;


public class ServerServiceImpl implements ServerService {

    private final AdminDAOImpl adminDAO; // Added dependency for AdminDAO
    // setter and getter from RMI class
    private boolean serverRunning = false; // Added tracking for server state
    private final UserDAOImpl userDAO;

    // Constructor injection for AdminDAO
    public ServerServiceImpl() {
        adminDAO = new AdminDAOImpl();
        userDAO = new UserDAOImpl();
    }

    /**
     * @param email
     * @param password
     * @return Admin
     */
    @Override
    public Admin login(String email, String password) {
        if (adminDAO.isAdminCradentialsValid(email, password)) {
            return adminDAO.getAdminByEmail(email);
        }
        return null;
    }

    /**
     * @param newAdmin
     * @return Admin
     */

    @Override
    public Admin addNewAdmin(Admin newAdmin) {
        if(adminDAO.addAdmin(newAdmin))
        {
            return adminDAO.getAdminByEmail(newAdmin.getAdminEmail());
        }

        return null;

    }

    /**
     * @param admin
     * @return true if logout successfully
     */
    @Override
    public boolean logout(Admin admin) {
        admin.setActive(false);
        return adminDAO.updateAdmin(admin);


    }

    /**
     * @param admin
     * @return
     */
    @Override
    public boolean updateAdmin(Admin admin) {
        return adminDAO.updateAdmin(admin);
    }

    /**
     * @return
     */


    @Override
    public boolean startServer() {
        serverRunning = true;
        return serverRunning;
    }

    /**
     * @return
     */
    @Override
    public boolean stopServer() {
        serverRunning = false;
        return !serverRunning;
    }

    /**
     * @return
     */
    @Override
    public boolean isActive() {
        return serverRunning;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public boolean addNewUser(User user) {
        return userDAO.addNewUser(user);
    }

    /**
     * @return
     */
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    /**
     * @param msg
     * @return
     */

    // needs to discuss
    @Override
    public boolean sendAnnounce(String msg) {
        // Placeholder implementation for broadcasting an announcement
        System.out.println("Announcement: " + msg);
        return true;
    }

    /**
     * @return
     */
    @Override
    public Map<UserStatus, Long> getUserStatus() {
        return userDAO.getUsersStatus();
    }

    /**
     * @return
     */
    @Override
    public Map<Gender, Long> getGenderStatistics() {
        return userDAO.getUsersGenders();
    }

    /**
     * @return
     */
    @Override
    public Map<Country, Long> getCountryStatistics() {
        return userDAO.getUsersCountries();
    }
}