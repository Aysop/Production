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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  String type;

/**
 * Product Constructor
 *
 * @param name         The product's name
 * @param manufacturer The product's manufacturer
 * @param type         The product's type
 */

public Product(String name,String manufacturer,String type){
    //this.id = id;
    this.name=name;
    this.manufacturer=manufacturer;
    this.type=type;
    }

    }
