package ufpr.dce.appdce;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class RUWorkingHoursView extends LinearLayout{

    public RUWorkingHoursView(Context context, String[] gridItems){
        super(context);
        this.setOrientation(VERTICAL);

        LinearLayout.LayoutParams gridParams = new LinearLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        gridParams.setMargins(5, 10, 5, 20);
        this.setLayoutParams(gridParams);
        this.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey2));

        LinearLayout rowLayout = new LinearLayout(context);
        TextView cellText;
        float textViewGravity;
        for (int counter = 0; counter < gridItems.length; counter++){
            if (counter % 3 == 0){
                rowLayout = new LinearLayout(context);
                this.addView(rowLayout);
                textViewGravity = 2f;
            }else{
                textViewGravity = 3f;
            }

            cellText = new TextView(context);
            cellText.setText(gridItems[counter]);
            cellText.setGravity(Gravity.CENTER);

            if (counter == 0){
                cellText.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            }

            TableLayout.LayoutParams textViewParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    textViewGravity);
            textViewParams.setMargins(2, 2, 2, 2);

            cellText.setLayoutParams(textViewParams);
            cellText.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBackground));

            rowLayout.addView(cellText);
        }
    }

}
