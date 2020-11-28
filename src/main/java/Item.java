/*---------------------------------------------------------
file: Item.java
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: Headers for Item accessor/mutator methods.
---------------------------------------------------------*/

@SuppressWarnings("ALL")
public interface Item {

  int getId();

  String getName();

  void setName(String name);

  String getManufacturer();

  void setManufacturer(String manufacturer);

}
