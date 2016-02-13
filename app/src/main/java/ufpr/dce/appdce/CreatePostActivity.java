package ufpr.dce.appdce;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreatePostActivity extends Fragment{
    // Progress Dialog
    private ProgressDialog pDialog;

    private EditText inputTitle;
    private EditText inputText;
    private EditText inputTags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.content_criar_post, container, false);

        getActivity().setTitle("Novo Post");

        // Edit Text
        inputTitle = (EditText) rootView.findViewById(R.id.inputTitle);
        inputText = (EditText) rootView.findViewById(R.id.inputText);
        inputTags = (EditText) rootView.findViewById(R.id.inputTags);

        // Create button
        Button btnCreateProduct = (Button) rootView.findViewById(R.id.btnCreatePost);

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

                RequestQueue queue = Volley.newRequestQueue(getActivity());

                String url = "http://192.168.0.15/AppDce/create_post.php";

                pDialog = new ProgressDialog(getActivity());
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
                                    Toast.makeText(getActivity(), response.get("message").toString(), Toast.LENGTH_SHORT).show();

                                    if(response.get("success").toString().equals("1")){
                                        Intent intent = new Intent(getActivity(), OpenPostActivity.class);

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
                                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

                queue.add(jsObjRequest);
            }
        });

        return rootView;
    }

    public void referenceToAPost(View view){
        boolean checked = ((CheckBox) view).isChecked();

        assert getView()!=null;
        EditText postReferencia = (EditText) getView().findViewById(R.id.post_referencia);

        if (checked){
            postReferencia.setVisibility(View.VISIBLE);
        }
        else{
            postReferencia.setVisibility(View.GONE);
        }
    }
}