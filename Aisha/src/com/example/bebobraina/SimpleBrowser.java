package com.example.bebobraina;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class SimpleBrowser extends Activity implements OnClickListener {

	Button go,back,forward,refresh,clear;
	WebView wv;
	EditText url;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simplebrowser);
	    wv=(WebView) findViewById(R.id.wbrowser);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setLoadWithOverviewMode(true);
		wv.getSettings().setUseWideViewPort(true);
		try{
		wv.loadUrl("http://www.google.com");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		wv.setWebViewClient(new WvClient());
		url=(EditText) findViewById(R.id.eturl);
		go=(Button) findViewById(R.id.bgo);
		back=(Button) findViewById(R.id.bBack);
		forward=(Button) findViewById(R.id.bForward);
		refresh=(Button) findViewById(R.id.bRefresh);
	    clear=(Button) findViewById(R.id.bHistory);
	    go.setOnClickListener(this);
	    back.setOnClickListener(this);
	    forward.setOnClickListener(this);
	    refresh.setOnClickListener(this);
	    clear.setOnClickListener(this);
		}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.bgo:
			String str=url.getText().toString();
			wv.loadUrl("http://"+str);
			InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromInputMethod(url.getWindowToken(), 0);
	        break;
		case R.id.bBack:
			if(wv.canGoBack())
				wv.goBack();
	        break;
		case R.id.bForward:
			if(wv.canGoForward())
				wv.goForward();
	        break;
		case R.id.bRefresh:
			  wv.reload();
	        break;
		case R.id.bHistory:
			wv.clearHistory();
	        break;
}
		
	}
	
class WvClient extends WebViewClient{

	/* (non-Javadoc)
	 * @see android.webkit.WebViewClient#shouldOverrideUrlLoading(android.webkit.WebView, java.lang.String)
	 */
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		// TODO Auto-generated method stub
		view.loadUrl(url);
		return true;
		}
	
	
}
}
