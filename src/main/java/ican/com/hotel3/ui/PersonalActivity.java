package ican.com.hotel3.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.pwittchen.prefser.library.Prefser;

import ican.com.hotel3.R;
import ican.com.hotel3.bean.User;

/**
 * Created by Administrator on 2017/4/14.
 */

public class PersonalActivity extends Activity {
    private User user;
    ImageButton imageB1,imageB2,imageB3,imageB4;
    Button serach;
    TextView uname;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.acvitivity_personal);
        //
        user = new Prefser(PersonalActivity.this).get("user",User.class,new User());
        //设置名字
        uname = (TextView) findViewById(R.id.textView);
        uname.setText(user.getUname());

        imageB1 = (ImageButton)findViewById(R.id.head);
        //异步加载图片
        Glide.with(this.getBaseContext()).load(user.getUphoto()).into(imageB1);

        imageB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PersonalActivity.this,
                        InformationActivity.class);
                PersonalActivity.this.startActivity(it);
            }
        });
        imageB2 = (ImageButton)findViewById(R.id.personal_card);
        imageB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PersonalActivity.this,
                        CardActivity.class);
                PersonalActivity.this.startActivity(it);
            }
        });
        imageB3 = (ImageButton)findViewById(R.id.personal_pwd);
        imageB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PersonalActivity.this,
                        ModiypwdActivity.class);
                PersonalActivity.this.startActivity(it);
                finish();
            }
        });

        imageB4 = (ImageButton)findViewById(R.id.houseinfo);
        imageB4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PersonalActivity.this,
                        HouseInfoActivity.class);
                PersonalActivity.this.startActivity(it);
            }
        });

        serach  = (Button)findViewById(R.id.serach);
        serach .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PersonalActivity.this,
                        MainActivity.class);
                PersonalActivity.this.startActivity(it);
            }
        });
    }
}
