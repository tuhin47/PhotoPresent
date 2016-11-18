package alifalamin4.photopresent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alifa on 15-Oct-16.
 */
public class SelectPhotoDateActivity extends AppCompatActivity {

    public static String dateOfAttendence=null;

    public static  String SERVER_URL = MainActivity.url;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectdate);

        Button setDate;

        setDate = (Button) findViewById(R.id.setdate);
        getReg();


        setDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("Ulala" + CourseSelectionPhotoActivity.courseName);
                DatePicker datePicker=(DatePicker) findViewById(R.id.datePicker);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth()+1;
                int year =  datePicker.getYear();

                String date=year+"/";
                if(month<10) date+="0";
                date+=month+"/";
                if(day<10) date+="0";
                date+=day;
                dateOfAttendence=date;
                System.out.println("ulala"+dateOfAttendence);


                String URL = SERVER_URL+"?tag=reg&"+"coursename="+CourseSelectionPhotoActivity.courseName+"&date="+dateOfAttendence;
                GetXMLTask task = new GetXMLTask();
                task.execute(new String[] { URL });
                Intent intent = new Intent(SelectPhotoDateActivity.this,TakePhotoAttendanceActivity.class);
                startActivity(intent);
            }

        });

       /* Button msetdate=(Button) findViewById(R.id.setdate);

        msetdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String coursename=CourseSelectionActivity.courseName;
                System.out.println("Ulala"+coursename);



                //System.out.println("ulala"+date);

                if (coursename.length() != 0 && date.length() != 0) {
                    String URL = SERVER_URL+"tag=reg&"+"coursename="+coursename+"&date="+date;
                    GetXMLTask task = new GetXMLTask();
                    task.execute(new String[] { URL });
//                    Intent intent = new Intent(SelectDateActivity.this,TakeAttendanceActivity.class);
//                    startActivity(intent);

                }



            }
        });*/

    }



//    public static String getDateFromDatePicker(DatePicker datePicker){
//        int day = datePicker.getDayOfMonth();
//        int month = datePicker.getMonth()+1;
//        int year =  datePicker.getYear();
//
//        String todaysdate =day+" "+month+" "+ year;
//
//        return todaysdate ;
//    }

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
                httpConnection.setRequestMethod("GET");
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
    private void getReg() {
        String url = "http://192.168.0.100:8084/sust_photo_present/photopresent";

        Log.d("123", url);

        final ProgressDialog progress;
//        progress = ProgressDialog.show(this, "dialog title",
//                "dialog message", true);



        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("tag", "reg");
                params.put("coursename",CourseSelectionPhotoActivity.courseName);
                params.put("date",date);/// mark
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }


}
