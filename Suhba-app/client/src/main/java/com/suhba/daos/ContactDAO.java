public interface ContactDAO extends Remote {
    void sendContactRequest(int senderUserId, String recipientPhone) throws RemoteException, DAOException;
    void acceptContactRequest(int userId1, int userId2) throws RemoteException, DAOException;
    void rejectContactRequest(int userId1, int userId2) throws RemoteException, DAOException;
    void blockContact(int userId, int contactUserId) throws RemoteException, DAOException;
    List<ContactDTO> getContacts(int userId) throws RemoteException, DAOException;
}