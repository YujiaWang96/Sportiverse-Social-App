package edu.northeastern.social_media2;

public class upload {
    public String propic;
    public String name2;
    public String title;
    public String id2;
    public String url;
    public String likes;
    public String key;
    public upload(){}
    public String getPropic(){return propic;}
    public String getName2(){return name2;}
    public String getTitle(){return title;}
    public String getId2(){return id2;}
    public String getUrl(){return url;}
    public String getLikes(){return likes;}
    public String getKey(){return key;}
    public upload(String propic,String name2,String title,String id2,String
            url,String likes,String key){
        this.propic=propic;
        this.name2=name2;
        this.title=title;
        this.id2=id2;
        this.url=url;
        this.likes=likes;
        this.key=key;
    }
}