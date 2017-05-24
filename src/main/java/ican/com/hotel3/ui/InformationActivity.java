package ican.com.hotel3.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.pwittchen.prefser.library.Prefser;

import ican.com.hotel3.R;
import ican.com.hotel3.bean.User;

/**
 * Created by Administrator on 2017/4/14.
 */

public class InformationActivity extends Activity {
    ImageButton back;
    ImageView info_head;
    EditText name,phone;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_information);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.information_title);

        //
        Prefser prefser = new Prefser(InformationActivity.this);
        User user = prefser.get("user", User.class, new User());
        back = (ImageButton)findViewById(R.id.back);
        info_head = (ImageView) findViewById(R.id.info_head);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);

        //加载头像
        Glide.with(this.getBaseContext()).load(user.getUphoto()).into(info_head);
        //设置名字电话
        name.setText(user.getUname());
        phone.setText(user.getUphone());
        //返回
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(InformationActivity.this,
                        PersonalActivity.class);
                InformationActivity.this.startActivity(it);
            }
        });

    }
}
