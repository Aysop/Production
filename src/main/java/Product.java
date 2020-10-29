/*---------------------------------------------------------
file: Product.java
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: Product class for db tableview
---------------------------------------------------------*/


public abstract class Product implements Item{

/**
 * Product get
 *
 * @return the products id
 * <p>
 * Product gets and sets
 * @return the products name
 */
//public int getId() {
//  return id;
//}

/**
 * Product gets and sets
 *
 * @return the products name
 */
public String getName(){
    return name;
    }

public void setName(String name){
    this.name=name;
    }

/**
 * Product gets and sets
 *
 * @return the products manufacturer
 */

public String getManufacturer(){
    return manufacturer;
    }

public void setManufacturer(String manufacturer){
    this.manufacturer=manufacturer;
    }

    // variable declarations
    int id;
    String name;
    String manufacturer;
    ItemType type;

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

public Product(String name,String manufacturer,ItemType type){
    this.id = id;
    this.name=name;
    this.manufacturer=manufacturer;
    this.type=type;
    }

  public String toString(){
    return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" +"Type: " + type.code;
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
  public Widget(String name, String manufacturer, ItemType type)  {
    super(name, manufacturer, type);
  }


}