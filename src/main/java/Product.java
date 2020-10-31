/*---------------------------------------------------------
file: Product.java
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: Production abstract class to hold relevant
      product info for subclasses.
---------------------------------------------------------*/


public abstract class Product implements Item {

  // variable declarations
  protected int id;
  protected String name;
  protected String manufacturer;
  protected ItemType type;

  /**
   * Product get
   *
   * @return the products name
   */

  public int getId() {
    return id;
  }

  /**
   * Product gets and sets
   *
   * @return the products name
   */

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Product gets and sets
   *
   * @return the products manufacturer
   */

  public String getManufacturer() {
    return manufacturer;
  }

  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Product gets and sets
   *
   * @return the products type
   */

  public ItemType getType() {
    return type;
  }

  public void setType(ItemType type) {
    this.type = type;
  }

  /**
   * Product Constructor
   *
   * @param name         The product's name
   * @param manufacturer The product's manufacturer
   * @param type         The product's type
   */

  public Product(int id, String name, String manufacturer, ItemType type) {
    this.id = id;
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * Displays relevant info for product class
   */

  public String toString() {
    return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: " + type;
  }

}

class Widget extends Product {

  /**
   * Product Constructor
   *
   * @param name         The product's name
   * @param manufacturer The product's manufacturer
   * @param type         The product's type
   */

  public Widget(int id, String name, String manufacturer, ItemType type) {
    super(id, name, manufacturer, type);
  }


}