package ican.com.hotel3.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.prefser.library.Prefser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.Map;

import ican.com.hotel3.R;
import ican.com.hotel3.bean.User;
import ican.com.hotel3.uitils.MyHttpService;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by Administrator on 207/4/14.
 */

public class ModiypwdActivity extends Activity {
    private User user;
    TextView mianModiypwd;
    EditText cur_psw,new_psw,again_psw;
    ImageButton back;
    Button update_psw;
    String url = "http://119.29.176.218:8080/hotel/user/changePassword";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_modifypwd);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.information_title);

        //获取当前登录用户信息
        final Prefser prefser = new Prefser(ModiypwdActivity.this);
        user = prefser.get("user", User.class, new User());

        //找到控件
        mianModiypwd = (TextView)findViewById(R.id.maintitle);
        cur_psw = (EditText) findViewById(R.id.cur_psw);
        new_psw = (EditText) findViewById(R.id.new_psw);
        again_psw = (EditText) findViewById(R.id.again_psw);
        update_psw = (Button) findViewById(R.id.update_psw);

        mianModiypwd.setText("修改密码");

        //返回
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ModiypwdActivity.this,
                        PersonalActivity.class);
                ModiypwdActivity.this.startActivity(it);
            }
        });

        update_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current = cur_psw.getText().toString();
                String newPsw = new_psw.getText().toString();
                String again = again_psw.getText().toString();

                if (current.length() < 6 || current.length() > 20)
                    Toast.makeText(ModiypwdActivity.this, "旧密码长度必须在6~20位之间", Toast.LENGTH_SHORT).show();
                else if ((newPsw.length() < 6 || newPsw.length() > 20) || (again.length() < 6 || again.length() > 20))
                    Toast.makeText(ModiypwdActivity.this, "新密码长度必须在6~20位之间", Toast.LENGTH_SHORT).show();
                else if (!newPsw.equals(again)){
                    Toast.makeText(ModiypwdActivity.this, "两次密码不匹配", Toast.LENGTH_SHORT).show();
                } else{
                    String reqContent = "uid=" + user.getUid() + "&oldPassword=" + current + "&newPassword=" + newPsw;
                    String response = null;
                    try {
                       response = MyHttpService.post(reqContent,url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (response == null){
                        Toast.makeText(ModiypwdActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                    }else {
                        GsonBuilder builder = new GsonBuilder();
                        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Gson gson = builder.create();
                        if (response.contains("state_code")){
                            Map map = gson.fromJson(response, Map.class);
                            String length = (String) map.get("length");
                            String not_exist = (String) map.get("not_exist");
                            String not_match = (String) map.get("not_match");
                            if (length != null) Toast.makeText(ModiypwdActivity.this, length, Toast.LENGTH_SHORT).show();
                            else if (not_exist != null) Toast.makeText(ModiypwdActivity.this, not_exist, Toast.LENGTH_SHORT).show();
                            else Toast.makeText(ModiypwdActivity.this, not_match, Toast.LENGTH_SHORT).show();
                        } else{
                            User user = gson.fromJson(response, User.class);
                            prefser.clear();
                            Toast.makeText(ModiypwdActivity.this, "修改成功!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });
    }
}
