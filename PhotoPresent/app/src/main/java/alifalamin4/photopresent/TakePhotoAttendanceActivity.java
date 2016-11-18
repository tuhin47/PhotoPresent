package alifalamin4.photopresent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * Created by alifa on 02-Oct-16.
 */
public class TakePhotoAttendanceActivity extends AppCompatActivity{
    static  final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView mimageview;
    private String[] regNum=null;
    ListView studentphotolistview;
    ArrayList<TakeAttendance> attendance=new ArrayList<TakeAttendance>();
    public static final String Server_Url=MainActivity.url;
    public String regNo=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.samplestudent);


        getdata();

        //istAdapter studentAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,rollnumbers);






    }

    private void getdata() {

        String url = MainActivity.url;

        Log.d("123", url);

        final ProgressDialog progress;
//        progress = ProgressDialog.show(this, "dialog title",
//                "dialog message", true);



        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        progress.dismiss();
                        /*Toast.makeText(MainActivity.this,"hello "+response,Toast.LENGTH_LONG).show();


                        if(response.equals(null)){
                            System.out.println("Hello");
                        }else{
                            System.out.println("Hi");
                        }*/


                        //Log.d("123", response.toString());

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            regNum = new String[jsonArray.length()];
                            System.out.println("ulala"+regNum);
                            for(int i=0;i<jsonArray.length();i++){
                                attendance.add(new TakeAttendance(Integer.parseInt(jsonArray.getJSONObject(i).get(i+"").toString())));
//                                regNum[i]  = jsonArray.getJSONObject(i).get(i+"").toString();
//                                System.out.println("ulala"+regNum[i]);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                        final TakePhotoAttendanceAdapter photoadapter=new TakePhotoAttendanceAdapter(TakePhotoAttendanceActivity.this, attendance);

                        studentphotolistview=(ListView) findViewById(R.id.studentlistview1);
                        studentphotolistview.setAdapter(photoadapter);

                        if(!hasCamera()) studentphotolistview.setEnabled(false);

                        studentphotolistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                Object listItem = studentphotolistview.getItemAtPosition(pos);
                                regNo=listItem.toString();

                                launchCamera(view);
                            }
                        });



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
                params.put("tag", "returnReg");
                params.put("coursename",CourseSelectionPhotoActivity.courseName);
                params.put("date",SelectPhotoDateActivity.dateOfAttendence);/// mark

                System.out.println("Ula"+CourseSelectionActivity.courseName+" Date="+SelectDateActivity.dateOfAttendence);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
    public void launchCamera(View view){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK ){
            Bundle extras=data.getExtras();
            Bitmap photo =(Bitmap) extras.get("data");

            String courseName=CourseSelectionPhotoActivity.courseName;
            String date=SelectPhotoDateActivity.dateOfAttendence;
            String classTime=SetDurationPhotoActivity.classTime;
            date=date.replaceAll("/","s");
            String reg=regNo;
            String photoName=classTime+"sep"+courseName+"sep"+regNo+"sep"+date;
            System.out.println("ulala"+photoName+"ualal");

            File f = new File(getCacheDir(), photoName+".jpg");
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

//Convert bitmap to byte array
            Bitmap bitmap = photo;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);

            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            sendImage(f);


        }
    }

    private void sendImage(File f) {
        RequestParams params = new RequestParams();
        try {
            params.put("File", f);

        } catch(FileNotFoundException e) {}


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(MainActivity.urlImage, params, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                // called before request is started
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

            }
            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });


    }


}
