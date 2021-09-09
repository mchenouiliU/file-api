package com.chencorp.fileapi.controller;

import com.chencorp.fileapi.model.File;
import com.chencorp.fileapi.model.FileData;
import com.chencorp.fileapi.service.FileApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FileApiController {

    @Autowired
    FileApiService fileApiService;

    @GetMapping("/file/{id}")
    public  @ResponseBody File getFile(@PathVariable("id") Long id) {
        return fileApiService.getFile(id);
    }

    @GetMapping("/file-data/{id}")
    public  @ResponseBody ResponseEntity<Resource> getFileData(@PathVariable("id") Long id) {
        final FileData fileData = fileApiService.getFileData(id);
        ByteArrayResource resource = new ByteArrayResource(fileData.getData());
        final File file = fileData.getFile();
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\""+ file.getName()+"\"")
                .header("Content-Type", fileApiService.convertFileNameToContentType(file.getName()))
                .contentLength(fileData.getData().length)
                .body(resource);
    }

    @GetMapping("/files")
    public  @ResponseBody List<File> getFiles(@RequestParam(name = "name", required = false) String name,
                               @RequestParam(name = "category", required = false) String category) {
        return fileApiService.filter(name, category);
    }

    @PostMapping("/file/{id}")
    public  @ResponseBody File updateFile(@PathVariable("id") Long id, @RequestBody byte[] data) {
        return fileApiService.updateFile(id, data);
    }

    @PutMapping("/file")
    public  @ResponseBody File createFile(@RequestParam(name="name") String name,
                           @RequestParam(name="category") String category,
                           @RequestBody byte[] data) {
        final File file = fileApiService.createFile(name, category, data);
        return file;
    }

    @DeleteMapping("/file/{id}")
    public void deleteFile(@PathVariable("id") Long id) {
        fileApiService.deleteFile(id);
    }
}
