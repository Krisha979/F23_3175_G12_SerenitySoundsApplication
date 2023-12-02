package com.example.f23_3175_g12_serenitysoundsapplication.Model;

public class Song {
    private String SongName;
    private String SongUrl;

    public Song() {
    }

    public Song(String songName, String songUrl) {
        SongName = songName;
        SongUrl = songUrl;
    }

    public String getSongName() {
        return SongName;
    }

    public void setSongName(String songName) {
        SongName = songName;
    }

    public String getSongUrl() {
        return SongUrl;
    }

    public void setSongUrl(String songUrl) {
        SongUrl = songUrl;
    }
}
