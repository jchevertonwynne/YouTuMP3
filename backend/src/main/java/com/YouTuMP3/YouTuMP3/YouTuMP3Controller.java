package com.YouTuMP3.YouTuMP3;

import java.io.File;
import java.util.regex.Pattern;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;

import com.YouTuMP3.YouTuMP3.beans.input.NewDefault;
import com.YouTuMP3.YouTuMP3.beans.input.VideoURL;
import com.YouTuMP3.YouTuMP3.beans.output.Count;
import com.YouTuMP3.YouTuMP3.beans.output.RawVideoURL;
import com.YouTuMP3.YouTuMP3.models.AudioRecord;
import com.YouTuMP3.YouTuMP3.models.AudioRecordRepository;
import com.YouTuMP3.YouTuMP3.models.AudioStatus;
import com.github.kiulian.downloader.OnYoutubeDownloadListener;
import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.model.YoutubeVideo;
import com.github.kiulian.downloader.model.formats.AudioFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class YouTuMP3Controller {
    private int count = 0;
    private String defaultMessage = "Default Message";

    @Autowired
    AudioRecordRepository audioRecordRepository;

    private Comparator<AudioFormat> audioFormatCompataor = new Comparator<AudioFormat>() {
        @Override
        public int compare(AudioFormat a, AudioFormat b) {
            if (a.mimeType().equals(b.mimeType())) {
                return a.bitrate() > b.bitrate() ? 1 : -1;
            }
            
            return a.itag().id() > b.itag().id() ? 1 : -1;
        }
    };

    @GetMapping(path="/")
    public String helloWorld() {
        return "YouTuMP3";
    }

    @GetMapping(path="/count")
    public Count count() {
        return new Count(count++, defaultMessage);
    }

    @GetMapping(path="/count/{message}")
    public Count countWithCustomMessage(@PathVariable String message) {
        return new Count(count++, message);
    }

    @GetMapping(path="/count/{message}/{part2}")
    public Count countWithCustomMessages(@PathVariable String message, @PathVariable String part2) {
        return new Count(count++, part2 + message);
    }

    @PostMapping(path="/message", consumes="application/json")
    public void changeMessage(@RequestBody NewDefault newDefault) {
        defaultMessage = newDefault.getNewDefault();
    }

    @GetMapping(path="/findall")
    public List<AudioRecord> findAll() {
        List<AudioRecord> audioRecords = audioRecordRepository.findAll();
        return audioRecords;    
    }

    @PostMapping(path="/insert", consumes="application/json")
    public List<AudioRecord> insertRecord() {
        List<AudioRecord> audioRecords = audioRecordRepository.findAll();
        return audioRecords;    
    }


    @PostMapping(path="/video", consumes="application/json")
    public RawVideoURL getRawVideoURL(@RequestBody VideoURL videoURL) {
        Pattern pattern = Pattern.compile("(?<==).*$");
        Matcher matcher = pattern.matcher(videoURL.getURL());
        matcher.find();
        String videoId = matcher.group();

        YoutubeDownloader downloader = new YoutubeDownloader();
        try {
            YoutubeVideo video = downloader.getVideo(videoId);
            List<AudioFormat> audioFormats = video.audioFormats();

            AudioFormat audioFormat = audioFormats.stream()
                .max(audioFormatCompataor)
                .get();

            //initialise record
            AudioRecord record = new AudioRecord();
            record.setStatus(AudioStatus.IN_PROGRESS);
            record.setYoutubeURl(videoURL.getURL());
            audioRecordRepository.save(record);

            video.downloadAsync(audioFormat, new File("public"), new OnYoutubeDownloadListener() {
                @Override
                public void onDownloading(int progress) {
                    System.out.println(String.format("downloading %d percent", progress));
                }

                @Override
                public void onFinished(File file) {
                    record.setAudioURL(file.getAbsolutePath());
                    record.setStatus(AudioStatus.SUCCESS);
                    audioRecordRepository.save(record);
                }

                @Override
                public void onError(Throwable throwable) {
                    record.setStatus(AudioStatus.FAILED);
                    audioRecordRepository.save(record);
                }
            });

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
