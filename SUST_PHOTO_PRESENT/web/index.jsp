

<%@page import="defaultPackage.CSVFile"%>
<%@page import="defaultPackage.ReadDB"%>
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
            static String preCourse, preReg = null, next = null, previous = null;

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

        %>
        <%
            ArrayList list = ReadDB.getUniCourse();
            if (request.getParameterMap().containsKey("course")) {
                preCourse = request.getParameter("course");
                preReg = null;
            }

            if (request.getParameterMap().containsKey("reg")) {
                preReg = request.getParameter("reg");
                //request.setAttribute("course", preCourse);

            } else if (request.getParameterMap().containsKey("CSV")) {
                /*MARK*/ CSVFile.createCSV(preCourse, ReadDB.getUniReg(preCourse), ReadDB.getUniDate(preCourse));

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






        <%

            ArrayList list2 = ReadDB.getUniReg(preCourse);

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


        <div id="below">

            <%
                ArrayList<String> urlList = null;

                urlList = ReadDB.getAllUrl(preCourse, preReg);

                //System.out.print(urlList.size());
                for (int i = 0; i < urlList.size(); i++) {
                    String option = (String) urlList.get(i);

                    int ins = option.indexOf("/Photo/");
                    option = option.substring(ins + 1);


            %>

            <img src="<%=request.getContextPath()%><%=option%>" alt="Smiley face" width="108" height="192"> 

            <%}%>


        </div>







    </body>
</html>




