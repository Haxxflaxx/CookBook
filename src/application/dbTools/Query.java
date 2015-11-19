package application.dbTools;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by haxxflaxx on 2015-10-29.
 */
public class Query {
    /**
     * Builds and executes a query to insert a tuple into the DB.
     * @param table Select table for data to be inserted into.
     * @param column Columns to receive values, e.g. "column1, column2,...columnN"
     * @param value Values to be inserted, e.g. "value1, value2,...valueN"
     * @exception SQLException if a database access error occurs.
     */
    public static void insertInto(String table, String column, String value) throws SQLException {
        String sql = "INSERT INTO " + table + " (" + column + ") " +
                "VALUES (" + value + ");";
        Connection.runUpdate(sql);
    }

    /**
     * Builds and executes a query to delete a tuple from the DB.
     * @param table Select table for removing data from.
     * @param criteria Select info for which tuple to delete. e.g. "ID=1"
     * @exception SQLException if a database access error occurs.
     */
    public static void deleteFrom(String table, String criteria) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE " + criteria + ";";
        Connection.runUpdate(sql);
    }

    /**
     * Builds and executes a query to fetch a tuple from the DB.
     * @param table Table for data selection.
     * @param column Column for data selection. e.g. "column1, column2,...columnN" and "*" for select all.
     * @param condition Conditions for which tuple to fetch. e.g. "ID=1"
     * @return String Array List with the fetched data set.
     * @exception SQLException if a database access error occurs.
     */
    public static ArrayList<ArrayList<String>> fetchData(String table, String column, String condition) throws SQLException {
        String sql = "SELECT " + column + " FROM " + table + " WHERE " + condition + ";";
        return Connection.runQuery(sql);
    }

    /**
     * Builds and executes a query to fetch a tuple from the DB.
     * @param table Table for data selection.
     * @param column Column for data selection. e.g. "column1, column2,...columnN" and "*" for select all.
     * @return String Array List with the fetched data set.
     * @exception SQLException if a database access error occurs.
     */
    public static ArrayList<ArrayList<String>> fetchData(String table, String column) throws SQLException {
        String sql = "SELECT " + column + " FROM " + table + ";";
        return Connection.runQuery(sql);
    }

    /**
     * Builds and executes a query to update an attribute in the DB.
     * @param table The table for the update.
     * @param column The attribute for the update.
     * @param value The new value.
     * @param condition Selection for which tuples to be updated.
     * @throws SQLException if a database access error occurs.
     */
    public static void updateData(String table, String column, String value, String condition) throws SQLException {
        String sql = "UPDATE " + table + " SET " + column + " = '" + value + "' WHERE " + condition + ";";
        Connection.runUpdate(sql);

    }

    /**
     * Builds and executes a query to update several attributes in the DB.
     * @param table The table for the update.
     * @param column A list of attributes to be updated.
     * @param value A list of new values.
     * @param condition Selection for which tuples to be updated.
     * @throws SQLException if a database access error occurs.
     */
    public static void updateData(String table, String[] column, String[] value, String condition) throws SQLException {
        String sql = "UPDATE " + table + " SET ";

        if (column.length == value.length){
            for (int i = 0; i < column.length; i++){
                sql += column[i] + " = '" + value[i] + "'";
                if (i != column.length - 1) sql += ", ";
            }
            sql += " WHERE " + condition + ";";

            System.out.println(sql);
            Connection.runUpdate(sql);
        }
        else{
            System.out.println("column and value does not contain the same amount of arguments");
        }
    }
}
