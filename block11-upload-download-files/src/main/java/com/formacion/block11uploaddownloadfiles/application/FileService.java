package com.formacion.block11uploaddownloadfiles.application;

import com.formacion.block11uploaddownloadfiles.controller.dto.FileInputDto;
import com.formacion.block11uploaddownloadfiles.controller.dto.FileOutputDto;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FileService {
    FileOutputDto addFile(FileInputDto fileInputDto);
    FileOutputDto getFileById(int id);
    FileOutputDto getFileByName(String fileName);
    List<FileOutputDto> getAllFiles(int pageNumber, int pageSize);
    void deleteFile(int id);
}
