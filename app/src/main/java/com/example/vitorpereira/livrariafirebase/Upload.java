package com.example.vitorpereira.livrariafirebase;

public class Upload {
    private String nameImage;
    private String urlImage;

    public Upload() {
    }

    public Upload(String name, String imageURL){
        if (name.trim().equals("")){
            name = "sem nome";
        }
        nameImage = name;
        urlImage = imageURL;
    }

    public String getNameImage() {
        return nameImage;
    }

    public void setNameImage(String nameImage) {
        this.nameImage = nameImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
