package com.example.bebobraina;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Help extends Activity {
	TextView vv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		
		vv=(TextView) this.findViewById(R.id.textView1);
		/*String s1="\tWelcome To Aisha\n\n\n Commands for Personal Assistant\n\n 1. For opening Internet\n say \"Open web\" or \"set web\"" ;
		String s2="\n 2. For Setting Alarm\n say \"set Alarm\"\n 3. For Play all Song\n say \"play song\" or  \"listen music\"";
		String s3="\n 4. For play particular song\n say \" play <song name>\"\n5. For Opening Calculator\n say \"open calculator\"";
		String s4="\n 6. For Opening Messaging\n say \"open messages\"\n7. For Opening Contacts\n say \"open contacts\"\n8. For Opening Gallery";
		String s5="\n say \"open gallery\"\n9. For Opening Camera\n say \"open camera\"\n 10. For Opening TextMemo\n say \"open memo\"";
		vv.setText(s1+s2+s3+s4+s5+"dvsitohyyyyyyyy\nyyyyyyyyyyy\nyyyyyyyyyyyyyyyyyyy\nyyyyyyyyyyyyyyyyyyyy\nyyyyyyyyyyyyyyyyyyyyyyyyy\nyyyyyyyyyyyyyyy");*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
