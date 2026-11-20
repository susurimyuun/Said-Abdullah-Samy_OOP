public class Podcast extends MediaItem{
    private String title;
    private String artist;
    private String episode;
    private Float duration;

    public Podcast(String title, String artist, String episode, float duration){
        this.title = title;
        this.artist = artist;
        this.episode = episode;
        this.duration = duration;
    }

    public Float getDuration() {
        return duration;
    }

    public String getEpisode() {
        return episode;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void play(){
        System.out.println("Now Playing Podcast " + getTitle() + " Episode " + getEpisode());
    }
    @Override
    public void pause() {
        System.out.println("Paused podcast: " + getTitle());
    }

    @Override
    public void stop() {
        System.out.println("Stopped podcast: " + getTitle());
    }
}
