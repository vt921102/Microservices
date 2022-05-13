package com.toanlv.springmvc.sevices;

import org.apache.commons.io.FilenameUtils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;
@Service // de thang spring hieu la service
public class ImageStorageService implements IStorageService {
    private Path storeFolder = Paths.get("uploads");
    // tao folder luu data chi 1 lan khi khoi tao service.
    public ImageStorageService(){
        try {
            Files.createDirectories(storeFolder);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Can not create folder to storage file");
        }
    }
    private boolean isImage(MultipartFile file){
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        return Arrays.asList(new String[] {"png","png","jpeg","bmp"}).contains(fileExtension.trim().toLowerCase());
    }
    @Override
    public String storeFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String generatedFilename = UUID.randomUUID().toString().replace("-","");
        generatedFilename = generatedFilename+"."+fileExtension;
        try {
            if(file.isEmpty()){
                throw new RuntimeException("Failed to store empty file");
            }
            if(!isImage(file)){
                throw new RuntimeException("This file is not image file");
            }
            // check file < 5MB
            float fileSizeInMegaByte = file.getSize()/1_000_000;
            if(fileSizeInMegaByte > 5.0f) {
                throw new RuntimeException("File must have size <= 5MB ");
            }
            Path destinationFilePath = this.storeFolder.resolve(Paths.get(generatedFilename))
                    .normalize().toAbsolutePath();
           if(!destinationFilePath.getParent().equals(this.storeFolder.toAbsolutePath())) {
               throw new RuntimeException("Can not store file out side current directory");
           }
           try (InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream,destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
           }
           return generatedFilename;
        } catch (Exception e){
            throw new RuntimeException("Failed to store file");

        }
    }

    @Override
    public Stream<Path> loadAll() {

        try {
            return Files.walk(this.storeFolder,1).
                    filter(path -> !path.equals(this.storeFolder))
                    .map(this.storeFolder::relativize);
        } catch (IOException e) {
            throw new RuntimeException("can not load all files",e);
        }
    }

    @Override
    public byte[] readFileContent(String filename) {
        Path file = storeFolder.resolve(filename);
        try {
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists()||resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            } else {
                throw new RuntimeException("Could not read file"+ filename);
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not read file"+ filename,e);
        }

    }

    @Override
    public void deleteAllFiles() {

    }
}
