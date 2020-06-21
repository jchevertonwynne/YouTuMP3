package com.YouTuMP3.YouTuMP3;

import java.io.IOException;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;

import org.springframework.stereotype.Component;

@Component
public class YouTubeDownloaderComponent {
    public YoutubeVideo getVideo(String videoId) throws YoutubeException, IOException {
        YoutubeDownloader downloader = new YoutubeDownloader();
        return downloader.getVideo(videoId);
    }
}