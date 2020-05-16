package com.YouTuMP3.YouTuMP3.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AudioRecordRepository extends JpaRepository<AudioRecord, Long> {
        List<AudioRecord> findAll();
}