package alifalamin4.photopresent;

import android.content.Intent;
import android.os.AsyncTask;
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

/**
 * Created by alifa on 05-Nov-16.
 */
public class SendNewRegistrationActivity extends AppCompatActivity {
    public static String Server_url=MainActivity.url;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendnewreg);

        final EditText mnewReg = (EditText) findViewById(R.id.enterregis);
        Button maddStudent=(Button) findViewById(R.id.addStudent);
        assert maddStudent != null;
        maddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String course=mnewReg.getText()+"";
                if (course!=null) {
                    String URL = Server_url+"?tag=dropper&"+"coursename="+AddDroppersActivity.courseName+"&regNo="+mnewReg.getText()+"";
                    GetXMLTask task = new GetXMLTask();
                    task.execute(new String[] { URL });
                    Intent intent = new Intent(SendNewRegistrationActivity.this,NewStudentAddedActivity.class);
                    startActivity(intent);

                }
            }
        });


    }
    class GetXMLTask extends AsyncTask<String, Void, String> {
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
