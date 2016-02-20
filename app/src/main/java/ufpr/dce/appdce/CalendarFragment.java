package ufpr.dce.appdce;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarFragment extends Fragment{
    public static final String DAY_SELECTED = "DAY_SELECTED";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.content_calendar, container, false);


        Calendar cal = Calendar.getInstance();

        String currentDay = String.valueOf(cal.get(Calendar.MONTH)) +
                "/" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        int currentMonth = cal.get(Calendar.MONTH);

        // the get() in the middle computes the whole calendar to the first day of the month.
        // without it, the day would then be set to be the first day of the CURRENT week
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.get(Calendar.DATE);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());


        ArrayList<String> calendarDays = new ArrayList<>();
        while (cal.get(Calendar.DAY_OF_WEEK) != 1 ||
                cal.get(Calendar.MONTH) <= currentMonth){
            String dayWMonth = String.valueOf(cal.get(Calendar.MONTH)) +
                    "/" + String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
            calendarDays.add(dayWMonth);

            cal.add(Calendar.DATE, 1);
        }

        CustomAdapter calendarAdapter;

        Bundle args = getArguments();
        if (args != null){
            int  daySelected;
            daySelected = args.getInt(DAY_SELECTED);
            calendarAdapter = new CustomAdapter(getActivity(),
                    calendarDays, currentDay, daySelected);
        }else{
            calendarAdapter = new CustomAdapter(getActivity(),
                    calendarDays, currentDay);
        }

        GridView calendarView = (GridView) rootView.findViewById(R.id.calendar_view);
        calendarView.setAdapter(calendarAdapter);

        return rootView;
    }

    public class CustomAdapter extends BaseAdapter{
        Context context;
        ArrayList<String> calendarDays;
        String currentDay;
        String currentMonth;
        int daySelected = 0;


        public CustomAdapter(Context context, ArrayList<String> calendarDays, String currentDay){
            this.context = context;
            this.calendarDays = calendarDays;

            String[] dayMonth = currentDay.split("/");
            this.currentMonth = dayMonth[0];
            this.currentDay = dayMonth[1];
        }

        public CustomAdapter(Context context, ArrayList<String> calendarDays, String currentDay, int daySelected){
            this.context = context;
            this.calendarDays = calendarDays;
            this.daySelected = daySelected;

            String[] dayMonth = currentDay.split("/");
            this.currentMonth = dayMonth[1];
            this.currentDay = dayMonth[0];
        }

        public int getCount() {
            return calendarDays.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position,
                            View convertView, ViewGroup parent){
            LinearLayout dayLayout = (LinearLayout) convertView;

            if (dayLayout == null) {
                TextView dateDay = new TextView(getActivity());

                dayLayout = new LinearLayout(context);
                dayLayout.addView(dateDay);

                GridView.LayoutParams dayLayoutParams = new GridView.LayoutParams(300, 300);
                dayLayout.setLayoutParams(dayLayoutParams);

                dayLayout.setId(ViewIdGenerator.generateViewId());
            }

            String dayMonth[] = calendarDays.get(position).split("/");

            String day = dayMonth[1];

            View childView = dayLayout.getChildAt(0);
            if (childView instanceof TextView){
                ((TextView) childView).setText(String.valueOf(day));
            }

            String month = dayMonth[0];
            if (month.equals(currentMonth)) {
                if (day.equals(String.valueOf(daySelected))){
                    dayLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey3));
                }else {
                    dayLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey2));
                }
            }else{
                dayLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey1));
            }

            return dayLayout;
        }
    }
}
