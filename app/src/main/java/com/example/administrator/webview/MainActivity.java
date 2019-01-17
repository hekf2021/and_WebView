package com.example.administrator.webview;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnBack;
    private Button btnGo;
    private Button btnZoomIn;
    private Button btnZoonOut;
    private WebView webView;
    private final String URL ="https://www.sina.com.cn/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView)this.findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setMessage(message);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                builder.create().show();
                return false;
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                //重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        webView.addJavascriptInterface(new JsObject(),"jsObject");//将JavaScript方法爆露给html页面

        webView.loadUrl(URL);

        btnBack = (Button)this.findViewById(R.id.button1);
        btnGo = (Button)this.findViewById(R.id.button2);
        btnZoomIn = (Button)this.findViewById(R.id.button3);
        btnZoonOut = (Button)this.findViewById(R.id.button4);
        btnGo.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnZoomIn.setOnClickListener(this);
        btnZoonOut.setOnClickListener(this);

    }

    /**
     * 申明JavaScript方法
     */
    public class JsObject{
        @JavascriptInterface
        public String getMessage(String name,String pswd){
            return name +":"+pswd;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                if(webView.canGoBack()){
                    webView.goBack();
                }
                break;
            case R.id.button2:
                if(webView.canGoForward()){
                    webView.goForward();
                }
                break;
            case R.id.button3:
                if(webView.canZoomIn()){
                    webView.zoomIn();
                }
                break;
            case R.id.button4:
                if(webView.canZoomOut()){
                    webView.zoomOut();
                }
                break;
        }
    }
}

