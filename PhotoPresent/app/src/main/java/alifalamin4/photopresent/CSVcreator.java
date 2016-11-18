package alifalamin4.photopresent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * Created by alifa on 06-Nov-16.
 */
public class CSVcreator {


    private static FileWriter mFileWriter;
    static CSVWriter writer;

    public static void csvCreator(String data){
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "AnalysisData.csv";
        String filePath = baseDir + File.separator + fileName;
        File f = new File(filePath );

// File exist
        try{
        if(f.exists() && !f.isDirectory()){
            mFileWriter = new FileWriter(filePath , true);
            writer = new CSVWriter(mFileWriter);
        }
        else {
            writer = new CSVWriter(new FileWriter(filePath));
        }}
        catch(Exception e){}
        //String[] data = {"Ship Name","Scientist Name", "...",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").formatter.format(date)});

        writer.writeNext(data);

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
