package com.code.safechain.ui.ecotope;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.code.safechain.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @Auther: hchen
 * @Date: 2020/7/4 0004
 * @Description:
 */
public class EcotopeFragment extends Fragment {


    @BindView(R.id.webview)
    WebView mWebview;
    private View view;
    private Unbinder unbinder;
    private WebSettings mSettings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ecotope, null);
        unbinder = ButterKnife.bind(this, view);

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

        mWebview.loadUrl("http://wx.80soho.com/#/ecology");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
