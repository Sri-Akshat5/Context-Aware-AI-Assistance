package com.context.aware.ai.assistance.service;

import org.springframework.web.multipart.MultipartFile;

public interface RagIngestionService {

    void ingestFile(MultipartFile file);
}
