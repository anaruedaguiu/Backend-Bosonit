package com.formacion.block11uploaddownloadfiles.repository;

import com.formacion.block11uploaddownloadfiles.domain.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    Optional<FileEntity> findFileByFileName(String fileName);
}
