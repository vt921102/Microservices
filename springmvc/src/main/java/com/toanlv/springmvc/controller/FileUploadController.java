package com.toanlv.springmvc.controller;

import com.toanlv.springmvc.model.ResponseObject;
import com.toanlv.springmvc.sevices.IStorageService;
import com.toanlv.springmvc.sevices.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.FileInputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/FileUpload")
public class FileUploadController {
    //File nay dung de upload file len server
    @Autowired
    IStorageService storageService ;
    @PostMapping(path = "/upload")
    public ResponseEntity<ResponseObject> fileUpload(@RequestParam("file")MultipartFile multipartFile){
        try {
            String generateFilename = storageService.storeFile(multipartFile);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Upload file successful",generateFilename)
            );
        }catch (Exception e){
            System.out.println(e.getStackTrace());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Fail","Upload file fail","")
            );
        }

    }
    @GetMapping("/file/{fileName:.+}")// .+ la duoi file
    // /file/9d62a58a0ca24b4484d93bba17d7d1cb.png
    public ResponseEntity<byte[]> getImageFilename(@PathVariable String fileName){
        try {
            byte [] result = storageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(result);
        }catch (Exception e) {
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("")
    ResponseEntity<ResponseObject> getUploadedImage( ){
        try {
            List<String> result = storageService.loadAll()
                    .map(path -> {
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "getImageFilename",path.getFileName().toString()).build().toUri().toString();
                     return urlPath;
                    }).collect(Collectors.toList());
            return ResponseEntity.ok().body(new ResponseObject("ok", "List file successful",result));
        }catch (Exception e){
            return ResponseEntity.ok().body(new ResponseObject("fail", "List file fail",""));

        }

    }
}
