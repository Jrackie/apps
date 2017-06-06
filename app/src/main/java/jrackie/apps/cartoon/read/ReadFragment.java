package jrackie.apps.cartoon.read;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jrackie.apps.R;
import jrackie.libs.deviceinfor.ScreenSizeEntity;
import jrackie.libs.imageloader.ImageLoader;
import jrackie.libs.widget.DivideImageView;

/**
 * Created by Administrator on 2016/3/18.
 */
public class ReadFragment extends Fragment {
    private static final String LOG_TAG = "ReadFragment";
    private View view;
    private RelativeLayout layout_loading;

    private TextView read_loading_page;
    private ImageView read_loading_anim;
    private TextView read_loading_text;
    private MainActivity context;

    public DivideImageView read_image;
    private static ImageLoader imageLoader;
    private static ScreenSizeEntity screenSize;

    public static ReadFragment newInstance(int page, String url, ImageLoader loader, ScreenSizeEntity screenSizeEntity) {
        Bundle args = new Bundle();
        args.putInt("page", page + 1);
        args.putString("url", url);
        imageLoader = loader;
        screenSize = screenSizeEntity;
        ReadFragment fragment = new ReadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        context=(MainActivity)getActivity();
        layout_loading = (RelativeLayout) view.findViewById(R.id.layout_loading);
        read_loading_page = (TextView) view.findViewById(R.id.read_loading_page);
        read_loading_text = (TextView) view.findViewById(R.id.read_loading_text);
        read_loading_anim = (ImageView) view.findViewById(R.id.read_loading_anim);
        read_image = (DivideImageView) view.findViewById(R.id.read_image);

        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_loading);
        read_loading_anim.startAnimation(animation);
        setLoadingPage(getArguments().getInt("page"));
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), Integer.parseInt(getArguments().getString("url")), ops);
//        ops.inSampleSize = carculateSampleSize(ops, screenSize.getWidth()/4, screenSize.getHeight()/4);
        ops.inSampleSize=2;
        ops.inJustDecodeBounds = false;
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),Integer.parseInt(getArguments().getString("url")),ops);
        setImage(bitmap);
        if(read_image.isDivide())
        imageLoader.cache.addBitmaptoCache("horizontal"+getArguments().getString("url"),bitmap);
        else
            imageLoader.cache.addBitmaptoCache(getArguments().getString("url"),bitmap);
//        loadImage(getArguments().getString("url"));
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cartoon_read_pic, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setdivideImage(Bitmap bitmap){
        read_image.setImageBitmap(bitmap);
    }
    public void loadImage(String url) {
        Log.i(LOG_TAG, url);
        String Direction=null;
        if(read_image.isDivide()) {
            Direction = "hrizontal";
            ViewGroup.LayoutParams layoutParams=read_image.getLayoutParams();
            layoutParams.width=read_image.getWidth()*2;
            read_image.setLayoutParams(layoutParams);
        }
        Bitmap bitmap = imageLoader.loadBitmapfromCache(url,Direction);
        if (bitmap == null)
            if(read_image.isDivide())
                bitmap=imageLoader.divideBitmap(url,Direction);
            else
            imageLoader.loadBitmapfromNet(url, screenSize.getWidth(), screenSize.getHeight(), new ImageLoader.DownloadCallback() {
                @Override
                public void dealBitmap(Bitmap bitmap,String url) {
                    setImage(bitmap);
                }
            });
        else
            setImage(bitmap);
    }

    public void setImage(Bitmap bitmap) {
        if (bitmap == null)
            read_loading_text.setText(R.string.read_loading_failed);
        else {
            read_image.setImageBitmap(bitmap);
            layout_loading.setVisibility(View.GONE);
        }
    }

    public void setLoadingPage(int page) {
        read_loading_page.setText(page + "");
    }
    private class divideViewTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float x=0;
            float y=0;
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    x=event.getX();
                    y=event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    if (x > screenSize.getWidth() / 3 && x < screenSize.getWidth() * 2 / 3 && y > screenSize.getHeight() / 5 && y < screenSize.getHeight() * 4 / 5) {
                        context.menuSwitch();
                    } else {
                        if (x > screenSize.getWidth() / 3 && x < screenSize.getWidth() * 2 / 3 && y < screenSize.getHeight() / 5)
                            context.trunPageView(context.status.getPage() - 1);
                        else if (x > screenSize.getWidth() / 3 && x < screenSize.getWidth() * 2 / 3 && y > screenSize.getHeight() * 4 / 5)
                            context.trunPageView(context.status.getPage() + 1);
                        else if ((x < screenSize.getWidth() / 3 && context.status.isLeftHandTP()) || (x > screenSize.getWidth() * 2 / 3 && !context.status.isLeftHandTP()))
                            context.trunPageView(context.status.getPage() + 1);
                        else
                            context.trunPageView(context.status.getPage() - 1);
                        if (context.isMenuShow)
                            context.menuSwitch();
                    }
                    return true;
            }
            return false;
        }
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
        Log.i("loader_pic", samplesize + "samplesize");
        return samplesize;
    }
}
