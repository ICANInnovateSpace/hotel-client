package ican.com.hotel3.adapter;

/**
 * Created by mrzhou on 17-5-14.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Map;

import ican.com.hotel3.R;


public class MyAdapter extends BaseAdapter {
    private List<Map<String, Object>> list;
    private LayoutInflater mInflater;
    private Context context;

    public MyAdapter(Context context, List<Map<String, Object>>  list) {
        this.context = context;
        this.list = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {

            holder=new ViewHolder();

            convertView = mInflater.inflate(R.layout.list_item, null);
            holder.room_rphoto = (ImageView)convertView.findViewById(R.id.room_rphoto);
            holder.room_rid = (TextView)convertView.findViewById(R.id.room_rid);
            holder.room_rtype = (TextView)convertView.findViewById(R.id.room_rtype);
            holder.room_rprice = (TextView)convertView.findViewById(R.id.room_rprice);
            holder.room_rdate = (TextView)convertView.findViewById(R.id.room_rdate);
            convertView.setTag(holder);

        }else {

            holder = (ViewHolder)convertView.getTag();
        }


        // 设置ListView的相关值
        holder.room_rtype.setText((CharSequence) list.get(position).get("room_rtype"));
        holder.room_rid.setText((CharSequence) list.get(position).get("room_rid"));
        holder.room_rprice.setText((CharSequence) list.get(position).get("room_rprice"));
        holder.room_rdate.setText((CharSequence) list.get(position).get("room_rdate"));
        Glide.with(context).load((String)list.get(position).get("room_rphoto")).into(holder.room_rphoto);

        return convertView;
    }

    public final class ViewHolder{
        public ImageView room_rphoto;
        public TextView room_rtype;
        public TextView room_rid;
        public TextView room_rprice;
        public TextView room_rdate;
    }
}