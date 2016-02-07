package ufpr.dce.appdce;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class FeedActivity extends AppCompatActivity{
    HashMap<String, String> postsIdsMap = new HashMap<>();
    int postCounter;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);

        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd%20HH:mm:ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);


        RequestQueue queue = Volley.newRequestQueue(getBaseContext());

        String url = "http://192.168.0.15/AppDce/get_posts.php?from_date=" + strDate + "&number_of_posts=10";

        CustomRequest jsObjRequest = new CustomRequest
                (url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("AppDCE", response.toString());

                        try {
                            if(response.get("success").toString().equals("1")){
                                JSONArray postsInfo = response.getJSONArray("posts");

                                LinearLayout feedView = (LinearLayout) findViewById(R.id.feed_layout_view);

                                for (postCounter = 0; postCounter < postsInfo.length(); postCounter++){
                                    JSONObject postInfo = postsInfo.getJSONObject(postCounter);
                                    createPostView (feedView, postInfo);
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        Log.d("AppDCE", error.toString());
                    }
                });

        queue.add(jsObjRequest);
    }

    private void createPostView(ViewGroup view, JSONObject object){
        try {
            RelativeLayout newPostView = new RelativeLayout(this);

            view.addView(newPostView);

            String postId = object.get("postid").toString();
            String postAuthor = object.get("responsible_abbreviation").toString();
            String postSubject = object.get("title").toString();
            String postText = object.get("text").toString();
            String postDatePosted = object.get("post_date").toString();
            String postTags = object.get("tags").toString();

            newPostView.setId(ViewIdGenerator.generateViewId());

            postsIdsMap.put(String.valueOf(newPostView.getId()), postId);

            // The user will be directed to the post itself if they click on it on the feed
            newPostView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int viewId = view.getId();

                    String postId = postsIdsMap.get(String.valueOf(viewId));

                    Intent intent = new Intent(getBaseContext(), OpenPostActivity.class);

                    intent.putExtra(OpenPostActivity.EXTRA_POST_ID, postId);

                    startActivity(intent);
                }
            });

            // Create Image Layout
            ImageView orgImageView = new ImageView(this);
            orgImageView.setImageResource(R.mipmap.ic_launcher);
            RelativeLayout.MarginLayoutParams imageMarginParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            imageMarginParams.setMargins(5, 5, 5, 5);
            newPostView.setLayoutParams(imageMarginParams);
            newPostView.addView(orgImageView);

            orgImageView.setId(ViewIdGenerator.generateViewId());


            // Creating posts' text views
            TextView orgAuthorView = new TextView(this);
            TextView postSubjectView = new TextView(this);
            TextView postTextView = new TextView(this);
            TextView postDatePostedView = new TextView(this);
            TextView postTagsView = new TextView(this);

            // Setting Ids to use with relative rules
            orgAuthorView.setId(ViewIdGenerator.generateViewId());
            postSubjectView.setId(ViewIdGenerator.generateViewId());
            postTextView.setId(ViewIdGenerator.generateViewId());
            postDatePostedView.setId(ViewIdGenerator.generateViewId());
            postTagsView.setId(ViewIdGenerator.generateViewId());

            // Setting views' texts
            orgAuthorView.setText(postAuthor);
            postSubjectView.setText(postSubject);
            postTextView.setText(postText);
            postDatePostedView.setText(postDatePosted);
            postTagsView.setText(postTags);

            // Setting views' text sizes
            orgAuthorView.setTextSize(22);
            postSubjectView.setTextSize(18);
            postTextView.setTextSize(15);
            postDatePostedView.setTextSize(12);
            postTagsView.setTextSize(12);

            // Adding views to RelativeView
            newPostView.addView(orgAuthorView);
            newPostView.addView(postSubjectView);
            newPostView.addView(postTextView);
            newPostView.addView(postDatePostedView);
            newPostView.addView(postTagsView);

            // ** Relative Rules and margins/paddings
            RelativeLayout.LayoutParams authorViewParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                authorViewParams.addRule(RelativeLayout.RIGHT_OF, orgImageView.getId());
            } else {
                authorViewParams.addRule(RelativeLayout.END_OF, orgImageView.getId());
            }
            orgAuthorView.setLayoutParams(authorViewParams);


            RelativeLayout.LayoutParams subjectViewParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                subjectViewParams.addRule(RelativeLayout.RIGHT_OF, orgImageView.getId());
            } else {
                subjectViewParams.addRule(RelativeLayout.END_OF, orgImageView.getId());
            }
            subjectViewParams.addRule(RelativeLayout.BELOW, orgAuthorView.getId());
            postSubjectView.setLayoutParams(subjectViewParams);


            RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            textViewParams.addRule(RelativeLayout.BELOW, orgImageView.getId());
            postTextView.setLayoutParams(textViewParams);
            postTextView.setPadding(5, 5, 5, 5);


            RelativeLayout.LayoutParams datePostedViewParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                datePostedViewParams.addRule(RelativeLayout.ALIGN_RIGHT, postTextView.getId());
            } else {
                datePostedViewParams.addRule(RelativeLayout.ALIGN_END, postTextView.getId());
            }
            datePostedViewParams.addRule(RelativeLayout.BELOW, postTextView.getId());
            datePostedViewParams.setMargins(0, 0, 5, 0);
            postDatePostedView.setLayoutParams(datePostedViewParams);


            RelativeLayout.LayoutParams tagsViewParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                tagsViewParams.addRule(RelativeLayout.ALIGN_LEFT, postTextView.getId());
                tagsViewParams.addRule(RelativeLayout.LEFT_OF, postDatePostedView.getId());
            } else {
                tagsViewParams.addRule(RelativeLayout.ALIGN_START, postTextView.getId());
                tagsViewParams.addRule(RelativeLayout.START_OF, postDatePostedView.getId());
            }
            tagsViewParams.addRule(RelativeLayout.BELOW, postTextView.getId());
            tagsViewParams.setMargins(5, 0, 0, 0);
            postTagsView.setLayoutParams(tagsViewParams);
            // **


        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

}
