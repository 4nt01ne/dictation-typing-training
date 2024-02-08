package be.dictation.training;

import com.google.cloud.texttospeech.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Component;

@Component
public class TextToSpeechService {

    public byte[] convertToSpeech(String text, String language) {
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
            // Set the text input to be synthesized
            SynthesisInput input = SynthesisInput.newBuilder()
                    .setText(text)
                    .build();

            // Build the voice request
            VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                    .setLanguageCode(language)
                    .setSsmlGender(SsmlVoiceGender.FEMALE)
                    .build();

            // Select the type of audio file you want
            AudioConfig audioConfig = AudioConfig.newBuilder()
                    .setAudioEncoding(AudioEncoding.MP3)
                    .build();

            // Perform the text-to-speech request
            SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

            // Get the audio contents from the response
            ByteString audioContents = response.getAudioContent();

            // Write the response to the output file.
            return audioContents.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error in converting text to speech".getBytes();
        }
    }
}
