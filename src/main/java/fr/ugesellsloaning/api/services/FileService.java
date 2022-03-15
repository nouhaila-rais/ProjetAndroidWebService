package fr.ugesellsloaning.api.services;

import fr.ugesellsloaning.api.configuration.FileStorageProperties;
import fr.ugesellsloaning.api.exceptions.FileStorageException;
import fr.ugesellsloaning.api.exceptions.MyFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {

    private final Path fileStorageLocation;

    @Autowired
    public FileService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public static String generateFileName(int length) {
        String chars = "a";
        StringBuilder pass = new StringBuilder(length);
        for (int x = 0; x < length; x++) {
            int i = (int) (Math.random() * chars.length());
            pass.append(chars.charAt(i));
        }
        return pass.toString();
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String uploadedFilename = StringUtils.cleanPath(file.getOriginalFilename());
        //String fileName = String.valueOf(Calendar.getInstance().getTimeInMillis());
        try {
            // Check if the file's name contains invalid characters
            if(uploadedFilename.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + uploadedFilename);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(uploadedFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            //return fileName;
            return (targetLocation.normalize().toUri()).toString();
        } catch (FileStorageException | IOException ex) {
            throw new FileStorageException("Could not store file " + uploadedFilename + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();

            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public void deleteFile(){

    }
}
