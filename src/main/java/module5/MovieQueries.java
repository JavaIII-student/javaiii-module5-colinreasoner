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
              "(id, title, rating, description) " +
              "VALUES (?, ?, ?, ?)");

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
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
          "id INTEGER PRIMARY KEY," +
          "title VARCHAR(255)," +
          "rating INTEGER," +
          "description VARCHAR(255))");

//			System.out.println("adding values into movies table");
//      stmt.executeUpdate("INSERT INTO movies VALUES (1, 'The Fellowship of the Ring', 8, 'The first movie in the Lord of the Rings Triology')");
//      stmt.executeUpdate("INSERT INTO movies VALUES (2, 'The Two Towers', 9, 'The second movie in the Lord of the Rings Triology')");
//      stmt.executeUpdate("INSERT INTO movies VALUES (3, 'The Return of the King', 7, 'The third movie in the Lord of the Rings Triology')");
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

	}

	public void close() {
		try {
			connection.close();
		} catch (SQLException sqlExeption) {
			sqlExeption.printStackTrace();
		}
	}

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
