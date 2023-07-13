package com.formacion.block11uploaddownloadfiles.controller;

import com.formacion.block11uploaddownloadfiles.application.StorageService;
import com.formacion.block11uploaddownloadfiles.application.impl.FileServiceImpl;
import com.formacion.block11uploaddownloadfiles.application.impl.FileStorageServiceImpl;
import com.formacion.block11uploaddownloadfiles.domain.FileEntity;
import com.formacion.block11uploaddownloadfiles.exception.FileNotFoundException;
import com.formacion.block11uploaddownloadfiles.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Path;
import java.util.Date;

@Controller
public class StorageController {
    private final StorageService storageService;
    @Autowired
    FileStorageServiceImpl fileStorageServiceImpl;
    @Autowired
    FileServiceImpl fileServiceImpl;
    @Autowired
    FileRepository fileRepository;
    @Autowired
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload/{type}")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @PathVariable String type) {
        URI location = URI.create("/file");
        String fileType = file.getOriginalFilename().split("\\.")[1];
        if(type.equals(fileType)) {
            storageService.store(file);

            FileEntity fileEntity = new FileEntity(file.getOriginalFilename(), type, new Date());
            fileEntity = fileRepository.save(fileEntity);

            return ResponseEntity.created(location).body("Successfully uploaded " + file.getOriginalFilename());
        } else {
            return ResponseEntity.badRequest().body("Invalid type of file");
        }
    }

    @GetMapping("storage/file/name/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> loadFileByFileName(@PathVariable String fileName) {
        try {
            Resource file = storageService.loadAsResource(fileName);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; fileName=\"" + file.getFilename() + "\"").body(file);
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("storage/file/id/{id}")
    @ResponseBody
    public ResponseEntity<?> loadFileById(@PathVariable int id) {
        try {
            Object file = fileServiceImpl.getFileById(id);
            return ResponseEntity.ok().body(file);
        } catch (FileNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("storage/setpath")
    public ResponseEntity<?> setDirectory(@RequestParam Path path) {
        storageService.setPath(path);
        return ResponseEntity.ok().body("File's location changed");
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<?> handleFileNotFound(FileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
