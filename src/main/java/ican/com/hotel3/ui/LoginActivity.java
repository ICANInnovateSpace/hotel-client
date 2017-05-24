package ican.com.hotel3.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by Administrator on 2017/4/13.
 * <p>
 * ===用户登录Activity===
 */

public class LoginActivity extends Activity {
    Button login;
    EditText username, password;
    TextView register;
    private String url = "http://119.29.176.218:8080/hotel/user/login";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        //找到所有控件
        register = (TextView) findViewById(R.id.register);
        login = (Button) findViewById(R.id.login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        //注册界面跳转
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this,
                        RegisterAcitvity.class);
                LoginActivity.this.startActivity(it);
            }
        });

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //登录按钮点击事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = username.getText().toString();
                String upsw = password.getText().toString();
                //校验输入
                if (uname.equals(""))
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                else if (upsw.equals(""))
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                else {
                    String reqContent = "uname=" + uname + "&upsw=" + upsw;
                    String response = null;
                    try {
                        //登录
                        response = MyHttpService.post(reqContent, url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //判断返回结果
                    if (response == null) {
                        Toast.makeText(LoginActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                    } else {
                        //解析json数据
                        GsonBuilder builder = new GsonBuilder();
                        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        Gson gson = builder.create();
                        if (response.contains("state_code")){
                            Map map = gson.fromJson(response, Map.class);
                            String returnUname = (String) map.get("uname");
                            String returnUpsw = (String) map.get("upsw");
                            String not_exist = (String) map.get("not_exist");
                            if (returnUname != null) Toast.makeText(LoginActivity.this, returnUname, Toast.LENGTH_SHORT).show();
                            else if (returnUpsw != null) Toast.makeText(LoginActivity.this, returnUpsw, Toast.LENGTH_SHORT).show();
                            else Toast.makeText(LoginActivity.this, not_exist, Toast.LENGTH_SHORT).show();
                        }else {
                            //获取服务端返回的用户信息
                            User user = gson.fromJson(response, User.class);
                            Prefser prefser = new Prefser(LoginActivity.this);
                            prefser.clear();
                            prefser.put("user",user);
                            //跳转到个人信息界面
                            Intent it = new Intent(LoginActivity.this,
                                    PersonalActivity.class);
                            LoginActivity.this.startActivity(it);
                        }
                    }
                }
            }
        });

    }


}
