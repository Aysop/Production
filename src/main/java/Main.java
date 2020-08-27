/*---------------------------------------------------------
file: <<Main.java>>
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: Introduction to Gradle project; 3 production
      tabs and a login screen created and displayed.
---------------------------------------------------------*/

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {


  @Override
  public void start(Stage primaryStage) {

    //creates text fields for login credentials
    primaryStage.setTitle("JavaFX Welcome");
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(25, 25, 25, 25));

    Text scenetitle = new Text("Welcome");
    scenetitle.setId("welcome-text");
    grid.add(scenetitle, 0, 0, 2, 1);

    Label userName = new Label("User Name:");
    grid.add(userName, 0, 1);

    TextField userTextField = new TextField();
    grid.add(userTextField, 1, 1);

    Label pw = new Label("Password:");
    grid.add(pw, 0, 2);

    PasswordField pwBox = new PasswordField();
    grid.add(pwBox, 1, 2);

    //creates login button
    Button btn = new Button("Sign in");
    HBox hbBtn = new HBox(10);
    hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
    hbBtn.getChildren().add(btn);
    grid.add(hbBtn, 1, 4);

    final Text actiontarget = new Text();
    grid.add(actiontarget, 1, 6);

    //handles button action when pressed
    btn.setOnAction(new EventHandler<>() {

      @Override
      public void handle(ActionEvent e) {
        Parent root = null;
        try {
          root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
        //displays tab selection after successful login
        primaryStage.setTitle("Tab Panels");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

      }
    });

    //displays login screen
    Scene scene = new Scene(grid, 300, 275);
    primaryStage.setScene(scene);
    scene.getStylesheets().add(
        Main.class.getResource("styling.css").toExternalForm());
    primaryStage.show();


  }


  public static void main(String[] args) {
    launch(args);
  }
}