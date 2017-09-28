package com.example.talit.projetotcc.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.volley.CustomVolleyRequest;
import com.example.talit.projetotcc.volley.SliderUtils;

import java.util.List;

/**
 * Created by talit on 12/08/2017.
 */

public class OfertasAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater lf;
    private List<SliderUtils>sliderImg;
    private ImageLoader imageLoader;
    private Integer imagens[] = {R.drawable.quueima,R.drawable.quueima,R.drawable.quueima};

    /*public OfertasAdapter(List<SliderUtils> sliderImg, Context context){

        this.sliderImg = sliderImg;
        this.context  = context;
    }*/
    public OfertasAdapter(Context context){

        this.context  = context;
    }
    @Override
    public int getCount() {
        return imagens.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    public Object instantiateItem(ViewGroup container, int position){
        lf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lf.inflate(R.layout.custom_imageview_slide,null);

        //SliderUtils utils = sliderImg.get(position);

        ImageView im = (ImageView) view.findViewById(R.id.imageView5);
        //imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        //imageLoader.get(utils.getSlideUrl(),ImageLoader.getImageListener(im,R.drawable.pit_stop,R.drawable.passatempo_morango));
        im.setImageResource(imagens[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);

        return view;
    }
    public void destroyItem(ViewGroup container, int position,Object object ){
        ViewPager vp = (ViewPager) container;
        View view = (View)object;
        vp.removeView(view);

    }
}
