package alifalamin4.photopresent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alifa on 02-Oct-16.
 */
public class TakeAttendanceActivity extends AppCompatActivity{
    private String[] regNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle(R.string.attendancenam);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.samplestudent);

        //istAdapter studentAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,rollnumbers);
        getReg();





    }
    private void getReg() {
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
                        ArrayList<TakeAttendance> attendance=new ArrayList<TakeAttendance>();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            regNum = new String[jsonArray.length()];
                            for(int i=0;i<jsonArray.length();i++){
                                attendance.add(new TakeAttendance(Integer.parseInt(jsonArray.getJSONObject(i).get(i+"").toString()),false));
//                                regNum[i]  = jsonArray.getJSONObject(i).get(i+"").toString();
//                                System.out.println("ulala"+regNum[i]);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                        final TakeAttendanceAdapter adapter=new TakeAttendanceAdapter(TakeAttendanceActivity.this, attendance);

                        ListView studentlistview=(ListView) findViewById(R.id.studentlistview1);
                        studentlistview.setAdapter(adapter);

//                        studentlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                            @Override
//                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
//                                TakeAttendance t= (TakeAttendance) adapterView.getItemAtPosition(pos);
//                                CheckBox cb = (CheckBox) view.getTag();
//                                cb.setChecked(true);
//
//
//
//                            }
//                        });



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
                params.put("coursename",CourseSelectionActivity.courseName);
                params.put("date",SelectDateActivity.dateOfAttendence);/// mark


                System.out.println("Ula"+CourseSelectionActivity.courseName+" Date="+SelectDateActivity.dateOfAttendence);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }


}
