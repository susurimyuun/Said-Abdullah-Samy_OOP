import java.util.HashMap;
import java.util.ArrayList;

public class MusicLibrary {
    private HashMap<String, ArrayList<Song>> library;

    public MusicLibrary() {
        this.library = new HashMap<>();
    }

    public void addSongToLibrary(Song song) {
        String albumName = song.getAlbum();

        library.putIfAbsent(albumName, new ArrayList<>());

        // Add the song to the specific album's list
        library.get(albumName).add(song);
    }

    public void displayLibrary() {
        System.out.println("=== Music Library ===");
        for (String album : library.keySet()) {
            System.out.println("Album: " + album);
            for (Song song : library.get(album)) {
                System.out.println("  - " + song.getTitle() + " (" + song.getDuration() + " mins)");
            }
        }
    }
}
