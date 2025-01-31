package com.suhba.daos.interfaces;

import com.suhba.exceptions.DAOException;
import java.io.InputStream;
import java.io.File;

public interface FileTransferDAO {
    // File Operations
    String uploadAttachment(File file) throws DAOException; // Returns URL/path
    InputStream downloadAttachment(String attachmentUrl) throws DAOException;
    void deleteAttachment(String attachmentUrl) throws DAOException;
}