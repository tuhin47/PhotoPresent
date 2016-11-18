package alifalamin4.photopresent;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by alifa on 03-Oct-16.
 */
public class TakeAttendanceAdapter extends ArrayAdapter<TakeAttendance> {

    final String SERVER_URL=MainActivity.url;

    public TakeAttendanceAdapter(Context context, ArrayList<TakeAttendance> attendances) {
        super(context,0, attendances);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.attendance_list_item,parent,false);
        }

        TakeAttendance takeAttendance=getItem(position);

        final TextView reg=(TextView) convertView.findViewById(R.id.reg_text_view);
        reg.setText(takeAttendance.getReg().toString());

        CheckBox present=(CheckBox) convertView.findViewById(R.id.present_chbox);
        present.setChecked(takeAttendance.isPresent());

        System.out.println("ula"+present.isSelected());

        present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regNo=reg.getText().toString();
                CheckBox checkBox = (CheckBox) view;
                Boolean pres = checkBox.isChecked();
                String presentNum=null;
                if(pres==true) presentNum=SetDurationActivity.classTime;
                else presentNum=0+"";

                String URL = SERVER_URL+"?tag=present&"+"coursename="+CourseSelectionActivity.courseName+"&date="+
                        SelectDateActivity.dateOfAttendence+"&regNo="+regNo+"&present="+presentNum;
                System.out.println("ulala"+ URL);

                System.out.println("ulala"+"coursename="+CourseSelectionActivity.courseName+"&date="+
                        SelectDateActivity.dateOfAttendence+"&regNo="+regNo+"&present="+pres);



                GetXMLTask task = new GetXMLTask();
                task.execute(new String[] { URL });

            }
        });



        return convertView;


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
