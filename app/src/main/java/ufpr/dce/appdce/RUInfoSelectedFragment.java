package ufpr.dce.appdce;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;


public class RUInfoSelectedFragment extends Fragment{
    static final String PAGE_SELECTED = "PAGE_SELECTED";
    private Context context;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        context = getActivity();

        Bundle args = getArguments();

        if (args == null) {
            return null;
        }

        String pageSelected = args.getString(PAGE_SELECTED);
        assert pageSelected != null;
        switch (pageSelected){
            case "Horário de Funcionamento":
                rootView = inflater.inflate(R.layout.ru_business_hours, container, false);

                configBusinessHourView();
                break;
            case "Preços":
                rootView = inflater.inflate(R.layout.ru_prices_fragment, container, false);

                configPricesView();
                break;
        }

        return rootView;
    }

    private void configBusinessHourView(){
        LinearLayout businessHoursView = (LinearLayout) rootView.
                findViewById(R.id.business_hours_view);

        businessHoursView.addView(new BusinessHoursTitleView(context, getResources().
                getString(R.string.central_ru)));
        businessHoursView.addView(new RUWorkingHoursView(context, getResources().
                getStringArray(R.array.central_ru_business_hours1)));
        businessHoursView.addView(new RUWorkingHoursView(context, getResources().
                getStringArray(R.array.central_ru_business_hours2)));
        businessHoursView.addView(new RUWorkingHoursView(context, getResources().
                getStringArray(R.array.central_ru_business_hours3)));

        businessHoursView.addView(new BusinessHoursTitleView(context, getResources().
                getString(R.string.poli_ru)));
        businessHoursView.addView(new RUWorkingHoursView(context, getResources().
                getStringArray(R.array.poli_ru_business_hours1)));

        businessHoursView.addView(new BusinessHoursTitleView(context, getResources().
                getString(R.string.agrarias_ru)));
        businessHoursView.addView(new RUWorkingHoursView(context, getResources().
                getStringArray(R.array.agrarias_ru_business_hours1)));

        businessHoursView.addView(new BusinessHoursTitleView(context, getResources().
                getString(R.string.botanico_ru)));
        businessHoursView.addView(new RUWorkingHoursView(context, getResources().
                getStringArray(R.array.botanico_ru_business_hours1)));

        TextView ruSource = new TextView(context);
        ruSource.setAutoLinkMask(Linkify.WEB_URLS);
        ruSource.setText(getString(R.string.ru_source));

        TableLayout.LayoutParams sourceParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        sourceParams.setMargins(5, 20, 5, 20);

        ruSource.setLayoutParams(sourceParams);
        businessHoursView.addView(ruSource);
    }

    private void configPricesView(){
        LinearLayout pricesView = (LinearLayout) rootView.
                findViewById(R.id.prices_view);

        pricesView.addView(new RUPricesView(context, getResources().
                getStringArray(R.array.breakfast_prices)));

        pricesView.addView(new RUPricesView(context, getResources().
                getStringArray(R.array.lunch_dinner_prices)));

        TextView eventsExplanation = new TextView(context);
        eventsExplanation.setText(getString(R.string.ru_event_explanation));

        TableLayout.LayoutParams explanationparams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        explanationparams.setMargins(5, 20, 5, 20);

        eventsExplanation.setLayoutParams(explanationparams);
        pricesView.addView(eventsExplanation);

        TextView ruSource = new TextView(context);
        ruSource.setAutoLinkMask(Linkify.WEB_URLS);
        ruSource.setText(getString(R.string.ru_source));

        TableLayout.LayoutParams sourceParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        sourceParams.setMargins(5, 20, 5, 20);

        ruSource.setLayoutParams(sourceParams);
        pricesView.addView(ruSource);
    }
}
