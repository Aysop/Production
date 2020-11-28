/*---------------------------------------------------------
file: MoviePlayer.java
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: MoviePlayer class to hold relevant product info.
---------------------------------------------------------*/

@SuppressWarnings("unused")
public class MoviePlayer extends Product implements MultimediaControl {

  //Class fields
  private final Screen screen;
  private final MonitorType monitorType;

  /**
   * Product Constructor
   *
   * @param id           The product's id
   * @param name         The product's name
   * @param manufacturer The product's manufacturer
   * @param screen       The product's screen size
   * @param monitorType  The product's monitor type
   */

  public MoviePlayer(int id, String name, String manufacturer, Screen screen,
      MonitorType monitorType) {
    super(id, name, manufacturer, ItemType.VISUAL);

    this.screen = screen;
    this.monitorType = monitorType;
  }


  /**
   * Initialized methods inherited from MultimediaControl interface
   */

  @Override
  public void play() {
    System.out.println("Playing movie");
  }

  @Override
  public void stop() {
    System.out.println("Stopping movie");
  }

  @Override
  public void previous() {
    System.out.println("Previous movie");
  }

  @Override
  public void next() {
    System.out.println("Next movie");
  }

  /**
   * Displays relevant info for product class
   */

  @Override
  public String toString() {
    return super.toString() + "\n"
        + screen + "\n" + "Monitor Type: " + monitorType;
  }

}
