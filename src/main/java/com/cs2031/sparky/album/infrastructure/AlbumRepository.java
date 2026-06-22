package com.cs2031.sparky.album.infrastructure;

import com.cs2031.sparky.album.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {}
