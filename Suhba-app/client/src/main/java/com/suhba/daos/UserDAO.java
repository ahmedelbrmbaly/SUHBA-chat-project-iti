public interface UserDAO extends Remote {
    UserDTO getUserById(int userId) throws RemoteException, DAOException;
    void updateUserStatus(int userId, UserStatus status) throws RemoteException, DAOException;
    void updateUserDetails(UserDTO user) throws RemoteException, DAOException;
    List<UserDTO> searchUsers(String query) throws RemoteException, DAOException; // For adding contacts
}