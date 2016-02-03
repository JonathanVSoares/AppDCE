package ufpr.dce.appdce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreatePostActivity extends AppCompatActivity{
    // Progress Dialog
    private ProgressDialog pDialog;

    private EditText inputTitle;
    private EditText inputText;
    private EditText inputTags;

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_post);

        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);


        // Edit Text
        inputTitle = (EditText) findViewById(R.id.inputTitle);
        inputText = (EditText) findViewById(R.id.inputText);
        inputTags = (EditText) findViewById(R.id.inputTags);

        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnCreatePost);

        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String title = inputTitle.getText().toString();
                String text = inputText.getText().toString();
                String tags = inputTags.getText().toString();

                Map<String, String> map = new HashMap<>();

                map.put("title",title);
                map.put("text",text);
                map.put("tags", tags);
                map.put("userid", "1");
                map.put("orgid", "1");

                RequestQueue queue = Volley.newRequestQueue(getBaseContext());

                String url = "http://192.168.0.15/AppDce/create_post.php";

                pDialog = new ProgressDialog(CreatePostActivity.this);
                pDialog.setMessage("Creating Post..");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();

                // Request a string response from the provided URL.
                CustomRequest jsObjRequest = new CustomRequest
                        (Request.Method.POST, url, map, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                pDialog.dismiss();

                                try {
                                    Toast.makeText(getApplicationContext(), response.get("message").toString(), Toast.LENGTH_SHORT).show();

                                    if(response.get("success").toString().equals("1")){
                                        Intent intent = new Intent(getBaseContext(), OpenPostActivity.class);

                                        intent.putExtra(OpenPostActivity.EXTRA_POST_ID, response.get("postid").toString());

                                        startActivity(intent);
                                    }
                                }
                                catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pDialog.dismiss();
                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                Log.d("AppDCE", error.toString());
                            }
                        });

                queue.add(jsObjRequest);
            }
        });
    }

    public void referenceToAPost(View view){
        boolean checked = ((CheckBox) view).isChecked();

        if (checked){
            EditText postReferencia = (EditText) findViewById(R.id.post_referencia);
            postReferencia.setVisibility(View.VISIBLE);
        }
        else{
            EditText postReferencia = (EditText) findViewById(R.id.post_referencia);
            postReferencia.setVisibility(View.GONE);
        }
    }
}