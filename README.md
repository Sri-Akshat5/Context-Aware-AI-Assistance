# Context-Aware AI Assistant
<p align="center">
   <a href="https://tokenly.codes/">
     <img src="https://img.shields.io/badge/Live-Demo-brightgreen?style=for-the-badge&logo=rocket" alt="Live Demo">
   </a>
 </p>
​
 <p align="center">
   <img src="https://img.shields.io/badge/Java-21-blue.svg?style=for-the-badge&logo=java" alt="Java">
   <img src="https://img.shields.io/badge/Spring%20Boot-3.3.0-green.svg?style=for-the-badge&logo=spring" alt="Spring Boot">
   <img src="https://img.shields.io/badge/Spring%20AI-1.0.0-blueviolet.svg?style=for-the-badge" alt="Spring AI">
   <img src="https://img.shields.io/badge/Gemini-AI-blue.svg?style=for-the-badge&logo=google-gemini" alt="Gemini">
   <img src="https://img.shields.io/badge/RAG-Enabled-orange.svg?style=for-the-badge" alt="RAG">
   <img src="https://img.shields.io/badge/MongoDB-Atlas-green.svg?style=for-the-badge&logo=mongodb" alt="MongoDB">
 </p>

An on-screen AI assistant that understands the context a user is currently looking at.

Instead of copying content into an external AI tool, users can select text directly inside an application, click **Ask AI**, and receive a contextual response without leaving their current workflow.

## Core Idea

**Select → Ask → Understand**

The application provides the context to the AI instead of requiring the user to explain that context manually.

---

## Features

- On-screen context-aware AI assistance
- Select text and ask questions directly
- Retrieval-Augmented Generation (RAG)
- MongoDB Atlas Vector Search
- Context-grounded responses
- Follow-up questions
- Gemini integration
- Application-specific knowledge support
- File-based knowledge ingestion
- Configurable system prompts


---

## How It Works

```text
User selects content
        ↓
     Click Ask AI
        ↓
Relevant context is retrieved
        ↓
Context is provided to the AI model
        ↓
AI generates a contextual response
        ↓
Response is streamed to the user
```

The goal is to remove the need for users to repeatedly copy content, switch applications, and manually explain context to an AI model.

---
## Architecture
```text
                    ┌─────────────────────┐
                    │     User Screen     │
                    │                     │
                    │   Select Content    │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │       Ask AI        │
                    └──────────┬──────────┘
                               │
                               ▼
                    ┌─────────────────────┐
                    │    Spring Boot      │
                    │     + Spring AI     │
                    └──────────┬──────────┘
                               │
                    ┌──────────┴──────────┐
                    │                     │
                    ▼                     ▼
          ┌──────────────────┐   ┌──────────────────┐
          │ MongoDB Atlas    │   │     Gemini       │
          │ Vector Search    │   │    AI Model      │
          └────────┬─────────┘   └────────┬─────────┘
                   │                      │
                   └──────────┬───────────┘
                              ▼
                    ┌─────────────────────┐
                    │ Contextual Response │
                    └─────────────────────┘
```
---
## RAG Pipeline
```
Source Files
     ↓
Document Loading
     ↓
Text Splitting
     ↓
Embedding Generation
     ↓
MongoDB Atlas Vector Search
     ↓
Semantic Retrieval
     ↓
Relevant Context
     ↓
Gemini
     ↓
Grounded Response
```

## Setup & Run

### 1. Clone the Repository

```bash
git clone https://github.com/Sri-Akshat5/Context-Aware-AI-Assistance.git

cd Context-Aware-AI-Assistance
```
### 2. Configure application.properties

Open:

`src/main/resources/application.properties`

Add your configuration:

```
spring.ai.google.genai.api-key=YOUR_GEMINI_API_KEY
spring.ai.google.genai.chat.options.model=YOUR_GEMINI_CHAT_MODEL

spring.ai.google.genai.embedding.api-key=YOUR_GEMINI_API_KEY
spring.ai.model.embedding.text=YOUR_GEMINI_EMBEDDING_MODEL

spring.mongodb.uri=YOUR_MONGODB_ATLAS_URI
spring.mongodb.database=YOUR_DATABASE_NAME
```

Replace the placeholder values with your own credentials and model names.

**Important**: Do not commit your API keys or MongoDB credentials to GitHub.

### 3. Configure the System Prompt

Update the system prompt according to your application's requirements.

The prompt file is located at:

`src/main/resources/prompt/chat-system.st`

This prompt controls how the AI responds to user queries and how it handles the retrieved application context.

### 4. Start the Application

Using Maven:

Windows

```
mvnw.cmd spring-boot:run
```

Linux / macOS

```
./mvnw spring-boot:run
```

The application will start on:

`http://localhost:8080`

### Using the RAG Pipeline
#### 5. Upload Knowledge

Upload your application documentation or source files using:

`POST /upload`

The uploaded files are processed and added to the vector store.

Example using cURL:

```
curl --location 'http://localhost:8080/upload' \
--form 'file=@"/path/to/your/file.md"'
```

You can upload multiple files individually to build your application's knowledge base.

### 6. Ask a Question

Once the RAG context has been uploaded, send a query using:

`POST /query?q=YOUR_QUESTION`

Example:

```
curl --location --request POST \
'http://localhost:8080/query?q=What%20is%20Tokenly'
```

The assistant retrieves relevant context from the vector store and generates a response based on that context.
