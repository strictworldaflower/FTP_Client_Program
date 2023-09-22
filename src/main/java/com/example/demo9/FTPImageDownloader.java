package com.example.demo9;

import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPImageDownloader {
    public static void main(String[] args) {
        String server = "";
        int port = 21;
        String username = "";
        String password = "";
        String remoteDirectory = "0:\\00";
        String localDirectory = "0:\\000\\";

        FTPClient ftpClient = new FTPClient();
        long startTime = System.currentTimeMillis();

        try {
            ftpClient.connect(server, port);
            ftpClient.login(username, password);
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.changeWorkingDirectory(remoteDirectory);

            String[] files = ftpClient.listNames();
            if (files != null) {
                for (String file : files) {
                    String cleanedFileName = file.replace("?", "");
                    String localFilePath = localDirectory + cleanedFileName;
                    FileOutputStream outputStream = new FileOutputStream(localFilePath);
                    ftpClient.retrieveFile(file, outputStream);
                    outputStream.close();
                }
            }

            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                System.out.println("다운로드 소요 시간: " + elapsedTime + "m/s");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
