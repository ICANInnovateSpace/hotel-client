package ican.com.hotel3.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import ican.com.hotel3.R;
import ican.com.hotel3.uitils.MyHttpService;

/**
 * Created by Administrator on 2017/4/14.
 *
 * ===用户注册的Activity===
 */

public class RegisterAcitvity extends Activity {
    EditText register_uname, register_psw, register_psw2, register_phone;
    private String url = "http://119.29.176.218:8080/hotel/user/register";
    private String response = null;
    Button register_reg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        //取到所有控件
        register_uname = (EditText) findViewById(R.id.register_uname);
        register_psw = (EditText) findViewById(R.id.register_psw);
        register_psw2 = (EditText) findViewById(R.id.register_psw2);
        register_phone = (EditText) findViewById(R.id.register_phone);
        register_reg = (Button) findViewById(R.id.register_reg);

        //注册按钮点击事件
        register_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框的信息
                String uname = register_uname.getText().toString();
                String phone = register_phone.getText().toString();
                String psw = register_psw.getText().toString();
                String psw2 = register_psw2.getText().toString();
                //校验是否通过
                if (validate(uname,phone,psw,psw2)){
                    //校验通过，注册
                    String reqContent = "uname=" + uname + "&upsw=" + psw + "&uphone=" + phone;
                    try {
                        //请求注册
                        response = MyHttpService.post(reqContent, url);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //判断返回值
                    if (response == null){
                        Toast.makeText(RegisterAcitvity.this, "未知错误", Toast.LENGTH_SHORT).show();
                    }else{
                        //解析json数据
                        Gson gson = new Gson();
                        Map map = gson.fromJson(response, Map.class);
                        String state_code = (String) map.get("state_code");
                        if ("0".equals(state_code)){
                            //注册失败
                            String returnName = (String) map.get("uname");
                            String returnPsw = (String) map.get("upsw");
                            String returnPhone = (String) map.get("uphone");
                            String returnExist = (String) map.get("exist");
                            //提示信息
                            if (!(returnName == null || returnName.equals(""))) Toast.makeText(RegisterAcitvity.this, returnName, Toast.LENGTH_SHORT).show();
                            else if (!(returnPsw == null || returnPsw.equals(""))) Toast.makeText(RegisterAcitvity.this, returnPsw, Toast.LENGTH_SHORT).show();
                            else if (!(returnPhone == null || returnPhone.equals(""))) Toast.makeText(RegisterAcitvity.this, returnPhone, Toast.LENGTH_SHORT).show();
                            else Toast.makeText(RegisterAcitvity.this, returnExist, Toast.LENGTH_SHORT).show();
                        }else{
                            //注册成功
                            Toast.makeText(RegisterAcitvity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            RegisterAcitvity.this.finish();
                        }
                    }
                }
            }
        });
    }

    /**
     * 非法输入校验
     *
     * @param uname 用户名
     * @param phone 手机号码
     * @param psw 密码
     * @param psw2 确认密码
     * @return 是否通过校验
     * */
    private boolean validate(String uname,String phone,String psw,String psw2){
        if(uname == null || "".equals(uname)){ Toast.makeText(RegisterAcitvity.this, "用户名不能为空", Toast.LENGTH_SHORT).show(); return false;}
        else if (psw == null || "".equals(psw)){ Toast.makeText(RegisterAcitvity.this, "密码不能为空", Toast.LENGTH_SHORT).show(); return false;}
        else if (phone == null || "".equals(phone)){ Toast.makeText(RegisterAcitvity.this, "手机号码不能为空", Toast.LENGTH_SHORT).show(); return false;}
        else{
            if (psw.length() < 6 || psw2.length() < 6){ Toast.makeText(RegisterAcitvity.this, "密码长度至少6位", Toast.LENGTH_SHORT).show(); return false;}
            else if (!psw.equals(psw2)){ Toast.makeText(RegisterAcitvity.this, "密码不一致", Toast.LENGTH_SHORT).show(); return false;}
            else return true;
        }
    }
}
