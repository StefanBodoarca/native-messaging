package com.ro.utils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.FileSystemException;
import java.util.ArrayList;

public final class FileHelper {
    private static final Logger logger = LogManager.getLogger(FileHelper.class);

    private FileHelper() {

    }

    private static String readFromInputStream(InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
                     = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }

    public static String readFile(String filePath) {
        FileInputStream inputStream = null;
        String data = null;
        try {
            inputStream = new FileInputStream(filePath);
            data = readFromInputStream(inputStream);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return data;
    }

    public static boolean fileExists(String filePath) {
        return new File(filePath).exists();
    }


    public static void deleteFile(File file) {
        try {
            if (file.delete()) {
                logger.info("Deleted file " + file.getPath());
            }
        } catch (Exception e) {
            logger.error("My message: failed to delete file");
            logger.error(e.getMessage(), e);
        }
    }

    public static void moveFile(File fileToMove, String moveToPath) throws IOException {
        String TARGET_FILE = moveToPath + "/" + fileToMove.getName();
        boolean isMoved = fileToMove.renameTo(new File(TARGET_FILE));
        if (!isMoved) {
            throw new FileSystemException(TARGET_FILE);
        }
    }

    public static boolean renameFile(String filenameOld, String filenameNew) throws IOException {
        File old = new File(filenameOld);
        File newFile = new File(filenameNew);
        if (newFile.exists()) {
            throw new IOException("File " + filenameNew + " exists!");
        }
        boolean isMoved = old.renameTo(new File(filenameNew));
        if (!isMoved) {
            throw new FileSystemException(filenameNew);
        }

        return true;
    }

    public static String addStringToFileName(String addition, String initialFilenamePath) throws FileNotFoundException {
        String fNameWithoutExtension = FilenameUtils.getBaseName(initialFilenamePath);
        String extension = "." + FilenameUtils.getExtension(initialFilenamePath);
        String result = fNameWithoutExtension + "-" + addition + extension;
        String path = initialFilenamePath.substring(0, initialFilenamePath.indexOf(FilenameUtils.getBaseName(initialFilenamePath)));
        return path + result;
    }


    public static boolean isFileClosed(File file) throws IOException {
        Process plsof = null;
        BufferedReader reader = null;
        try {
            plsof = new ProcessBuilder(new String[]{"lsof", "|", "grep", file.getAbsolutePath()}).start();
            reader = new BufferedReader(new InputStreamReader(plsof.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(file.getAbsolutePath())) {
                    reader.close();
                    plsof.destroy();
                    //System.out.println("False " + file.getName() + " " + file.length());
                    return false;
                }
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        reader.close();
        plsof.destroy();
        //System.out.println("True " + file.getName() + " " + file.length());
        return true;
    }

    public static ArrayList<String> listFilesForFolder(ArrayList<String> filesInFolder, final File folder) throws FileNotFoundException {
        if (!folder.exists()) {
            throw new FileNotFoundException(folder.getAbsolutePath() + " not exists");
        }
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                filesInFolder = listFilesForFolder(filesInFolder, fileEntry);
            } else {
                filesInFolder.add(fileEntry.getName());
            }
        }
        return filesInFolder;
    }
}
