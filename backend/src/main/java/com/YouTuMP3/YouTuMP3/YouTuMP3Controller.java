package com.YouTuMP3.YouTuMP3;

import com.YouTuMP3.YouTuMP3.beans.input.VideoURL;
import com.YouTuMP3.YouTuMP3.beans.output.RawVideoURL;
import com.YouTuMP3.YouTuMP3.models.AudioRecord;
import com.YouTuMP3.YouTuMP3.models.AudioRecordRepository;
import com.YouTuMP3.YouTuMP3.models.AudioStatus;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class YouTuMP3Controller {
    private final Comparator<AudioFormat> audioFormatComparator = (a, b) -> {
        if (a.mimeType().equals(b.mimeType())) {
            return Integer.compare(a.bitrate(), b.bitrate());
        }

        return Integer.compare(a.itag().id(), b.itag().id());
    };

    AudioRecordRepository audioRecordRepository;

    public YouTuMP3Controller(AudioRecordRepository audioRecordRepository) {
        this.audioRecordRepository = audioRecordRepository;
    }

    @GetMapping(path="/findall")
    public List<AudioRecord> findAll() {
        return audioRecordRepository.findAll();
    }

    @PostMapping(path="/video", consumes="application/json")
    public RawVideoURL getRawVideoURL(@RequestBody VideoURL videoURL) {
        Pattern pattern = Pattern.compile("(?<==).*$");
        Matcher matcher = pattern.matcher(videoURL.getUrl());

        if (!matcher.find()) {
            return new RawVideoURL("fail");
        }

        String videoId = matcher.group();

        YoutubeDownloader downloader = new YoutubeDownloader();
        try {
            YoutubeVideo video = downloader.getVideo(videoId);
            List<AudioFormat> audioFormats = video.audioFormats();

            Optional<AudioFormat> optionalAudioFormat = audioFormats.stream()
                    .max(audioFormatComparator);

            if (optionalAudioFormat.isEmpty()) {
                return new RawVideoURL("fail");
            }

            AudioFormat audioFormat = optionalAudioFormat.get();

            //initialise record
            AudioRecord record = new AudioRecord();
            record.setStatus(AudioStatus.IN_PROGRESS);
            record.setYoutubeURl(videoURL.getUrl());
            audioRecordRepository.save(record);

            video.downloadAsync(
                    audioFormat,
                    new File("public"),
                    new DownloadManager(audioRecordRepository, record)
            );

            return new RawVideoURL(audioFormat.url());
        }
        catch (YoutubeException e) {
            System.out.println("lmao");
        }
        catch (IOException e) {
            System.out.println("yolo");
        }
        return new RawVideoURL("this didn't work idiot");
    }
}
