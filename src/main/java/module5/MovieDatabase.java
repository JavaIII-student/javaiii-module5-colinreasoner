package module5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MovieDatabase extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader();
    loader.setClassLoader(getClass().getClassLoader());
    loader.setLocation(getClass().getResource("MovieDatabase.fxml"));
    Parent root = loader.load();

    Scene scene = new Scene(root);
    stage.setTitle("Movie Database");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

/*
public class AddressBook extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    Parent root =
        FXMLLoader.load(getClass().getResource("AddressBook.fxml"));

    Scene scene = new Scene(root);
    stage.setTitle("Address Book");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
*/
