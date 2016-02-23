package ufpr.dce.appdce;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Post {
    private Context context;
    private String postId;
    private String author;
    private String subject;
    private String text;
    private String postDate;
    private String tags;
    private String eventBeg = null;
    private String eventEnd = null;

    public Post(JSONObject postObject, Context context) throws JSONException{
        this.context = context;

        try {
            postId = postObject.get("postid").toString();
            author = postObject.get("responsible_abbreviation").toString();
            subject = postObject.get("title").toString();
            text = postObject.get("text").toString();
            postDate = postObject.get("post_date").toString();
            tags = postObject.get("tags").toString();

            if (postObject.has("event_date_beg") && postObject.has("event_date_end")){
                eventBeg = postObject.get("event_date_beg").toString();
                eventEnd = postObject.get("event_date_end").toString();
            }

        }catch(JSONException e){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Algum problema ocorreu ao tentar pegar os dados do evento =/");
            AlertDialog alert = builder.create();
            alert.show();
            throw e;
        }
    }

    public Context getContext() {
        return context;
    }

    public String getPostId() {
        return postId;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getTags() {
        return tags;
    }

    public String getEventBeg() {
        return eventBeg;
    }

    public String getEventEnd() {
        return eventEnd;
    }
}
