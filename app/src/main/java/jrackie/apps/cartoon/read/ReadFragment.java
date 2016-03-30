package jrackie.apps.cartoon.read;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.jar.Attributes;

import jrackie.apps.R;
import jrackie.libs.config.ServerConfig;
import jrackie.libs.deviceinfo.ScreenSizeEntity;
import jrackie.libs.imageloader.ImageLoader;

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

    private ImageView read_image;
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
        layout_loading = (RelativeLayout) view.findViewById(R.id.layout_loading);
        read_loading_page = (TextView) view.findViewById(R.id.read_loading_page);
        read_loading_text = (TextView) view.findViewById(R.id.read_loading_text);
        read_loading_anim = (ImageView) view.findViewById(R.id.read_loading_anim);
        read_image = (ImageView) view.findViewById(R.id.read_image);

        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_loading);
        read_loading_anim.startAnimation(animation);
        setLoadingPage(getArguments().getInt("page"));
        loadImage(getArguments().getString("url"));
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cartoon_read_fragment, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void loadImage(String url) {
        Log.i(LOG_TAG, url);
        Bitmap bitmap = imageLoader.loadBitmapfromCache(url);
        if (bitmap == null)
            imageLoader.loadBitmapfromNet(url, screenSize.getWidth(), 0, new ImageLoader.DownloadCallback() {
                @Override
                public void dealBitmap(Bitmap bitmap) {
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
}
