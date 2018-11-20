/*
 * Author: Colin Reasoner
 * Course: CSE-40481 Java Programming III
 * Section: 134318
 * November 24, 2018
 */

package module5;

import java.util.List;

public class TestMovieDB {
	public static void main(String[] args) {
		MovieQueries movieQueries = new MovieQueries();

    movieQueries.resetMovieDatabase();
//    movieQueries.addMovie("The Fellowship of the Ring", 8, "The first movie in the Lord of the Rings Triology");
//    movieQueries.addMovie("The Two Towers", 9, "The second movie in the Lord of the Rings Triology");
//    movieQueries.addMovie("The Return of the King", 7, "The third movie in the Lord of the Rings Triology");
//    movieQueries.addMovie("The Princess Bride", 10, "Best action/adventure/romance of all time");
//    movieQueries.addMovie("Star Wars: The Phantom Menace", 1, "A bad movie executed badly");

    List<Movie> myMovies = movieQueries.getAllMovies();
    System.out.println("All movies...");
    for (Movie m : myMovies) {
      System.out.println(m.toStringFull());
    }

    myMovies = movieQueries.getMoviesByTitle("The" + "%");
    System.out.println("Movies that start with 'The'");
    for (Movie m : myMovies) {
      System.out.println(m.toStringFull());
    }

    movieQueries.close();
  }

}
