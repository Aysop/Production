public class AudioPlayer extends Product implements MultimediaControl {


  String supportedAudioFormats;
  String supportedPlaylistFormats;


  public AudioPlayer(int id, String name, String manufacturer, String supportedAudioFormats, String supportedPlaylistFormats){
    super(id, name, manufacturer, ItemType.AUDIO);

    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  };

  //@Override
  //public int getId() {
  //  return 0;
  //}

  public void play(){
    System.out.println("Playing");
  }
  public void stop(){
    System.out.println("Stopping");
  }
  public void previous(){
    System.out.println("Previous");
  }
  public void next(){
    System.out.println("Next");
  }

  @Override
  public String toString(){
    return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" +"Type: " + type + "\n"+ "Supported Audio Formats: " +
        supportedAudioFormats + "\n" + "Supported Playlist Formats: " + supportedPlaylistFormats;
  }
}
