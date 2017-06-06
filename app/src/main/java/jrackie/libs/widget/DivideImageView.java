package jrackie.libs.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/4/15.
 */
public class DivideImageView extends ImageView {
    private boolean isDivide=false;

    public boolean isDivide() {
        return isDivide;
    }



    public DivideImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DivideImageView(Context context) {
        super(context);
    }

    public void setIsDivide(boolean isDivide) {
        this.isDivide = isDivide;
    }

    public DivideImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}
