package ican.com.hotel3.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.pwittchen.prefser.library.Prefser;

import ican.com.hotel3.R;
import ican.com.hotel3.bean.User;


public class MainActivity extends Activity {
    private User user;
    ImageButton back;
    Button single_house;
    TextView mianhotle;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.information_title);
        mianhotle = (TextView)findViewById(R.id.maintitle);
        mianhotle.setText("ICAN双创酒店");
        //
        user = new Prefser(MainActivity.this).get("user",User.class,new User());

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,
                        PersonalActivity.class);
                MainActivity.this.startActivity(it);
            }
        });

        single_house = (Button) findViewById(R.id.single_house);
        single_house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,
                        QueryRoomActivity.class);
                MainActivity.this.startActivity(it);
                finish();
            }
        });
    }
}
