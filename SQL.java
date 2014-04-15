import java.sql.*;

public class SQL {

  private static final String dbURL = "jdbc:mysql://localhost:3306/sampledb";
  private static final String username = "root";
  private static final String password = "root";

  /* ------------------------------------------------------------------------ */

  public static void main(String... args) {

    try {
      Connection conn = DriverManager.getConnection(dbURL, username, password);

      insert(conn); // create
      select(conn); // read
      update(conn); // update
      delete(conn); // delete

      conn.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /* ------------------------------------------------------------------------ */

  private static void insert(Connection conn) throws SQLException {
    String sql = "INSERT INTO " +
        "Users (username, password, fullname, email) " +
        "VALUES (?, ?, ?, ?)";

    PreparedStatement statement = conn.prepareStatement(sql);
    statement.setString(1, "bill");
    statement.setString(2, "secretpass");
    statement.setString(3, "Bill Gates");
    statement.setString(4, "bill.gates@microsoft.com");

    int rowsInserted = statement.executeUpdate();
    if (rowsInserted > 0) {
      System.out.println("A new user was inserted successfully!");
    }
  }

  /* ------------------------------------------------------------------------ */

  private static void select(Connection conn) throws SQLException {
    String sql = "SELECT * FROM Users";

    Statement statement = conn.createStatement();
    ResultSet result = statement.executeQuery(sql);

    int count = 0;

    while (result.next()) {
      String name = result.getString(2);
      String pass = result.getString(3);
      String fullname = result.getString("fullname");
      String email = result.getString("email");

      String output = "User #%d: %s - %s - %s - %s";
      System.out.println(String.format(
          output, ++count, name, pass, fullname, email));
    }
  }

  /* ------------------------------------------------------------------------ */

  private static void update(Connection conn) throws SQLException {
    String sql = "UPDATE Users " +
        "SET password=?, fullname=?, email=? " +
        "WHERE username=?";

    PreparedStatement statement = conn.prepareStatement(sql);
    statement.setString(1, "123456789");
    statement.setString(2, "William Henry Bill Gates");
    statement.setString(3, "bill.gates@microsoft.com");
    statement.setString(4, "bill");

    int rowsUpdated = statement.executeUpdate();
    if (rowsUpdated > 0) {
      System.out.println("An existing user was updated successfully!");
    }
  }

  /* ------------------------------------------------------------------------ */

  private static void delete(Connection conn) throws SQLException {
    String sql = "DELETE FROM Users WHERE username=?";

    PreparedStatement statement = conn.prepareStatement(sql);
    statement.setString(1, "bill");

    int rowsDeleted = statement.executeUpdate();
    if (rowsDeleted > 0) {
      System.out.println("A user was deleted successfully!");
    }
  }
}