package com.dpap.bookingapp.images;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/images")
public class ImageController {
    private final ImageRepository repository;

    public ImageController(ImageRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Image>> getImages(@RequestParam Long placeId) {
        return ResponseEntity.ok(repository.findAllByPlaceId(placeId));
//        return ResponseEntity.ok(repository.findAllByPlaceId(placeId).stream().map(Image::getUrl).toList());
    }
}
