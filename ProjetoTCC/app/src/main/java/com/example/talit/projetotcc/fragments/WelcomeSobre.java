package com.example.talit.projetotcc.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.activities.LoginCliente;

/**
 * Created by talit on 05/03/2017.
 */

public class WelcomeSobre extends Fragment {

    private static ImageView m2;
    private static View v;

    public WelcomeSobre(){

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_sobre, container, false);
        //m2 = (ImageView) v.findViewById(R.id.iv_f2_2);
        //startAnimation();
       /* v.findViewById(R.id.btn_ingressar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginCliente.class));
            }
        });*/
        return v;
    }

    public static void startAnimation(){


        ViewCompat.animate(m2)
                .translationX(pxFromDp(v.getContext(),250.0f))
                .alpha(0.8f)
                .setStartDelay(300)
                .setDuration(800).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();

    }
    public static void restartAnim(){
        ViewCompat.animate(m2)
                .translationX(0)
                .alpha(0)
                .setStartDelay(0)
                .setDuration(10).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();
    }
    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
