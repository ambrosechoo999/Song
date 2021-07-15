package sg.edu.rp.c346.id20008189.song;

import java.io.Serializable;

public class Song implements Serializable {
    private 	int id;
    private 	String songContent;
    private     String title;
    private      String singers;
    private     int year;
    private  int stars;

    public Song(int id, String title,String singers,int year,int stars ) {
        this.id = id;
        this.songContent = songContent;
        this.singers=singers;
        this.stars=stars;
        this.title=title;
        this.year=year;
    }

    public int getId() {  return id;  }

    public String getSongContent() { return songContent; }

    public void setSongContent(String songContent) {
        this.songContent = songContent;
    }

    @Override
    public String toString() { return "Song:" + id + ", " + songContent;  }

}


