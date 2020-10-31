/*---------------------------------------------------------
file: Screen.java
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: Screen class to hold relevant product info.
---------------------------------------------------------*/

public class Screen implements ScreenSpec {

  // Class fields
  private String resolution;
  private int refreshRate;
  private int responseTime;

  /**
   * Product Record Constructor
   *
   * @param resolution   The screen's resolution
   * @param refreshRate  The screen's refresh rate
   * @param responseTime The screen's response time
   */

  public Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  /**
   * Gets screen's resolution
   *
   * @return resolution
   */

  @Override
  public String getResolution() {
    return resolution;
  }

  /**
   * Sets screen's resolution
   *
   * @param resolution screen's resolution
   */

  public void setResolution(String resolution) {
    this.resolution = resolution;
  }

  /**
   * Gets screen's refresh rate
   *
   * @return refreshRate
   */

  @Override
  public int getRefreshRate() {
    return refreshRate;
  }

  /**
   * Sets screen's refresh rate
   *
   * @param refreshRate screen's refresh rate
   */

  public void setRefreshRate(int refreshRate) {
    this.refreshRate = refreshRate;
  }

  /**
   * Gets screen's response time
   *
   * @return response time
   */

  @Override
  public int getResponseTime() {
    return responseTime;
  }

  /**
   * Sets screen's response time
   *
   * @param responseTime screen's response time
   */

  public void setResponseTime(int responseTime) {
    this.responseTime = responseTime;
  }

  /**
   * Displays relevant info for product class
   */

  @Override
  public String toString() {
    return "Screen:" + "\n" + "Resolution: " + resolution + "\n" + "Refresh rate: " + refreshRate
        + "\n"
        + "Response time: " + responseTime;
  }
}
