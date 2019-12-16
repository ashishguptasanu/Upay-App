package volunteer.upay.com.upay.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import volunteer.upay.com.upay.R;

public class WebviewActivity extends AppCompatActivity {

    WebView wv1;
    String url;
    ProgressDialog progressDialog;
    String firebaseToken;
    private ProgressBar progressBar;
    private TextView loading_status;

    public static void open(@NonNull Context context, @NonNull String url, @NonNull String label) {
        Intent intentFee = new Intent(context, WebviewActivity.class);
        intentFee.putExtra("url_web_view", url);
        intentFee.putExtra("label", label);
        context.startActivity(intentFee);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        url = getIntent().getStringExtra("url_web_view");
        if (getIntent().getStringExtra("label") != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle(getIntent().getStringExtra("label"));
        }
        progressBar = findViewById(R.id.progressBar);
        loading_status = findViewById(R.id.loading_status);
        wv1 = findViewById(R.id.web_view);
        wv1.getSettings().setLoadsImagesAutomatically(true);
        wv1.getSettings().setJavaScriptEnabled(true);
        wv1.getSettings().setAppCacheEnabled(true);
        wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv1.setWebViewClient(new MyBrowser());
        wv1.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                                        String contentDisposition, String mimetype,
                                        long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        firebaseToken = FirebaseInstanceId.getInstance().getToken();
        String postData = null;
        try {
            postData = "device_type=" + URLEncoder.encode("android", "UTF-8") + "&firebase_token=" + URLEncoder.encode(firebaseToken, "UTF-8");
            Log.d("dataPosted", "True");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        wv1.postUrl(url, postData.getBytes());
        wv1.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                view.setVisibility(View.GONE);
                showProgress("", "Please Wait..");
                view.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByTagName('header')[0];"
                        + "head.parentNode.removeChild(head);" + "console.log('ashish');" +
                        "})()");
                view.loadUrl("javascript:(function() { " +
                        "var footer = document.getElementsByTagName('footer')[0];"
                        + "footer.parentNode.removeChild(footer);" +
                        "})()");
                view.loadUrl("javascript:(function() { " +
                        "var nav = document.getElementsByTagName('nav')[0];"
                        + "nav.parentNode.removeChild(nav);" +
                        "})()");
                view.loadUrl("javascript:(function() { " +
                        "var set = document.getElementsByClassName('banner');"
                        + "set[0].style.margin = '0px';" +
                        "})()");
                if (progress == 100) {
                    while (!injectJavaScript(view)) {
                        view.setVisibility(View.VISIBLE);
                    }
                    progressBar.setVisibility(View.GONE);
                    loading_status.setVisibility(View.GONE);
                } else {
                    loading_status.setText("Loading... (" + progress + "%)");
                    progressBar.setVisibility(View.VISIBLE);
                    loading_status.setVisibility(View.VISIBLE);
                }
            }
        });
        wv1.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:(function() { " +
                        "var head = document.getElementsByTagName('header')[0];"
                        + "head.parentNode.removeChild(head);" + "console.log('ashish');" +
                        "})()");
                view.loadUrl("javascript:(function() { " +
                        "var footer = document.getElementsByTagName('footer')[0];"
                        + "footer.parentNode.removeChild(footer);" +
                        "})()");
                view.loadUrl("javascript:(function() { " +
                        "var nav = document.getElementsByTagName('nav')[0];"
                        + "nav.parentNode.removeChild(nav);" +
                        "})()");
                view.loadUrl("javascript:(function() { " +
                        "var set = document.getElementsByClassName('banner');"
                        + "set[0].style.margin = '0px';" +
                        "})()");
                progressDialog.dismiss();
                view.setVisibility(View.VISIBLE);
            }
        });
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_view_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_home) {
            Intent intent = new Intent(this, FullscreenActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }*/


    @Override
    public void onBackPressed() {
        if (wv1 != null && wv1.canGoBack()) {
            wv1.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String postData = null;
            try {
                postData = "device_type=" + URLEncoder.encode("android", "UTF-8") + "&firebase_token=" + URLEncoder.encode(firebaseToken, "UTF-8");
                Log.d("dataPosted", "True");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            view.postUrl(url, postData.getBytes());
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (wv1.canGoBack()) {
                        wv1.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean injectJavaScript(WebView view) {
        view.loadUrl("javascript:(function() { " +
                "var head = document.getElementsByTagName('header')[0];"
                + "head.parentNode.removeChild(head);" + "console.log('ashish');" +
                "})()");
        view.loadUrl("javascript:(function() { " +
                "var footer = document.getElementsByTagName('footer')[0];"
                + "footer.parentNode.removeChild(footer);" +
                "})()");
        view.loadUrl("javascript:(function() { " +
                "var nav = document.getElementsByTagName('nav')[0];"
                + "nav.parentNode.removeChild(nav);" +
                "})()");
        view.loadUrl("javascript:(function() { " +
                "var set = document.getElementsByClassName('banner');"
                + "set[0].style.margin = '0px';" +
                "})()");
        return true;
    }

    private void showProgress(String title, String msg) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msg);
        //progressDialog.show();
    }
}
