package ru.learnUp.learnupjava23.messagesService;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageProducer messageProducer;
    private final FileDirectoryProvider fileDirectoryProvider;

    public MessageController(MessageProducer messageProducer, FileDirectoryProvider fileDirectoryProvider) {
            this.messageProducer = messageProducer;
            this.fileDirectoryProvider = fileDirectoryProvider;
        }

        @GetMapping
        public String sendMessage(@RequestParam(value = "message") String message) {
            messageProducer.sendMessage(message);
            return "OK";
        }

        @GetMapping("/file/write")
        public String writeIntoFile(
                @RequestParam String fileName,
                @RequestParam String text
    ) throws IOException {
            fileDirectoryProvider.writeString(fileName, text);
            return fileName;
        }

        @GetMapping(
                value = "/file/data/{fileName}",
                produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
        )
        public @ResponseBody byte[] getFile(@PathVariable String fileName) throws IOException {
            return fileDirectoryProvider.getFile(fileName);
        }
    }