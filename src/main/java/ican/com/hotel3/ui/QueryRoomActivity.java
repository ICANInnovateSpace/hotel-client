package ican.com.hotel3.ui;

/**
 * Created by mrzhou on 17-5-13.
 */

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.pwittchen.prefser.library.Prefser;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import ican.com.hotel3.R;
import ican.com.hotel3.bean.User;
import ican.com.hotel3.uitils.MyHttpService;

public class QueryRoomActivity extends Activity {
    private User user;
    private String url = "http://119.29.176.218:8080/hotel/room/query";
    private TextView view2;
    private EditText live_days;
    private Spinner spinner;
    private ArrayAdapter adapter;
    private Button btn_date,btn_time,check;
    private int year, month, day,hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        //
        user = new Prefser(QueryRoomActivity.this).get("user",User.class,new User());

        spinner = (Spinner) findViewById(R.id.spinner_room);
        view2 = (TextView) findViewById(R.id.spinner_text);
        live_days = (EditText) findViewById(R.id.live_days);

        //将可选内容与ArrayAdapter连接起来
        adapter = ArrayAdapter.createFromResource(this, R.array.plantes, android.R.layout.simple_spinner_item);

        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());

        //设置默认值
        spinner.setVisibility(View.VISIBLE);

        //设置日期
        btn_date = (Button) findViewById(R.id.btn_date);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date();
                datePickerDialog();
            }
        });

        //设置时间
        btn_time = (Button) findViewById(R.id.btn_time);
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time();
                timePickerDialog();
            }
        });

        //查询房间
        check = (Button) findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rtype,orderDate,days;
                //判断房间类型
                if ("无限制".equals(view2.getText())) rtype = null;
                else if ("单人房".equals((view2.getText()))) rtype = "1";
                else if ("双人房".equals(view2.getText())) rtype = "2";
                else rtype = "3";
                //设置订房时间和天数
                orderDate = btn_date.getText().toString() + " " + btn_time.getText().toString();
                days = live_days.getText().toString();
                String reqContent;
                if (rtype == null) reqContent = "orderDate=" + orderDate + "&days=" + days;
                else reqContent = "rtype=" + rtype + "&orderDate=" + orderDate + "&days=" + days;
                String response = null;
                try {
                    //查询
                    response = MyHttpService.post(reqContent, url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //判断响应信息
                if (response == null){
                    Toast.makeText(QueryRoomActivity.this, "未知错误", Toast.LENGTH_SHORT).show();
                }else {
                    Gson gson = new Gson();
                    if (response.contains("state_code")){
                        Map map = gson.fromJson(response, Map.class);
                        String state_code = (String) map.get("state_code");
                        if (state_code.equals("1")){
                            //成功但房满了
                            String full = (String) map.get("full");
                            Toast.makeText(QueryRoomActivity.this, full, Toast.LENGTH_SHORT).show();
                        }else {
                            //失败
                            String order_date = (String) map.get("orderDate");
                            String tips_days = (String) map.get("days");
                            String error_type = (String) map.get("err_rtype");
                            if (order_date != null) Toast.makeText(QueryRoomActivity.this, "请输入正确的时间", Toast.LENGTH_SHORT).show();
                            else if (tips_days != null) Toast.makeText(QueryRoomActivity.this, tips_days, Toast.LENGTH_SHORT).show();
                            else Toast.makeText(QueryRoomActivity.this, error_type, Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        //获取客房列表信息
                        ArrayList rooms = gson.fromJson(response, ArrayList.class);
                        //跳转界面
                        Intent it = new Intent(QueryRoomActivity.this,RoomsActivity.class);
                        //传值
                        it.putExtra("rooms",rooms);
                        it.putExtra("odate",orderDate);
                        it.putExtra("days",days);
                        QueryRoomActivity.this.startActivity(it);
                        finish();
                    }
                }
            }
        });
    }

    //使用XML形式操作
    class SpinnerXMLSelectedListener implements OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                   long arg3) {
            view2.setText(String.valueOf(adapter.getItem(arg2)));
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }

    }

    //日期控件
    private void datePickerDialog() {
        new DatePickerDialog(QueryRoomActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String sMonth =  String.valueOf(monthOfYear+1);
                String sDay =  String.valueOf(dayOfMonth);
                if (monthOfYear < 10) sMonth = "0" + String.valueOf(monthOfYear+1);
                if (dayOfMonth < 10) sDay = "0" + String.valueOf(dayOfMonth);
                String date = year + "-" + sMonth + "-" +sDay;
                btn_date.setText(date);
            }
        }, year, month, day).show();
    }

    //时间控件
    private void timePickerDialog() {
        new TimePickerDialog(QueryRoomActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int mounte) {
                String sHour =  String.valueOf(hour);
                String sMounte =  String.valueOf(mounte);
                if (hour < 10) sHour = "0" + String.valueOf(hour);
                if (mounte < 10) sMounte = "0" + String.valueOf(mounte);
                String time =  sHour + ":" + sMounte;
                btn_time.setText(time);
            }
        }, hour, minute, true).show();
    }

    //获取当前系统时间
    private void date() {
        Calendar c = Calendar.getInstance();
        //年
        year = c.get(Calendar.YEAR);
        //月
        month = c.get(Calendar.MONTH);
        //日
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    //获取当前系统时间
    private void time() {
        Calendar c = Calendar.getInstance();

        //时
        hour = c.get(Calendar.HOUR_OF_DAY);
        //分
        minute = c.get(Calendar.MINUTE);
    }
}
