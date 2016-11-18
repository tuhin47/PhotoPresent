package alifalamin4.photopresent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.os.AsyncTask;
import android.widget.TextView;


/**
 * Created by alifa on 29-Sep-16.
 */


public class CreateClassActivity extends AppCompatActivity {

    TextView outputText;
    public static String courseCode = "CSE333";
    public static  String SERVER_URL = MainActivity.url;


    private String strRet(String str){
        str= str.replaceAll(" ","_");
        //System.out.println("Ulala"+str);
        return str;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createclassfile);
        getSupportActionBar().setTitle(R.string.createcouses);
        Button mCreate;

        mCreate = (Button) findViewById(R.id.createdonebutton);



        mCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText mCourse = (EditText) findViewById(R.id.editcourse);




                EditText mLastRollNumber = (EditText) findViewById(R.id.firstrollnumber);
                if (mCourse.length() != 0 && mLastRollNumber.length() != 0) {
                    String URL = SERVER_URL+"?tag=class&"+"courseCode="+strRet(""+mCourse.getText())+"&lastRegNum="+strRet(""+mLastRollNumber.getText());
                    GetXMLTask task = new GetXMLTask();
                    task.execute(new String[] { URL });
                    Intent intent = new Intent(CreateClassActivity.this,SendingClassActivity.class);
                    startActivity(intent);

                }


            }
        });


    }

    private class GetXMLTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String output = null;
            for (String url : urls) {
                output = getOutputFromUrl(url);
            }
            return output;
        }

        private String getOutputFromUrl(String url) {
            StringBuffer output = new StringBuffer("");
            try {
                InputStream stream = getHttpConnection(url);
                BufferedReader buffer = new BufferedReader(
                        new InputStreamReader(stream));
                String s = "";
                while ((s = buffer.readLine()) != null)
                    output.append(s);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return output.toString();
        }

        private InputStream getHttpConnection(String urlString) throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("POST");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }

        @Override
        protected void onPostExecute(String output) {

           // outputText.setText(output);
        }
    }


}
