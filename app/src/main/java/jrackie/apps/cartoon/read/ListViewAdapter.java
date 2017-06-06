package jrackie.apps.cartoon.read;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;

import jrackie.apps.R;
import jrackie.libs.deviceinfor.ScreenSizeEntity;
import jrackie.libs.imageloader.ImageLoader;
import jrackie.libs.widget.DivideImageView;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.cartoon_read_pic,parent,false);
            viewHolder.loading_layout=(RelativeLayout)convertView.findViewById(R.id.layout_loading);
            viewHolder.read_loading_text=(TextView)convertView.findViewById(R.id.read_loading_text);
            viewHolder.read_loading_page=(TextView)convertView.findViewById(R.id.read_loading_page);
            viewHolder.read_loading_anim=(ImageView)convertView.findViewById(R.id.read_loading_anim);
            viewHolder.read_image=(DivideImageView)convertView.findViewById(R.id.read_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_loading);
        viewHolder.read_loading_anim.startAnimation(animation);
        viewHolder.read_loading_page.setText((position + 1) + "");
        viewHolder.read_loading_text.setText(R.string.read_loading_text);
        viewHolder.loading_layout.setVisibility(View.VISIBLE);
//        AssetManager as = context.getResources().getAssets();
//        try {
//            Bitmap bitmap=BitmapFactory.decodeStream(as.open(urls[position]));
//            if(bitmap!=null){
//            viewHolder.read_image.setImageBitmap(bitmap);
//            viewHolder.loading_layout.setVisibility(View.GONE);}
//
//        }catch (IOException e)
//        {
//
//        }
        String Direction=null;
//        if(viewHolder.read_image.isDivide()) {
//            Direction = "vertical";
//            ViewGroup.LayoutParams layoutParams=viewHolder.read_image.getLayoutParams();
//            layoutParams.height=viewHolder.read_image.getHeight()*2;
//            viewHolder.read_image.setLayoutParams(layoutParams);
//        }
//        else{
//            Direction = null;
//            ViewGroup.LayoutParams layoutParams=viewHolder.read_image.getLayoutParams();
//            layoutParams.height=viewHolder.read_image.getHeight();
//            viewHolder.read_image.setLayoutParams(layoutParams);
//        }
        Bitmap bitmap=loader.loadBitmapfromCache(urls[position],Direction);
        if(bitmap==null)
            if(viewHolder.read_image.isDivide())
                bitmap=loader.divideBitmap(urls[position],Direction);
            else {
                BitmapFactory.Options ops = new BitmapFactory.Options();
                ops.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(context.getResources(),Integer.parseInt(urls[position]), ops);
//                ops.inSampleSize = carculateSampleSize(ops, screenSize.getWidth(), screenSize.getHeight());
                ops.inSampleSize=2;
                ops.inJustDecodeBounds = false;
                bitmap= BitmapFactory.decodeResource(context.getResources(),Integer.parseInt(urls[position]),ops);
                if(bitmap==null){
                    viewHolder.read_loading_text.setText(R.string.read_loading_failed);
                }
                else{
                    viewHolder.read_image.setImageBitmap(bitmap);
                    viewHolder.loading_layout.setVisibility(View.GONE);
                }
//                loader.loadBitmapfromNet(urls[position], screenSize.getWidth(), screenSize.getHeight(), new ImageLoader.DownloadCallback() {
//                    @Override
//                    public void dealBitmap(Bitmap bitmap, String url) {
//                        if (url == urls[position]) {
//                            viewHolder.read_image.setImageBitmap(bitmap);
//                            viewHolder.loading_layout.setVisibility(View.GONE);
//                        } else
//                            viewHolder.read_loading_text.setText(R.string.read_loading_failed);
//                    }
//                });
            }
        else{
            viewHolder.read_image.setImageBitmap(bitmap);
            viewHolder.loading_layout.setVisibility(View.GONE);
        }
        return convertView;
    }
    public void setImage(Bitmap bitmap,ViewHolder viewHolder){
        viewHolder.read_image.setImageBitmap(bitmap);
    }
    final class ViewHolder {
        RelativeLayout loading_layout;
        TextView read_loading_page;
        TextView read_loading_text;
        ImageView read_loading_anim;
        DivideImageView read_image;
    }
    private int carculateSampleSize(BitmapFactory.Options ops, int rewidth, int reheight) {
        int samplesize = 1;
        final int width = ops.outWidth;
        final int height = ops.outHeight;
        Log.i("loader_pic", width + "ops");
        Log.i("loader_pic", height + "ops");
        Log.i("loader_pic", rewidth + "req");
        Log.i("loader_pic",reheight + "req");
        if (rewidth < width && reheight < height) {
            final int halfwidth = width / 2;
            final int halfheight = height / 2;
            while (halfheight / samplesize > reheight && halfwidth / samplesize > rewidth) {
                samplesize *= 2;
            }
        }
        return samplesize;
    }
}
