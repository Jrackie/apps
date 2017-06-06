package jrackie.apps.cartoon.read;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import jrackie.apps.R;

/**
 * Created by Administrator on 2016/4/15.
 */
public class GridAdapter extends BaseAdapter{
    Context context;
    String[] list;
    public GridAdapter(Context context,String[] list){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder=null;
        if(convertView==null)
        {
            viewholder=new Viewholder();
            convertView= LayoutInflater.from(context).inflate(R.layout.episode_item,parent,false);
            viewholder.textView=(TextView)convertView.findViewById(R.id.episode_text);
            convertView.setTag(viewholder);
        }else
            viewholder=(Viewholder)convertView.getTag();
        viewholder.textView.setText(list[position]);
        viewholder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] urls=null;
                if(((TextView)v).getText()=="166")
                urls=new String[]{R.drawable.a1661+"",R.drawable.a1662+"",R.drawable.a1663+"",R.drawable.a1664+"",R.drawable.a1665+"",R.drawable.a1666+"",R.drawable.a1667+"",R.drawable.a1668+"",R.drawable.a1669+"",R.drawable.a16610+"",R.drawable.a16611+"",R.drawable.a16612+"",R.drawable.a16613+"",R.drawable.a16614+"",R.drawable.a16615+"",R.drawable.a16616+""};
                else
                    urls=null;
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("episode",((TextView)v).getText());
                intent.putExtra("cartoonname","七大罪");
                intent.putExtra("urls",urls);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
    public class Viewholder{
        TextView textView;
    }
}
