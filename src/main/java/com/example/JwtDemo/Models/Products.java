package com.example.JwtDemo.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="ImageTable")
public class Products {
    @Id
    int imageId;
    String imageName;
    String  imageType;
    @Lob
    byte[] imageData;


    public Products() {
    }

    public Products(int imageId, String imageName, String imageType,byte[] imageData) {
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageType = imageType;
        this.imageData=imageData;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageDate) {
        this.imageData = imageData;
    }



}
