/*---------------------------------------------------------
file: ProductionRecord.java
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: Production Record class to hold relevant product info.
---------------------------------------------------------*/

import java.util.Date;

public class ProductionRecord {

  private int productionNumber;
  private int productID;
  private String serialNumber;
  private Date dateProduced;

  // Product Type Counters
  private static int countAU = 0;
  private static int countVI = 0;
  private static int countAM = 0;
  private static int countVM = 0;

  /**
   * Gets product's number
   *
   * @return production Number
   */

  public int getProductionNumber() {
    return productionNumber;
  }

  /**
   * Sets production number
   *
   * @param productionNumber product's production number
   */

  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  /**
   * Gets product's id
   *
   * @return productID
   */

  public int getProductID() {
    return productID;
  }

  /**
   * Sets product's id
   *
   * @param productID product's id
   */

  public void setProductID(int productID) {
    this.productID = productID;
  }

  /**
   * Product Record gets and sets
   */

  public String getSerialNumber() {
    return serialNumber;
  }

  /**
   * Sets product's serial number
   *
   * @param serialNumber product's serial number
   */

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  /**
   * Product Record gets and sets
   */

  public Date getDateProduced() {
    return dateProduced = new Date(dateProduced.getTime());
  }

  /**
   * Sets product's date produced
   *
   * @param dateProduced product's production date
   */

  public void setDateProduced(Date dateProduced) {
    this.dateProduced = new Date(dateProduced.getTime());
  }

  /**
   * Product Record Constructor
   *
   * @param product The product's production count
   * @param count   The product's id
   */

  ProductionRecord(Product product, int count) {
    // Class fields
    this.productionNumber = count;
    productID = product.id;
    serialNumber = determineSerialNumber(product);
    dateProduced = new Date();

  }

  /**
   * Product Record Constructor
   *
   * @param productionNumber The product's production count
   * @param productID        The product's id
   * @param serialNumber     The product's serial num
   * @param dateProduced     The product's production date
   */

  ProductionRecord(int productionNumber, int productID, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  /**
   * Displays relevant info for product class
   */

  @Override
  public String toString() {

    return "Prod. Num: " + productionNumber + " Product ID: " + productID + " Serial Num: "
        + serialNumber + " Date: " + dateProduced;
  }

  /**
   * Determines products serial number
   *
   * @param product The product needing to be serialized
   */

  public String determineSerialNumber(Product product) {
    String format = "";
    String manufacturer;
    String serialID;
    StringBuilder placeholder = new StringBuilder();

    switch (product.type.code()) {
      case "AU":
        format = String.format("%05d", countAU);
        countAU++;
        break;
      case "VI":
        format = String.format("%05d", countVI);
        countVI++;
        break;
      case "AM":
        format = String.format("%05d", countAM);
        countAM++;
        break;
      case "VM":
        format = String.format("%05d", countVM);
        countVM++;
        break;
      default:
        break;
    }

    if (product.manufacturer.length() < 3) { // checks manufacturer name length
      placeholder.append("@".repeat(
          3 - (product.manufacturer.length()))); // appends placeholders for names less than req.
      manufacturer = product.manufacturer;
      serialID = placeholder + manufacturer + product.type.code() + format;
    } else {
      manufacturer = product.manufacturer.substring(0, 3);
      serialID = manufacturer + product.type.code() + format;
    }
    return serialID;
  }

  public void calibrateCount() {
    switch (getSerialNumber().substring(3, 5)) {
      case "AU":
        countAU++;
        break;
      case "VI":
        countVI++;
        break;
      case "AM":
        countAM++;
        break;
      case "VM":
        countVM++;
        break;
      default:
        break;
    }
  }


}
