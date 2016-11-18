package alifalamin4.photopresent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by alifa on 11-Nov-16.
 */
public class SetDurationPhotoActivity extends AppCompatActivity {

    public static String classTime=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duraitionofclass);

        final EditText mtext=(EditText) findViewById(R.id.classtime);
        Button mSetDuration=(Button) findViewById(R.id.setduration);



        mSetDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mtext.getText()!=null){
                    classTime=mtext.getText()+"";
                    Intent intent = new Intent(SetDurationPhotoActivity.this,SelectPhotoDateActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
