public interface GroupDAO extends Remote {
    void addUserToGroup(int groupId, int userId) throws RemoteException, DAOException;
    void removeUserFromGroup(int groupId, int userId) throws RemoteException, DAOException;
    void updateGroupCategory(int groupId, String category) throws RemoteException, DAOException;
    GroupDTO getGroupDetails(int groupId) throws RemoteException, DAOException;
}