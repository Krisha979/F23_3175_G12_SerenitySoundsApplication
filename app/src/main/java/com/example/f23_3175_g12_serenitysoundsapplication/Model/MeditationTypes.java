package com.example.f23_3175_g12_serenitysoundsapplication.Model;

public class MeditationTypes {
    private int ImgId;
    private String ImgName;
    private int ImgPic;

    public int getImgId() {
        return ImgId;
    }

    public void setImgId(int imgId) {
        ImgId = imgId;
    }

    public String getImgName() {
        return ImgName;
    }

    public void setImgName(String imgName) {
        ImgName = imgName;
    }

    public int getImgPic() {
        return ImgPic;
    }

    public void setImgPic(int imgPic) {
        ImgPic = imgPic;
    }

    public MeditationTypes(int imgId, String imgName, int imgPic) {
        ImgId = imgId;
        ImgName = imgName;
        ImgPic = imgPic;
    }

    public MeditationTypes() {
    }
}
