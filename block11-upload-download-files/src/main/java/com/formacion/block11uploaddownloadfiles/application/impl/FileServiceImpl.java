package com.formacion.block11uploaddownloadfiles.application.impl;

import com.formacion.block11uploaddownloadfiles.application.FileService;
import com.formacion.block11uploaddownloadfiles.controller.dto.FileInputDto;
import com.formacion.block11uploaddownloadfiles.controller.dto.FileOutputDto;
import com.formacion.block11uploaddownloadfiles.domain.FileEntity;
import com.formacion.block11uploaddownloadfiles.exception.FileNotFoundException;
import com.formacion.block11uploaddownloadfiles.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    FileRepository fileRepository;

    @Override
    public FileOutputDto addFile(FileInputDto fileInputDto) {
        return fileRepository.save(new FileEntity(fileInputDto))
                .fileToFileOutputDto();
    }

    @Override
    public FileOutputDto getFileById(int id) {
        if(fileRepository.findById(id).isEmpty()) {
            throw new FileNotFoundException("The file with " + id + " does not exist.");
        } else {
            return fileRepository.findById(id).orElseThrow()
                    .fileToFileOutputDto();
        }
    }

    @Override
    public FileOutputDto getFileByName(String fileName) {
        if(fileRepository.findFileByFileName(fileName).isEmpty()) {
            throw new FileNotFoundException("The file with " + fileName + " does not exist.");
        } else {
            return fileRepository.findFileByFileName(fileName).orElseThrow()
                    .fileToFileOutputDto();
        }
    }

    @Override
    public List<FileOutputDto> getAllFiles(int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return fileRepository.findAll(pageRequest).getContent()
                .stream()
                .map(FileEntity::fileToFileOutputDto)
                .toList();
    }

    @Override
    public void deleteFile(int id) {
        if(fileRepository.findById(id).isEmpty()) {
            throw new FileNotFoundException("The file with id " + id + " does not exist.");
        } else {
            fileRepository.deleteById(id);
        }
    }
}
