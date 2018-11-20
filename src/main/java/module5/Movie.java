/*
 * Author: Colin Reasoner
 * Course: CSE-40481 Java Programming III
 * Section: 134318
 * November 24, 2018
 */

package module5;

public class Movie {
  private int movieId;
  private String title;
  private int rating;
  private String description;

  public Movie(int movieId, String title, int rating, String description) {
    this.movieId = movieId;
    this.title = title;
    this.rating = rating;
    this.description = description;
  }

  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return this.getTitle();
  }

  public String toStringFull() {
    return "ID: " + this.getMovieId() +
        " Title: " + this.getTitle() +
        " Rating: " + this.getRating() +
        " Description: " + this.getDescription();
  }

}
