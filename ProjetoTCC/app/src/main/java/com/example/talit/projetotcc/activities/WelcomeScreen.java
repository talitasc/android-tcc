package com.example.talit.projetotcc.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import me.drakeet.materialdialog.MaterialDialog;


import com.example.talit.projetotcc.Manifest;
import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.fragments.WelcomeApresentacao;
import com.example.talit.projetotcc.fragments.WelcomeSobre;
import com.example.talit.projetotcc.sqlight.DbConn;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

public class WelcomeScreen extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private DbConn dbconn;
    public static Activity act;
    private static ImageView m2;
    private SimpleDraweeView myDraweeView;
    private LinearLayout imaLayout;
    public static final String TAG = "LOG";
    public static final int REQUEST_PERMISSIONS_CODE = 128;
    private MaterialDialog mMaterialDialog;

    @Override
    protected void onResume() {
        super.onResume();
        String login;
        dbconn = new DbConn(WelcomeScreen.this);
        if (dbconn.selectConsumidor() != null) {
            if (dbconn.selectConsumidor().getStatus() == 2) {
                    if(dbconn.selectConsumidor().getTpAcesso() == 2 || dbconn.selectConsumidor().getTpAcesso()== 3) {
                        //Intent intent = new Intent(this, Teste.class);
                        Intent intent = new Intent(this, PaginalnicialConsumidor.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.act_welcome_screen);
        act = this;
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);
        myDraweeView = (SimpleDraweeView) findViewById(R.id.imLocais);
        imaLayout = (LinearLayout) findViewById(R.id.layoutImage);

        layouts = new int[]{
                R.layout.fragment_apresentacao,
                R.layout.fragment_sobre,
                R.layout.fragment_comece};

        addBottomDots(0);
        viewPagerAdapter = new ViewPagerAdapter();
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        DraweeController controller =
                Fresco.newDraweeControllerBuilder()
                        .setUri("res:///" + R.drawable.gifloginestabelecimento)
                        .setAutoPlayAnimations(true)
                        .build();
        myDraweeView.setController(controller);

    }


    public  void btnSkipClick(View v)
    {
            launchHomeScreen();
    }

    public  void btnNextClick(View v)
    {

        int current = getItem(1);
        if (current < layouts.length) {
            viewPager.setCurrentItem(current);
        } else {
            launchHomeScreen();
        }

    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            addGifs(position);

            if (position == layouts.length - 1) {

                btnNext.setText(getString(R.string.start));
                btnSkip.setVisibility(View.GONE);
            } else {

                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }

            if(position == 1){

                DraweeController controller =
                        Fresco.newDraweeControllerBuilder()
                                .setUri("res:///" + R.drawable.gifloginconsumidor)
                                .setAutoPlayAnimations(true)
                                .build();
                myDraweeView.setController(controller);
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dot_inactive));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[currentPage].setTextColor(getResources().getColor(R.color.dot_active));
        }
    }
    private void addGifs(int currentPage){

        imaLayout.removeAllViews();
        if(currentPage == 1){
            imaLayout.removeAllViews();
            DraweeController controller =
                    Fresco.newDraweeControllerBuilder()
                            .setUri("res:///" + R.drawable.gifloginconsumidor)
                            .setAutoPlayAnimations(true)
                            .build();
            myDraweeView.setController(controller);
            imaLayout.addView(myDraweeView);
            callAccessLocation();

        }else if(currentPage == 2){
            imaLayout.removeAllViews();
            DraweeController controller =
                    Fresco.newDraweeControllerBuilder()
                            .setUri("res:///" + R.drawable.gifloginestabelecimento)
                            .setAutoPlayAnimations(true)
                            .build();
            myDraweeView.setController(controller);
            imaLayout.addView(myDraweeView);
            callAccessVoice();

        }else if (currentPage == 0){
            imaLayout.removeAllViews();
            DraweeController controller =
                    Fresco.newDraweeControllerBuilder()
                            .setUri("res:///" + R.drawable.gifloginestabelecimento)
                            .setAutoPlayAnimations(true)
                            .build();
            myDraweeView.setController(controller);
            imaLayout.addView(myDraweeView);
        }else{
            imaLayout.removeAllViews();
        }
    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        startActivity(new Intent(this, RedirecionaPessoaJuridica.class));
        finish();
    }

    public class ViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;


        public ViewPagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
    private void callDialog( String message, final String[] permissions ){
        mMaterialDialog = new MaterialDialog(this)
                .setTitle("Permission")
                .setMessage( message )
                .setPositiveButton("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ActivityCompat.requestPermissions(WelcomeScreen.this, permissions, REQUEST_PERMISSIONS_CODE);
                        mMaterialDialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });
        mMaterialDialog.show();
    }

    public void callAccessLocation() {
        Log.i(TAG, "callAccessLocation()");

        if( ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
                callDialog("É preciso a permissão para apresentação dos eventos locais.", new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION});
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_CODE);
            }
        }

    }

    public void callAccessVoice() {
        Log.i(TAG, "callAccessLocation()");

        if( ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECORD_AUDIO)){
                callDialog("É preciso a permissão para utilizar os atalhos de voz.", new String[]{android.Manifest.permission.RECORD_AUDIO});
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSIONS_CODE);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
