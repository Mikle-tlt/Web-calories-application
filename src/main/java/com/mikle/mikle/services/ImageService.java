package com.mikle.mikle.services;

import com.mikle.mikle.exception.EntityNotFoundException;
import com.mikle.mikle.models.Image;
import com.mikle.mikle.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ImageService {
    private final ImageRepository imageRepository;

    public Image findImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException(format("Image with id %s not found.", id));
        });
    }
}
