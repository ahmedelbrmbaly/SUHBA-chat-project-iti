public interface ChatDAO extends Remote {
    ChatDTO createDirectChat(int userId1, int userId2) throws RemoteException, DAOException;
    ChatDTO createGroupChat(String groupName, List<Integer> participantIds) throws RemoteException, DAOException;
    List<ChatDTO> getChatsForUser(int userId) throws RemoteException, DAOException;
}