/*
 * Author: Colin Reasoner
 * Course: CSE-40481 Java Programming III
 * Section: 134318
 * November 24, 2018
 */

package module5;

import java.awt.event.ActionEvent;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class MovieDatabaseController {
  @FXML private ListView<Movie> listView;
  @FXML private TextField titleTextField;
  @FXML private TextField ratingTextField;
  @FXML private TextField descriptionTextField;
  @FXML private TextField findByTitleTextField;

  // interacts with the movie database
  private final MovieQueries movieQueries = new MovieQueries();

  // stores list of Movie objects that restuls from a database query
  private final ObservableList<Movie> movieList = FXCollections.observableArrayList();

  // populate listView and set up listener for selection events
  public void initialize() {
    listView.setItems(movieList); // bind to movieList
    getAllEntries();

    // when ListView selection changes, display selected movie's data
    listView.getSelectionModel().selectedItemProperty().addListener(
        (observableValue, oldValue, newValue) -> {
          displayMovie(newValue);
        }
    );
  }

  // get all the entries from the database to populate movieList
  private void getAllEntries() {
    movieList.setAll(movieQueries.getAllMovies());
    selectFirstEntry();
  }

  // select first item in listView
  private void selectFirstEntry() {
    listView.getSelectionModel().selectFirst();
  }

  // display movie information
  private void displayMovie(Movie movie) {
    if (movie != null) {
      titleTextField.setText(movie.getTitle());
      ratingTextField.setText(String.format("%d", movie.getRating()));
      descriptionTextField.setText(movie.getDescription());
    }
    else {
      titleTextField.clear();
      ratingTextField.clear();
      descriptionTextField.clear();
    }
  }

  // add a new entry
  @FXML
  public void addEntryButtonPressed(ActionEvent event) {
    int result = movieQueries.addMovie(
        titleTextField.getText(), Integer.getInteger(ratingTextField.getText()),
        descriptionTextField.getText());

    if (result == 1) {
      displayAlert(AlertType.INFORMATION, "Entry Added",
          "New entry successfully added.");
    }
    else {
      displayAlert(AlertType.ERROR, "Entry Not Added",
          "Unable to add entry.");
    }

    getAllEntries();
  }

  // find entries with the specified title
  @FXML
  public void findButtonPressed(ActionEvent event) {
    List<Movie> movies = movieQueries.getMoviesByTitle(
        findByTitleTextField.getText() + "%");

    if (movies.size() > 0) {
      movieList.setAll(movies);
      selectFirstEntry();
    }
    else {
      displayAlert(AlertType.INFORMATION, "Title not found",
          "There are no entries with the specified title");
    }
  }

  // browse all entries
  @FXML
  public void browseAllButtonPressed(ActionEvent event) {
    getAllEntries();
  }

  // display an Alert dialog
  private void displayAlert(AlertType type, String title, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(title);
    alert.setContentText(message);
    alert.showAndWait();
  }

}
