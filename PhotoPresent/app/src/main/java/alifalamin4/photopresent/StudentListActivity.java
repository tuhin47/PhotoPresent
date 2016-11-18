package alifalamin4.photopresent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by alifa on 02-Oct-16.
 */
public class StudentListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.couresnames);
        setContentView(R.layout.courselayout);


        String[] coursename={"cse133 Structured programming language",
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

        ListAdapter courseadapter= new ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item,coursename);
        ListView courseview= (ListView) findViewById(R.id.course_list_view);
        courseview.setAdapter(courseadapter);


    }
}
