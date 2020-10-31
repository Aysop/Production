/*---------------------------------------------------------
file: Controller.java
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: Controller for all tab pages; Applies their
      functionality.
---------------------------------------------------------*/

import java.sql.PreparedStatement;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {

  // FXML Class Fields
  @FXML
  public ChoiceBox<ItemType> choiceBox;
  @FXML
  public ComboBox<String> cmbBox;
  @FXML
  public Label errorLabel;
  @FXML
  public ListView<Product> productSelection;
  @FXML
  public TableView<Product> productView;
  @FXML
  public TableColumn<Product, Integer> idCol;
  @FXML
  public TableColumn<Product, ItemType> typeCol;
  @FXML
  public TableColumn<Product, String> nameCol;
  @FXML
  public TableColumn<Product, String> manufacturerCol;
  @FXML
  public TextField nameText;
  @FXML
  public TextField manufacturerText;
  @FXML
  public TextArea productRecord;
  @FXML
  public Label errorLabel2;
  @FXML
  public Label successLabel2;
  @FXML
  public Label successLabel;

  // Class fields
  private static int count = 0;
  private final ArrayList<String> productionLogs = new ArrayList<>();
  private final ObservableList<String> productList = FXCollections.observableArrayList();
  private final ObservableList<Product> productLine = FXCollections.observableArrayList();


  /**
   * Handles button actions on Product Line tab
   */

  public void addProduct(MouseEvent mouseEvent) {
    addToDB();
  }

  /**
   * Handles button actions on Produce tab
   */

  public void recordProduction(MouseEvent mouseEvent) {
    createItem();
  }

  /**
   * Functions in it run at start
   */

  public void initialize() {
    listDB();
    populateChoiceBox();
    populateCmbBox();
    populateDB();
  }


  /**
   * Assigns value types for the database's table columns
   */

  public void listDB() {
    idCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
    nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
    manufacturerCol.setCellValueFactory(new PropertyValueFactory<Product, String>("manufacturer"));
    typeCol.setCellValueFactory(new PropertyValueFactory<Product, ItemType>("type"));

    productView.setItems(productLine);
  }

  /**
   * populates choice box on Product Line tab
   */

  public void populateChoiceBox() {

    for (ItemType item : ItemType.values()) {
      choiceBox.getItems().add(item);
    }

  }

  /**
   * Populates combo box with numberical choices 1-10
   */

  public void populateCmbBox() {
    //populates combo boxes at start
    for (int i = 1; i <= 10; i++) {
      cmbBox.setEditable(true);
      cmbBox.getItems().add(Integer.toString(i));
      cmbBox.getSelectionModel().selectFirst();
    }
  }


  /**
   * Populates database list
   */

  public void populateDB() {  // SpotBugs finds "Experimental", may fail to clean up rs checked exception

    final String JDBC_DRIVER = "org.h2.Driver";

    final String DB_URL = "jdbc:h2:./res/productionDB";

    //  Database credentials

    final String USER = "";

    final String PASS = ""; // SpotBug finds "Security" issue, no password

    Connection conn = null;

    Statement stmt = null;

    try {

      // STEP 1: Register JDBC driver

      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query

      stmt = conn.createStatement();

      String sql = "SELECT * FROM PRODUCT";

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {

        int id = Integer.parseInt(rs.getString("id"));
        String name = rs.getString("name");
        String manufacturer = rs.getString("manufacturer");
        ItemType type = ItemType.valueOf(rs.getString("type"));
        Widget widget = new Widget(id, name, manufacturer, type);

        productLine.add(widget);
        productList.add(widget.toString());
        productSelection.getItems().add(widget);

      }

      // STEP 4: Clean-up environment

      stmt.close();

      conn.close();

    } catch (ClassNotFoundException | SQLException e) {

      e.printStackTrace();


    }
  }

  /**
   * inserts user entries to database
   */

  public void addToDB() {
    successLabel.setText("");
    errorLabel.setText("");

    final String JDBC_DRIVER = "org.h2.Driver";

    final String DB_URL = "jdbc:h2:./res/productionDB";

    //  Database credentials

    final String USER = "";

    final String PASS = ""; // SpotBug finds "Security" issue, no password

    Connection conn = null;

    PreparedStatement stmt = null;

    try {

      // STEP 1: Register JDBC driver

      Class.forName(JDBC_DRIVER);
      //SpotBugs finds "Bad Practice" here, method may fail to close db resource
      //STEP 2: Open a connection

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query

      try {//Catches null exceptions for Product Line choice box
        String prodName = nameText.getText();
        String prodManufacturer = manufacturerText.getText();
        ItemType prodType = choiceBox.getValue();

        String sql = " INSERT INTO Product(type, manufacturer, name) VALUES ( ?, ?, ?)";

        if (prodName.equals("") || prodManufacturer.equals("")) {
          errorLabel.setText("Please fill in all the forms");
        } else {
          stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
          stmt.setString(1, prodType.toString());
          stmt.setString(2, prodManufacturer);
          stmt.setString(3, prodName);

          stmt.executeUpdate();

          updateLists();

          successLabel.setText("Added Successfully.");
          nameText.clear();
          manufacturerText.clear();
          choiceBox.getSelectionModel().clearSelection();
        }

      } catch (NullPointerException e) {

        errorLabel.setText("Please select a product type.");
      }
    } catch (Exception se) {
      //Handle errors for JDBC
      se.printStackTrace();
    }//Handle errors for Class.forName
    finally {
      //finally block used to close resources
      try {
        if (stmt != null) {
          conn.close();
        }
      } catch (SQLException ignored) {
      }// do nothing
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException se) {
        se.printStackTrace();
      }//end finally try
    }
  }


  /**
   * Adds users entries to GUI database list
   */

  public void updateLists() { // SpotBugs finds "Experimental" here, may fail to clean up rs checked exception
    final String JDBC_DRIVER = "org.h2.Driver";

    final String DB_URL = "jdbc:h2:./res/productionDB";

    //  Database credentials

    final String USER = "";

    final String PASS = ""; // SpotBug finds "Security" issue, no password

    Connection conn = null;

    Statement stmt = null;

    try {

      // STEP 1: Register JDBC driver

      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection

      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query

      stmt = conn.createStatement();

      String sql = "SELECT TOP 1 * FROM PRODUCT ORDER BY ID DESC";

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {

        int id = Integer.parseInt(rs.getString("id"));
        String name = rs.getString("name");
        String manufacturer = rs.getString("manufacturer");
        ItemType type = ItemType.valueOf(rs.getString("type"));

        productLine.add(
            new Widget(id, name, manufacturer, type));
        productList.add(
            new Widget(id, name, manufacturer, type).toString());
        productSelection.getItems()
            .add(new Widget(id, name, manufacturer, type));

      }

      // STEP 4: Clean-up environment

      stmt.close();

      conn.close();

    } catch (ClassNotFoundException | SQLException e) {

      e.printStackTrace();


    }
  }

  /**
   * Adds newly made product to Production Log
   *
   * @param product The newly made product
   * @param count   The product's manufacturer
   */

  public void addToLog(Product product, int count) {
    productRecord.setText("");
    ProductionRecord recordLog = new ProductionRecord(product, count);
    productionLogs.add(recordLog.toString());
    for (String a : productionLogs) {
      productRecord.appendText(a + "\n");
    }
  }

  /**
   * Creates new product in the "Produce" tab
   */

  public void createItem() {
    errorLabel2.setText("");
    successLabel2.setText("");

    try {
      Product product = productSelection.getSelectionModel().getSelectedItem();
      int tally = Integer.parseInt(cmbBox.getValue());

      for (int i = 0; i < tally; i++) {
        count++;
        addToLog(product, count);
      }

      successLabel2.setText("Added Successfully.");
    } catch (NullPointerException e) {
      errorLabel2.setText("Please select a product.");
    }

  }


}



