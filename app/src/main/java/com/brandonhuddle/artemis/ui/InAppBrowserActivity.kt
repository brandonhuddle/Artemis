package com.brandonhuddle.artemis.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.brandonhuddle.artemis.R


class InAppBrowserActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent.extras == null || !intent.extras!!.containsKey("loadUrl")) {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }

        setContentView(R.layout.activity_in_app_browser)

        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true

        webView.loadUrl(intent.extras!!.getString("loadUrl")!!)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                if (request.url.scheme == "com.brandonhuddle.artemis") {
                    if (request.url.queryParameterNames.contains("code")) {
                        val code = request.url.getQueryParameter("code")
                        val returnIntent = Intent()
                        returnIntent.putExtra("result", code)
                        setResult(RESULT_OK, returnIntent)
                        finish()
                    } else {
                        val returnIntent = Intent()
                        setResult(RESULT_CANCELED, returnIntent)
                        finish()
                    }
                    return true
                } else if (request.url.scheme == "intent") {
                    val intent = Intent.parseUri(request.url.toString(), Intent.URI_INTENT_SCHEME)

                    if (intent != null) {
                        val fallbackUrl = intent.getStringExtra("browser_fallback_url")

                        return if (fallbackUrl != null) {
                            webView.loadUrl(fallbackUrl)
                            true
                        } else {
                            false
                        }
                    }
                }

                return false
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
        } else {
            val returnIntent = Intent()
            setResult(RESULT_CANCELED, returnIntent)
            finish()
        }
        return true
    }
}