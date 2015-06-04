package playlist.model;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DataStax Academy Sample Application
 *
 * Copyright 2013 DataStax
 *
 */

public class ArtistsDAO extends CassandraData {

  private static final Logger logger = Logger.getLogger(ArtistsDAO.class.getName());

  //
  // This class retrieves Artist names from the artist table in Cassandra
  // It has a single static method which is given the artist's first letter
  // and returns a list of Artists
  //

  // Static finder method

  /**
   *
   * Returns a list of artists that begin with the specified letter.  The artist
   * may be returned in ascending or descending order
   *
   * @param first_letter - first letter of the Artists name
   * @param desc - return the results in ascending or descending order
   * @return - Return the artists names as list of Strings
   */

  public static List<String> listArtistByLetter(String first_letter, boolean desc) {

    //
    // Build a query. This is an example of executing a simple statement.
    //

    String queryText = "SELECT * FROM artists_by_first_letter WHERE first_letter = ?";
    if (desc) {
      logger.log(Level.INFO, "in the if conditional");
      queryText.concat(" ORDER BY artist DESC");
    } 
    logger.log(Level.INFO, "desc is {0}", desc);
    logger.log(Level.INFO, "Query string is {0}", queryText);
    PreparedStatement preparedStatement = getSession().prepare(queryText);
    BoundStatement boundStatement = preparedStatement.bind(first_letter);

    //
    // Obtain the results in a ResultSet object
    //

    ResultSet results = getSession().execute(boundStatement);

    //
    // Allocate an empty list of strings to return the artists
    //

    List<String> artists = new ArrayList<>();

    //
    // Iterate over the results.  For each row, retrieve the "artist" column as a String.
    // and add it to the list of strings.
    //

    for (Row row : results) {
       artists.add(row.getString("artist"));     // Lets use column 0 since there is only one column
    }

    //
    // Return the list of strings.
    //

    return artists;
  }
}
