package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.configuration.FileStorageProperties;
import fr.ugesellsloaning.api.entities.Account;
import fr.ugesellsloaning.api.entities.Media;
import fr.ugesellsloaning.api.services.AccountServices;
import fr.ugesellsloaning.api.services.MediaServices;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;


@Api( tags={"Operations \"Media\""})
@RestController
@RequestMapping("/api/media")
public class MediaController {
    @Autowired
    MediaServices mediaServices;

    @Autowired
    FileStorageProperties fileStorageProperties;

    @Autowired
    ServletContext context;

    @PostMapping("/")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
        Media media = new Media();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(fileStorageProperties.getUploadDir() + fileName);
        media.setFilename(fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/media/")
                .path(fileName)
                .toUriString();
        media.setPath(fileDownloadUri);
        mediaServices.save(media);
        return ResponseEntity.ok(fileDownloadUri);
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity download(@PathVariable String fileName) {
        Path path = Paths.get(fileStorageProperties.getUploadDir() + fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/jpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity delete(@PathVariable String filename){
        File file = new File(fileStorageProperties.getUploadDir()+filename);
        if(file.delete()){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.notFound().build();
        }

    }



}
