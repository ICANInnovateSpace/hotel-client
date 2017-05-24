package ican.com.hotel3.uitils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mrzhou on 17-5-22.
 */

public class DateUtil {
    private static final int S = 1000;
    private static final int M = 60 * S;
    private static final int H = 60 * M;
    private static final int D = 24 * H;

    public static String getOrderQuitDate(Date orderDate, String days) throws ParseException {
        //日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //取得下单入住日期
        String format = simpleDateFormat.format(orderDate);
        String[] split = format.split(" ");
        //用于判断下单时间是否在12点之前
        Date currentDate = simpleDateFormat.parse(split[0] + " 12:00");
        //将字符串类型的天数转换成int类型
        int day = Integer.parseInt(days);
        //判断入住时间是否在12点前，若在12点前，到当天12点算作一天
        if (orderDate.getTime() < currentDate.getTime()) {
            day -= 1;
        }
        long quit = orderDate.getTime() + day * D;
        //算出退房的日期
        return new SimpleDateFormat("yyyy-MM-dd 12:00").format(new Date(quit));
    }
}
