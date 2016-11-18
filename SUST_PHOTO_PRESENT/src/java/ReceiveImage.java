/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author alifa
 */
@WebServlet(urlPatterns = {"/receiveimage"})
public class ReceiveImage extends HttpServlet {

    private boolean isMultipart;

    private String filePath = "c:\\temp";
    private int maxFileSize = 1000000 * 1024;
    private int maxMemSize = 1000000 * 1024;
    private File file;

    private static final long serialVersionUID = 1L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReceiveImage</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReceiveImage at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        //processRequest(request, response);

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(isMultipart = ServletFileUpload.isMultipartContent(request));

        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("c:\\temp"));

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                System.out.println(fi.isFormField());
                if (fi.isFormField()) {
                    System.out.println("Got a form field: " + fi.getFieldName() + " " + fi);
                } else {
                    // Get the uploaded file parameters
                    //System.out.println(fi.getString("Command"));

                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();

                    System.out.println(fieldName);
                    System.out.println(fileName);
                    String courseHour, course, regNo, date, temp = fileName;
                    int j = temp.indexOf("sep");

                    courseHour = temp.substring(0, j);
                    temp = temp.substring(j + 3);

                    j = temp.indexOf("sep");

                    course = temp.substring(0, j);

                    temp = temp.substring(j + 3);

                    j = temp.indexOf("sep");

                    regNo = temp.substring(0, j);

                    date = temp.substring(j + 3);
                    date = date.replaceAll("s", "-");

                    System.out.println("ualal" + courseHour + course + regNo + date);

                    System.out.println(contentType);

                    long sizeInBytes = fi.getSize();
                    // Write the file

                    String uploadFolder = getServletContext().getRealPath("") + "Photo\\" + course + "\\" + regNo + "\\";
//                    String uploadFolder =  "\\SUST_PHOTO_PRESENT\\Photo\\" + course + "\\" + regNo + "\\";

                    uploadFolder = uploadFolder.replace("\\build", "");
                    Path path = Paths.get(uploadFolder);
                    //if directory exists?
                    if (!Files.exists(path)) {
                        try {
                            Files.createDirectories(path);
                        } catch (IOException e) {
                            //fail to create directory
                            e.printStackTrace();
                        }
                    }
                    //uploadFolder+= "\\"+date+".jpg";   
                    System.out.println(fileName);
                    System.out.println(uploadFolder);

//               
                    file = new File(uploadFolder + date);

                    date = date.replaceAll("-", "/");
                    date = date.substring(0, 10);

                    String total_url = uploadFolder + date.replaceAll("/", "-") + ".jpg";
                    System.out.println(total_url);

                    String formattedUrl = total_url.replaceAll("\\\\", "/");

                    System.out.println(formattedUrl.substring(38));

                    System.out.println("-------------->>>>>>>>" + course + "  " + date + regNo + total_url);

                    AddUser.updateUrl(courseHour, course, date, regNo, formattedUrl.substring(38));
                    fi.write(file);

//                    System.out.println("-------------->>>>>>>>" +course+"  "+ date+ regNo+total_url);
//                   try {
//            SavePhoto.saveIMG("CSE", "2016", "tada","F:\\1.png");
//        } catch (IOException ex) {
//            Logger.getLogger(SavePhoto.class.getName()).log(Level.SEVERE, null, ex);
//        }
                    System.out.println(uploadFolder);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
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
