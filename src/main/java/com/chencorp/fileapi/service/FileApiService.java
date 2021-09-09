package com.chencorp.fileapi.service;

import com.chencorp.fileapi.dao.FileDao;
import com.chencorp.fileapi.dao.FileDataDao;
import com.chencorp.fileapi.exception.AlreadyExistException;
import com.chencorp.fileapi.exception.NotFoundException;
import com.chencorp.fileapi.model.File;
import com.chencorp.fileapi.model.FileData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FileApiService {

    @Autowired
    private FileDao fileDao;

    @Autowired
    private EntityManager em;

    @Autowired
    private FileDataDao fileDataDao;

    public File getFile(Long id) {
        final Optional<File> byId = fileDao.findById(id);
        if (byId.isEmpty()) {
            throw new NotFoundException("File does not exist");
        }
        return byId.get();
    }

    public FileData getFileData(Long id) {
        final Optional<FileData> byId = fileDataDao.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("FileData does not exist");
        }
        return byId.get();
    }

    public String convertFileNameToContentType(String name) {
        final String extension = name.substring(name.lastIndexOf("."));
        switch (extension) {
            case "pdf" : return "application/pdf";
            case "txt" : return "text/plain";
            default: return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }

    public List<File> filter(String name, String category) {
        final List<File> files = fileDao.findByNameAndCategory(name, category);

        return files;
    }

    public synchronized File updateFile(Long id, byte[] data) {
        final File file = getFile(id);
        FileData fileData = new FileData();
        fileData.setData(data);
        fileData.setFile(file);
        fileData.setVersion(file.getDataList().size()+1);
        fileDataDao.save(fileData);
        em.refresh(file);
        return file;
    }

    public File createFile(String name, String category, byte[] data) {
        if (!fileDao.findByName(name).isEmpty()) {
            throw new AlreadyExistException("File with this name already exist");
        }
        File file = new File();
        file.setCategory(category);
        file.setName(name);
        fileDao.save(file);

        FileData fileData = new FileData();
        fileData.setData(data);
        fileData.setVersion(1);
        fileData.setFile(file);
        fileDataDao.save(fileData);
        em.refresh(file);
        return file;
    }

    public void deleteFile(Long id) {
        final File file = getFile(id);
        fileDataDao.deleteAllById(
                file.getDataList()
                        .stream()
                        .map(FileData::getId)
                        .collect(Collectors.toSet())
        );
        fileDao.deleteById(id);
    }

}
