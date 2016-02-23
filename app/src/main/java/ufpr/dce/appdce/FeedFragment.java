package ufpr.dce.appdce;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class FeedFragment extends Fragment {
    private HashMap<String, String> postsIdsMap = new HashMap<>();
    private ArrayList<Post> postList;
    private PostViewAdapter dataAdapter;
    private Date nextDateToRequest = null;
    private boolean loadingMore = false;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View rootView = inflater.inflate(R.layout.content_feed, container, false);

        getActivity().setTitle("Feed");

        ListView listView = (ListView) rootView.findViewById(R.id.feed_list_view);

        postList = new ArrayList<>();
        dataAdapter = new PostViewAdapter(context, R.layout.post_view, postList);
        listView.setAdapter(dataAdapter);

        Date dateNow = new Date();
        requestPosts(10, dateNow);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount) && !(loadingMore)) {
                    requestPosts(7, nextDateToRequest);
                }
            }
        });

        return rootView;
    }

    public void requestPosts(int numberOfPosts, Date dateToRequest){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss",
                Locale.getDefault());
        String strDate = sdfDate.format(dateToRequest);

        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "http://192.168.0.15/AppDce/get_posts.php?from_date=" +
                strDate + "&number_of_posts=" + numberOfPosts;

        CustomRequest jsObjRequest = new CustomRequest
                (url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(!response.get("success").toString().equals("1")){
                                return;
                            }

                            JSONArray postsInfo = response.getJSONArray("posts");

                            assert getView() != null;


                            for (int postCounter = 0;
                                    postCounter < postsInfo.length();postCounter++){
                                JSONObject postInfo = postsInfo.getJSONObject(postCounter);
                                Post post = new Post(postInfo, context);
                                postList.add(post);
                                dataAdapter.add(post);
                            }

                            dataAdapter.notifyDataSetChanged();
                            loadingMore = false;

                            JSONObject lastPostInfo = postsInfo.getJSONObject(postsInfo.length() - 1);
                            String sLastPostDate = lastPostInfo.get("post_date").toString();

                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                            try {
                                Date dLastPostDate = formatter.parse(sLastPostDate);
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(dLastPostDate);
                                cal.add(Calendar.SECOND, -1);
                                nextDateToRequest = cal.getTime();
                            }catch (ParseException e){
                                e.printStackTrace();
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsObjRequest);
        loadingMore = true;
    }

    private class PostViewAdapter extends ArrayAdapter<Post> {

        private ArrayList<Post> postList;

        public PostViewAdapter(Context context, int textViewResourceId,
                               ArrayList<Post> postList) {
            super(context, textViewResourceId, postList);
            this.postList = new ArrayList<>();
            this.postList.addAll(postList);
        }

        private class ViewHolder {
            TextView author;
            TextView subject;
            TextView text;
            TextView datePosted;
            TextView tags;
            TextView eventBeg;
            TextView eventEnd;
        }

        public void add(Post post) {
            this.postList.add(post);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            Post post = this.postList.get(position);



            boolean isEvent = post.getEventBeg() != null && !post.getEventBeg().isEmpty()
                    && post.getEventEnd() != null && !post.getEventEnd().isEmpty();

            if (convertView == null) {
                LayoutInflater vi = LayoutInflater.from(getContext());

                holder = new ViewHolder();
                if (isEvent){
                    convertView = vi.inflate(R.layout.event_view, parent, false);
                    holder.eventBeg = (TextView) convertView.findViewById(R.id.event_beg);
                    holder.eventEnd = (TextView) convertView.findViewById(R.id.event_end);
                }else {
                    convertView = vi.inflate(R.layout.post_view, parent, false);
                }

                holder.author = (TextView) convertView.findViewById(R.id.org_author_view);
                holder.subject = (TextView) convertView.findViewById(R.id.post_subject_view);
                holder.text = (TextView) convertView.findViewById(R.id.post_text_view);
                holder.datePosted = (TextView) convertView.findViewById(R.id.post_date_posted_view);
                holder.tags = (TextView) convertView.findViewById(R.id.post_tags_view);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (isEvent){
                holder.eventBeg.setText(post.getEventBeg());
                holder.eventEnd.setText(post.getEventEnd());
            }

            holder.author.setText(post.getAuthor());
            holder.subject.setText(post.getSubject());
            holder.text.setText(post.getText());
            holder.datePosted.setText(post.getPostDate());
            holder.tags.setText(post.getTags());


            convertView.setId(ViewIdGenerator.generateViewId());

            postsIdsMap.put(String.valueOf(convertView.getId()), post.getPostId());

            // The user will be directed to the post itself if they click on it on the feed
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int viewId = view.getId();

                    Intent intent = OpenPostActivity.newOpenPostActivity(context, postsIdsMap.get(String.valueOf(viewId)));

                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

}
