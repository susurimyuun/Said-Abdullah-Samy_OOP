import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Playlist {
    List<MediaItem> playlists = new ArrayList<>();
    private Song song1;
    private Song song2;
    private Song song3;
    private Podcast pod1;
    private Podcast pod2;

    public void addMedia(MediaItem item) {
        playlists.add(item);
    }

    public void removeMedia(MediaItem item) {
        playlists.remove(item);
    }

    public void playPlaylists(){
        System.out.println("--- Starting Playlist ---");
        for (MediaItem lists : playlists){
            lists.play();
        }
        System.out.println("--- Playlist Finished ---");
    }
}
