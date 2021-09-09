package com.chencorp.fileapi.dao;

import com.chencorp.fileapi.model.File;
import com.chencorp.fileapi.model.FileData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDataDao extends CrudRepository<FileData, Long> {
}
