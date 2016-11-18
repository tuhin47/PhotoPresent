//STEP 1. Import required packages

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class AddUser {

    public static void main(String[] args) {

        //        mAddUser("CSE100", 2013331040);
//        viewAllUser(ReadDB.getUniReg("cse"), "cse", "6/6/16");
//        updatePresent("CSECSE", "9-11-2016", "1004", true);
//        updateUrl("EEE", "2016/11/01", "101001", "abcde");
        addDropper("EEE", "111111");

    }

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    /*insert student info into the info table*/
    public static void mAddUser(String couseName, int lastRoll) {

        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            int i;
            i = lastRoll / 100;
            i *= 100;
            i++;

            for (; i <= lastRoll; i++) {
                sql = "insert into testdb.info (Course_Name,Reg_No)  values('" + couseName + "'," + i + ");";
                stmt.executeUpdate(sql);
            }

            System.out.println("Inserted records into the table...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    /*
    add dropper into info table
     */
    public static void addDropper(String couseName, String roll) {

        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            sql = "insert into testdb.info (Course_Name,Reg_No)  values('" + couseName + "','" + roll + "');";
            stmt.executeUpdate(sql);

            System.out.println("Inserted records into the table...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    /*insert student reg,coursename,date into the present table*/
    public static void viewAllUser(ArrayList<String> regNum, String courseName, String date) {

        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            for (int i = 0; i < regNum.size(); i++) {
                String reg = regNum.get(i);
                sql = "insert into testdb.present (Course_Name,Reg_No,Date,Present)  values('" + courseName + "','" + reg + "','" + date + "',false);";
//                System.out.println(sql);
                stmt.executeUpdate(sql);
            }

            System.out.println("Inserted records into the table...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    /*
    update regular present
     */
    public static void updatePresent(String courseName, String date, String regNo, String present) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            sql = "UPDATE testdb.present SET Present=" + present + " WHERE Course_Name=+'" + courseName + "'&&Date='" + date + "' && Reg_No='" + regNo + "';";
            stmt.executeUpdate(sql);
            System.out.println("Updated records into the table...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }

    }

    /*
    update present and photo_url into the present Table
     */
    public static void updateUrl(String classHour,String courseName, String date, String regNo, String url) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            sql = "UPDATE testdb.present SET Present='"+classHour+"',Photo_url='" + url + "' WHERE Course_Name=+'" + courseName + "'&&Date='" + date + "' && Reg_No='" + regNo + "';";
            stmt.executeUpdate(sql);
            System.out.println("Updated records into the table...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }

    }

    public static void deleteCourse(String course) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement();
            String sql;

            sql = "DELETE FROM testdb.info WHERE Course_Name='" + course+"';";
            stmt.executeUpdate(sql);
            sql = "DELETE FROM testdb.present WHERE Course_Name='" + course+"';";
            stmt.executeUpdate(sql);
            System.out.println("Updated records into the table...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }
    }

}
