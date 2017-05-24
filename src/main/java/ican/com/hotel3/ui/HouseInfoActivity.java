package ican.com.hotel3.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pwittchen.prefser.library.Prefser;

import java.util.List;

import ican.com.hotel3.R;
import ican.com.hotel3.bean.Order;
import ican.com.hotel3.bean.Room;
import ican.com.hotel3.bean.User;
import ican.com.hotel3.uitils.QRCode;

/**
 * Created by Administrator on 2017/4/15.
 */

public class HouseInfoActivity extends Activity {
    private User user;
    ImageButton back;
    TextView mianHouseInfo;
    TextView in_date,out_date,in_days,room_id,room_type,in_user,telephone,price;
    ImageView qrcode;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_houseinfo);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.information_title);
        mianHouseInfo = (TextView)findViewById(R.id.maintitle);
        mianHouseInfo.setText("订房信息");
        //
        Prefser prefser = new Prefser(HouseInfoActivity.this);

        user = prefser.get("user",User.class,new User());
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(HouseInfoActivity.this,
                        PersonalActivity.class);
                HouseInfoActivity.this.startActivity(it);
            }
        });

        //判断状态是否订房
        if ("1".equals(user.getUstate())){
            //找到各个文本控件
            in_date = (TextView) findViewById(R.id.in_date);
            out_date = (TextView) findViewById(R.id.out_date);
            in_days = (TextView) findViewById(R.id.in_days);
            room_id = (TextView) findViewById(R.id.room_id);
            room_type = (TextView) findViewById(R.id.room_type);
            in_user = (TextView) findViewById(R.id.in_user);
            telephone = (TextView) findViewById(R.id.telephone);
            price = (TextView) findViewById(R.id.price);
            qrcode = (ImageView) findViewById(R.id.qrcode);

            List<Order> orders = user.getOrders();
            //获取订单信息
            final Order order = orders.get(0);

            //生成二维码
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = QRCode.generateBitmap(order.getOid(), 130, 130);
                    qrcode.setImageBitmap(bitmap);
                }
            }).start();

            //获取订单的房间信息
            Room room = order.getRoom();

            int in_month = order.getOdate().getMonth() + 1;
            int out_month = order.getOquit().getMonth() + 1;
            //设置显示信息
            in_date.setText(in_month + "月" + order.getOdate().getDate() + "日");
            out_date.setText(out_month + "月" + order.getOquit().getDate() + "日");
            in_days.setText(order.getOdays());
            room_id.setText(room.getRid());
            if (room.getRtype().equals("1")) room_type.setText("单人房");
            else if (room.getRtype().equals("2")) room_type.setText("双人房");
            else room_type.setText("家庭房");
            in_user.setText(user.getUname());
            telephone.setText(user.getUphone());
            price.setText(room.getRprice());



        }else {
            //未订房
            room_type = (TextView) findViewById(R.id.room_type);
            room_type.setText("您还未订房");
        }
    }
}
