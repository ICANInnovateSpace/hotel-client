package ican.com.hotel3.control;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import ican.com.hotel3.bean.User;

/**
 * Created by Administrator on 2017/4/19.
 */

public class UpdateUserData {
    private static final int RANDOM_LENGTH = 16;
    private static final String BOUNDARY = "WebKitFormBoundary" + getRandomString(RANDOM_LENGTH);
    private static final String PREFIX = "--";
    private static final String NEW_LINE = "\r\n";


    /**
     * 客户端实现用户资料修改，（含上传图片）
     * @param user 封装需要更新到数据库的用户信息的对象
     * @param url 请求服务端的url
     * @param filePath 需要上传文件的路径
     * @return 服务端返回的结果
     * */
    public String update(User user, String url, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists())
            file.createNewFile();
        String fileName = file.getName();
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
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            //获取http输出流OutputStream
            OutputStream outputStream = urlConnection.getOutputStream();
            //创建StringBuffer对象，将需要请求的内容先存储到sb对象里
            StringBuffer requestData = new StringBuffer();
            requestData.append(PREFIX + BOUNDARY + NEW_LINE);
            requestData.append("Content-Disposition: form-data; name=\"uname\"" + NEW_LINE);
            requestData.append(NEW_LINE);
            requestData.append(user.getUname() + NEW_LINE);
            requestData.append(PREFIX + BOUNDARY + NEW_LINE);
            requestData.append("Content-Disposition: form-data; name=\"usex\"" + NEW_LINE);
            requestData.append(NEW_LINE);
            requestData.append(user.getUsex() + NEW_LINE);
            requestData.append(PREFIX + BOUNDARY + NEW_LINE);
            requestData.append("Content-Disposition: form-data; name=\"photo\"; filename=\"" + fileName + "\"" + NEW_LINE);
            requestData.append(NEW_LINE);
            //向服务端写请求内容
            //写前面一段
            outputStream.write(requestData.toString().getBytes());
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            //关文件输入流
            fileInputStream.close();
            //继续写尾部
            outputStream.write(NEW_LINE.getBytes());
            outputStream.write((PREFIX + BOUNDARY + PREFIX + NEW_LINE).getBytes());
            outputStream.flush();
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

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成随机字符串
     *
     * @param length 需要生成随机字符串的长度
     * */
    public static String getRandomString(int length){
        String randomBase = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(randomBase.length());
            sb.append(randomBase.charAt(index));
        }
        return sb.toString();
    }
}
