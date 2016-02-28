package ufpr.dce.appdce;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

public class RUBusinessHoursTitleView extends TextView {

    public RUBusinessHoursTitleView(Context context, String viewText){
        super(context);

        TableLayout.LayoutParams textViewParams = new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewParams.setMargins(10, 20, 10, 20);

        this.setLayoutParams(textViewParams);
        this.setTextSize(22);
        this.setText(viewText);
    }
}
