package org.example.spring.controllers;

import org.example.spring.models.main.File;
import org.example.spring.repositories.main.FileRepository;
import org.example.spring.services.FileService;
import org.example.spring.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/api/files")
public class FileController {
    @Autowired
    private StorageService storageService;
    @Autowired
    private FileService fileService;

    @PostMapping
    public ResponseEntity<?> saveAvatar(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId)
    {
        try {
            String fileName = file.getOriginalFilename();

            String filePath = storageService.put("files", fileName, file);
            if (filePath != null)
                fileService.saveUrlPath(filePath, userId);
            return ResponseEntity.ok(filePath);
        }catch(Exception ex)
        {
            return ResponseEntity.ok(ex.getMessage());
        }

    }
    @PostMapping("/temp")
    public ResponseEntity<?> savetempAvatar(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId)
    {
        try {
            String fileName = file.getOriginalFilename();

            String filePath = storageService.put("files", fileName, file);
            return ResponseEntity.ok(filePath);
        }catch(Exception ex)
        {
            return ResponseEntity.ok(ex.getMessage());
        }

    }
    @GetMapping("/{user_id}")
    public ResponseEntity<?> getAvatar(@PathVariable Long user_id)
    {
        try{
            Optional<File> file = fileService.getAvatar(user_id);
            return  ResponseEntity.ok(file);
        }catch(Exception ex)
        {
            return ResponseEntity.ok(ex.getMessage());
        }
    }
    @PostMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestBody String urlPath) {
        if (urlPath.startsWith("\"") && urlPath.endsWith("\"")) {
            urlPath = urlPath.substring(1, urlPath.length() - 1);
        }
        try {
            storageService.delete(urlPath, "files");
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the file");
        }
    }
    @PostMapping("/full")
    public ResponseEntity<String> deleteFileFull(@RequestBody Long user_id) {
        try {
            fileService.deleteFile(user_id);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the file");
        }
    }

}
