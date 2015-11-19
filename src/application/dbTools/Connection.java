package application.dbTools;



import java.sql.*;
import java.util.ArrayList;


/**
 * Created by haxxflaxx on 2015-10-01.
 */
public class Connection {

    /**
     * Opens SQLite connection to Dishes.db
     * @return c SQLite connection
     */
    private static java.sql.Connection open()
    {
        java.sql.Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Dishes.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }

    /**
     * Closes SQLite connection
     * @param c SQLite connection
     * @exception SQLException if a database access error occurs.
     */
    private static void close(java.sql.Connection c) throws SQLException {
        c.close();
        System.out.println("Closed database successfully");
    }

    /**
     * Executes update to the DB.
     * @param sql application.dbTools.Query to be run in the update.
     * @exception SQLException if a database access error occurs.
     */
    public static void runUpdate(String sql) throws SQLException {

        java.sql.Connection c = open();
        c.setAutoCommit(false);
        Statement stmt = c.createStatement();

        stmt.executeUpdate(sql);

        stmt.close();
        c.commit();
        close(c);
    }

    /**
     * Executes a query on the DB.
     * @param sql SQL query to be executed.
     * @return A list of tuples as ArrayLists.
     * @exception SQLException if a database access error occurs.
     */
    public static ArrayList<ArrayList<String>> runQuery(String sql) throws SQLException {
        java.sql.Connection c = open();
        Statement stmt;
        ResultSet rs;
        int columnCount;
        ArrayList<ArrayList<String>> result = new ArrayList<>();


        stmt = c.createStatement();
        rs = stmt.executeQuery(sql);

        columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            ArrayList<String> resultPart = new ArrayList<>();
            for (int i = 1; i < columnCount + 1; i++) {
                resultPart.add(rs.getString(i));
            }
            result.add(resultPart);
        }

        stmt.close();
        close(c);
        return result;
    }

}

