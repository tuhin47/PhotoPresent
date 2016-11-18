
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/photopresent"})
public class PhotoPresent extends HttpServlet {

//     list;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

//        System.out.println("swoing url---->>>>"+request.getRequestURI());
        PrintWriter out = response.getWriter();

        System.out.println("Here is the tag=" + request.getParameter("tag"));
        boolean condition = false;

        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
//            System.out.println("here is the ==" + params.nextElement());

            if (params.nextElement().endsWith("tag")) {
                condition = true;
                break;
            }

        }

        if (condition == true) {
            if (request.getParameter("tag").equals("get")) {
                String data = ReadDB.getUniData();
//                System.out.println(data);
                out.println(data);
            } else if (request.getParameter("tag").equals("class") && request.getParameter("courseCode") != null && request.getParameter("lastRegNum") != null) {
                String courseCode = request.getParameter("courseCode");
                String lastRegNum = request.getParameter("lastRegNum");
                System.out.println("COURSE CODE: " + courseCode + " LAST REG NUM: " + lastRegNum);
                int mlastreg = Integer.parseInt(lastRegNum);
                AddUser.mAddUser(courseCode, mlastreg);
                System.out.println("Print last registration : " + mlastreg);
            } else if (request.getParameter("tag").equals("reg")) {
                String coursename = request.getParameter("coursename");
                String date = request.getParameter("date");
                System.out.println(coursename + "  " + date);
                ArrayList<String> list = ReadDB.getUniReg(coursename);
                //String str=ReadDB.arrayListToString(list);

                if (!ConditionHandle.isPreviouslyExist(coursename, date)) {
                    AddUser.viewAllUser(list, coursename, date);
                }

            } else if (request.getParameter("tag").equals("returnReg")) {
                String coursename = request.getParameter("coursename");
                String date = request.getParameter("date");

                ArrayList<String> list = ReadDB.getUniReg(coursename);

                String str = ReadDB.arrayListToString(list);

//                str = ReadDB.getUniReg(coursename, date);
                out.print(str);

            } else if (request.getParameter("tag").equals("present")) {
                String courseName = request.getParameter("coursename");
                String regNo = request.getParameter("regNo");
                String date = request.getParameter("date");
                String present = request.getParameter("present");
//                boolean present = false;
//                if (b.equals("true")) {
//                    present = true;
//                }

                System.out.println(courseName + regNo + date + present);

                AddUser.updatePresent(courseName, date, regNo, present);

            } else if (request.getParameter("tag").equals("CSV")) {

                String course = request.getParameter("coursename");
                ArrayList<String> regNum = ReadDB.getUniReg(course);
                ArrayList<String> date = ReadDB.getUniDate(course);
                String output = CSVFile.createCSV(course, regNum, date);
                out.print(output);

            } else if (request.getParameter("tag").equals("dropper")) {
                String course = request.getParameter("coursename");
                String regNo = request.getParameter("regNo");
                System.err.println(course + " " + regNo);

                AddUser.addDropper(course, regNo);
                // out.print("done");

            } else if (request.getParameter("tag").equals("deleteclass")) {
                String course = request.getParameter("coursename");
                System.out.println("delete="+course);
               AddUser.deleteCourse(course);

            } else {
                System.out.println("COURSE CODE NOT FOUND!");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        //

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
