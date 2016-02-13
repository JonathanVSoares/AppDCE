package ufpr.dce.appdce;

import org.json.JSONException;
import org.json.JSONObject;

public class Post {
    private String postId;
    private String author;
    private String subject;
    private String text;
    private String postDate;
    private String tags;

    public Post(JSONObject postObject){
        try {
            postId = postObject.get("postid").toString();
            author = postObject.get("responsible_abbreviation").toString();
            subject = postObject.get("title").toString();
            text = postObject.get("text").toString();
            postDate = postObject.get("post_date").toString();
            tags = postObject.get("tags").toString();
        }catch(JSONException e){
            e.printStackTrace();
        }
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
}
