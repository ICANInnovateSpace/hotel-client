package ican.com.hotel3.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.pwittchen.prefser.library.Prefser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ican.wxpay.api.WxPayApi;
import com.ican.wxpay.lib.WechatPayData;
import com.ican.wxpay.lib.WechatPayException;

import java.io.IOException;
import java.text.SimpleDateFormat;

import ican.com.hotel3.R;
import ican.com.hotel3.bean.Order;
import ican.com.hotel3.bean.User;
import ican.com.hotel3.uitils.MyHttpService;
import ican.com.hotel3.uitils.QRCode;

/**
 * Created by mrzhou on 17-5-23.
 */

public class NativePayActivity extends Activity {
    private User user;
    private ImageView pay_qrcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        //
        final Prefser prefser = new Prefser(NativePayActivity.this);
        user = prefser.get("user",User.class,new User());
        //找到imageview控件
        pay_qrcode = (ImageView) findViewById(R.id.pay_qrcode);

        final Order order = (Order) getIntent().getSerializableExtra("order");
        Bitmap bitmap = QRCode.generateBitmap(order.getWxPayUrl(), 300, 300);
        pay_qrcode.setImageBitmap(bitmap);

        //开启线程查询订单
        new Thread(new Runnable() {
            @Override
            public void run() {
                WxPayApi nativePay = new WxPayApi();
                WechatPayData wechatPayData = new WechatPayData();
                //设置单号
                String wxNO = order.getWxNO();
                wechatPayData.addSubData("out_trade_no", wxNO);
                WechatPayData response = null;
                boolean flag = true;
                while(flag) {
                    try {
                        //向微信后台查询订单
                        response = nativePay.queryOrder(wechatPayData);
                    } catch (WechatPayException e) {
                        e.printStackTrace();
                    }
                    //判断返回结果
                    if (response != null){
                        //获取返回状态码
                        String return_code = response.getDataByKey("return_code").toString();

                        if (return_code.equals("SUCCESS") && response.getDataByKey("result_code").toString().equals("SUCCESS")){
                            String trade_state = response.getDataByKey("trade_state").toString();
                            if (trade_state.equals("SUCCESS")){
                                //订单已支付
                                String url = "http://119.29.176.218:8080/hotel/order/downOrder";
                                String rid = order.getRoom().getRid();
                                String uid = user.getUid();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                                String odate = simpleDateFormat.format(order.getOdate());
                                String oquit = simpleDateFormat.format(order.getOquit());
                                String odays = order.getOdays();
                                String ototal = order.getOtotal();
                                String wxPayUrl = order.getWxPayUrl();
                                //拼接请求内容
                                String reqContent = "room.rid=" + rid + "&ouid=" + uid + "&odate=" + odate
                                        + "&odays=" + odays + "&oquit=" + oquit + "&wxNO=" + wxNO + "&wxPayUrl="
                                        + wxPayUrl + "&ototal=" + ototal;
                                String result = null;
                                try {
                                    //插入订单信息到服务器端
                                    result = MyHttpService.post(reqContent,url);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //解析json数据
                                GsonBuilder builder = new GsonBuilder();
                                builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                                Gson gson = builder.create();
                                if (result == null || result.contains("state_code")){
                                    //弹出提示框
                                    AlertDialog.Builder alert = new AlertDialog.Builder(NativePayActivity.this);
                                    alert.setTitle("预定出错");
                                    alert.setMessage("出现未知错误，请与客服联系，电话13544494187");
                                }else{
                                    user = gson.fromJson(result, User.class);
                                    prefser.clear();
                                    prefser.put("user",user);
                                    //Toast.makeText(NativePayActivity.this,"预定客房成功",Toast.LENGTH_LONG).show();
                                }
                                flag = false;
                                //结束
                                finish();
                            }
                        }
                    }
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}
