/*---------------------------------------------------------
file: Controller.java
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: Controller for all tab pages; Applies their
      functionality.
---------------------------------------------------------*/

import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

  @FXML
  public TableView<Product> productList;
  public TableColumn<Product, String> typeCol;
  public TableColumn<Product, String> nameCol;
  public TableColumn<Product, String> manufacturerCol;
  public ChoiceBox choiceBox;
  public TextField nameText;
  public TextField manufacturerText;
  public ComboBox cmbBox;
  public Label errorLabel;

  ObservableList<Product> obList = FXCollections.observableArrayList();


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
    System.out.println("...but the second mouse gets the cheese.");
  }

  /**
   * Runs whatever functions placed into it at program start
   */
  public void initialize() {
    listDB();
    populateDB();
    choiceBoxSelect();

    //populates combo boxes at start
    for (int i = 1; i <= 10; i++) {
      cmbBox.setEditable(true);
      cmbBox.getItems().add(Integer.toString(i));
      cmbBox.getSelectionModel().selectFirst();
    }
  }

  /**
   * populates choice box on Product Line tab
   */
  public void choiceBoxSelect() {
    choiceBox.getItems().add("AUDIO");
    choiceBox.getItems().add("VISUAL");
    choiceBox.getItems().add("AUDIO_MOBILE");
    choiceBox.getItems().add("VIDEO_MOBILE");
  }

  /**
   * inserts user entries to database
   */
  public void addToDB() {

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
      /**
       * Catches null exceptions for Product Line choice box
       */
      try {
        String prodName = nameText.getText();
        String prodManufacturer = manufacturerText.getText();
        String prodType = choiceBox.getValue().toString();
        String sql = " INSERT INTO Product(type, manufacturer, name) VALUES ( ?, ?, ? )";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, prodType);
        stmt.setString(2, prodManufacturer);
        stmt.setString(3, prodName);

        stmt.executeUpdate();
        userFieldsToList();


      } catch (NullPointerException e) {
        errorLabel.setText("Please select a product type.");
      }


    } catch (SQLException se) {
      //Handle errors for JDBC
      se.printStackTrace();
    } catch (Exception e) {
      //Handle errors for Class.forName
      e.printStackTrace();
    } finally {
      //finally block used to close resources
      try {
        if (stmt != null) {
          conn.close();
        }
      } catch (SQLException se) {
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
   * Assigns value types for the database's table columns Used https://www.youtube.com/watch?v=LoiQVoNil9Q&t=5s
   * as ref
   */
  public void listDB() {

    nameCol.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
    manufacturerCol.setCellValueFactory(new PropertyValueFactory<Product, String>("manufacturer"));
    typeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("type"));

    productList.setItems(obList);

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

        obList.add(
            new Product(rs.getString("name"), rs.getString("manufacturer"), rs.getString("type")));

      }

      // STEP 4: Clean-up environment

      stmt.close();

      conn.close();

    } catch (ClassNotFoundException e) {

      e.printStackTrace();


    } catch (SQLException e) {

      e.printStackTrace();

    }


  }

  /**
   * Adds users entries to GUI database list
   */
  public void userFieldsToList() { // SpotBugs finds "Experimental" here, may fail to clean up rs checked exception
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

        obList.add(
            new Product(rs.getString("name"), rs.getString("manufacturer"), rs.getString("type")));

      }

      // STEP 4: Clean-up environment

      stmt.close();

      conn.close();

    } catch (ClassNotFoundException e) {

      e.printStackTrace();


    } catch (SQLException e) {

      e.printStackTrace();

    }
  }

}





