package com.baloghlan.springbatchexample.service.impl;

import com.baloghlan.springbatchexample.service.FileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class FileServiceImpl implements FileService {
    @Value("${files-base-directory}")
    private String baseDirectory;

    @Override
    @SneakyThrows
    public void saveFileToResourceFolder(byte[] bytes, String fileName) {
        File path = new File(baseDirectory + fileName);
        path.createNewFile();
        FileOutputStream output = new FileOutputStream(path);
        output.write(bytes);
        output.close();
    }

    @Override
    public File getFileByFilename(String filename) {
        return new File(baseDirectory, filename);
    }
}
