package moldel;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Film {
    private final int ID;
    private String title;
    private String director;
    private int lenth;
    private int year;
    private MediaForm media;
    private ImageIcon cover;
    private boolean original;
    private ArrayList<Actor> mainCharacters;
    private boolean borrowed;
    private int lendCount;

    public Film(int ID, String title, String director, int lenth, int year, MediaForm media, String coverPath, boolean original, ArrayList<Actor> mainCharacters, boolean borrowed, int lendCount) {
        this.ID=ID;
        this.title = title;
        this.director = director;
        this.lenth = lenth;
        this.year = year;
        this.media = media;
        this.mainCharacters=mainCharacters;
        cover= new ImageIcon(coverPath); 
        Image image = cover.getImage(); 
        Image newimg = image.getScaledInstance(183, 273,  java.awt.Image.SCALE_SMOOTH);
        cover = new ImageIcon(newimg);
        cover.setDescription(coverPath);
        this.original = original;
        this.borrowed = borrowed;
        this.lendCount = lendCount;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getLenth() {
        return lenth;
    }

    public int getYear() {
        return year;
    }

    public MediaForm getMedia() {
        return media;
    }

    public int getID() {
        return ID;
    }

    public ImageIcon getCover() {
        return cover;
    }

    

    public boolean isOriginal() {
        return original;
    }

    public ArrayList<Actor> getMainCharacters() {
        return mainCharacters;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public int getLendCount() {
        return lendCount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setLenth(int lenth) {
        this.lenth = lenth;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMedia(MediaForm media) {
        this.media = media;
    }

    public void changeCover(String newCoverPath) {
        cover = new ImageIcon(newCoverPath);
    }

    public void setOriginal(boolean original) {
        this.original = original;
    }

    public void setMainCharacters(ArrayList<Actor> mainCharacters) {
        this.mainCharacters = mainCharacters;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public void increaseLendCount() {
        this.lendCount++;
    }
    
    
    
}
