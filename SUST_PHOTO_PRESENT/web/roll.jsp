
<%@page import="java.io.IOException"%>
<%@page import="java.awt.Desktop"%>
<%@page import="java.io.File"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.FileNotFoundException"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>


    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <%!
            static ArrayList<Integer> getAllPresent(String course, String reg) {

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
//                    Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
//                    Logger.getLogger(ReadDB.class.getName()).log(Level.SEVERE, null, ex);
                }

                return presentList;
            }

            public static String createCSV(String courseName, ArrayList<String> regNum, ArrayList<String> date) throws FileNotFoundException {

                File myFile = new File("D:/PROject/WORK/SUST_PHOTO_PRESENT/" + courseName + ".csv");
                PrintWriter pw = new PrintWriter(myFile);
                StringBuilder sb = new StringBuilder();

                sb.append("Registration_NO");
                sb.append(',');
                for (String x : date) {
                    sb.append(x);
                    sb.append(',');
                }
 sb.append(',');
                sb.append("Total");
                sb.append('\n');

                for (String reg : regNum) {
                    ArrayList<Integer> present = getAllPresent(courseName, reg);
                    sb.append(reg);
                    sb.append(',');
                    int total = 0;
                    for (Integer pp : present) {
                        sb.append(pp);
                        if (pp >= 1) {
                            total += pp;
                        }
                        sb.append(',');
                    }
                    sb.append(',');
                    sb.append(total);
                    sb.append('\n');

                }

//        System.out.println(sb.toString());
                pw.write(sb.toString());
                pw.close();
                if (Desktop.isDesktopSupported()) {
                    try {

                        Desktop.getDesktop().open(myFile);
                    } catch (IOException ex) {
                        // no application registered for PDFs
                    }
                }
                System.out.println("done!");
                return sb.toString();

            }

            static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
            static final String DB_URL = "jdbc:mysql://localhost:3306";

            //  Database credentials
            static final String USER = "root";
            static final String PASS = "";

            static ArrayList<String> getUniReg(String course) {
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

                } catch (ClassNotFoundException ex) {

                } finally {
                    return reg_list;
                }
            }

            static ArrayList<String> getUniDate(String course) {
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

                } catch (ClassNotFoundException ex) {

                } finally {
                    return date_list;
                }
            }


        %>
        <%
            if (request.getParameterMap().containsKey("CSV")) {
                createCSV("CSE200", getUniReg("CSE200"), getUniDate("CSE200"));

            }
        %>



        <div id="CSV" >

            <!--<p><strong>Previous/Next</strong></p>-->
            <form action="roll.jsp">
                <input type="submit" name="CSV" value="CSV" >

            </form>

        </div>
    </body>

</html>
