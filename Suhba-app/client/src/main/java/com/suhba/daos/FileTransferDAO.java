public interface FileTransferDAO extends Remote {
    String uploadAttachment(File file) throws RemoteException, DAOException; // Returns URL
    File downloadAttachment(String attachmentUrl) throws RemoteException, DAOException;
}