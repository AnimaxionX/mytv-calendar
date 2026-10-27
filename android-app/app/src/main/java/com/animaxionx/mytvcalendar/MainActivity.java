package com.animaxionx.mytvcalendar;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends Activity {
    private WebView webView;
    private static final String HOME = "https://animaxionx.github.io/mytv-calendar/?source=android-apk";
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(Color.rgb(24,31,49));
        getWindow().setNavigationBarColor(Color.rgb(24,31,49));
        webView = new WebView(this);
        webView.setBackgroundColor(Color.WHITE);
        webView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        setContentView(webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        settings.setMediaPlaybackRequiresUserGesture(false);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Uri uri=request.getUrl();
                String host=uri.getHost();
                if(host!=null && (host.equals("animaxionx.github.io") || host.endsWith(".supabase.co"))) return false;
                if(request.isForMainFrame()) {
                    try { startActivity(new Intent(Intent.ACTION_VIEW,uri)); } catch(Exception ignored){}
                    return true;
                }
                return false;
            }
        });
        if(state!=null)webView.restoreState(state);else webView.loadUrl(HOME);
    }
    @Override protected void onSaveInstanceState(Bundle out) { webView.saveState(out); super.onSaveInstanceState(out); }
    @Override public void onBackPressed() { if(webView.canGoBack())webView.goBack();else super.onBackPressed(); }
    @Override protected void onDestroy() { webView.destroy();super.onDestroy(); }
}
