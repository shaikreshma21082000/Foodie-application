package com.niit.service;

import com.niit.model.DishImage;
import com.niit.repository.DishImageRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DishImageServiceImpl implements DishImageService{
    DishImageRepository dishImageRepository;
    @Autowired
    public DishImageServiceImpl(DishImageRepository dishImageRepository)
    {
        this.dishImageRepository=dishImageRepository;
    }

    @Override
    public String addImageOfDish(DishImage dishImage) {
        dishImageRepository.save(dishImage);
        RestaurantServiceImpl.ImageId=dishImage.getImageId();
        System.out.println("photo id"+dishImage.getImageId());
        return dishImage.getImageId();
    }

    @Override
    public DishImage getImageOfDish(String imageId) {
        return dishImageRepository.findById(imageId).get();
    }

    @Override
    public String updateImageOfDish(String imageId, MultipartFile file) throws IOException {
        List<DishImage> list=dishImageRepository.findAll();
        DishImage photo=new DishImage();
        if(list!=null) {
            for (DishImage image : list) {
                if (image.getImageId().equals(imageId)) {
                    photo=image;
                    photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
                    dishImageRepository.save(photo);
                }
            }
        }
        RestaurantServiceImpl.ImageId=photo.getImageId();
        return photo.getImageId();
    }
}
