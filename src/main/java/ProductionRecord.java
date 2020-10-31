import java.util.Date;

public class ProductionRecord {

  int productionNumber;
  String productID;
  String serialNumber;
  Date dateProduced;



  private static int countAU = 0;
  private static int countVI = 0;
  private static int countAM = 0;
  private static int countVM = 0;


  public int getProductionNumber() {
    return productionNumber;
  }

  public void setProductionNumber(int productionNumber) {
    this.productionNumber = productionNumber;
  }

  public String getProductID() {
    return productID;
  }

  public void setProductID(String productID) {
    this.productID = productID;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public Date getDateProduced() {
    return dateProduced;
  }

  public void setDateProduced(Date dateProduced) {
    this.dateProduced = dateProduced;
  }

  ProductionRecord(Product product, int count) {

    this.productionNumber = count;
    productID = product.name;
    serialNumber =
        product.manufacturer.substring(0, 3) + product.type.code() + determineSerialNumber(product);
    dateProduced = new Date();

  }

  ProductionRecord(int productionNumber, String productID, String serialNumber, Date dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  @Override
  public String toString() {
    return "Prod. Num: " + productionNumber + " Product ID: " + productID + " Serial Num: "
        + serialNumber + " Date: " + dateProduced;
  }


  public String determineSerialNumber(Product product) {
    String format = "";

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
    }
    return format;
  }


}
