package com.baloghlan.springbatchexample.service;

import java.io.File;

public interface FileService {
    void saveFileToResourceFolder(byte[] bytes, String fileName);
    File getFileByFilename(String filename);
}
