package com.ywzheng.web.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by yongwei on 2016/9/13.
 */

public class WebViewHelper {

    private static ProgressBar mProgressBar;

    @SuppressLint("SetJavaScriptEnabled")
    public static void init(Context context, WebView webView, ProgressBar progressBar, String url) {

        mProgressBar = progressBar;
        WebSettings mSettings = webView.getSettings();

        //设置WebView属性，能够执行Javascript脚本
        mSettings.setJavaScriptEnabled(true);

        // 保留缩放功能但是隐藏缩放控件
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);
        mSettings.setDisplayZoomControls(false);

        // 设置可以访问文件
        mSettings.setAllowFileAccess(true);
        // 启用数据库
        mSettings.setDatabaseEnabled(true);
        String dir = context.getApplicationContext()
                .getDir("database", MODE_PRIVATE).getPath();
        // 启用地理定位
        mSettings.setGeolocationEnabled(true);
        // 设置定位的数据库路径
        mSettings.setGeolocationDatabasePath(dir);

        mSettings.setLoadWithOverviewMode(true);
        mSettings.setDefaultTextEncodingName("UTF-8");
        mSettings.setNeedInitialFocus(false);// 禁止webview上面控件获取焦点(黄色边框)
        mSettings.setDomStorageEnabled(true);//使用localStorage则必须打开
        mSettings.setBlockNetworkImage(false);// 首先阻塞图片，让图片不显示,页面加载好以后，在放开图片：
        mSettings.setSupportMultipleWindows(false);// 设置同一个界面
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新的窗口
        mSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        mSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染等级
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webView.canGoBack();
        webView.setWebChromeClient(new ChromeClient());
        webView.setWebViewClient(new Client());
        if (hasNetWorkConection(context)) {
            // 根据cache-control决定是否从网络上取数据。
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //优先加载缓存
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        //webview设置长按监听
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= 19) {
            webView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            webView.getSettings().setLoadsImagesAutomatically(false);
        }

        webView.loadUrl(url);
    }

    private static class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            mProgressBar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    private static class Client extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
            if (url != null)
                view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            view.setVisibility(View.VISIBLE);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            // 这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，
        }
    }

    /**
     * 判断是否具有网络连接
     */
    public static final boolean hasNetWorkConection(Context ctx) {
        // 获取连接活动管理器
        final ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取连接的网络信息
        final NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable());
    }
}
