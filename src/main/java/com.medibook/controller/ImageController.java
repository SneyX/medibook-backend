package com.medibook.controller;

import com.medibook.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("path") List<MultipartFile> files, @RequestParam(value = "room_id", required = false) Long idRoom) throws IOException {
        imageService.uploadImages(files, idRoom);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/upload/typeroom")
    public ResponseEntity<?> uploadTyperoomImage(@RequestParam("path") List<MultipartFile> files, @RequestParam(value = "typeroom_id", required = false) Long idTyperoom) throws IOException {
        imageService.uploadTyperoomImage(files, idTyperoom);

        return ResponseEntity.ok().build();
    }
}

