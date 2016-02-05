package ufpr.dce.appdce;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    private TextView postSubjectView;
    private TextView postAuthorView;
    private TextView postTextView;
    private TextView postDateView;
    private TextView postTagsView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_post);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            postSubjectView = (TextView) findViewById(R.id.post_subject_view);
            postAuthorView = (TextView) findViewById(R.id.org_author_view);
            postTextView = (TextView) findViewById(R.id.post_text_view);
            postDateView = (TextView) findViewById(R.id.post_date_posted_view);
            postTagsView = (TextView) findViewById(R.id.post_tags_view);

            String postId = extras.getString(EXTRA_POST_ID);

            RequestQueue queue = Volley.newRequestQueue(getBaseContext());

            String url = "http://192.168.0.15/AppDce/get_post_details.php?post_id=" + postId;

            CustomRequest jsObjRequest = new CustomRequest
                    (url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                if(response.get("success").toString().equals("1")){
                                    JSONObject postInfo = response.getJSONObject("post");

                                    postSubjectView.setText(postInfo.getString("title"));
                                    postAuthorView.setText(postInfo.getString("responsible_name"));
                                    postTextView.setText(postInfo.getString("text"));
                                    postDateView.setText(postInfo.getString("post_date"));
                                    postTagsView.setText("Tags: " + postInfo.getString("tags"));
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
                        }
                    });

            queue.add(jsObjRequest);
        }

    }
}
