package com.example.bebobraina;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class About extends Activity {
	TextView vv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		/*vv=(TextView) this.findViewById(R.id.textView1);
		
		vv.setText("aaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\naaaaaaaadfffffffffff\nfffffffffff\nffffffffffff\nfffffffffffffffffffffffaaaaaaaadfffffffffffffffffffffffffffffffffffffffffffffffffffffffff\n");
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
