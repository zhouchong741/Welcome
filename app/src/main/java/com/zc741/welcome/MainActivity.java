package com.zc741.welcome;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.dd.CircularProgressButton;
import com.dd.morphingbutton.MorphingButton;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

import tyrantgit.explosionfield.ExplosionField;

public class MainActivity extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;
    private CircularProgressButton mCircularProgressButton;
    private CircularProgressButton mCircularButton1;
    private CircularProgressButton mCircularProgressButton2;
    private MorphingButton mBtnMorph;
    private NumberProgressBar mNumberProgressBar;

    //来自网络的图片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addStoragePermission();
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.ic_account_circle_white_24dp, "home"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "home"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_add_shopping_cart_white_24dp, "home"))
                .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "home"))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });


        //
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        mNumberProgressBar = (NumberProgressBar) findViewById(R.id.numberProgressBar);

        //mProgressBar.setProgress(10);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.VISIBLE);
        Button button = (Button) findViewById(R.id.button);

        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoad();
            }
        });

        Button bubble = (Button) findViewById(R.id.bubble);
        BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(MainActivity.this).inflate(R.layout.bubble_layout, null);
        final PopupWindow popupWindow = BubblePopupHelper.create(MainActivity.this, bubbleLayout);

        assert bubble != null;
        bubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location = new int[2];
                v.getLocationInWindow(location);
                popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, location[0], v.getHeight() + location[1]);
            }
        });


        assert imageView != null;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExplosionField explosionField = ExplosionField.attach2Window(MainActivity.this);
                explosionField.explode(v);
                imageView.setVisibility(View.GONE);
            }
        });

        Button to = (Button) findViewById(R.id.to);
        assert to != null;
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            }
        });




        /*PhotoView photoView = (PhotoView) findViewById(R.id.photoView);
        photoView.enable();
        Info info = photoView.getInfo();
        //Info info = PhotoView.getImageViewInfo(ImageView);
        photoView.animaFrom(info);
        photoView.animaTo(info, new Runnable() {
            @Override
            public void run() {

            }
        });
        photoView.setAnimaDuring(400);
        int d = photoView.getAnimaDuring();
        float maxScale = photoView.getMaxScale();
        photoView.setMaxScale(maxScale);*/
        //photoView.setInterpolator();


        //
        mCircularProgressButton = (CircularProgressButton) findViewById(R.id.btnWithText);
        mCircularButton1 = (CircularProgressButton) findViewById(R.id.circularButton1);
        mCircularProgressButton2 = (CircularProgressButton) findViewById(R.id.circularButton2);

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void addStoragePermission() {
        int requestPhoneState = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (requestPhoneState != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                System.out.println("权限申请了");
            } else {
                // Permission Denied
                System.out.println("权限被拒绝了");
                finish();
            }
        }
    }
    
    private void downLoad() {
        String url = "http://www.zc741.com/weather/weather.apk";
        String targetPath = Environment.getExternalStorageDirectory().getPath() + "/weather.apk";
        HttpUtils utils = new HttpUtils();
        utils.download(url, targetPath, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                System.out.println("success");
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);

                long percent = current * 100 / total;
                System.out.println(percent);
                mProgressBar.setProgress((int) percent);
                mNumberProgressBar.setProgress((int) percent);
                mCircularProgressButton.setProgress((int) percent);
                mCircularButton1.setProgress((int) percent);
                mCircularProgressButton2.setProgress((int) percent);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                System.out.println("error=" + e);
            }
        });
    }


}
