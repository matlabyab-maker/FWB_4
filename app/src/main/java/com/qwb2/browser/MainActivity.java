package com.fwb.browser;

import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private EditText urlBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Main Container
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        // --- TOP BAR (File, Send, Back, Forward, Copy) ---
        LinearLayout topBar = new LinearLayout(this);
        topBar.setOrientation(LinearLayout.HORIZONTAL);
        
        Button btnFile = new Button(this); btnFile.setText("File");
        Button btnSend = new Button(this); btnSend.setText("Send");
        Button btnBack = new Button(this); btnBack.setText("<");
        Button btnForward = new Button(this); btnForward.setText(">");
        Button btnCopy = new Button(this); btnCopy.setText("Copy");

        topBar.addView(btnFile);
        topBar.addView(btnSend);
        topBar.addView(btnBack);
        topBar.addView(btnForward);
        topBar.addView(btnCopy);
        mainLayout.addView(topBar);

        // --- WEBVIEW (The Main Content) ---
        webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        // FORCE DESKTOP MODE
        settings.setUserAgentString("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
        
        webView.setWebViewClient(new WebViewClient());
        
        LinearLayout.LayoutParams webParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
        mainLayout.addView(webView, webParams);

        // --- BOTTOM BAR (URL, Refresh, Go, Down) ---
        LinearLayout bottomBar = new LinearLayout(this);
        bottomBar.setOrientation(LinearLayout.HORIZONTAL);

        urlBar = new EditText(this);
        urlBar.setHint("Enter URL...");
        
        Button btnGo = new Button(this); btnGo.setText("Go");
        Button btnRefresh = new Button(this); btnRefresh.setText("R");
        Button btnDown = new Button(this); btnDown.setText("↓");

        bottomBar.addView(urlBar, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        bottomBar.addView(btnGo);
        bottomBar.addView(btnRefresh);
        bottomBar.addView(btnDown);
        mainLayout.addView(bottomBar);

        setContentView(mainLayout);

        // --- LOGIC ---
        btnGo.setOnClickListener(v -> {
            String url = urlBar.getText().toString();
            if (!url.startsWith("http")) url = "https://" + url;
            webView.loadUrl(url);
        });

        btnBack.setOnClickListener(v -> { if(webView.canGoBack()) webView.goBack(); });
        btnForward.setOnClickListener(v -> { if(webView.canGoForward()) webView.canGoForward(); });
        btnRefresh.setOnClickListener(v -> webView.reload());
        btnCopy.setOnClickListener(v -> Toast.makeText(this, "Copy Mode", Toast.LENGTH_SHORT).show());
        btnSend.setOnClickListener(v -> Toast.makeText(this, "Sending...", Toast.LENGTH_SHORT).show());

        webView.loadUrl("https://www.google.com");
    }
}
