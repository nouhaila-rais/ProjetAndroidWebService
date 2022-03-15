package fr.ugesellsloaning.api.controllers;

import fr.ugesellsloaning.api.configuration.FileStorageProperties;
import fr.ugesellsloaning.api.entities.Media;
import fr.ugesellsloaning.api.services.MediaServices;
import io.swagger.annotations.Api;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Api( tags={"Operations \"Media\""})
@RestController
@RequestMapping("/api/media")
public class MediaController {
    @Autowired
    MediaServices mediaServices;

    @Autowired
    FileStorageProperties fileStorageProperties;

    @PostMapping(path = "/")
    public ResponseEntity uploadToDB(@RequestParam("file") MultipartFile file) {
        Media media = new Media();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        media.setFilename(fileName);
        try {
            media.setFile(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaServices.save(media);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(fileName).path("/db")
                .toUriString();
        return ResponseEntity.ok(fileDownloadUri);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity downloadFileFromLocal(@PathVariable String fileName) {
        Path path = Paths.get(fileStorageProperties.getUploadDir() + fileName);
        Resource resource = null;
        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("contentType"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/download/{fileName:.+}/db")
    public ResponseEntity downloadFromDB(@PathVariable String fileName) {
        Media media = mediaServices.getMediaByFilename(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("contentType"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(media.getFile());
    }
}
