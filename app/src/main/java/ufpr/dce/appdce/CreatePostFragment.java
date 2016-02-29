package ufpr.dce.appdce;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreatePostFragment extends Fragment{
    // Progress Dialog
    private ProgressDialog pDialog;

    private EditText inputTitle;
    private TextView eventDateBeg;
    private TextView eventDateEnd;
    private TextView eventTimeBeg;
    private TextView eventTimeEnd;
    private EditText inputText;
    private EditText inputTags;
    private CheckBox cBoxEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_create_post, container, false);

        getActivity().setTitle("Novo Post");

        inputTitle = (EditText) rootView.findViewById(R.id.inputTitle);
        eventDateBeg = (TextView) rootView.findViewById(R.id.inputEventDateBeg);
        eventDateEnd = (TextView) rootView.findViewById(R.id.inputEventDateEnd);
        eventTimeBeg = (TextView) rootView.findViewById(R.id.inputEventTimeBeg);
        eventTimeEnd = (TextView) rootView.findViewById(R.id.inputEventTimeEnd);
        CheckBox cBoxReference = (CheckBox) rootView.findViewById(R.id.reference_post_box);
        cBoxEvent = (CheckBox) rootView.findViewById(R.id.event_box);
        inputText = (EditText) rootView.findViewById(R.id.inputText);
        inputTags = (EditText) rootView.findViewById(R.id.inputTags);
        Button btnCreateProduct = (Button) rootView.findViewById(R.id.btnCreatePost);

        cBoxReference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReferencInput(v);
            }
        });

        cBoxEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEventInputs(v);
            }
        });

        eventDateBeg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setPickerOwner(eventDateBeg);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        eventDateEnd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DatePickerFragment newFragment = new DatePickerFragment();
                newFragment.setPickerOwner(eventDateEnd);
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        eventTimeBeg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.setPickerOwner(eventTimeBeg);
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        eventTimeEnd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                TimePickerFragment newFragment = new TimePickerFragment();
                newFragment.setPickerOwner(eventTimeEnd);
                newFragment.show(getFragmentManager(), "timePicker");
            }
        });

        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean eventFieldsEmpty = cBoxEvent.getVisibility() == View.VISIBLE &&
                        ((eventDateBeg.getText().toString().equals("")) || (eventTimeBeg.getText().toString().equals("")) ||
                                (!eventDateEnd.getText().toString().equals("") && eventTimeEnd.getText().toString().equals("")) ||
                                (eventDateEnd.getText().toString().equals("") && !eventTimeEnd.getText().toString().equals(""))
                        );
                if (!inputTitle.getText().toString().equals("") &&
                        !inputText.getText().toString().equals("") &&
                        !eventFieldsEmpty){
                    createPost();
                }else{
                    Toast.makeText(getActivity(),
                            R.string.mandatory_field_missing, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    private void showReferencInput(View view){
        boolean checked = ((CheckBox) view).isChecked();

        assert getView()!=null;
        EditText postReferencia = (EditText) getView().findViewById(R.id.post_referencia);

        if (checked){
            postReferencia.setVisibility(View.VISIBLE);
        }else{
            postReferencia.setVisibility(View.GONE);
        }
    }

    private void showEventInputs(View view){
        boolean checked = ((CheckBox) view).isChecked();

        if (checked){
            eventDateBeg.setVisibility(View.VISIBLE);
            eventDateEnd.setVisibility(View.VISIBLE);
            eventTimeBeg.setVisibility(View.VISIBLE);
            eventTimeEnd.setVisibility(View.VISIBLE);
        }else{
            eventDateBeg.setVisibility(View.GONE);
            eventDateEnd.setVisibility(View.GONE);
            eventTimeBeg.setVisibility(View.GONE);
            eventTimeEnd.setVisibility(View.GONE);
        }
    }

    private void createPost(){
        Map<String, String> map = new HashMap<>();

        map.put("title",inputTitle.getText().toString());
        map.put("text",inputText.getText().toString());
        if (!inputTags.getText().toString().equals(""))
            map.put("tags", inputTags.getText().toString());
        map.put("userid", "1");
        map.put("orgid", "1");

        if (cBoxEvent.isChecked()){
            String sEventDateBeg = eventDateBeg.getText().toString();
            String sEventTimeBeg = eventTimeBeg.getText().toString();
            String sEventDateEnd = eventDateEnd.getText().toString();
            String sEventTimeEnd = eventTimeEnd.getText().toString();

            if (!(sEventDateBeg.equals("") || sEventTimeBeg.equals(""))){
                try {
                    SimpleDateFormat formatterApp = new SimpleDateFormat("d/M/yyyy H:m", Locale.getDefault());
                    SimpleDateFormat formatterBD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                    Date parsedDateBeg = formatterApp.parse(sEventDateBeg + " " + sEventTimeBeg);
                    String sEventDateTimeBeg = formatterBD.format(parsedDateBeg);

                    map.put("event_date", sEventDateTimeBeg);
                    if (!(sEventDateEnd.equals("") || sEventTimeEnd.equals(""))) {
                        Date parsedDateEnd = formatterApp.parse(sEventDateEnd + " " + sEventTimeEnd);

                        if(parsedDateBeg.before(parsedDateEnd)) {
                            String sEventDateTimeEnd = formatterBD.format(parsedDateEnd);

                            map.put("event_date_end", sEventDateTimeEnd);
                        }else{
                            Toast.makeText(getActivity(),
                                    "A data de final deve ser após a data de início",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }catch (ParseException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Um erro ocorreu D=", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        Log.d("title", inputTitle.getText().toString());
        Log.d("text", inputText.getText().toString());
        Log.d("tags", inputTags.getText().toString());

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        String url = Util.SERVER_IP + "create_post.php";

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Creating Post..");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        // Request a string response from the provided URL.
        CustomRequest jsObjRequest = new CustomRequest
                (Request.Method.POST, url, map, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();

                        try {
                            Toast.makeText(getActivity(), response.get("message").toString(),
                                    Toast.LENGTH_SHORT).show();

                            if(response.get("success").toString().equals("1")){

                                Intent intent = OpenPostActivity.newOpenPostActivity(getActivity(),
                                        response.get("postid").toString());

                                startActivity(intent);
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsObjRequest);
    }


    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        private TextView pickerOwner;

        public void setPickerOwner(TextView pickerOwner){
            this.pickerOwner = pickerOwner;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            pickerOwner.setText(new StringBuilder().append(hourOfDay).
                    append(":").append(minute));
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        private TextView pickerOwner;

        public void setPickerOwner(TextView pickerOwner){
            this.pickerOwner = pickerOwner;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            pickerOwner.setText(new StringBuilder().append(day).
                    append("/").append(month + 1).append("/").append(year));
        }
    }
}