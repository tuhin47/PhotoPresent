
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVFile {

    public static void main(String[] args) throws FileNotFoundException {
        createCSV("EEE", ReadDB.getUniReg("EEE"), ReadDB.getUniDate("EEE"));
    }

    public static String createCSV(String courseName, ArrayList<String> regNum, ArrayList<String> date) throws FileNotFoundException {
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
}
