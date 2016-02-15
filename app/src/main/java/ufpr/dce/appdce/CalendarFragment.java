package ufpr.dce.appdce;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class CalendarFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.content_calendar, container, false);



        GridView calendarView = (GridView) rootView.findViewById(R.id.calendar_view);

        return rootView;
    }
}
