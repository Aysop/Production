/*---------------------------------------------------------
file: ItemType.java
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: Enum declaration for Item types.
---------------------------------------------------------*/

public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIO_MOBILE("AM"),
  VISUAL_MOBILE("VM");

  public final String code;

  ItemType(String code) {
    this.code = code;
  }

  public String code() {
    return code;
  }
}
