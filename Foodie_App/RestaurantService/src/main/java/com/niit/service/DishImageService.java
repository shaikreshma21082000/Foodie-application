package com.niit.service;

import com.niit.model.DishImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DishImageService {
    String addImageOfDish(DishImage dishImage);
    DishImage getImageOfDish(String imageId);
    String updateImageOfDish(String imageId, MultipartFile file) throws IOException;
}