package com.ecommerce.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
public class ImageServController {
    private final String uploadsPath=System.getProperty("user.dir")+"/uploads/products/";

    @GetMapping("/products/images/{fileName}")
    public ResponseEntity<Resource> getImages(@PathVariable String filename) throws IOException {
        Path path= Paths.get(uploadsPath+filename);
        Resource resource=new UrlResource(path.toUri());
        if(!resource.exists()){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(resource);
    }

}
