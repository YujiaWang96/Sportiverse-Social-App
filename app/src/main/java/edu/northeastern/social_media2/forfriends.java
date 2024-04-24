package edu.northeastern.social_media2;

public class forfriends {
    public String tyaname;
    public String tyaimg;
    public String tyaid;
    public forfriends() {
    }
    public String getTyaame() {return tyaname;}
    public String getTyaimg() {
        return tyaimg;
    }
    public String getTyaid(){return tyaid;}
    public forfriends(String name, String img,String id)
    {
        this.tyaname = name;
        this.tyaimg = img;
        this.tyaid = id;
    }
}