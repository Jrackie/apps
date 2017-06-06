package jrackie.apps.cartoon.read;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jrackie.apps.R;

/**
 * Created by Administrator on 2016/4/15.
 */
public class EpisodeFragment extends Fragment{
    @Nullable
    private GridView grid;
    private TextView total;
    private TextView reflash;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.episode_show_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        grid=(GridView)view.findViewById(R.id.grid);
        total=(TextView)view.findViewById(R.id.total_episode);
        reflash=(TextView)view.findViewById(R.id.reflash);
        List<episodeInfor> list= new ArrayList<episodeInfor>();
//        grid.setAdapter(new GridAdapter(getContext(),));
        super.onActivityCreated(savedInstanceState);
    }
}
