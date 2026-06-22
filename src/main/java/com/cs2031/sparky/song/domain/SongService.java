package com.cs2031.sparky.song.domain;

import com.cs2031.sparky.song.infrastructure.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepo;

    public SongService(SongRepository songRepo) {
        this.songRepo = songRepo;
    }

    public List<Song> findByAlbum(Long albumId) {
        return songRepo.findByAlbumId(albumId);
    }
}
