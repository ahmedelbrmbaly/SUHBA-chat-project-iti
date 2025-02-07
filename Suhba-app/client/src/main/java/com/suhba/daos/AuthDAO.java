public interface AuthDAO extends Remote {
    boolean register(UserDTO user) throws RemoteException, DAOException;
    UserDTO login(String phone, String password) throws RemoteException, DAOException;
    void logout(int userId) throws RemoteException, DAOException;
}