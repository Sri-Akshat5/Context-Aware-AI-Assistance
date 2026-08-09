package com.context.aware.ai.assistance.service;

import com.context.aware.ai.assistance.dto.ResponseDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService{

    private final ChatClient chatClient;
    private final QuestionAnswerAdvisor questionAnswerAdvisor;

    @Value("classpath:prompt/chat-system.st")
    private Resource systemPrompt;

    public ChatServiceImpl(ChatClient.Builder chatClientBuilder, VectorStore vectorStore){
        this.chatClient=chatClientBuilder.build();
        this.questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
                .searchRequest( SearchRequest.builder()
                        .topK(5)
                        .similarityThreshold(0.70)
                        .build()
                ).build();

    }

    @Override
    public ResponseDto responseQuery(String q){

        if(q.isBlank() || q==null){
            return new ResponseDto(
                    "400",
                    "Query cannot be blank",
                    null);
        } else {
            String systemPromptReq = new SystemPromptTemplate(systemPrompt)
                    .createMessage()
                    .getText();


            String response = this.chatClient.prompt()
                    .system(systemPromptReq)
                    .user(q)
                    .advisors(questionAnswerAdvisor)
                    .call()
                    .content();

            return new ResponseDto(
                    "200",
                    "Query processed successfully",
                    response
            );
        }

    }


}
