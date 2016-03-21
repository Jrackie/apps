package jrackie.apps.cartoon.read;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import jrackie.libs.config.ServerConfig;
import jrackie.libs.deviceinfo.ScreenSizeEntity;
import jrackie.libs.imageloader.ImageLoader;

/**
 * Created by Administrator on 2016/3/18.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String LOG_TAG = "Main_viewPagerAdapter";

    private ImageLoader loader;
    private String[] urls;
    private ScreenSizeEntity screenSize;

    public ViewPagerAdapter(FragmentManager fm, ImageLoader loader,String[] urls,ScreenSizeEntity screenSize) {
        super(fm);
        this.loader = loader;
        this.urls = urls;
        this.screenSize=screenSize;
    }

    @Override
    public Fragment getItem(int position) {
        return ReadFragment.newInstance(position,urls[position],loader,screenSize);
    }

    @Override
    public int getCount() {
        return urls.length;
    }
}
