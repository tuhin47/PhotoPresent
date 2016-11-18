
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author alifa
 */
public class ReadDB {

    public static void main(String[] args) {

       
//        getAllUrl("CSE200", "2013331010");
//        getUniCourse();
//        System.out.println(ReadDB.getUniData());
//        ArrayList<String> reg_list = getUniReg("eee");
//        System.out.println(getUniReg("cse_", "31-10-2016"));

        /* ArrayList<Integer>list=getAllPresent("EEE", "101001");
        
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }*/
 /*ArrayList<String>list=getUniDate("EEE");
        
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
         */
    }

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public static String getUniData() {
        Connection conn = null;
        Statement stmt = null;

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            //conn.setAutoCommit(false);
            String sql;

            sql = "select distinct Course_Name from testdb.info";
            ResultSet rs = stmt.executeQuery(sql);

//            conn.setAutoCommit(false);
            // Extract data from result set
            JSONArray jsonDataArray = new JSONArray();

            int i = 0;
            while (rs.next()) {
                JSONObject jsonData = new JSONObject();
                // out.println("DONE");
                String Course_Name = rs.getString("Course_Name");
                System.out.println(Course_Name);
                jsonData.put(i + "", Course_Name);
                jsonDataArray.put(jsonData);
                i++;
            }

            return jsonDataArray.toString();

        } catch (Exception ex) {
            Logger.getLogger(AddUser.class.getName()).log(Level.SEVERE, null, ex);
            return "error";
        }
    }

    public static String arrayListToString(ArrayList<String> list) {

        JSONArray jsonDataArray = new JSONArray();

        for (int i = 0; i < list.size(); i++) {
            JSONObject jsonData = new JSONObject();
            jsonData.put(i + "", list.get(i));
            jsonDataArray.put(jsonData);

        }
        return jsonDataArray.toString();
    }

    public static String getUniReg(String course, String date) {
        Connection conn = null;
        Statement stmt = null;
        JSONArray jsonDataArray = new JSONArray();

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            //conn.setAutoCommit(false);
            String sql;

            sql = "SELECT DISTINCT Reg_No,Present FROM testdb.present WHERE Course_Name='" + course + "' && Date='" + date + "';";
            ResultSet rs = stmt.executeQuery(sql);

            int i = 1;
            while (rs.next()) {
                JSONObject jsonData = new JSONObject();
                jsonData.put(i + "", rs.getInt("Reg_No"));
                jsonData.put("pres", rs.getInt("Present"));
                jsonDataArray.put(jsonData);
                i++;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println(jsonDataArray.toString());
            return jsonDataArray.toString();
        }
    }

    /*provide unique Registration Number from info table and return to an arraylist*/
    public static ArrayList<String> getUniReg(String course) {
        Connection conn = null;
        Statement stmt = null;
        ArrayList<String> reg_list = new ArrayList<>();

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            //conn.setAutoCommit(false);
            String sql;

            sql = "SELECT DISTINCT Reg_No FROM testdb.info WHERE Course_Name='" + course + "' ORDER BY Reg_No;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
//                System.out.println("" + rs.getInt("Reg_No"));
                reg_list.add(rs.getString("Reg_No"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return reg_list;
        }
    }

    public static ArrayList<String> getUniDate(String course) {
        Connection conn = null;
        Statement stmt = null;
        ArrayList<String> date_list = new ArrayList<>();

        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            //conn.setAutoCommit(false);
            String sql;

            sql = "SELECT DISTINCT Date FROM testdb.present WHERE Course_Name='" + course + "' ORDER BY Date;";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //System.out.println("" + rs.getInt("Date"));
                String temp = rs.getString("Date");
                date_list.add(temp);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return date_list;
        }
    }

    public static ArrayList<Integer> getAllPresent(String course, String reg) {

        ArrayList<Integer> presentList = new ArrayList<Integer>();
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            //conn.setAutoCommit(false);
            String sql = String.format("SELECT Present FROM testdb.present WHERE Course_Name='%s' && Reg_No='%s' ORDER BY Date;", course, reg);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                presentList.add(rs.getInt("Present"));

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return presentList;
    }

    public static ArrayList<String> getAllUrl(String course, String reg) {

        ArrayList<String> urlList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Execute SQL query
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);

            //conn.setAutoCommit(false);
            String sql = String.format("SELECT Photo_url FROM testdb.present WHERE Course_Name='%s' && Reg_No='%s' ORDER BY Date;", course, reg);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                String str = rs.getString("Photo_url");

                if (str != null) {
                    System.out.println(str);
                    urlList.add(str);
                }

            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return urlList;
    }

    static String retPreviousReg(String current, ArrayList<String> list) {

        String previous = current;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i + 1).equals(current)) {
                return previous = list.get(i);
            }
        }
        return previous;

    }

    static String retNextReg(String current, ArrayList<String> list) {

        String next = current;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).equals(current)) {
                return next = list.get(i+1);
            }
        }
        return next;

    }

}
