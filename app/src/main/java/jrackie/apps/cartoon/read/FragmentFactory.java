package jrackie.apps.cartoon.read;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

import jrackie.libs.deviceinfo.ScreenSizeEntity;
import jrackie.libs.imageloader.ImageLoader;

/**
 * Created by Administrator on 2016/3/18.
 */
public class FragmentFactory {
    private ImageLoader loader;
    private ScreenSizeEntity entity;

    public FragmentFactory(ImageLoader loader, ScreenSizeEntity entity) {
        this.loader = loader;
        this.entity = entity;
    }

}
