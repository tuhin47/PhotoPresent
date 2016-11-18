package alifalamin4.photopresent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by alifa on 03-Oct-16.
 */
public class TakePhotoAttendanceAdapter extends ArrayAdapter<TakeAttendance> {


    public TakePhotoAttendanceAdapter(Context context, ArrayList<TakeAttendance> attendances) {
        super(context,0, attendances);


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.photo_attendance_list_item,parent,false);
        }

        TakeAttendance takeAttendance=getItem(position);

        TextView reg=(TextView) convertView.findViewById(R.id.photo_text_id);
        reg.setText(takeAttendance.getReg().toString());


        return convertView;
    }



}
