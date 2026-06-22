package com.cs2031.sparky.user.application;

import com.cs2031.sparky.user.domain.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    record FavoriteRequest(Long songId) {}

    @PostMapping("/favorites")
    public ResponseEntity<Void> addFavorite(@AuthenticationPrincipal UserDetails user,
                                            @RequestBody FavoriteRequest req) {
        userService.addFavorite(user.getUsername(), req.songId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/favorites")
    public ResponseEntity<Void> removeFavorite(@AuthenticationPrincipal UserDetails user,
                                               @RequestBody FavoriteRequest req) {
        userService.removeFavorite(user.getUsername(), req.songId());
        return ResponseEntity.ok().build();
    }
}
