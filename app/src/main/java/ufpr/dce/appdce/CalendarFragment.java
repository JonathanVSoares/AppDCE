package ufpr.dce.appdce;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.ui.TwoWayGridView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

public class CalendarFragment extends Fragment{
    public static final String DAY_SELECTED = "DAY_SELECTED";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.content_calendar, container, false);


        Calendar cal = Calendar.getInstance();

        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        int currentMonth = cal.get(Calendar.MONTH);

        // the get() in the middle computes the whole calendar to the first day of the month
        // without it, the day would then be set to be the first day of the CURRENT week
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.get(Calendar.DATE);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());


        ArrayList<String> calendarDays = new ArrayList<>();
        while (cal.get(Calendar.DAY_OF_WEEK) != 1 ||
                cal.get(Calendar.MONTH) <= currentMonth){
            Log.d("days", String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
            calendarDays.add(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
            cal.add(Calendar.DATE, 1);
        }

        Bundle args = getArguments();
        int  daySelected = currentDay;
        if (args != null){
            daySelected = args.getInt(DAY_SELECTED);
        }

        CustomAdapter calendarAdapter = new CustomAdapter(getActivity(), calendarDays, currentDay, daySelected);

        TwoWayGridView calendarView = (TwoWayGridView) rootView.findViewById(R.id.calendar_view);
        calendarView.setAdapter(calendarAdapter);

        return rootView;
    }

    public class CustomAdapter extends BaseAdapter{
        Context context;
        Vector<String> viewsIds = new Vector<>();
        ArrayList<String> calendarDays;
        int currentDay;
        int daySelected;
        boolean currentMonth = false;

        public CustomAdapter(Context context, ArrayList<String> calendarDays, int currentDay, int daySelected){
            this.context = context;
            this.calendarDays = calendarDays;
            this.currentDay = currentDay;
            this.daySelected = daySelected;
        }

        public int getCount() {
            return calendarDays.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return Integer.parseInt(viewsIds.get(position));
        }

        public View getView(int position,
                            View convertView, ViewGroup parent){
            LinearLayout dayLayout = (LinearLayout) convertView;

            if (dayLayout == null) {

                int day = Integer.parseInt(calendarDays.get(position));

                if (day == 1) {
                    currentMonth = !currentMonth;
                }


                TextView dateDay = new TextView(getActivity());
                dateDay.setText(String.valueOf(day));

                dayLayout = new LinearLayout(context);
                LinearLayout.LayoutParams dayLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //dayLayout.setLayoutParams(dayLayoutParams);
                if (currentMonth) {
                    dayLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey2));
                }

                dayLayout.addView(dateDay);
                dayLayout.setId(ViewIdGenerator.generateViewId());
                Log.d("Appdce", String.valueOf(dayLayout.getId()));
                viewsIds.add(position, String.valueOf(dayLayout.getId()));
            }else{
                Log.d("testing", String.valueOf(position));
            }

            return dayLayout;
        }
    }
}
