package com.disney.play;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.google.gson.Gson;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 *
 */
public class TestMySQL
{
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/test";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "dbpass";

    /**
     * Read from JSON column
     */
    @Test
    public void testReadJsonColumn() throws Exception
    {
        Connection conn = null;
        Statement stmt = null;
        try
        {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to a selected database...");
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            // Execute a query
            String sql = "SELECT * FROM t1";
            System.out.println("Creating statement...");
            stmt = (Statement) conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            // Extract data from result set
            while (rs.next())
            {
                String jdoc = rs.getString("jdoc");

                // Display values
                System.out.print("JSON: " + jdoc + "\n");
            }
            rs.close();
        }
        finally
        {
            // finally block used to close resources
            try
            {
                if (stmt != null)
                    conn.close();
            }
            catch (SQLException se)
            {
            } // do nothing
            try
            {
                if (conn != null)
                    conn.close();
            }
            catch (SQLException se)
            {
                se.printStackTrace();
            }
        }
    }
    
    /**
     * Write to JSON column
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void testWriteJsonColumn() throws ClassNotFoundException, SQLException
    {
        Connection conn = null;
        Statement stmt = null;
        try
        {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to a selected database...");
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            // Execute query
            System.out.println("Creating statement...");
            stmt = (Statement) conn.createStatement();
            
            long value = System.currentTimeMillis();

            String sql = "INSERT INTO t1 VALUES('{\"key\": \"" + value + "\"}')";
            stmt.executeUpdate(sql);

        }
        finally
        {
            // finally block used to close resources
            try
            {
                if (stmt != null)
                    conn.close();
            }
            catch (SQLException se)
            {
            } // do nothing
            try
            {
                if (conn != null)
                    conn.close();
            }
            catch (SQLException se)
            {
                se.printStackTrace();
            } 
        }
    }

    /**
     * Improperly formated JSON should throw exception
     * 
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test(expected=SQLException.class)
    public void testWriteInvalidJsonColumn() throws ClassNotFoundException, SQLException
    {
        Connection conn = null;
        Statement stmt = null;
        try
        {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to a selected database...");
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            System.out.println("Creating statement...");
            stmt = (Statement) conn.createStatement();
            
            long value = System.currentTimeMillis();

            String badSQL = "INSERT INTO t1 VALUES('{\"key\": \"" + value + "\"}}}}}}}}}}}}}')";
            stmt.executeUpdate(badSQL);

        }
        finally
        {
            // finally block used to close resources
            try
            {
                if (stmt != null)
                    conn.close();
            }
            catch (SQLException se)
            {
            } // do nothing
            try
            {
                if (conn != null)
                    conn.close();
            }
            catch (SQLException se)
            {
                se.printStackTrace();
            } 
        } 
    }

    /**
     * Read from JSON column
     */
    @Test
    public void testDeserializeJsonColumn() throws Exception
    {
        Connection conn = null;
        Statement stmt = null;
        try
        {
            // Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            // Open a connection
            System.out.println("Connecting to a selected database...");
            conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            // Execute a query
            String sql = "SELECT * FROM t1";
            System.out.println("Creating statement...");
            stmt = (Statement) conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            // Extract data from result set
            while (rs.next())
            {
                String jdoc = rs.getString("jdoc");

                Gson gson = new Gson();
                gson.fromJson(jdoc, TestMySQL.ModelObject.class);
                
                // Display values
                System.out.print("GSON: " + gson.toJson(jdoc) + "\n");
            }
            rs.close();
        }
        finally
        {
            // finally block used to close resources
            try
            {
                if (stmt != null)
                    conn.close();
            }
            catch (SQLException se)
            {
            } // do nothing
            try
            {
                if (conn != null)
                    conn.close();
            }
            catch (SQLException se)
            {
            }
        }
    }

    @SuppressWarnings("unused")
    private static class ModelObject
    {
        private String key;
        
        @SuppressWarnings("unused")
        public ModelObject(String key)
        {
            this.key = key;
        }
    }
}
