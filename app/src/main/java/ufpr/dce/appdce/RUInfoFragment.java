package ufpr.dce.appdce;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RUInfoFragment extends Fragment{
    private String[] infoListStrings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.ru_info_fragment, container, false);

        infoListStrings = getResources().getStringArray(R.array.ru_info_list);
        ListView infoList = (ListView) rootView.findViewById(R.id.ru_info_list);

        infoList.setAdapter(new ArrayAdapter<>(getActivity(),
                R.layout.ru_info_list_layout, infoListStrings));
        infoList.setOnItemClickListener(new InfoListClickListener());

        return rootView;
    }

    private class InfoListClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Bundle bundle = new Bundle();
            bundle.putString(RUInfoSelectedFragment.PAGE_SELECTED, infoListStrings[position]);

            Fragment fragment = new RUInfoSelectedFragment();
            fragment.setArguments(bundle);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }
}
