package jrackie.apps.cartoon.read;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import jrackie.apps.R;
import jrackie.libs.deviceinfo.ScreenSizeEntity;
import jrackie.libs.imageloader.ImageLoader;

/**
 * Created by Administrator on 2016/3/21.
 */
public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private ImageLoader loader;
    private String[] urls;
    private ScreenSizeEntity screenSize;

    public ListViewAdapter(Context context, ImageLoader loader, String[] urls, ScreenSizeEntity screenSize) {
        this.context = context;
        this.loader = loader;
        this.urls = urls;
        this.screenSize = screenSize;
    }

    @Override
    public int getCount() {
        return urls.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.cartoon_read_fragment,parent,false);
            viewHolder.loading_layout=(RelativeLayout)convertView.findViewById(R.id.layout_loading);
            viewHolder.read_loading_text=(TextView)convertView.findViewById(R.id.read_loading_text);
            viewHolder.read_loading_page=(TextView)convertView.findViewById(R.id.read_loading_page);
            viewHolder.read_loading_anim=(ImageView)convertView.findViewById(R.id.read_loading_anim);
            viewHolder.read_image=(ImageView)convertView.findViewById(R.id.read_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FrameLayout.LayoutParams layoutParams=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewHolder.loading_layout.setLayoutParams(layoutParams);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_loading);
        viewHolder.read_loading_anim.startAnimation(animation);
        viewHolder.read_loading_page.setText((position + 1) + "");
        viewHolder.read_loading_text.setText(R.string.read_loading_text);
        Bitmap bitmap=loader.loadBitmapfromCache(urls[position]);
        if(bitmap==null)
               loader.loadBitmapfromNet(urls[position], screenSize.getWidth(), 0, new ImageLoader.DownloadCallback() {
                @Override
                public void dealBitmap(Bitmap bitmap) {
                    viewHolder.read_image.setImageBitmap(bitmap);
                    viewHolder.loading_layout.setVisibility(View.GONE);
                }
            });
        else{
            viewHolder.read_image.setImageBitmap(bitmap);
            viewHolder.loading_layout.setVisibility(View.GONE);
        }
        return convertView;
    }

    final class ViewHolder {
        RelativeLayout loading_layout;
        TextView read_loading_page;
        TextView read_loading_text;
        ImageView read_loading_anim;
        ImageView read_image;
    }
}
