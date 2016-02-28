package ufpr.dce.appdce;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RUWeekMenuView extends LinearLayout {
    private Context context;
    private static final int[] CELL_HEIGHT = {180, 250, 250};
    private static final int[] COLUMNS_WIDTH = {150, 300};

    public RUWeekMenuView (Context context, String[][] gridItems, Calendar firstDayOfWeek){
        super(context);
        this.context = context;
        this.setOrientation(HORIZONTAL);

        LinearLayout.LayoutParams gridParams = new LinearLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT);
        gridParams.setMargins(5, 10, 5, 20);
        this.setLayoutParams(gridParams);
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey2));

        LinearLayout columnLayout = new LinearLayout(context);
        float textViewWeight;

        String[] times_of_day = context.getResources().getStringArray(R.array.times_of_day);

        for (int counter = 0; counter < times_of_day.length; counter++){
            if (counter == 0) {
                columnLayout = new LinearLayout(context);

                columnLayout.setLayoutParams(new LayoutParams(COLUMNS_WIDTH[0],
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                columnLayout.setOrientation(VERTICAL);
                this.addView(columnLayout);

                textViewWeight = 5f;
                columnLayout.addView(createText(Gravity.CENTER, " \n " ,
                        textViewWeight));
            }

            textViewWeight = 3f;
            columnLayout.addView(createText(Gravity.CENTER, times_of_day[counter],
                    textViewWeight, CELL_HEIGHT[counter]));
        }

        String[] daysOfWeek = context.getResources().getStringArray(R.array.days_of_week);
        Calendar weekDay = firstDayOfWeek;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (int counter = 0; counter < gridItems.length; counter++){
            columnLayout = new LinearLayout(context);

            columnLayout.setLayoutParams(new LayoutParams(COLUMNS_WIDTH[1],
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            columnLayout.setOrientation(VERTICAL);
            this.addView(columnLayout);

            textViewWeight = 5f;
            String columnDay = format.format(weekDay.getTime());
            columnLayout.addView(createText(Gravity.CENTER, daysOfWeek[counter] + "\n" + columnDay,
                    textViewWeight));

            textViewWeight = 3f;
            for(int counter2 = 0; counter2 < gridItems[counter].length; counter2++) {
                columnLayout.addView(createText(gridItems[counter][counter2],
                        textViewWeight, CELL_HEIGHT[counter2]));
            }

        }
    }

    private TextView createText(String text, float textViewWeight, int textViewHeight){
        return createText(Gravity.TOP | Gravity.START, text, textViewWeight, textViewHeight);
    }

    private TextView createText(int textViewGravity, String text, float textViewWeight){
        return createText(textViewGravity, text, textViewWeight,
                TableLayout.LayoutParams.WRAP_CONTENT);
    }

    private TextView createText(int textViewGravity, String text, float textViewWeight, int textViewHeight){
        TextView textView = new TextView(context);

        textView.setText(text);
        textView.setGravity(textViewGravity);
        textView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBackground));

        TableLayout.LayoutParams textViewParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                textViewHeight,
                textViewWeight);
        textViewParams.setMargins(2, 2, 2, 2);

        textView.setLayoutParams(textViewParams);

        return textView;
    }
}
