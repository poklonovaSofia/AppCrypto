package org.example.spring.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.example.spring.drivers.storages.StorageDriverInterface;
import org.example.spring.drivers.storages.StorageDriverMinio;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class StorageService {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.username}")
    private String minioUsername;

    @Value("${minio.password}")
    private String minioPassword;
    private StorageDriverInterface driver;
    private Map<String, StorageDriverInterface> drivers = new HashMap<>();

    public StorageService() {
        System.out.println("Storage Service Created");
    }

    @PostConstruct
    public void init() {
        drivers.put("MinIo", new StorageDriverMinio(minioUrl, minioUsername, minioPassword));
        driver = drivers.get("MinIo");
    }

    /**
     * Получить экземпляр драйвера для работы с ним напрямую
     * @param disk
     * @return
     */
    public StorageDriverInterface disk(String disk) {
        return drivers.get(disk);
    }

    /**
     * Отдавать экземпляр драйвера по умочанию
     * тогда не понадобиться описывать фасад для всего драйвера
     * @return
     */
    public StorageDriverInterface disk() {
        return driver;
    }

    /**
     * Оболочка под драйвер - выбранный по умолчанию
     * @param bucketName
     * @param fileName
     * @param file
     * @return
     */
    public String put(String bucketName, String fileName, MultipartFile file) {
        return driver.put(bucketName, fileName, file);
    }

    public void delete(String urlPath, String bucketName) {
        driver.delete(urlPath, bucketName);
    }
}