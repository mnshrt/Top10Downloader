package com.example.android.top10downloader;

/**
 * Created by emkayx on 30-09-2015.
 */
public class Applications {
    private String name;
    private String artist;
    private String releaseDate;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Name: "+ getName() + "\n"+
                "Artist: "+getArtist()+"\n"+
                "Release Date: "+getReleaseDate()+"\n";
    }
}
