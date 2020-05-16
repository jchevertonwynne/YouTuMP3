package com.YouTuMP3.YouTuMP3.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AudioRecordRepository extends JpaRepository<AudioRecord, Long> {
        List<AudioRecord> findAll();
}