/*---------------------------------------------------------
file: <<Main.java>>
  by: Joseph Morelli
 org: COP 3003, Fall 2020
 for: Introduction to scenebuilder; 3 production
      tabs scenes have been built on to include
      various text fields, labels, areas, etc.
---------------------------------------------------------*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
    primaryStage.setTitle("Production");
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
