package edu.northeastern.social_media2;

public class userinfo {
    public String name;
    public String img;
    public userinfo() {
    }
    public String getName() {
        return name;
    }
    public String getImg() {
        return img;
    }
    public userinfo(String name, String img)
    {
        this.name = name;
        this.img = img;
    }
}