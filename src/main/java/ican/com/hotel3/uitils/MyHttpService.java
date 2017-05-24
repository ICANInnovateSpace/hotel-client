package ican.com.hotel3.uitils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by mrzhou on 17-5-13.
 *
 */

public class MyHttpService {
    public static String post(String content, String url) throws IOException {
        //创建URL对象
        try {
            URL httpUrl = new URL(url);
            //使用URL对象打开HttpConnection对象
            HttpURLConnection urlConnection = (HttpURLConnection) httpUrl.openConnection();
            //设置请求属性
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Charset", "UTF-8");

            //获取http输出流OutputStream
            OutputStream outputStream = urlConnection.getOutputStream();
            //创建StringBuffer对象，将需要请求的内容先存储到sb对象里

            outputStream.write(content.getBytes());
            //关流
            outputStream.close();
            //获取服务端返回的状态码
            if (urlConnection.getResponseCode() == 200) {
                //使用connection对象获取InputStream对象
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    //将获取到的数据存到StringBuffer对象
                    buffer.append(line);
                }
                //关流
                reader.close();
                //返回数据
                return buffer.toString();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
