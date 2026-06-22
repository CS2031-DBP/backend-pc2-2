package com.cs2031.sparky.album.application;

import com.cs2031.sparky.album.domain.Album;
import com.cs2031.sparky.album.domain.AlbumService;
import com.cs2031.sparky.song.domain.Song;
import com.cs2031.sparky.song.domain.SongService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;
    private final SongService songService;

    public AlbumController(AlbumService albumService, SongService songService) {
        this.albumService = albumService;
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAlbums() {
        List<Map<String, Object>> result = albumService.findAll().stream()
                .map(a -> Map.<String, Object>of(
                        "id", a.getId(),
                        "title", a.getTitle(),
                        "year", a.getYear(),
                        "coverUrl", a.getCoverUrl()
                ))
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{albumId}/songs")
    public ResponseEntity<List<Map<String, Object>>> getSongs(@PathVariable Long albumId) {
        List<Map<String, Object>> result = songService.findByAlbum(albumId).stream()
                .map(s -> Map.<String, Object>of(
                        "id", s.getId(),
                        "title", s.getTitle(),
                        "duration", s.getDuration(),
                        "albumId", s.getAlbum().getId()
                ))
                .toList();
        return ResponseEntity.ok(result);
    }
}
