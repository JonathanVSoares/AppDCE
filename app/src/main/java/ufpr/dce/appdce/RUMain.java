package ufpr.dce.appdce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class RUMain extends AppCompatActivity{

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ru_main);

        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void OpenTodaysMenu (View view){
        Intent intent = new Intent(getBaseContext(), RUMenuView.class);

        startActivity(intent);
    }

    public void OpenInfo (View view){

    }

    public void OpenCreatePost (View view){
        Intent intent = new Intent(getBaseContext(), CreatePostActivity.class);

        startActivity(intent);
    }

}
