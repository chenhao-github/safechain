package com.code.safechain.ui.consult;

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
public class ConsultFragment extends Fragment {


    @BindView(R.id.webview)
    WebView mWebview;
    private Unbinder unbinder;
    private WebSettings mSettings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consult, null);
        unbinder = ButterKnife.bind(this, view);

        mSettings = mWebview.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebview.setWebViewClient(new WebViewClient());

//        mWebview.loadUrl("http://wx.80soho.com/#/consulation");
        mWebview.loadUrl("http://h5.safe-chain.io/#/consulation");
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
