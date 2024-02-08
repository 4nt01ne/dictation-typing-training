package be.dictation.training;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TextToSpeechController {

    @Autowired
    TextToSpeechService textToSpeechService;

    @PostMapping(value = "/convert",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody ResponseEntity<ByteArrayResource> convertTextToSpeech(@RequestBody Text text) {
        // Call the text-to-speech service
        String content = text.getContent();
        String language = text.getLanguage();
        byte[] audioBytes = textToSpeechService.convertToSpeech(content, language);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("audio", "mpeg"));
        headers.setContentType(new MediaType("x-goog-user-project", "dictation-typing-training"));

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new ByteArrayResource(audioBytes));
    }
}
