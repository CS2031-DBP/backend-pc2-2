package com.cs2031.sparky.album.domain;

import com.cs2031.sparky.album.infrastructure.AlbumRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepo;

    public AlbumService(AlbumRepository albumRepo) {
        this.albumRepo = albumRepo;
    }

    public List<Album> findAll() {
        return albumRepo.findAll();
    }
}
