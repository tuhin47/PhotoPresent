

<%@page import="java.io.IOException"%>
<%@page import="java.awt.Desktop"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.File"%>
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
        <link rel="stylesheet" href="index.css" type="text/css">

        <title>Photo Present</title>
    </head>
    <body>





        <div id="topbar">
            <strong><font color="white">Photo Present</font>
            </strong>
        </div>

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

            public static String createCSV(HttpServletRequest request, String courseName, ArrayList<String> regNum, ArrayList<String> date) throws FileNotFoundException {

                File myFile = new File(courseName + ".csv");
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


        <%!            static String preCourse, preReg = null, next = null, previous = null;

            String retPreviousReg(String current, ArrayList<String> list) {

                String previous = current;
                for (int i = 0; i < list.size() - 1; i++) {
                    if (list.get(i + 1).equals(current)) {
                        return previous = list.get(i);
                    }
                }
                return previous;

            }

            String retNextReg(String current, ArrayList<String> list) {

                String next = current;
                for (int i = 0; i < list.size() - 1; i++) {
                    if (list.get(i).equals(current)) {
                        return next = list.get(i + 1);
                    }
                }
                return next;

            }

            public ArrayList<String> getUniCourse() {
                Connection conn = null;
                Statement stmt = null;
                ArrayList<String> list = new ArrayList<>();

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

                    sql = "select distinct Course_Name from testdb.info";;
                    ResultSet rs = stmt.executeQuery(sql);

                    while (rs.next()) {
                        String temp = rs.getString("Course_Name");
                        list.add(temp);
//                System.out.println(temp);

                    }

                } catch (SQLException | ClassNotFoundException ex) {

                }
                return list;

            }
        %>
        <%
            ArrayList list = getUniCourse();
            if (request.getParameterMap().containsKey("course")) {
                preCourse = request.getParameter("course");
                preReg = null;
            }

            if (request.getParameterMap().containsKey("reg")) {
                preReg = request.getParameter("reg");
                //request.setAttribute("course", preCourse);

            } else if (request.getParameterMap().containsKey("CSV")) {
                /*MARK*/ createCSV(request, preCourse, getUniReg(preCourse), getUniDate(preCourse));

            }


        %>

        <div id="upleft" class="upleft">

            <form action="index.jsp" >

                <select  name="course" >
                    <%  for (int i = 0; i < list.size(); i++) {
                            String option = (String) list.get(i);
                    %>
                    <%if (option.equals(preCourse)) {%>  
                    <option selected="selected" value="<%= option%>"><%= option%> </option>
                    <% }%>
                    <option value="<%= option%>" ><%= option%> </option>
                    <% }%>
                </select>


                <input type="submit" value="SUBMIT">
                <input type="submit" name="CSV" value="CSV" >

                <br>
            </form>

        </div>
        <!--        <div id="CSV" >
        
                    <p><strong>Previous/Next</strong></p>
                    <form action="index.jsp">
                        <input type="submit" name="CSV" value="CSV" >
        
                    </form>
        
                </div>-->






        <%!/*
            private ArrayList<String> getUniReg(String course) {
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
             */

        %>

        <%

            ArrayList list2 = getUniReg(preCourse);

            previous = retPreviousReg(preReg, list2);
            next = retNextReg(preReg, list2);
            if (request.getParameterMap().containsKey("nextButton")) {
                preReg = next;
                previous = retPreviousReg(preReg, list2);
                next = retNextReg(preReg, list2);

            } else if (request.getParameterMap().containsKey("previousButton")) {
                preReg = previous;
                previous = retPreviousReg(preReg, list2);
                next = retNextReg(preReg, list2);

            }

        %>



        <div id="upright" class="upright">

            <form action="index.jsp">
                <select  name="reg" >
                    <%  for (int i = 0; i < list2.size(); i++) {

                            String option = (String) list2.get(i);
                            if (preReg == null) {
                                preReg = (String) list2.get(0);
                            }

                    %>
                    <%if (option.equals(preReg)) {%>

                    <option selected="selected" value="<%= option%>"><%= option%></option> 
                    <% }%>
                    <option value="<%= option%>"><%= option%></option>
                    <% }%>
                </select>

                <input type="submit" value="SUBMIT">
                <br>
            </form>

        </div>
        <div id="buttonbar" >

            <!--<p><strong>Previous/Next</strong></p>-->
            <form action="index.jsp">
                <input type="submit" name="previousButton" value="❮" >
                <input type="submit" name="nextButton" value="❯">
            </form>

            <br>
        </div>



        <%!            ArrayList<String> getAllUrl(String course, String reg) {

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

                } catch (SQLException ex) {

                }

                return urlList;
            }
        %>




        <div id="below">

            <%
                ArrayList<String> urlList = null;

                urlList = getAllUrl(preCourse, preReg);

                //System.out.print(urlList.size());
                for (int i = 0; i < urlList.size(); i++) {
                    String option = (String) urlList.get(i);
                    System.out.println("url---->>>=" + request.getContextPath() + option);

            %>

            <img src="<%=request.getContextPath()%><%=option%>" alt="Smiley face" width="108" height="192"> 

            <%}%>


        </div>







    </body>
</html>




