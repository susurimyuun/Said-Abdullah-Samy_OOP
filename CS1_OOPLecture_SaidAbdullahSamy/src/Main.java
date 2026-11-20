public class Main {
    public static void main(String[] args) {
        // Create Songs
        Song song1 = new Song("Merry Christmas, I Miss You", "Alex Chricton", "Single", 4.07f);
        Song song2 = new Song("Merry Christmas, Please Don't Call","Bleachers", "Single", 3.23f);
        Song song3 = new Song("Glimpse of Us", "Joji", "SMITHEREENS",3.54f);

        Podcast pod1 = new Podcast("Tag Blast : GTA 6 Collector Edition Rp. 7 Jutaan⁉\uFE0F Playstation 6 Bochor⁉\uFE0F","Tara Arts", "110",27.18f);

        MusicLibrary library = new MusicLibrary();
        library.addSongToLibrary(song1);
        library.addSongToLibrary(song2);
        library.addSongToLibrary(song3);
        library.displayLibrary();

        System.out.println();

        // Test Collection 2: LinkedList (Playlist) and Polymorphism
        Playlist myPlaylist = new Playlist();
        myPlaylist.addMedia(song1);
        myPlaylist.addMedia(pod1);
        myPlaylist.addMedia(song3);
        myPlaylist.addMedia(song2);

        // This will print the custom play() messages for both Songs and Podcasts
        myPlaylist.playPlaylists();
    }
}