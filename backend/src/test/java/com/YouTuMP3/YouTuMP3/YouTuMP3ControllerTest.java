package com.YouTuMP3.YouTuMP3;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import com.YouTuMP3.YouTuMP3.beans.input.VideoURL;
import com.YouTuMP3.YouTuMP3.beans.output.RawVideoURL;
import com.YouTuMP3.YouTuMP3.models.AudioRecordRepository;
import com.alibaba.fastjson.JSONObject;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;

import com.github.kiulian.downloader.model.formats.AudioFormat;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;


class YouTuMP3ControllerTest {

    private static YouTuMP3Controller youTuMP3Controller;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @BeforeAll
    public static void setup() {
        AudioRecordRepository audioRecordRepository = mock(AudioRecordRepository.class);
        YouTubeDownloaderComponent youTubeDownloaderComponent = mock(YouTubeDownloaderComponent.class);
        try {
            when(youTubeDownloaderComponent.getVideo("tVj0fziHbkA")).thenReturn(mock(YoutubeVideo.class));

            YoutubeVideo youtubeVideo = mock(YoutubeVideo.class);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("itag", 139);
            jsonObject.put("url", "https://www.youtube.com/watch?v=Lt8Qpzri2mw");
            List<AudioFormat> audioFormats = List.of(new AudioFormat(jsonObject));
            when(youtubeVideo.audioFormats()).thenReturn(audioFormats);
            when(youTubeDownloaderComponent.getVideo("Lt8Qpzri2mw")).thenReturn(youtubeVideo);
        } catch (YoutubeException | IOException e) {
            e.printStackTrace();
        }
        youTuMP3Controller = new YouTuMP3Controller(audioRecordRepository, youTubeDownloaderComponent);
    }

    @Test
    public void shouldFailForInvalidURL() {
        VideoURL videoURL = new VideoURL();
        videoURL.setUrl("http://badurl.com");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            youTuMP3Controller.getRawVideoURL(videoURL);
            fail("this should have illegal arg'd");
        });
    }

    @Test
    public void shouldFailForValidURLWithNoAudioFormats() {
        VideoURL videoURL = new VideoURL();
        videoURL.setUrl("https://www.youtube.com/watch?v=tVj0fziHbkA");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            youTuMP3Controller.getRawVideoURL(videoURL);
            fail("this should have illegal arg'd for no audio options");
        });
    }

    @Test
    public void shouldPassForValidURLWithAudioFormats() {
        VideoURL videoURL = new VideoURL();
        videoURL.setUrl("https://www.youtube.com/watch?v=Lt8Qpzri2mw");
        RawVideoURL rawVideoURL = youTuMP3Controller.getRawVideoURL(videoURL);
        System.out.println(rawVideoURL);
    }
}