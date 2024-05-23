package org.example.spring.drivers.storages;

import org.springframework.web.multipart.MultipartFile;

public interface StorageDriverInterface {
    public String put (String bucketName, String fileName, MultipartFile file);
    public void delete (String urlPath, String bucketName);
}
