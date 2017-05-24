package ican.com.hotel3.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.prefser.library.Prefser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import ican.com.hotel3.R;
import ican.com.hotel3.adapter.MyAdapter;
import ican.com.hotel3.bean.Order;
import ican.com.hotel3.bean.User;
import ican.com.hotel3.uitils.DateUtil;
import ican.com.hotel3.uitils.MyHttpService;

/**
 * Created by mrzhou on 17-5-14.
 */

public class RoomsActivity extends Activity {
    private User user;
    private ListView listview;
    private String odate,days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        //获取intent传过来的值
        user = new Prefser(RoomsActivity.this).get("user",User.class,new User());
        ArrayList rooms = (ArrayList) getIntent().getSerializableExtra("rooms");
        odate = getIntent().getStringExtra("odate");
        days = getIntent().getStringExtra("days");
        String orderQuitDate = null;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(odate);
            orderQuitDate = DateUtil.getOrderQuitDate(date, days);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //将客房信息设置到适配器需要的list中
        List<Map<String, Object>> dataList = getData(rooms,odate,orderQuitDate);
        //找到listview
        listview = (ListView) findViewById(R.id.rooms_listview);
        //创建适配器
        MyAdapter adapter = new MyAdapter(this, dataList);
        //绑定适配器
        listview.setAdapter(adapter);

        //为单一列表行添加点击事件
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取item项中的TextView
                TextView room_rprice = (TextView) view.findViewById(R.id.room_rprice);
                TextView room_rid = (TextView) view.findViewById(R.id.room_rid);
                TextView room_rtype = (TextView) view.findViewById(R.id.room_rtype);
                //转换成文本
                final String text_rid = room_rid.getText().toString();
                String text_rprice = room_rprice.getText().toString().substring(0, room_rtype.getText().length());
                String text_rtype = room_rtype.getText().toString();

                //弹出提示框
                AlertDialog.Builder builder = new AlertDialog.Builder(RoomsActivity.this);
                builder.setTitle("房间预定");
                builder.setMessage("预定" + text_rid + "客房," + text_rtype
                        + "，需要支付" + text_rprice + "元，确定下单吗？");
                //确认按钮
                builder.setPositiveButton("立即下单", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if ("1".equals(user.getUstate()))
                            Toast.makeText(RoomsActivity.this, "你已经订过房间啦", Toast.LENGTH_SHORT).show();
                        else{
                            //请求获取下单微信扫码支付的订单信息
                            String url = "http://119.29.176.218:8080/hotel/order/getPayUrl";
                            String reqContent = "room.rid=" + text_rid + "&ouid=" + user.getUid()
                                    + "&odate=" + odate + "&odays=" + days;
                            String response = null;
                            try {
                                response = MyHttpService.post(reqContent,url);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //解析json数据
                            GsonBuilder builder = new GsonBuilder();
                            builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            Gson gson = builder.create();
                            if (response == null){
                                Toast.makeText(RoomsActivity.this,"未知错误",Toast.LENGTH_SHORT).show();
                            } else if (response.contains("state_code")){
                                //失败
                                Map map = gson.fromJson(response, Map.class);
                                String illegal_orid = (String) map.get("illegal_orid");
                                String illegal_ouid = (String) map.get("illegal_ouid");
                                String failure = (String) map.get("failure");
                                String err_orid = (String) map.get("err_orid");
                                if (illegal_orid != null) Toast.makeText(RoomsActivity.this,illegal_orid,Toast.LENGTH_SHORT).show();
                                else if (illegal_ouid != null) Toast.makeText(RoomsActivity.this,illegal_ouid,Toast.LENGTH_SHORT).show();
                                else if (failure != null) Toast.makeText(RoomsActivity.this,failure,Toast.LENGTH_SHORT).show();
                                else Toast.makeText(RoomsActivity.this,err_orid,Toast.LENGTH_SHORT).show();
                            } else {
                                //成功
                                Order order = gson.fromJson(response, Order.class);
                                Intent intent = new Intent(RoomsActivity.this,NativePayActivity.class);
                                intent.putExtra("order", order);
                                RoomsActivity.this.startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
                //取消按钮
                builder.setNegativeButton("我再想想", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.create().show();
            }
        });
    }

    /**
     * 将ArraList中的数据转换成适配器需要的List<Map<String,Obeject>>
     *
     * @param rooms 客房信息
     * @return
     */
    private List<Map<String, Object>> getData(ArrayList rooms,String odate,String oquit) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            HashMap<String, Object> room = (HashMap<String, Object>) rooms.get(i);

            Map<String, Object> map = new HashMap<>();
            String rtype = (String) room.get("rtype");
            if ("1".equals(rtype)) rtype = "单人房";
            else if ("2".equals(rtype)) rtype = "双人房";
            else rtype = "家庭房";

            map.put("room_rid", room.get("rid"));
            map.put("room_rtype", rtype);
            map.put("room_rphoto", room.get("rphoto"));
            map.put("room_rprice", Integer.parseInt(room.get("rprice").toString()) * Integer.parseInt(days) + "￥");
            map.put("room_rdate",odate + " 到 " + oquit);

            list.add(map);
        }

        return list;
    }
}
