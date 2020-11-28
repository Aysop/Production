/*---------------------------------------------------------
file: Controller.java
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: Controller for all tab pages; Applies their
      functionality.
---------------------------------------------------------*/

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

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
  public TextField newEmpName;
  @FXML
  public TextField newEmpPW;
  @FXML
  public TextArea newEmpCredentials;
  @FXML
  public TextArea productRecord;
  @FXML
  public Label errorLabel2;
  @FXML
  public Label successLabel2;
  @FXML
  public Label successLabel;

  // Class fields
  private final ArrayList<ProductionRecord> productionLogs = new ArrayList<>(); // Holds production logs for TextArea
  private final ArrayList<String> employeeDetails = new ArrayList<>();
  private final ObservableList<String> productList = FXCollections
      .observableArrayList(); // Holds products toString() info for ListView
  private final ObservableList<Product> productLine = FXCollections
      .observableArrayList(); // Holds product info for TableView
  Properties prop = new Properties();


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
    populateLog();
  }


  /**
   * Assigns value types for the database's table columns
   */

  public void listDB() {
    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

    productView.setItems(productLine);
  }

  /**
   * populates choice box on Product Line tab
   */

  public void populateChoiceBox() {
    // populates choice box
    for (ItemType item : ItemType.values()) {
      choiceBox.getItems().add(item);
    }

  }

  /**
   * Populates combo box with numerical choices 1-10
   */

  public void populateCmbBox() {
    // populates combo boxes
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
   * Populates production log list
   */

  public void populateLog() {  // SpotBugs finds "Experimental", may fail to clean up rs checked exception

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

      String sql = "SELECT * FROM PRODUCTIONRECORD";

      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {

        int prodNum = rs.getInt("production_num");
        int prodID = rs.getInt("product_id");
        String serialNum = rs.getString("serial_num");
        Date date = (Date) rs.getObject("date_produced");

        ProductionRecord productionRecord = new ProductionRecord(prodNum, prodID, serialNum, date);
        productionRecord.calibrateCount();

        productRecord.appendText(productionRecord.toString() + "\n");

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
          PauseTransition visiblePause = new PauseTransition(
              Duration.seconds(3)
          );
          visiblePause.setOnFinished(
              event -> errorLabel.setText("")
          );
          visiblePause.play();
        } else {
          stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
          stmt.setString(1, prodType.toString());
          stmt.setString(2, prodManufacturer);
          stmt.setString(3, prodName);

          stmt.executeUpdate();

          updateProductLists();

          successLabel.setText("Added Successfully.");
          nameText.clear();
          manufacturerText.clear();
          choiceBox.getSelectionModel().clearSelection();
          PauseTransition visiblePause = new PauseTransition(
              Duration.seconds(3)
          );
          visiblePause.setOnFinished(
              event -> successLabel.setText("")
          );
          visiblePause.play();
        }

      } catch (NullPointerException e) {

        errorLabel.setText("Please select a product type.");
        PauseTransition visiblePause = new PauseTransition(
            Duration.seconds(3)
        );
        visiblePause.setOnFinished(
            event -> errorLabel.setText("")
        );
        visiblePause.play();
      }
    } catch (Exception se) {
      //Handle errors for JDBC
      se.printStackTrace();
    }//Handle errors for Class.forName
    finally {
      //finally block used to close resources
      try {
        if (stmt != null) {
          stmt.close();
          conn.close();
        }
      } catch (SQLException ignored) {
      }// do nothing
      try {
        if (conn != null) {
          assert stmt != null;
          stmt.close();
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

  public void updateProductLists() { // SpotBugs finds "Experimental" here, may fail to clean up rs checked exception
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
        Widget newWidget = new Widget(id, name, manufacturer, type);

        productLine.add(newWidget);
        productList.add(newWidget.toString());
        productSelection.getItems().add(newWidget);
      }

      // STEP 4: Clean-up environment

      stmt.close();

      conn.close();

    } catch (ClassNotFoundException | SQLException e) {

      e.printStackTrace();

    }
  }

  /**
   * Adds users entries to GUI database list
   */

  public void updateLogLists() { // SpotBugs finds "Experimental" here, may fail to clean up rs checked exception
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

      String sql = "SELECT TOP 1 * FROM PRODUCTIONRECORD ORDER BY PRODUCTION_NUM DESC";

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        int prodNum = rs.getInt("production_num");
        int prodID = rs.getInt("product_id");
        String serialNum = rs.getString("serial_num");
        Date date = (Date) rs.getObject("date_produced");

        ProductionRecord productionRecord = new ProductionRecord(prodNum, prodID, serialNum, date);
        productionLogs.add(productionRecord);
        productRecord.appendText(productionRecord.toString() + "\n");

      }

      // STEP 4: Clean-up environment

      stmt.close();

      conn.close();

    } catch (ClassNotFoundException | SQLException e) {

      e.printStackTrace();


    }
  }

  /**
   * Adds newly made products to Production Log
   *
   * @param product The newly made product
   */

  public void addToLog(Product product) {

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
        String sql = " INSERT INTO ProductionRecord(product_id, serial_num, date_produced) VALUES (?, ?, current_timestamp)";
        ProductionRecord recordLog = new ProductionRecord(product, productionLogs.size());
        productionLogs.add(recordLog);
        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(2, recordLog.getSerialNumber());
        stmt.setInt(1, recordLog.getProductID());

        stmt.executeUpdate();
        updateLogLists();


      } catch (NullPointerException e) {
        System.out.println("oof");
      }
    } catch (Exception se) {
      //Handle errors for JDBC
      se.printStackTrace();
    }//Handle errors for Class.forName
    finally {
      //finally block used to close resources
      try {
        if (stmt != null) {
          stmt.close();
          conn.close();
        }
      } catch (SQLException ignored) {
      }// do nothing
      try {
        if (conn != null) {
          assert stmt != null;
          stmt.close();
          conn.close();
        }
      } catch (SQLException se) {
        se.printStackTrace();
      }//end finally try
    }
  }

  /**
   * Creates new product in the "Produce" tab
   */

  public void createItem() {
    errorLabel2.setText("");
    successLabel2.setText("");

    try { // Catches NullPointer exceptions
      Product product = productSelection.getSelectionModel().getSelectedItem();

      try { // Catches Runtime exceptions
        int tally = Integer.parseInt(cmbBox.getValue());
        for (int i = 0; i < tally; i++) {
          addToLog(product);
          successLabel2.setText("Added Successfully.");
          PauseTransition visiblePause = new PauseTransition(
              Duration.seconds(3)
          );
          visiblePause.setOnFinished(
              event -> successLabel2.setText("")
          );
          visiblePause.play();


        }
      } catch (RuntimeException e) {
        errorLabel2.setText("Please enter a #.");
        PauseTransition visiblePause = new PauseTransition(
            Duration.seconds(3)
        );
        visiblePause.setOnFinished(
            event -> errorLabel2.setText("")
        );
        visiblePause.play();
      }
    } catch (
        NullPointerException e) {
      errorLabel2.setText("Please select a product.");
      PauseTransition visiblePause = new PauseTransition(
          Duration.seconds(3)
      );
      visiblePause.setOnFinished(
          event -> errorLabel2.setText("")
      );
      visiblePause.play();

    }

  }

  /**
   * Create new employee
   */

  public void createEmp(MouseEvent mouseEvent) {

    try {


    } catch (NullPointerException e) {
      System.out.println("try again");
    }



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
        String sql = " INSERT INTO EMPLOYEE(name, email, username, password) VALUES (?, ?, ?, ?)";
        String empName = newEmpName.getText();
        String empPW = newEmpPW.getText();

        Employee newEmployee = new Employee(empName, empPW);

        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(4, newEmployee.getPassword());
        stmt.setString(3, newEmployee.getUsername());
        stmt.setString(2, newEmployee.getEmail());
        stmt.setString(1, newEmployee.getName().toString());

        stmt.executeUpdate();
        newEmpName.clear();
        newEmpPW.clear();
        newEmpCredentials.setText(newEmployee.toString());


      } catch (NullPointerException e) {
        System.out.println("oof");
      }
    } catch (Exception se) {
      //Handle errors for JDBC
      se.printStackTrace();
    }//Handle errors for Class.forName
    finally {
      //finally block used to close resources
      try {
        if (stmt != null) {
          stmt.close();
          conn.close();
        }
      } catch (SQLException ignored) {
      }// do nothing
      try {
        if (conn != null) {
          assert stmt != null;
          stmt.close();
          conn.close();
        }
      } catch (SQLException se) {
        se.printStackTrace();
      }//end finally try
    }
  }

  public void fetchAllEmpDetails() { // SpotBugs finds "Experimental" here, may fail to clean up rs checked exception
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

      String sql = "SELECT * FROM EMPLOYEE";

      ResultSet rs = stmt.executeQuery(sql);

      employeeDetails.clear();

      while (rs.next()) {
        String empName = rs.getString("name");
        String pw = rs.getString("password");

        Employee employee = new Employee(empName, pw);
        employeeDetails.add(employee.toString());
      }

      // STEP 4: Clean-up environment

      stmt.close();

      conn.close();

    } catch (ClassNotFoundException | SQLException e) {

      e.printStackTrace();


    }
  }


  public void login(MouseEvent mouseEvent) {



  }

  public void listEmployees(MouseEvent mouseEvent) {
    fetchAllEmpDetails();
    newEmpCredentials.clear();
    for (String empDetails : employeeDetails) {
      newEmpCredentials.appendText(empDetails);
    }
  }
}



