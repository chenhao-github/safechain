package com.code.safechain.ui.my;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.code.safechain.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OnlineServiceActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView mWebview;
    private WebSettings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_service);
        ButterKnife.bind(this);

        mSettings = mWebview.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebview.setWebViewClient(new WebViewClient());

        //不使用缓存
        mSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
// 设置可以支持缩放
        mSettings.setSupportZoom(true);
// 设置出现缩放工具
        mSettings.setBuiltInZoomControls(true);
//扩大比例的缩放
        mSettings.setUseWideViewPort(true);
        // 缩放至屏幕的大小 ,自由缩放 ，隐藏缩放提示
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);
        mSettings.setDisplayZoomControls(false);
        //支持自动加载图片
        mSettings.setLoadsImagesAutomatically(true);
//设置编码格式
        mSettings.setDefaultTextEncodingName("utf-8");
//自适应屏幕
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mSettings.setLoadWithOverviewMode(true);

//        mWebview.loadUrl("http://wx.80soho.com/#/ecology");
        mWebview.loadUrl("http://ddt.zoosnet.net/LR/Chatpre.aspx?id=DDT78423483&lng=cn");
    }

}
