package module5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MovieDatabase extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Parent root =
        FXMLLoader.load(getClass().getClassLoader().getResource("MovieDatabase.fxml"));

    Scene scene = new Scene(root);
    stage.setTitle("Movie Database");
    stage.setScene(scene);
    stage.show();
  }

}
