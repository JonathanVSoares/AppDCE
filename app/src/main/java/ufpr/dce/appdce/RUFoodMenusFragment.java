package ufpr.dce.appdce;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RUFoodMenusFragment extends Fragment{
    private Context context;
    private String[] weekDays;
    private LinearLayout menuView;
    private Calendar cal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        context = getActivity();
        View rootView = inflater.inflate(R.layout.content_ru_menu_view, container, false);
        menuView = (LinearLayout) rootView.findViewById(R.id.ru_week_menu_view);

        cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, 1);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


        weekDays = new String[7];
        for (int dayCounter = 0; dayCounter < weekDays.length; dayCounter++ ){
            weekDays[dayCounter] = format.format(cal.getTime());
            cal.add(Calendar.DATE, 1);
        }

        cal.set(Calendar.DAY_OF_WEEK, 1);

        String firstDayOfWeek = weekDays[0];
        String lastDayOfWeek = weekDays[6];


        RequestQueue queue = Volley.newRequestQueue(context);

        String url = Util.SERVER_IP + "get_ru_menus.php?from_date=" +
                firstDayOfWeek + "&to_date=" + lastDayOfWeek;

        CustomRequest jsObjRequest = new CustomRequest
                (url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //ToDo: move to another method
                            if (response.get("success").toString().equals("1")) {
                                JSONObject weekMenus = response.getJSONObject("menus");

                                String[][] menus = new String[7][3];
                                for (int dayCounter = 0; dayCounter < 7; dayCounter++) {
                                    if (weekMenus.has(weekDays[dayCounter])) {
                                        JSONObject dayMenus = weekMenus.getJSONObject(weekDays[dayCounter]);

                                        for (int timeOfDayCounter = 0; timeOfDayCounter < 3; timeOfDayCounter++) {
                                            if (dayMenus.has(String.valueOf(timeOfDayCounter + 1))) {

                                                JSONArray menu = dayMenus.getJSONArray(String.valueOf(timeOfDayCounter + 1));

                                                StringBuilder itemsMenu = new StringBuilder();
                                                for (int itemCounter = 0; itemCounter < menu.length(); itemCounter++) {
                                                    itemsMenu.append(menu.get(itemCounter)).append("\n");
                                                }

                                                menus[dayCounter][timeOfDayCounter] = itemsMenu.toString();

                                            } else {
                                                menus[dayCounter][timeOfDayCounter] = " ";
                                            }
                                        }
                                    } else {
                                        for (int timeOfDayCounter = 0; timeOfDayCounter < 3; timeOfDayCounter++) {
                                            menus[dayCounter][timeOfDayCounter] = " ";
                                        }
                                    }
                                }

                                menuView.addView(new RUWeekMenuView(context, menus, cal));
                            }else{
                                if (response.has("message")){
                                    Log.d("Mensagem de erro:", response.getString("message"));
                                }
                                //ToDo:add else
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context,
                                error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsObjRequest);

        return rootView;
    }
}
