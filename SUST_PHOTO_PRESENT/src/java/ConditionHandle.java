
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author TUHIN
 */
public class ConditionHandle {

    public static void main(String args[]) {
        System.out.println(isPreviouslyExist("tuhin", "3-11-2016"));
    }

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    /*
    verify the condition is the date && coursename previously created in present database
     */
    public static boolean isPreviouslyExist(String course, String date) {

        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            String sql = String.format("SELECT COUNT(*) as total from testdb.present WHERE Course_Name='%s' && Date ='%s';", course, date);

            ResultSet rs = stmt.executeQuery(sql);
            int total = 0;
            while (rs.next()) {
                total = rs.getInt("total");
                break;
            }

            System.out.println(total);
            if (total == 0) {
                return false;
            } else {
                return true;
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConditionHandle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConditionHandle.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }
    
    

}
