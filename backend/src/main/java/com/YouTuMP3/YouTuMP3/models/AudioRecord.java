package com.YouTuMP3.YouTuMP3.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="youtube_audio")
public class AudioRecord implements Serializable {

    private static final long serialVersionUID = -2343243243242432341L;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(name="youtubeurl", nullable=false)
    private String youtubeURl;

    @Column(name="audiourl")
    private String audioURL;

    @Column(name="status", nullable=false)
    @Enumerated(EnumType.STRING)
    private AudioStatus status;

    public String getYoutubeURl() {
        return youtubeURl;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public AudioStatus getStatus() {
        return status;
    }

    public void setYoutubeURl(String youtubeURl) {
        this.youtubeURl = youtubeURl;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    public void setStatus(AudioStatus status) {
        this.status = status;
    }
}