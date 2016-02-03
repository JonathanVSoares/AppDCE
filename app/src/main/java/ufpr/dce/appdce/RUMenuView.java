package ufpr.dce.appdce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RUMenuView extends AppCompatActivity{
    String[][][] menu = new String[7][3][7];

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Calendar cal = Calendar.getInstance();
        int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR) - 1;
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        cal.add(Calendar.DATE, - dayOfWeek + 1);

        for (int counterDayOfWeek = 1; counterDayOfWeek <= 7; counterDayOfWeek++){
            createMenuTitlesView(cal, dayOfWeek);

            cal.add(Calendar.DATE, + 1);
        }

        RUAllMenus allMenus = new RUAllMenus();
        RUWeekMenu weekMenu = allMenus.getMenu(weekOfYear);
        menu = weekMenu.getMenu();


        LinearLayout breakfastView = (LinearLayout) findViewById (R.id.breakfastView);

        for (int counterDayOfWeek = 0; counterDayOfWeek < 7; counterDayOfWeek++)
            createMenuView(breakfastView, counterDayOfWeek, 0, dayOfWeek);


        LinearLayout lunchView = (LinearLayout) findViewById (R.id.lunchView);

        for (int counterDayOfWeek = 0; counterDayOfWeek < 7; counterDayOfWeek++)
            createMenuView(lunchView, counterDayOfWeek, 1, dayOfWeek);


        LinearLayout dinnerView = (LinearLayout) findViewById (R.id.dinnerView);

        for (int counterDayOfWeek = 0; counterDayOfWeek < 7; counterDayOfWeek++)
            createMenuView(dinnerView, counterDayOfWeek, 2, dayOfWeek);


    }

    private void createMenuView(LinearLayout foodTime, int dayOfWeek, int timeOfDay, int todaysDayOfWeek){

        StringBuilder stringBuilder = new StringBuilder();

        for (String menuItem : menu[dayOfWeek][timeOfDay])
        {
            if (menuItem != null)
            {
                stringBuilder.append(menuItem);
                stringBuilder.append("\n");
            }
        }

        TextView textView = new TextView(this);
        textView.setText(stringBuilder.toString());
        textView.setTextSize(14);

        if (dayOfWeek == todaysDayOfWeek - 1)
        {
            textView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrey2));
        }
        else
        {
            textView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrey3));
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 3.0f);
        params.setMargins(2, 1, 2, 1);

        textView.setLayoutParams(params);

        foodTime.addView(textView);
    }

    private void createMenuTitlesView(Calendar cal, int todaysDayOfWeek){

        DateFormat dateFormat = new SimpleDateFormat("dd/MM");
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);


        int dayOfWeekId = getResources().getIdentifier("week_day_name" + dayOfWeek, "string", this.getPackageName());

        String dayOfWeekName = getResources().getString(dayOfWeekId);

        TextView textView = new TextView(this);


        textView.setText(dateFormat.format(cal.getTime()) + "\n" + dayOfWeekName);
        textView.setTextSize(16);

        if (dayOfWeek == todaysDayOfWeek)
        {
            textView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrey2));
        }
        else
        {
            textView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorGrey1));
        }


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 3.0f);
        params.setMargins(2, 1, 2, 1);

        textView.setGravity(Gravity.CENTER);

        textView.setLayoutParams(params);


        LinearLayout linearView = (LinearLayout) findViewById (R.id.weekDaysView);
        linearView.addView(textView);
    }
}
