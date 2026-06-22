package com.cs2031.sparky.user.domain;

import com.cs2031.sparky.song.infrastructure.SongRepository;
import com.cs2031.sparky.user.infrastructure.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final SongRepository songRepo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepo, SongRepository songRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.songRepo = songRepo;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    public User register(String email, String password, String name) {
        if (userRepo.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use");
        }
        return userRepo.save(new User(email, encoder.encode(password), name));
    }

    public void addFavorite(String email, Long songId) {
        User user = userRepo.findByEmail(email).orElseThrow();
        var song = songRepo.findById(songId).orElseThrow(() -> new IllegalArgumentException("Song not found"));
        user.getFavorites().add(song);
        userRepo.save(user);
    }

    public void removeFavorite(String email, Long songId) {
        User user = userRepo.findByEmail(email).orElseThrow();
        user.getFavorites().removeIf(s -> s.getId().equals(songId));
        userRepo.save(user);
    }
}
