package com.formacion.block11uploaddownloadfiles.domain;

import com.formacion.block11uploaddownloadfiles.controller.dto.FileInputDto;
import com.formacion.block11uploaddownloadfiles.controller.dto.FileOutputDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {
    @Id
    @GeneratedValue
    private int idFile;
    @Column(name = "file_name")
    private String fileName;
    private Date uploadDate;
    private String category;

    public FileEntity(FileInputDto fileInputDto) {
        this.idFile = fileInputDto.getIdFile();
        this.fileName = fileInputDto.getFileName();
        this.uploadDate = fileInputDto.getUploadDate();
        this.category = fileInputDto.getCategory();
    }

    public FileEntity(String fileName, String category, Date uploadDate) {
        this.fileName = fileName;
        this.category = category;
        this.uploadDate = uploadDate;
    }

    public FileOutputDto fileToFileOutputDto() {
        return new FileOutputDto(
                this.idFile,
                this.fileName,
                this.uploadDate,
                this.category
        );
    }
}
