/*---------------------------------------------------------
file: AudioPlayer.java
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: AudioPlayer class to hold relevant product info..
---------------------------------------------------------*/

public class AudioPlayer extends Product implements MultimediaControl {

  // Class fields
  private final String supportedAudioFormats;
  private final String supportedPlaylistFormats;

  /**
   * Product Constructor
   *
   * @param id                       The product's id
   * @param name                     The product's name
   * @param manufacturer             The product's manufacturer
   * @param supportedAudioFormats    The product's supported audio formats
   * @param supportedPlaylistFormats The product's supported playlist formats
   */

  public AudioPlayer(int id, String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(id, name, manufacturer, ItemType.AUDIO);

    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * Initialized methods inherited from MultimediaControl interface
   */

  public void play() {
    System.out.println("Playing");
  }

  public void stop() {
    System.out.println("Stopping");
  }

  public void previous() {
    System.out.println("Previous");
  }

  public void next() {
    System.out.println("Next");
  }

  /**
   * Displays relevant info for product class
   */

  @Override
  public String toString() {
    return super.toString() + "\n"
        + "Supported Audio Formats: " +
        supportedAudioFormats + "\n" + "Supported Playlist Formats: " + supportedPlaylistFormats;
  }
}
