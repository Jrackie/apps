package jrackie.libs.deviceinfor;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Administrator on 2016/1/20.
 */
public class DeviceInfo {
    public static ScreenSizeEntity getScreenSize(Context context){
        ScreenSizeEntity screenSize=new ScreenSizeEntity();
        DisplayMetrics metrics= context.getApplicationContext().getResources().getDisplayMetrics();
        screenSize.setWidth(metrics.widthPixels);
        screenSize.setHeight(metrics.heightPixels);
        return screenSize;
    }
}
