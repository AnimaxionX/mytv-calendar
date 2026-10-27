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
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(false);
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        webView.setInitialScale(0);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        settings.setMediaPlaybackRequiresUserGesture(false);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {
            @Override public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url != null && url.startsWith("https://animaxionx.github.io/mytv-calendar/")) {
                    String js = "(function(){"
                        + "var m=document.querySelector('meta[name=viewport]');"
                        + "if(!m){m=document.createElement('meta');m.name='viewport';document.head.appendChild(m);}"
                        + "m.content='width=device-width,initial-scale=1,maximum-scale=1,viewport-fit=cover';"
                        + "document.documentElement.classList.add('android-app');"
                        + "if(!document.getElementById('android-app-mobile-fix')){"
                        + "var c=document.createElement('style');c.id='android-app-mobile-fix';"
                        + "c.textContent='html.android-app,html.android-app body{width:100%;max-width:100%;overflow-x:hidden}'"
                        + "+'html.android-app .shell{display:block!important;min-height:0!important}'"
                        + "+'html.android-app .side{display:none!important}'"
                        + "+'html.android-app .content{width:100%!important;max-width:100%!important;min-width:0!important;padding:16px 12px 96px!important;overflow-x:hidden!important}'"
                        + "+'html.android-app .top{height:72px!important;padding:9px 10px!important;position:sticky!important;top:0!important;z-index:100!important}'"
                        + "+'html.android-app .word{font-size:17px!important}'"
                        + "+'html.android-app .tag{font-size:11px!important}'"
                        + "+'html.android-app .mobile-nav{display:grid!important;grid-template-columns:repeat(5,minmax(0,1fr))!important;position:fixed!important;bottom:0!important;left:0!important;right:0!important;width:100%!important;height:72px!important;padding:4px!important;z-index:10000!important;background:#e7f5ff!important}'"
                        + "+'html.android-app .mobile-nav button{min-width:0!important;font-size:10px!important}'"
                        + "+'html.android-app .stats{display:grid!important;grid-template-columns:repeat(3,minmax(0,1fr))!important;gap:8px!important}'"
                        + "+'html.android-app .stat{min-width:0!important;padding:10px 8px!important}'"
                        + "+'html.android-app .home-grid{display:grid!important;grid-template-columns:repeat(2,minmax(0,1fr))!important;gap:10px!important}'"
                        + "+'html.android-app .home-grid>.empty{grid-column:1/-1!important;overflow-wrap:normal!important;word-break:normal!important}'"
                        + "+'html.android-app .cal-grid,html.android-app .cal-week{width:100%!important;min-width:0!important}'"
                        + "+'html.android-app .card{max-width:100%!important;overflow-wrap:break-word!important}';"
                        + "document.head.appendChild(c);}"
                        + "})();";
                    view.evaluateJavascript(js, null);
                }
            }
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
