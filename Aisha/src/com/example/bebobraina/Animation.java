package com.example.bebobraina;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class Animation extends Activity {

	WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.loading_animation);
         wv=(WebView)findViewById(R.id.webView1);
         wv.getSettings().setLoadWithOverviewMode(true);
 		wv.getSettings().setUseWideViewPort(false);
         
         wv.loadUrl("file:///android_asset/loading.html");
         Thread t=new Thread(){
 	    	
     	    public void run(){
     	    	       try{
     	    		    	     sleep(9000);
     	    		    	    Class c = Class.forName("com.example.bebobraina.MainActivity");
     							Intent in=new Intent(Animation.this,c);
     	         			startActivity(in); 	 		    	     
     	    		  
     	    	       }catch(Exception e){}
     	    	      
     	    
     	    }
     	    };	
     	    t.start();
         
        
            }

}
