package alifalamin4.photopresent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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
 * Created by alifa on 03-Oct-16.
 */
public class AddDroppersActivity extends AppCompatActivity {


    private ArrayAdapter<String> courseadapter=null;
    private ListView courseview=null;
    String[] coursename=null;
    public static String courseName=null;

    public String[] getCourseData(){
        String[] data=null;
        return  data;

    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.coures);
        setContentView(R.layout.courselayout);

        courseview= (ListView) findViewById(R.id.course_list_view);

        getData();



    /*    String[] coursename=  {"cse133 Structured programming language",
                "cse335 Operating System",
                "cse351 Management Information System",
                "cse365 Communication Engineering",
                "cse137 Data Structure",
                "cse237 Algorithm Design and Analysis",
                "cse233 Object Oriented Programming",

                "cse239 Numerical Analysis",
                "cse143 Discrete Mathematics",
                "cse243 Theory of Computation and Concrete Mathematics",
                "cse333 Database System Lab"};
    */









    }

    private void getData() {
        String url = MainActivity.url;

        Log.d("123", url);

        final ProgressDialog progress;
        progress = ProgressDialog.show(this, "dialog title",
                "please wait", true);



        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        progress.dismiss();
                        /*Toast.makeText(MainActivity.this,"hello "+response,Toast.LENGTH_LONG).show();


                        if(response.equals(null)){
                            System.out.println("Hello");
                        }else{
                            System.out.println("Hi");
                        }*/

                        JSONArray jsonArray=null;

                        Log.d("123", response.toString());
                        try {
                            jsonArray = new JSONArray(response);
                            coursename = new String[jsonArray.length()];
                            for(int i=0;i<jsonArray.length();i++){
                                coursename[i]  = jsonArray.getJSONObject(i).get(i+"").toString();
                                System.out.println("ulala"+coursename[i]);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        courseadapter= new ArrayAdapter<String>(AddDroppersActivity.this,android.R.layout.simple_selectable_list_item,coursename);
                        courseview.setAdapter(courseadapter);
                        courseview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                                Object listItem = courseview.getItemAtPosition(pos);
                                courseName=listItem.toString();
                                System.out.println("ula"+courseName);

                                // String courseName=(String) ((ListView) courseview.getSource()).getSelectionModel().getSelectedItem();
                                Intent intent = new Intent(AddDroppersActivity.this,SendNewRegistrationActivity.class);
                                startActivity(intent);


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
                params.put("tag", "get");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }


}



