package defaultPackage;


import defaultPackage.ReadDB;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVFile {

    public static void main(String[] args) throws FileNotFoundException {
        String courseName = "CSE202";
        createCSV(courseName, ReadDB.getUniReg(courseName), ReadDB.getUniDate(courseName));
        String dir = System.getProperty("user.dir");
        System.out.println("current dir = " + dir);
    }

    public static String createCSV(String courseName, ArrayList<String> regNum, ArrayList<String> date) throws FileNotFoundException {

        File myFile = new File(System.getProperty("user.dir") + "/" + courseName + ".csv");
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
            ArrayList<Integer> present = ReadDB.getAllPresent(courseName, reg);
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
    /*  public static String createCSV(String courseName, ArrayList<String> regNum, ArrayList<String> date) throws FileNotFoundException {
        //PrintWriter pw = new PrintWriter(new File(courseName + ".csv"));
        StringBuilder sb = new StringBuilder();

        sb.append("Registration_NO");
        sb.append(',');
        for (String x : date) {
            sb.append(x);
            sb.append(',');
        }
        sb.append("Total");
        sb.append('\n');

        for (String reg : regNum) {
            ArrayList<Integer> present = ReadDB.getAllPresent(courseName, reg);
            sb.append(reg);
            sb.append(',');
            int total = 0;
            for (Integer pp : present) {
                sb.append(pp);
                if (pp == 1) {
                    total++;
                }
                sb.append(',');
            }
            sb.append(total);
            sb.append('\n');

        }

//        System.out.println(sb.toString());
//        pw.write(sb.toString());
//        pw.close();
        System.out.println("done!");
        return sb.toString();

    }
     */
}
