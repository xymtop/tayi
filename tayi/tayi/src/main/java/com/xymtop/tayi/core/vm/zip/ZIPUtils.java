package com.xymtop.tayi.core.vm.zip;

/**
 * @author 小野喵
 * @version 1.0
 * @description: TODO
 * @date 2023/12/27 14:24
 */
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.*;


import java.io.*;
import java.util.*;
import java.util.zip.*;



public class ZIPUtils {
    private String zipFilePath;

    public ZIPUtils(String zipFilePath) {
        this.zipFilePath = zipFilePath;
    }

    public void createZipFile() throws IOException {
        new ZipOutputStream(new FileOutputStream(zipFilePath)).close();
    }

    public void addFileToZip(String filePath) throws IOException {
        File fileToAdd = new File(filePath);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath, true));
             FileInputStream fis = new FileInputStream(fileToAdd)) {

            ZipEntry zipEntry = new ZipEntry(fileToAdd.getName());
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
        }
    }

    public List<String> readZipFileStructure() throws IOException {
        List<String> fileNames = new ArrayList<>();
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                fileNames.add(zipEntry.getName());
                zipEntry = zis.getNextEntry();
            }
        }
        return fileNames;
    }

    public void extractZipFile(String destDir) throws IOException {
        File destDirFile = new File(destDir);
        if (!destDirFile.exists()) {
            destDirFile.mkdir();
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                File newFile = new File(destDirFile, zipEntry.getName());
                new File(newFile.getParent()).mkdirs();

                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                zipEntry = zis.getNextEntry();
            }
        }
    }
}

