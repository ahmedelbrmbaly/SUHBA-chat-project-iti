public interface MessageDAO extends Remote {
    void sendMessage(MessageDTO message) throws RemoteException, DAOException;
    void markMessageAsRead(long messageId) throws RemoteException, DAOException;
    List<MessageDTO> getMessageHistory(long chatId) throws RemoteException, DAOException;
    List<MessageDTO> getPendingMessages(int userId) throws RemoteException, DAOException; // Offline messages
}