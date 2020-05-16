package com.YouTuMP3.YouTuMP3;

import com.YouTuMP3.YouTuMP3.models.AudioRecord;
import com.YouTuMP3.YouTuMP3.models.AudioRecordRepository;
import com.YouTuMP3.YouTuMP3.models.AudioStatus;
import com.github.kiulian.downloader.OnYoutubeDownloadListener;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class DownloadManager implements OnYoutubeDownloadListener {
    AudioRecordRepository audioRecordRepository;
    AudioRecord record;

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
}
