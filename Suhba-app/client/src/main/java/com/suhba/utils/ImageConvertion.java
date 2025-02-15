package com.suhba.utils;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public class ImageConvertion {

    public Blob convertImageToBlob(File file) throws IOException, SQLException {
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] imageBytes = fileInputStream.readAllBytes();
        fileInputStream.close();
        return new SerialBlob(imageBytes);
    }

}
