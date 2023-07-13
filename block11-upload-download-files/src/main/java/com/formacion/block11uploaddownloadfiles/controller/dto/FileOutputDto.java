package com.formacion.block11uploaddownloadfiles.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileOutputDto {
    private int idFile;
    private String fileName;
    private Date uploadDate;
    private String category;
}
