//STEP 1. Import required packages

import java.sql.*;

public class GetClassData {
    // JDBC driver name and database URL

    static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
     static final String DB_URL="jdbc:mysql://localhost:3306";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public static void mAddUser(String couseName,int lastRoll) {
    
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            System.out.println("Connected database successfully...");
//
//            //STEP 4: Execute a query
//            System.out.println("Inserting records into the table...");
            stmt = conn.createStatement();
            String sql,userName="root",passWord="";
            sql="insert into testdb.testuser (Course_name,Last_Roll)  values('"+couseName+"','"+lastRoll+"');";
            //INSERT INTO `testuser` (`Course_name`, `Last_Roll`) VALUES ('ojiol', '564856');

//            String sql = "INSERT INTO Registration "
//                    + "VALUES (100, 'Zara', 'Ali', 18)";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Registration "
//                    + "VALUES (101, 'Mahnaz', 'Fatma', 25)";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Registration "
//                    + "VALUES (102, 'Zaid', 'Khan', 30)";
//            stmt.executeUpdate(sql);
//            sql = "INSERT INTO Registration "
//                    + "VALUES(103, 'Sumit', 'Mittal', 28)";
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
    }//end main
}//end JDBCExample
