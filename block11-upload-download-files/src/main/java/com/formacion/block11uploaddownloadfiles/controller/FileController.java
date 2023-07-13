package com.formacion.block11uploaddownloadfiles.controller;

import com.formacion.block11uploaddownloadfiles.application.impl.FileServiceImpl;
import com.formacion.block11uploaddownloadfiles.controller.dto.FileInputDto;
import com.formacion.block11uploaddownloadfiles.controller.dto.FileOutputDto;
import com.formacion.block11uploaddownloadfiles.exception.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    FileServiceImpl fileServiceImpl;

    @PostMapping
    public FileOutputDto addFile(@RequestBody FileInputDto fileInputDto) {
        return fileServiceImpl.addFile(fileInputDto);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FileOutputDto> getFileById(@PathVariable int id) {
        try {
            return ResponseEntity.ok().body(fileServiceImpl.getFileById(id));
        } catch (FileNotFoundException e){
            throw new FileNotFoundException("The file with id: " + id + " does not exist.");
        }
    }

    @GetMapping("/name/{fileName}")
    public ResponseEntity<FileOutputDto> getFileByName(@PathVariable String fileName) {
        try {
            return ResponseEntity.ok().body(fileServiceImpl.getFileByName(fileName));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("The file with the name: " + fileName + " does not exist.");
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable int id) {
        fileServiceImpl.deleteFile(id);
        return ResponseEntity.ok().body("The file with id: " + id + " was deleted.");
    }
}