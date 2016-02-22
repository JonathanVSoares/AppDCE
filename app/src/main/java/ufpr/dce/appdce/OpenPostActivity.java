package ufpr.dce.appdce;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class OpenPostActivity extends AppCompatActivity {
    public static final String EXTRA_POST_ID = "EXTRA_POST_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_post);

        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            requestPost(extras);
        }
    }

    private void requestPost(Bundle extras){
        String postId = extras.getString(EXTRA_POST_ID);

        RequestQueue queue = Volley.newRequestQueue(getBaseContext());

        String url = "http://192.168.0.15/AppDce/get_post_details.php?post_id=" + postId;

        CustomRequest jsObjRequest = new CustomRequest
                (url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        buildPost(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(),
                                error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsObjRequest);
    }

    private void buildPost(JSONObject response){
        TextView postSubjectView = (TextView) findViewById(R.id.post_subject_view);
        TextView postAuthorView = (TextView) findViewById(R.id.org_author_view);
        TextView postTextView = (TextView) findViewById(R.id.post_text_view);
        TextView postDateView = (TextView) findViewById(R.id.post_date_posted_view);
        TextView postTagsView = (TextView) findViewById(R.id.post_tags_view);

        try {
            if(response.get("success").toString().equals("1")){
                JSONObject postInfo = response.getJSONObject("post");

                //String tagsText = getResources().getString();

                postSubjectView.setText(postInfo.getString("title"));
                postAuthorView.setText(postInfo.getString("responsible_abbreviation"));
                postTextView.setText(postInfo.getString("text"));
                postDateView.setText(postInfo.getString("post_date"));
                postTagsView.setText(new StringBuilder().
                        append(getResources().getString(R.string.tags_string)).
                        append(postInfo.getString("tags")));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static Intent newOpenPostActivity(Context context, String postId){
        Intent intent = new Intent(context, OpenPostActivity.class);
        Bundle b = new Bundle();
        b.putString(EXTRA_POST_ID, postId);
        intent.putExtras(b);

        return intent;
    }
}
