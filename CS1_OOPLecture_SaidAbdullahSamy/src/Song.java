import javax.print.attribute.standard.Media;

public class Song extends MediaItem {
    private String title;
    private String artist;
    private String album;
    private float duration;
    public Song(String title, String artist, String album, float duration){
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }
    public Float getDuration() {
        return duration;
    }

    public String getAlbum() {
        return album;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }
    @Override
    public void play(){
        System.out.println("Now Playing " + getTitle() + " by " + getArtist());
    }
    @Override
    public void pause() {
        System.out.println("Paused song: " + getTitle());
    }

    @Override
    public void stop() {
        System.out.println("Stopped song: " + getTitle());
    }
}
