package com.cs2031.sparky.song.infrastructure;

import com.cs2031.sparky.song.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    List<Song> findByAlbumId(Long albumId);
}
