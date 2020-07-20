package com.example.selfcheck

import android.os.Bundle
import android.util.Log
import android.webkit.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var Studentname: EditText
    lateinit var Schoolname: EditText
    lateinit var date: EditText
    lateinit var click: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(
            this,
            "처음 인적사항 입력 이후에는 어플을 실행시키자마자 자동으로 자가진단이 완료됩니다.\n\n개발자: 보들",
            Toast.LENGTH_LONG
        ).show()


        webview.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }

        val settings: WebSettings = webview.getSettings()
        settings.javaScriptEnabled = true

        setContentView(R.layout.activity_main)
        val webV: WebView = findViewById(R.id.webview)
        webV.settings.javaScriptEnabled = true
        //webV.webViewClient = WebViewClient()
        webV.webChromeClient = WebChromeClient()
        webV.webViewClient = WebViewClientClass()
        webV.loadUrl("https://eduro.jje.go.kr/hcheck/index.jsp")
        Studentname = findViewById(R.id.name)
        Schoolname = findViewById(R.id.schoolName)
        date = findViewById(R.id.date)
        click = findViewById(R.id.click)


        click.setOnClickListener() {

            val firstBtn = "javascript:" +
                    "document.getElementsByClassName('btn_confirm')[1].click();"


            webV?.evaluateJavascript(firstBtn) {
                Log.d("firstBtn", it)
            }

        }


    }



    inner class WebViewClientClass : WebViewClient() {
        //페이지 이동
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            Log.d("check URL", view.url)
            view.loadUrl(view.url)
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)



            val schoolNM = "javascript:" +
                    "document.getElementsById('schulNm')[1].value = $Schoolname;"

            view?.evaluateJavascript(schoolNM) {
                Log.d("schoolNM", it)
            }

            val studentNM = "javascript:" +
                    "document.getElementsById('pName')[1].value = $Studentname;"

            view?.evaluateJavascript(studentNM) {
                Log.d("studentNM", it)
            }

            val studentDate = "javascript:" +
                    "document.getElementsById('frnoRidno')[1].value = $date;"

            view?.evaluateJavascript(studentDate) {
                Log.d("studentDate", it)
            }

            val confirm = "javascript:" +
                    "document.getElementsById('btnConfirm')[1].click();"

            view?.evaluateJavascript(confirm) {
                Log.d("confirm", it)
            }

        }
    }
}
