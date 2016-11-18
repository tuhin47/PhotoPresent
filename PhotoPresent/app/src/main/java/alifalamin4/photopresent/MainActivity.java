package alifalamin4.photopresent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static String server="http://10.100.6.151";
    public static  String url = server+":8084/sust_photo_present/photopresent";
    public static  String urlImage=server+":8084/sust_photo_present/receiveimage";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(R.string.firstname);



        Button createclassbutton=(Button) findViewById(R.id.createclass);
        createclassbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateClassActivity.class);
                startActivity(intent);
            }
        });
        Button mtakephotoattendance=(Button) findViewById(R.id.takephotoattendance);

        mtakephotoattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CourseSelectionPhotoActivity.class);
                startActivity(intent);
            }
        });


        Button mstudentlist=(Button)findViewById(R.id.studentlist);
        mstudentlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StudentListActivity.class);
                startActivity(intent);
            }
        });
        Button mtakeattendance=(Button) findViewById(R.id.takeattendance);

        mtakeattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CourseSelectionActivity.class);
                startActivity(intent);
            }
        });

        Button getdata=(Button) findViewById(R.id.getdata);
        getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,GetDataActivity.class);
                startActivity(intent);
            }
        });
        Button maddStudent=(Button) findViewById(R.id.studentlist);

        maddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddDroppersActivity.class);
                startActivity(intent);
            }
        });

        Button about=(Button) findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });

        Button removeclass=(Button) findViewById(R.id.removeclass);
        removeclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,DeleteClassActivity.class);
                startActivity(intent);
            }
        });
    }


}
