package org.example.spring.services;

import org.example.spring.models.auth.User;
import org.example.spring.models.main.File;
import org.example.spring.repositories.auth.UserRepository;
import org.example.spring.repositories.main.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StorageService storageService;


    public File saveUrlPath(String filePath, Long userId) {
        AtomicBoolean flag = new AtomicBoolean(false);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        File file = fileRepository.findByUserId(userId).orElseGet(() -> {
            flag.set(true);
            File newFile = new File();
            newFile.setUser(user);
            return newFile;
        });
        if(!flag.get())
            storageService.delete(file.getUrlPath(), "files");

        file.setUrlPath(filePath);
        return fileRepository.save(file);
    }

    public Optional<File> getAvatar(Long userId) {
        return fileRepository.findByUserId(userId);
    }

    public void deleteFile(Long userId) throws FileNotFoundException {
        Optional<File> fileOpt = fileRepository.findByUserId(userId);
        if (fileOpt.isPresent()) {
            fileRepository.delete(fileOpt.get());
        } else {
            throw new FileNotFoundException("File not found for user id: " + userId);
        }
    }
}
