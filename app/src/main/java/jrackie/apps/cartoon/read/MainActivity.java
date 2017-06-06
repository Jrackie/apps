package jrackie.apps.cartoon.read;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jrackie.apps.R;
import jrackie.libs.deviceinfor.DeviceInfo;
import jrackie.libs.deviceinfor.ScreenSizeEntity;
import jrackie.libs.imageloader.ImageLoader;
import jrackie.libs.widget.DivideImageView;


/**
 * Created by Administrator on 2016/2/23.
 */
public class MainActivity extends FragmentActivity {

    private static final String LOG_TAG = "MainActivity_Read";
    private float layout_top_height = 0;
    private float layout_bottom_height = 0;
    private float layout_left_width = 0;
    private float layout_right_width = 0;
    private ScreenSizeEntity screenSize;
    private ImageLoader imageLoader;

    private ImageView read_tips;
    private RelativeLayout layout_top;
    private LinearLayout layout_left;
    private LinearLayout layout_right;
    private LinearLayout layout_bottom;
    private RelativeLayout menu_layout;
    private LinearLayout episode_layout;
    private boolean menuChanging = false;


    private ViewPager cartoon_viewpager;
    private ListView cartoon_listview;
    //menu top
    private ImageView back_arrow;
    private TextView read_header_msg;
    private TextView read_save;
    private TextView read_show_tips;
    //menu left
    private CheckBox read_light_system;
    private VerticalSeekBar read_light_seekbar;
    //menu right
    private TextView read_episode;
    private TextView read_night_mode_switch;
    private TextView read_feedback;
    //menu bottom
    private TextView read_page_now;
    private VerticalSeekBar read_page_seekbar;
    private TextView read_screen_mode;
    private TextView read_direction_TP;
    private TextView read_BP_divide;
    private TextView read_hand_TP;
    private TextView read_settings;
    //from intent
    private int episode=0;
    private String cartoonName=null;
    private boolean stateleft=true;
    private String[] urls;
    //tips show record
    public boolean isMenuShow = true;
    public ReadStatus status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        // urls=new String[]{"20151015053100ShuDong1.png","20151015053100ShuDong2.png","20151015053100ShuDong3.png","20151015053100ShuDong4.png","20151015053100ShuDong5.png","20151015053100ShuDong6.png"};
        //测试数据
        urls = new String[]{"http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F6c224f4a20a44623f67eab5a9922720e0cf3d756.jpg&thumburl=http%3A%2F%2Fimg5.imgtn.bdimg.com%2Fit%2Fu%3D3927708351%2C2777334840%26fm%3D21%26gp%3D0.jpg",
                "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fb3fb43166d224f4a476e973308f790529822d11f.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D2036318443%2C3183604534%26fm%3D21%26gp%3D0.jpg",
                "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fimages.ali213.net%2Fpicfile%2Fpic%2F2010-01-04%2F16152586736.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1939547636%2C1595111580%26fm%3D21%26gp%3D0.jpg",
                "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fimgstore.cdn.sogou.com%2Fapp%2Fa%2F100540002%2F834169.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D2134535013%2C2517280499%26fm%3D21%26gp%3D0.jpg",
                "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fa.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fd8f9d72a6059252d34fd59c9349b033b5ab5b911.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3463529391%2C2236922801%26fm%3D21%26gp%3D0.jpg",
                "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fimages.ali213.net%2Fpicfile%2Fpic%2F2010-01-04%2F1645325308.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D435642940%2C3423835998%26fm%3D21%26gp%3D0.jpg"};
//        urls = new String[]{"a0001.jpg","a0002.jpg","a0003.jpg","a0004.jpg","a0005.jpg","a0006.jpg","a0007.jpg","a0008.jpg","a0009.jpg","a0010.jpg","a0011.jpg","a0012.jpg","a0013.jpg","a0014.jpg","a0015.jpg","a0016.jpg","a0017.jpg","a0018.jpg","a0019.jpg"};
        Intent intent=getIntent();
        cartoonName=intent.getStringExtra("cartoonname");
        episode=intent.getIntExtra("episode", 0);
        urls=intent.getStringArrayExtra("urls");
        if(cartoonName==null) {
            cartoonName = "七大罪";
            episode = 166;
            urls = new String[]{R.drawable.a1661 + "", R.drawable.a1662 + "", R.drawable.a1663 + "", R.drawable.a1664 + "", R.drawable.a1665 + "", R.drawable.a1666 + "", R.drawable.a1667 + "", R.drawable.a1668 + "", R.drawable.a1669 + "", R.drawable.a16610 + "", R.drawable.a16611 + "", R.drawable.a16612 + "", R.drawable.a16613 + "", R.drawable.a16614 + "", R.drawable.a16615 + "", R.drawable.a16616 + ""};
        }
        screenSize = DeviceInfo.getScreenSize(this);
        status = getStatus();
        imageLoader = new ImageLoader(this);
        imageLoader.setIsCacheToDisk(true);

