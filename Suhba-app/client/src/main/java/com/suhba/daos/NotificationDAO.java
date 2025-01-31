public interface NotificationDAO extends Remote {
    void subscribeToNotifications(int userId, NotificationListener listener) throws RemoteException, DAOException;
}

// Client implements this to receive notifications
public interface NotificationListener extends Remote {
    void onContactStatusChanged(int contactUserId, UserStatus status) throws RemoteException;
    void onMessageReceived(MessageDTO message) throws RemoteException;
}