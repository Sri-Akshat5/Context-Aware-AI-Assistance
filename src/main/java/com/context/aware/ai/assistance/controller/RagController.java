package com.context.aware.ai.assistance.controller;


import com.context.aware.ai.assistance.service.RagIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class RagController {

    private final RagIngestionService ragIngestionService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file){

        if(file.isEmpty()){
            return new ResponseEntity<>("File is required", HttpStatus.BAD_REQUEST);
        }else {
            ragIngestionService.ingestFile(file);
            return new  ResponseEntity<>("File uploaded", HttpStatus.OK);
        }

    }

}
