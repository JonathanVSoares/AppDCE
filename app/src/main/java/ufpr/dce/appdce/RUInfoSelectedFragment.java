package ufpr.dce.appdce;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
                break;
            case "Identificação":
                rootView = inflater.inflate(R.layout.ru_identification_fragment, container, false);
                break;
            default:
                return null;
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
        ruSource.setText(getString(R.string.ru_source));

        TableLayout.LayoutParams sourceParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                TableLayout.LayoutParams.WRAP_CONTENT);

        ruSource.setLayoutParams(sourceParams);
        businessHoursView.addView(ruSource);
    }

    private void configGrid(int viewId, int arrayId){
        GridView grid = (GridView) rootView.findViewById(viewId);

        String[] items = getResources().
                getStringArray(arrayId);
        grid.setAdapter(new BusinessHoursAdapter(items));
        grid.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGrey3));
        grid.setHorizontalSpacing(1);
        grid.setVerticalSpacing(1);
    }

    private class BusinessHoursAdapter extends BaseAdapter{
        String[] gridItems;

        public BusinessHoursAdapter(String[] gridItems){
            this.gridItems = gridItems;
        }

        public int getCount() {
            return gridItems.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position,
                            View convertView, ViewGroup parent) {
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setBackgroundColor(Color.WHITE);

            TextView textView = new TextView(context);
            textView.setText(gridItems[position]);

            linearLayout.addView(textView);
            return linearLayout;
        }
    }
}
