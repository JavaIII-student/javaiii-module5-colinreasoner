/*
 * Author: Colin Reasoner
 * Course: CSE-40481 Java Programming III
 * Section: 134318
 * November 24, 2018
 */

package module5;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MovieQueries {
	private static final String URL = "jdbc:derby:Movies;create=true";

	private Connection connection;
	private PreparedStatement selectAllMovies;
	private PreparedStatement selectMoviesByTitle;
	private PreparedStatement insertNewMovie;

	public MovieQueries() {
		try {
			System.out.println("Connecting to database URL: " + URL);
			this.connection = DriverManager.getConnection(URL);

      // Create query that selects all movies ordered by title
			this.selectAllMovies = this.connection.prepareStatement(
			    "SELECT * FROM movies ORDER BY title");

			// Create query that selects entries with titles that begin with the specified titles
      this.selectMoviesByTitle = this.connection.prepareStatement(
          "SELECT  * FROM movies WHERE title LIKE ? " +
          "ORDER BY title");

      this.insertNewMovie = this.connection.prepareStatement(
          "INSERT INTO movies " +
              "(title, rating, description) " +
              "VALUES (?, ?, ?)");

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
	}

	// select all movies in the database
  public List<Movie> getAllMovies() {
	  try (ResultSet resultSet = this.selectAllMovies.executeQuery()) {
      List<Movie> results = new ArrayList<>();

      while (resultSet.next()) {
        results.add(new Movie(
            resultSet.getInt("id"),
            resultSet.getString("title"),
            resultSet.getInt("rating"),
            resultSet.getString("description")));
      }

      return results;
    }
    catch (SQLException sqlException) {
	    sqlException.printStackTrace();
    }

    return null;
  }

  public List<Movie> getMoviesByTitle(String title) {
    try {
      this.selectMoviesByTitle.setString(1, title);
    }
    catch (SQLException sqlException) {
      sqlException.printStackTrace();
      return null;
    }

    try (ResultSet resultSet = this.selectMoviesByTitle.executeQuery()) {
      List<Movie> results = new ArrayList<>();

      while (resultSet.next()) {
        results.add(new Movie(
            resultSet.getInt("id"),
            resultSet.getString("title"),
            resultSet.getInt("rating"),
            resultSet.getString("description")));
      }

      return results;
    }
    catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }

	  return null;
  }

  public int addMovie(String title, int rating, String description) {
    try {
      insertNewMovie.setString(1, title);
      insertNewMovie.setInt(2, rating);
      insertNewMovie.setString(3, description);

      return insertNewMovie.executeUpdate();
    }
    catch (SQLException sqlException) {
      sqlException.printStackTrace();
      return 0;
    }
  }

  public void resetMovieDatabase() {
		Statement stmt = null;
		try {
			stmt = connection.createStatement();

			if (tableExists("movies")) {
			  System.out.println("Dropping movies table");
			  stmt.execute("DROP TABLE movies");
      }

      System.out.println("Creating movies Table - This will throw an exception if the table is already created.");
      stmt.execute("CREATE TABLE movies (" +
          "id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)," +
          "title VARCHAR(255)," +
          "rating INTEGER," +
          "description VARCHAR(255))");
		}
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

	}

	// Close the DB connection
	public void close() {
		try {
			this.connection.close();
		}
		catch (SQLException sqlExeption) {
			sqlExeption.printStackTrace();
		}
	}

	// Test if a table exists
  // Modified slightly from
  // https://stackoverflow.com/questions/5866154/how-to-create-table-if-it-doesnt-exist-using-derby-db
  public boolean tableExists(String sTableName) throws SQLException{
    if(this.connection!=null)
    {
      DatabaseMetaData dbmd = connection.getMetaData();
      ResultSet rs = dbmd.getTables(null, null, sTableName.toUpperCase(),null);
      if(rs.next())
      {
        System.out.println("Table "+rs.getString("TABLE_NAME")+" already exists !!");
        return true;
      }
    }
    return false;
  }
}
