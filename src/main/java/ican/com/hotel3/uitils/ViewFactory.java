package ican.com.hotel3.uitils;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import ican.com.hotel3.R;

/**
 * Created by Administrator on 2017/4/12.
 */

public class ViewFactory {
    /**
     * 获取ImageView视图的同时加载显示url
     *
     *
     * @return
     */
    public static ImageView getImageView(Context context, String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);
        ImageLoader.getInstance().displayImage(url, imageView);
        return imageView;
    }
}