        initViews();

        read_tips.setVisibility(View.INVISIBLE);


        read_tips.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                read_tips.setVisibility(View.GONE);
                return true;
            }
        });
        read_show_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                read_tips.setVisibility(View.VISIBLE);
            }
        });
        read_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Direction = null;
                if (status.isHorizontalTP()) {
                    if (((ReadFragment) cartoon_viewpager.getAdapter().instantiateItem(cartoon_viewpager, status.getPage())).read_image.isDivide())
                        Direction = "horizontal";
                } else if (((ListViewAdapter.ViewHolder) (((ListViewAdapter) cartoon_listview.getAdapter()).getView(status.getPage(), cartoon_listview.getChildAt(status.getPage() - cartoon_listview.getFirstVisiblePosition()), cartoon_listview)).getTag()).read_image.isDivide()) {
                    Direction = "vertical";
                }
                Bitmap bitmap = null;
                File f = new File("/sdcard/LRcartoon/", cartoonName + episode + status.getPage() + ".png");
                f.mkdirs();
                if (f.exists()) {
                    f.delete();
                }
                try {
                    FileOutputStream out = new FileOutputStream(f);
                    bitmap = imageLoader.loadBitmapfromCache(urls[status.getPage()], Direction);
                    if (bitmap == null)
                        throw new FileNotFoundException();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
                    out.flush();
                    out.close();
                    Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(MainActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Log.i(LOG_TAG, "io failed");
                }
            }
        });
        read_light_system.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i(LOG_TAG, "check" + isChecked);
                if (!status.isNightMode()) {
                    if (isChecked)
                        changeBrightness(-1);
                    else
                        changeBrightness(read_light_seekbar.getProgress());
                }
            }
        });
        read_light_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!read_light_system.isChecked() && !status.isNightMode())
                    changeBrightness(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        read_episode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(episode_layout.getVisibility()==View.GONE)
                episode_layout.setVisibility(View.VISIBLE);
                else
                    episode_layout.setVisibility(View.GONE);
            }
        });
        read_night_mode_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!status.isNightMode()) {
                    changeBrightness(0.1f);
                    read_night_mode_switch.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.read_day, 0, 0);
                } else {
                    changeBrightness(-1);
                    read_night_mode_switch.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.read_night, 0, 0);
                }
                status.setNightMode(!status.isNightMode());
            }
        });

        read_direction_TP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.isHorizontalTP()) {
                    status.setIsHorizontalTP(false);
                    setReadDirectionView();
                    menuSwitch();
                } else {
                    status.setIsHorizontalTP(true);
                    setReadDirectionView();
                    menuSwitch();
                }
            }
        });
        read_page_seekbar.setMax(urls.length);
        read_page_seekbar.setProgress(status.getPage() + 1);
        read_page_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cartoon_viewpager.setCurrentItem(progress - 1);
                cartoon_listview.setSelection(progress - 1);
                read_page_now.setText(progress + " / " + urls.length);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        read_BP_divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.isHorizontalTP()) {
                    ReadFragment f = (ReadFragment) cartoon_viewpager.getAdapter().instantiateItem(cartoon_viewpager, status.getPage());
                    if (!f.read_image.isDivide()) {
                        Bitmap bitmap = imageLoader.loadBitmapfromCache(urls[status.getPage()], "horizontal");
                        if (bitmap == null)
                            bitmap = imageLoader.divideBitmap(urls[status.getPage()], "horizontal");
                        f.read_image.setIsDivide(true);
                        ViewGroup.LayoutParams layoutParams = f.read_image.getLayoutParams();
                        layoutParams.width = f.read_image.getWidth() * 2;
                        f.read_image.setLayoutParams(layoutParams);
                        f.setdivideImage(bitmap);
                    } else {
                        Bitmap bitmap = imageLoader.loadBitmapfromCache(urls[status.getPage()], null);
                        f.read_image.setIsDivide(false);
                        ViewGroup.LayoutParams layoutParams = f.read_image.getLayoutParams();
                        layoutParams.width = f.read_image.getWidth() / 2;
                        f.read_image.setLayoutParams(layoutParams);
                        f.setdivideImage(bitmap);
                    }

                } else {
                    View view = ((ListViewAdapter) cartoon_listview.getAdapter()).getView(status.getPage(), cartoon_listview.getChildAt(status.getPage() - cartoon_listview.getFirstVisiblePosition()), cartoon_listview);
                    ListViewAdapter.ViewHolder holder = (ListViewAdapter.ViewHolder) view.getTag();
                    if (!holder.read_image.isDivide()) {
                        Bitmap bitmap = imageLoader.loadBitmapfromCache(urls[status.getPage()], "vertical");
                        holder.read_image.setIsDivide(true);
                        if (bitmap == null)
                            bitmap = imageLoader.divideBitmap(urls[status.getPage()], "vertical");
                        ViewGroup.LayoutParams layoutParams = holder.read_image.getLayoutParams();
                        layoutParams.width = holder.read_image.getHeight() * 2;
                        holder.read_image.setLayoutParams(layoutParams);
                        ((ListViewAdapter) cartoon_listview.getAdapter()).setImage(bitmap, holder);
                    } else {
                        holder.read_image.setIsDivide(false);
                        ViewGroup.LayoutParams layoutParams = holder.read_image.getLayoutParams();
                        layoutParams.width = holder.read_image.getHeight() / 2;
                        holder.read_image.setLayoutParams(layoutParams);
                        cartoon_listview.getAdapter().notifyAll();
                    }

                }
            }
        });
        read_hand_TP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.isLeftHandTP()) {
                    status.setIsLeftHandTP(false);
                    setTipsView();
                    read_tips.setVisibility(View.VISIBLE);
                    menuSwitch();
                } else {
                    status.setIsLeftHandTP(true);
                    setTipsView();
                    read_tips.setVisibility(View.VISIBLE);
                    menuSwitch();
                }
            }
        });
        read_page_now.setText((cartoon_viewpager.getCurrentItem() + 1) + " / " + urls.length);
        read_light_seekbar.setProgress((cartoon_viewpager.getCurrentItem() + 1));
        GridView gridView=(GridView)findViewById(R.id.grid);
        String[] list=new String[]{"167","166"};
        gridView.setAdapter(new GridAdapter(this, list));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String[] urls=null;
                Log.i(LOG_TAG,"x");
                if(position==1)
                    urls=new String[]{R.drawable.a1661+"",R.drawable.a1662+"",R.drawable.a1663+"",R.drawable.a1664+"",R.drawable.a1665+"",R.drawable.a1666+"",R.drawable.a1667+"",R.drawable.a1668+"",R.drawable.a1669+"",R.drawable.a16610+"",R.drawable.a16611+"",R.drawable.a16612+"",R.drawable.a16613+"",R.drawable.a16614+"",R.drawable.a16615+"",R.drawable.a16616+""};
                else
                    urls=new String[]{R.drawable.a1671+"",R.drawable.a1672+"",R.drawable.a1673+"",R.drawable.a1674+"",R.drawable.a1675+"",R.drawable.a1676+"",R.drawable.a1677+"",R.drawable.a1678+"",R.drawable.a1679+"",R.drawable.a16710+"",R.drawable.a16711+"",R.drawable.a16712+"",R.drawable.a16713+"",R.drawable.a16714+"",R.drawable.a16715+"",R.drawable.a16716+"",R.drawable.a16717+"",R.drawable.a16718+"",R.drawable.a16719+""};
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                intent.putExtra("episode",((TextView)view).getText());
                intent.putExtra("cartoonname","七大罪");
                intent.putExtra("urls",urls);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                MainActivity.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setStatus(status);
    }

    private void initViews() {
        episode_layout=(LinearLayout)findViewById(R.id.esipode_layout);
        menu_layout = (RelativeLayout) findViewById(R.id.menu_layout);
        read_tips = (ImageView) findViewById(R.id.read_tips);
        layout_top = (RelativeLayout) findViewById(R.id.layout_top);
        layout_bottom = (LinearLayout) findViewById(R.id.layout_bottom);
        layout_left = (LinearLayout) findViewById(R.id.layout_left);
        layout_right = (LinearLayout) findViewById(R.id.layout_right);
        cartoon_viewpager = (ViewPager) findViewById(R.id.cartoon_viewpager);
        cartoon_listview = (ListView) findViewById(R.id.cartoon_listview);
        back_arrow = (ImageView) findViewById(R.id.read_back);
        read_header_msg = (TextView) findViewById(R.id.read_header_msg);
        read_save = (TextView) findViewById(R.id.read_save);
        read_show_tips = (TextView) findViewById(R.id.read_show_tips);
        read_light_system = (CheckBox) findViewById(R.id.read_light_system);
        read_light_seekbar = (VerticalSeekBar) findViewById(R.id.read_light_seekbar);
        read_episode = (TextView) findViewById(R.id.read_episode);
        read_night_mode_switch = (TextView) findViewById(R.id.read_night_mode_switch);
        read_feedback = (TextView) findViewById(R.id.read_feedback);
        read_page_now = (TextView) findViewById(R.id.read_page_now);
        read_page_seekbar = (VerticalSeekBar) findViewById(R.id.read_page_seekbar);
        read_screen_mode = (TextView) findViewById(R.id.read_screen_mode);
        read_direction_TP = (TextView) findViewById(R.id.read_direction_TP);
        read_BP_divide = (TextView) findViewById(R.id.read_BP_divide);
        read_hand_TP = (TextView) findViewById(R.id.read_hand_TP);
        read_settings = (TextView) findViewById(R.id.read_settings);

        cartoon_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                reflashViewTP(position);
                if (isMenuShow)
                    menuSwitch();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        cartoon_viewpager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), imageLoader, urls, screenSize));
        cartoon_listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL && isMenuShow)
                    menuSwitch();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                reflashViewTP(firstVisibleItem);
            }
        });
        cartoon_listview.setAdapter(new ListViewAdapter(this, imageLoader, urls, screenSize));
        setTipsView();
        setReadDirectionView();
        View.OnTouchListener listener = new View.OnTouchListener() {
            float x = 0;
            float y = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(LOG_TAG, event.getX() + "" + v.getTag() + event.getY());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i(LOG_TAG, event.getX() + " down " + v.getTag() + event.getY());
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        return clickTP_Menu(x, y, event.getX(), event.getY());
                }
                return false;
            }
        };
        View.OnTouchListener listener1 = new View.OnTouchListener() {
            float x = 0;
            float y = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i(LOG_TAG, event.getX() + " down " + event.getY());
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.i(LOG_TAG, event.getX() + " down " + event.getY());
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (Math.abs(event.getX() - x) < 5 && Math.abs(event.getY() - y) < 5)
                            return false;
                }
                return false;
            }
        };
        cartoon_listview.setOnTouchListener(listener);
        cartoon_viewpager.setOnTouchListener(listener);
    }

    //change window Brightness
    private void changeBrightness(float progress) {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        if (progress == -1) {
            wlp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            wlp.screenBrightness = (progress < 0 ? 1 : progress / 255f);
        }
        window.setAttributes(wlp);
    }

    //what to do when click the view
    private boolean clickTP_Menu(float x, float y, float lx, float ly) {

        if (Math.abs(lx - x) < 5 && Math.abs(ly - y) < 5) {
            Log.i(LOG_TAG, lx + " up " + ly);
            DivideImageView view=null;
            if(status.isHorizontalTP()){
                view=((ReadFragment)cartoon_viewpager.getAdapter().instantiateItem(cartoon_viewpager,status.getPage())).read_image;
            } else {
                view=((ListViewAdapter.ViewHolder)(((ListViewAdapter)cartoon_listview.getAdapter()).getView(status.getPage(),cartoon_listview.getChildAt(status.getPage() - cartoon_listview.getFirstVisiblePosition()),cartoon_listview)).getTag()).read_image;
            }
            if(!view.isDivide()||!status.isHorizontalTP()) {
                if (x > screenSize.getWidth() / 3 && x < screenSize.getWidth() * 2 / 3 && y > screenSize.getHeight() / 5 && y < screenSize.getHeight() * 4 / 5) {
                    menuSwitch();
                } else {
                    if (x > screenSize.getWidth() / 3 && x < screenSize.getWidth() * 2 / 3 && y < screenSize.getHeight() / 5)
                        trunPageView(status.getPage() - 1);
                    else if (x > screenSize.getWidth() / 3 && x < screenSize.getWidth() * 2 / 3 && y > screenSize.getHeight() * 4 / 5)
                        trunPageView(status.getPage() + 1);
                    else if ((x < screenSize.getWidth() / 3 && status.isLeftHandTP()) || (x > screenSize.getWidth() * 2 / 3 && !status.isLeftHandTP()))
                        trunPageView(status.getPage() + 1);
                    else
                        trunPageView(status.getPage() - 1);
                    if (isMenuShow)
                        menuSwitch();
                }
            }else{
               if (x > screenSize.getWidth() / 3 && x < screenSize.getWidth() * 2 / 3 && y > screenSize.getHeight() / 5 && y < screenSize.getHeight() * 4 / 5) {
                    menuSwitch();
                } else {
                    if(stateleft) {
                        if (x > screenSize.getWidth() / 3 && x < screenSize.getWidth() * 2 / 3 && y < screenSize.getHeight() / 5)
                            trunPageView(status.getPage() - 1);
                        else if (x > screenSize.getWidth() / 3 && x < screenSize.getWidth() * 2 / 3 && y > screenSize.getHeight() * 4 / 5)
                            translationAnimator(view,0,-view.getWidth()/2,true);
                        else if ((x < screenSize.getWidth() / 3 && status.isLeftHandTP()) || (x > screenSize.getWidth() * 2 / 3 && !status.isLeftHandTP()))
                            translationAnimator(view,0,-view.getWidth()/2,true);
                        else
                            trunPageView(status.getPage() - 1);
                        if (isMenuShow)
                            menuSwitch();
                    }else {
                        if (x > screenSize.getWidth() / 3 && x < screenSize.getWidth() * 2 / 3 && y < screenSize.getHeight() / 5)
                            translationAnimator(view,-view.getWidth() / 2 ,0 , true);
                        else if (x > screenSize.getWidth() / 3 && x < screenSize.getWidth() * 2 / 3 && y > screenSize.getHeight() * 4 / 5)
                            trunPageView(status.getPage() + 1);
                        else if ((x < screenSize.getWidth() / 3 && status.isLeftHandTP()) || (x > screenSize.getWidth() * 2 / 3 && !status.isLeftHandTP()))
                            trunPageView(status.getPage() + 1);
                        else
                            translationAnimator(view,-view.getWidth()/2,0,true);
                        if (isMenuShow)
                            menuSwitch();
                    }
                    stateleft=!stateleft;
                }
            }
            return true;
        }
        return false;
    }

    //read direction setter
    private void setReadDirectionView() {
//        View.OnTouchListener listener=new View.OnTouchListener() {
//            float x = 0;
//            float y = 0;
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        Log.i(LOG_TAG, event.getX() + " down " + event.getY());
//                        x = event.getX();
//                        y = event.getY();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        return clickTP_Menu(x, y, event.getX(), event.getY());
//                }
//                return false;
//            }
//        };
        if (status.isHorizontalTP()) {
//            cartoon_viewpager.setOnTouchListener(listener);
//        if(status.getEpisodeLast()==episode)
//        cartoon_viewpager.setCurrentItem(status.getPage())
            cartoon_viewpager.setVisibility(View.VISIBLE);
            cartoon_listview.setVisibility(View.GONE);
            read_direction_TP.setText(R.string.read_vertical_TP);
            read_direction_TP.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.read_vertical_tp, 0, 0);
        } else {
//            cartoon_listview.setOnTouchListener(listener);
//            if(status.getEpisodeLast()==episode)
//            cartoon_listview.setVerticalScrollbarPosition(status.getPage());
            cartoon_listview.setVisibility(View.VISIBLE);
            cartoon_viewpager.setVisibility(View.GONE);
            read_direction_TP.setText(R.string.read_horizontal_TP);
            read_direction_TP.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.read_horizontal_tp, 0, 0);
        }
    }

    public void trunPageView(int position) {
        if (status.isHorizontalTP()) {
            cartoon_viewpager.setCurrentItem(position);
        } else
            cartoon_listview.smoothScrollToPosition(position);
        stateleft=true;
    }

    //Reflash view when turn the page
    public void reflashViewTP(int position) {
        status.setPage(position);
        read_page_now.setText((position + 1) + " / " + urls.length);
        read_page_seekbar.setProgress(position + 1);
    }

    //tipsview is lefthandTP or righthandTP
    private void setTipsView() {
        if (status.isLeftHandTP()) {
            read_tips.setImageResource(R.drawable.read_left_hand_tp_tips);
            read_hand_TP.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.read_right_hand_tp, 0, 0);
            read_hand_TP.setText(R.string.read_right_hand_TP);
        } else {
            read_tips.setImageResource(R.drawable.read_right_hand_tp_tips);
            read_hand_TP.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.read_left_hand_tp, 0, 0);
            read_hand_TP.setText(R.string.read_left_hand_TP);
        }
    }

    //show menu or not
    public void menuSwitch() {
        if (!menuChanging) {
            menuChanging = true;
            Log.i(LOG_TAG, "" + isMenuShow);
            if (layout_top_height == 0) {
                layout_top_height = layout_top.getHeight();
                layout_bottom_height = layout_bottom.getHeight();
                layout_left_width = layout_left.getWidth();
                layout_right_width = layout_right.getWidth();
            }
            //Toast.makeText(this,""+layout_top_height,Toast.LENGTH_LONG).show();
            if (isMenuShow) {
                translationAnimator(layout_top, 0, -layout_top_height, false);
                translationAnimator(layout_bottom, 0, layout_bottom_height, false);
                translationAnimator(layout_left, 0, -layout_left_width, true);
                translationAnimator(layout_right, 0, layout_right_width, true);
            } else {
                translationAnimator(layout_top, -layout_top_height, 0, false);
                translationAnimator(layout_bottom, layout_bottom_height, 0, false);
                translationAnimator(layout_left, -layout_left_width, 0, true);
                translationAnimator(layout_right, layout_right_width, 0, true);
            }
            isMenuShow = !isMenuShow;
            menuChanging = false;
        }
    }

    //animator
    private void translationAnimator(final View view, float from, float to, final boolean isDirectionX) {
        ValueAnimator animatorTranslate = ValueAnimator.ofFloat(from, to);
        animatorTranslate.setDuration(400);
        animatorTranslate.setTarget(view);
        animatorTranslate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                if (isDirectionX)
                    view.setTranslationX(value);
                else
                    view.setTranslationY(value);
            }
        });
        animatorTranslate.start();
    }

    //阅读界面的状态保存
    private ReadStatus getStatus() {
        SharedPreferences sharePref = getPreferences(MODE_PRIVATE);
        int episodeLast = sharePref.getInt("episodeLast", 0);
        boolean isHorizontalTP = sharePref.getBoolean("isHorizontalTP", true);
        boolean isLandscapeMode = sharePref.getBoolean("isLandscapeMode", false);
        boolean isLeftHandTP = sharePref.getBoolean("isLeftHandTP", false);
        boolean lightFS = sharePref.getBoolean("lightFS", false);
        int lightProgress = sharePref.getInt("lightProgress", 0);
        boolean nightMode = sharePref.getBoolean("nightMode", false);
        int page = 0;
        if (episodeLast == episode)
            page = sharePref.getInt("page", 0);
        return new ReadStatus(episodeLast, isHorizontalTP, isLandscapeMode, isLeftHandTP, lightFS, lightProgress, nightMode, page);
    }

    //读取阅读界面的状态
    private void setStatus(ReadStatus status) {
        SharedPreferences sharePref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePref.edit();
        editor.putInt("episodeLast", status.getEpisodeLast());
        editor.putBoolean("isHorizontalTP", status.isHorizontalTP());
        editor.putBoolean("isLandscapeMode", status.isLandscapeMode());
        editor.putBoolean("isLeftHandTP", status.isLeftHandTP());
        editor.putBoolean("lightFS", status.isLightFS());
        editor.putInt("lightProgress", status.getLightProgress());
        editor.putBoolean("nightMode", status.isNightMode());
        editor.putInt("page", status.getPage());
        editor.commit();
    }
}
