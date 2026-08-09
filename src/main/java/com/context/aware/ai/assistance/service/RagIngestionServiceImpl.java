package com.context.aware.ai.assistance.service;


import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class RagIngestionServiceImpl implements RagIngestionService {


    private final VectorStore vectorStore;

    private final TokenTextSplitter textSplitter =
            TokenTextSplitter.builder()
                    .withChunkSize(800)
                    .withMinChunkSizeChars(350)
                    .withMinChunkLengthToEmbed(20)
                    .withMaxNumChunks(10000)
                    .withKeepSeparator(true)
                    .build();

    @Override
    public void ingestFile(MultipartFile file) {
        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);

            Document document = new Document(content, Map.of(
                    "source", file.getOriginalFilename(),
                    "fileName", file.getOriginalFilename()
                    ));

            List<Document> chunks = textSplitter.apply(List.of(document));

            vectorStore.add(chunks);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to read file: " + file.getOriginalFilename(),
                    e
            );
        }

    }


}

