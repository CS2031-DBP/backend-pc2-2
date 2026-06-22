package com.cs2031.sparky.config;

import com.cs2031.sparky.album.domain.Album;
import com.cs2031.sparky.album.infrastructure.AlbumRepository;
import com.cs2031.sparky.song.domain.Song;
import com.cs2031.sparky.song.infrastructure.SongRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AlbumRepository albumRepo;
    private final SongRepository songRepo;

    public DataInitializer(AlbumRepository albumRepo, SongRepository songRepo) {
        this.albumRepo = albumRepo;
        this.songRepo = songRepo;
    }

    @Override
    public void run(String... args) {
        seed("Count Your Blessings", 2006, List.of(
                "Pray for Plagues", "Tell Slater Not to Wash His Dick",
                "Off the Heezay", "Slow Dance", "Liquor & Love Lost"));

        seed("Suicide Season", 2008, List.of(
                "The Comedown", "Suicide Season", "Don't Go",
                "Death Breath", "Chelsea Smile"));

        seed("There Is a Hell...", 2010, List.of(
                "Crucify Me", "Anthem", "It Never Ends",
                "Fuck", "Alligator Blood"));

        seed("Sempiternal", 2013, List.of(
                "Can You Feel My Heart", "The House of Wolves",
                "Empire (Let Them Sing)", "Sleepwalking",
                "Go to Hell, for Heaven's Sake"));

        seed("That's the Spirit", 2015, List.of(
                "Doomed", "Happy Song", "Throne",
                "True Friends", "Follow You"));

        seed("Amo", 2019, List.of(
                "I Apologise If You Feel Something", "MANTRA",
                "Nihilist Blues", "In the Dark", "Wonderful Life"));

        seed("Post Human: Survival Horror", 2020, List.of(
                "Dear Diary,", "Parasite Eve", "Teardrops",
                "Obey", "Kingslayer"));

        seed("Post Human: Nex Gen", 2024, List.of(
                "LosT", "DArkSide", "Kool-Aid", "AmEN!", "n/A"));
    }

    private void seed(String title, int year, List<String> songs) {
        Album album = albumRepo.save(new Album(title, year, "https://via.placeholder.com/300"));
        songs.forEach(name -> songRepo.save(new Song(name, "3:45", album)));
    }
}
