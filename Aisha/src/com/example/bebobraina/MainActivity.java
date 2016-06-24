package com.example.bebobraina;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Category;
import org.alicebot.ab.Chat;
import org.alicebot.ab.Graphmaster;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.database.DataSetObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.AlarmClock;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InlinedApi")
public class MainActivity extends Activity implements OnInitListener
{
	protected static final int RESULT_SPEECH = 1;
    private static final String TAG = "ChatActivity";
    public Bot bot;
	public static Chat chat;
	String s;
	PackageManager packageManager;
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    AudioManager ma;
    private ImageButton buttonSend,buttonSpeak;
    private TextToSpeech textToSpeech;
    Intent intent;
   

    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_main);
        SQLStorage entry=new SQLStorage(this);
        entry.open();
        entry.insertdata();
        entry.close();
        ma=(AudioManager)getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        buttonSend = (ImageButton) findViewById(R.id.buttonSend);
        buttonSpeak = (ImageButton) findViewById(R.id.buttonSpeak);

        listView = (ListView) findViewById(R.id.listView1);
        textToSpeech = new TextToSpeech(this, this);   	
        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.single_message);
        listView.setAdapter(chatArrayAdapter);

        chatText = (EditText) findViewById(R.id.chatText);
        buttonSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                 String msg=chatText.getText().toString(); 
                 sendChatMessage(msg);
                 chat(msg);
            }
        });
        buttonSpeak.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
            	Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
				intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
				try 
				{
					startActivityForResult(intent, RESULT_SPEECH);
				} catch (ActivityNotFoundException a)
				{
					Toast t = Toast.makeText(getApplicationContext(),
							"Ops! Your device doesn't support Speech to Text",
							Toast.LENGTH_SHORT);
					t.show();
				}

            }
        });

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
        AssetManager assets = getResources().getAssets();
		File Dir = new File(Environment.getExternalStorageDirectory().toString() + "/Aisha/bots/Brainbot");
		if(!Dir.exists())
		{
			boolean b = Dir.mkdirs();
			if (Dir.exists())
			{
				try 
				{
					for (String dir : assets.list("Brainbot"))
					{
						File subdir = new File(Dir.getPath() + "/" + dir);
						boolean subdir_check = subdir.mkdirs();
						for (String file : assets.list("Brainbot/" + dir))
						{
							File f = new File(Dir.getPath() + "/" + dir + "/" + file);
							if (f.exists())
							{
								continue;
							}
							InputStream in = null;
							OutputStream out = null;
							in = assets.open("Brainbot/" + dir + "/" + file);
							out = new FileOutputStream(Dir.getPath() + "/" + dir + "/" + file);
							copyFile(in, out);
							in.close();
							in = null;
							out.flush();
							out.close();
							out = null;
						}
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
        	
		try
		{
			s=Environment.getExternalStorageDirectory().toString() + "/Aisha";
			AIMLProcessor.extension =  new PCAIMLProcessorExtension();
			bot = new Bot("Brainbot", s,"aiml2csv");				
			chat = new Chat(bot);
		
		}catch(Exception e)
		{System.out.print("in catch");
		}
    }
    
    private boolean sendChatMessage(String msg)
    {
        chatArrayAdapter.add(new ChatMessage(false, msg));
        return true;
    }
    
     public void onInit(int status)
     {
    	 if (status == TextToSpeech.SUCCESS)
    	 {
    		 int result = textToSpeech.setLanguage(Locale.US);
    		 if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
    		 {
    			 Log.e("error", "This Language is not supported");
    		 }
    		 else
    		 {
    			 convertTextToSpeech("KK");
    			 try
    			 {
    				 updateStorage();
    			 } catch (Exception e) {
			
    				 Dialog d=new Dialog(this);
    				 d.setTitle("Fail");
    				 TextView tv=new TextView(this);
    				 tv.setText(e.toString());
    				 d.setContentView(tv);
    				 d.show();
    			 }
    		 }
    	 }
    	 else
    	 {
    		 Log.e("error", "Initilization Failed!");
    	 }
  	 }
     private void convertTextToSpeech(String text) {
   	 
   	  textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
   	 }
     @Override
     
     public void onDestroy() {
    	 try
    	 {
    		 mainFunction();	
    		 textToSpeech.shutdown();
    	 }catch(Exception e){
    		 Dialog d=new Dialog(this);
    		 d.setTitle("Fail");
    		 TextView tv=new TextView(this);
	    	 tv.setText(e.toString());
	    	 d.setContentView(tv);
	    	 d.show();
    	 }
	 }
     private void copyFile(InputStream in, OutputStream out) throws IOException
     {
    	 byte[] buffer = new byte[1024];
    	 int read;
    	 while((read = in.read(buffer)) != -1){
    		 out.write(buffer, 0, read);
 		}
     }
     public void updateStorage()throws Exception{
    	 SQLStorage entry=new SQLStorage(MainActivity.this);
    	 String firstname,middlename,lastname,gender,age;
    	 entry.open();
     	 firstname=entry.getFirstName();
    	 middlename=entry.getMiddleName();
    	 lastname=entry.getLastName();
    	 gender=entry.getGender();
    	 age=entry.getAge();
    	 chat.multisentenceRespond("setfirstname "+firstname);   
    	 chat.multisentenceRespond("setmiddlename "+middlename);   
    	 chat.multisentenceRespond("setlastname "+lastname);   
    	 chat.multisentenceRespond("setgender "+gender);   
    	 chat.multisentenceRespond("setage "+age);   
    	 entry.close();
        	 
     }
     public void mainFunction ()
     {
    	 MagicBooleans.trace_mode = false;
 		Graphmaster.enableShortCuts = true;
 		//Timer timer = new Timer();
 		try{
 			String firstname = chat.multisentenceRespond("getfirstname");
 			String middlename = chat.multisentenceRespond("getmiddlename");
 			String lastname = chat.multisentenceRespond("getlastname");
 			String gender = chat.multisentenceRespond("getgender");
 			String age = chat.multisentenceRespond("getage");
			SQLStorage entry=new SQLStorage(MainActivity.this);
			entry.open();
			entry.updateEntry(firstname,middlename,lastname,gender,age);
			entry.close();
 		}catch(Exception e)
 		{}
     }	
     
     void sendMessage(String msg)
     {
    	 String response = chat.multisentenceRespond(msg);
    	 
    	 if(response.contains("#CMD"))
    	 {
    		 response=command(response);
    	 }
    	 chatArrayAdapter.add(new ChatMessage(true, response));
    	 convertTextToSpeech(response);
    	 chatText.setText("");		 
     }
     String command(String res)
     {
    	 res=res.replace("#CMD", "");
    	 int a=Integer.parseInt(res);
    	 switch(a)
    	 {
    	 	case 1:
    	 		ma.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    			res="Mobile is now on general mode.";
    			break;
 			
    	 	case 2:
    	 		ma.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    	 		res="Mobile is now on silent mode.";
    	 		break;
    	 	case 3:
    	 		ma.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
    	 		res="Mobile is now on vibrate mode.";
    	 		break;
    	 	default:
    	 		res="couldn't recognise your command";
    	 		break;
    	 }
    	 return res;	     	 
     }
     
     public void chat(String msg)
     {
    	 msg=msg.toLowerCase();
    	 if(msg.contains("alarm"))
    	 {
    		 String text= "As you wish";
    		 LinkedList <String> numbers= new LinkedList<String>();
    		 Pattern p=Pattern.compile("\\d+");
    		 Matcher m=p.matcher(msg);
    		 while(m.find())
    			 numbers.add(m.group());
 
    		 convertTextToSpeech(text);
    		 chatArrayAdapter.add(new ChatMessage(true, "As you wish"));
    		 Intent i=new Intent(AlarmClock.ACTION_SET_ALARM);
    		 i.putExtra(AlarmClock.EXTRA_MESSAGE, "Aisha Alarm");
    		 if(numbers.size()!=0)
    		 {
    			 if(numbers.size()==1)
    			 {
    				 int hr;
    				 String s0=numbers.get(0);
    				 hr=Integer.parseInt(s0);
    				 if(msg.contains("pm")&& hr<12)
    					 hr=hr+12;
    				 i.putExtra(AlarmClock.EXTRA_HOUR,hr);
    				 i.putExtra(AlarmClock.EXTRA_MINUTES, 00);
    			 }
    			 else if(numbers.size()==2)
    			 {
    				 int hr,min;
    				 String s0=numbers.get(0);
     	    		hr=Integer.parseInt(s0);
     	    		String s1=numbers.get(1);
     	    		min=Integer.parseInt(s1);
     	    		if(msg.contains("pm")&& hr<12)
     	    			hr=hr+12;
     	    		i.putExtra(AlarmClock.EXTRA_HOUR,hr);
     	    		i.putExtra(AlarmClock.EXTRA_MINUTES,min);
     	    		
     	    	}
     	    }
    		 chatText.setText("");
    		 startActivity(i);
     	    
     	    
         }
         else if(msg.contains("web ") || msg.contains("open internet") )
         {
           if(msg.contains("web")||msg.contains("internet"))
           {
        	String text= "Opening Internet";
         	convertTextToSpeech(text);
         	 chatText.setText("");
         	try{
                	chatArrayAdapter.add(new ChatMessage(true, "Opening Internet"));
                	 chatText.setText("");
     			Class c= Class.forName("com.example.bebobraina.SimpleBrowser");
     			Intent i=new Intent(MainActivity.this,c);
     			startActivity(i);
     			}catch(ClassNotFoundException e){ e.printStackTrace();}
           }	  
         }  
      	 else if(msg.contains("open"))
       	 {
    	     
      		 msg=msg.replace("open ","");
    		 msg=msg.replace(" ", "");
       		 if(msg.contains("messages"))
       		 {
       		   	String text= "Opening Messages";
             	convertTextToSpeech(text);
            	chatArrayAdapter.add(new ChatMessage(true, text));
            	 chatText.setText("");
             	Intent intent = new Intent(Intent.ACTION_MAIN);
             	intent.setType("vnd.android-dir/mms-sms");
             	int flags = Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP |
             	    Intent.FLAG_ACTIVITY_CLEAR_TOP;
             	intent.setFlags(flags);
             	startActivity(intent);             	
         	 
       		 }
       		 else
       		 {
       			 packageManager = getPackageManager();
       			 List<PackageInfo> packageList = packageManager.getInstalledPackages(PackageManager.GET_PERMISSIONS);
       			 
       			 for(int i=0;i<packageList.size();i++)
       			 {
       				 PackageInfo a=packageList.get(i);
       				String text=(String)a.packageName;
       				 if(text.contains(msg))
       				 {
       					 Intent app;
       					 PackageManager manager = getPackageManager();
       					 try 
       					 {
       						 app = manager.getLaunchIntentForPackage(text);
       						 if (app == null)
       							 throw new PackageManager.NameNotFoundException();
       						 app.addCategory(Intent.CATEGORY_LAUNCHER);
       						 startActivity(app);
       						 
       					 } catch (PackageManager.NameNotFoundException e)
       					 {
       					 }
       					int index=text.lastIndexOf(".");
          	       	     chatArrayAdapter.add(new ChatMessage(true, "opening "+text.substring(index+1)));
          	       	     convertTextToSpeech("opening "+text.substring(index+1));
          	       	     chatText.setText("");
       				 }
       			 }
       			
       				 
       		 }
       	 }
        else if(msg.contains("play"))
         {
            if(msg.contains("play song")){
        	String title="";
         	if(msg.contains("play song")){
         		title=msg;
         	    title=title.replace("play song ", "");
        
         	}
         	ArrayList<HashMap<String, String>> songs;
         	ArrayList<HashMap<String, String>> search;
         	FetchSong s=new FetchSong();
     		songs= new ArrayList<HashMap<String, String>>();
     		search= new ArrayList<HashMap<String, String>>();
     		boolean flag=false;
     		//songs.clear();
     		songs=s.fetch();
     		
 		
     		for(int i=0;i<songs.size();i++)
     		{
             if(songs.get(i).get("songTitle").toUpperCase().contains(title.toUpperCase()))
     		{
     		      HashMap<String,String> songsMap= new HashMap<String,String>();
             	    songsMap.put("songTitle",(String)songs.get(i).get("songTitle"));
                 	songsMap.put("songPath",(String)songs.get(i).get("songPath"));
             		search.add(songsMap);
             		flag=true;
                 
     		}
             
     		}
     	
             if(flag==false){
             	chatArrayAdapter.add(new ChatMessage(true,"Song not Found"));
             	convertTextToSpeech("Song not Found");
             	 chatText.setText("");
             }
             else if(search.size()==1){
             	String path = search.get(0).get("songPath");
             	String title1=search.get(0).get("songTitle");
 				Uri audio = Uri.parse("file://"+path);
 				Intent intent = new Intent( Intent.ACTION_VIEW);
 	            intent.setDataAndType(audio, "audio/*");  
 	            chatArrayAdapter.add(new ChatMessage(true, "Opening "+title1));
            	convertTextToSpeech("Opening "+title1);
            	 chatText.setText("");
 	            startActivity(intent);
             }else{
             	chatArrayAdapter.add(new ChatMessage(true, "Opening List"));
             	convertTextToSpeech("Opening List");
             	 chatText.setText("");
					try {
						Class c = Class.forName("com.example.bebobraina.Music");
						Intent in=new Intent(MainActivity.this,c);
         			in.putExtra("songlistoriginal",search);
         			startActivity(in);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
     			
              }
            }
         
        else if(msg.contains("play video")){
        	String title="";
         	if(msg.contains("play video"))
         	{
         	    title=msg;
         	    title=title.replace("play video ", "");
         	}
         	  	ArrayList<HashMap<String, String>> songs;
         	ArrayList<HashMap<String, String>> search;
         	FetchVideo s=new FetchVideo();
        		songs= new ArrayList<HashMap<String, String>>();
        		search= new ArrayList<HashMap<String, String>>();
        		boolean flag=false;
        		//songs.clear();
        		songs=s.fetch();
        		
        	
        		for(int i=0;i<songs.size();i++)
        		{
             if(songs.get(i).get("videoTitle").toUpperCase().contains(title.toUpperCase()))
        		{
        		      HashMap<String,String> songsMap= new HashMap<String,String>();
             	    songsMap.put("videoTitle",(String)songs.get(i).get("videoTitle"));
                 	songsMap.put("videoPath",(String)songs.get(i).get("videoPath"));
             		search.add(songsMap);
             		flag=true;
                 
        		}
             
        		}
        	
             if(flag==false){
             	chatArrayAdapter.add(new ChatMessage(true,"Video not Found"));
             	convertTextToSpeech("Video not Found");
             	 chatText.setText("");
             }
             else if(search.size()==1){
             	String path = search.get(0).get("videoPath");
             	String title1=search.get(0).get("videoTitle");
        			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        			intent.setDataAndType(Uri.parse(path), "video/mp4");
        			chatArrayAdapter.add(new ChatMessage(true, "Opening "+title1));
                	convertTextToSpeech("Opening "+title1);
                	 chatText.setText("");
 




       			startActivity(intent);
        			
             }else{
             	chatArrayAdapter.add(new ChatMessage(true, "Opening List"));
             	convertTextToSpeech("Opening List");
             	 chatText.setText("");
        			try {
        				Class c = Class.forName("com.example.bebobraina.OpenVideo");
        				Intent in=new Intent(MainActivity.this,c);
         			in.putExtra("videolistoriginal",search);
         			startActivity(in);
        			} catch (ClassNotFoundException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			
             }

        
        }
         }
        else if(msg.contains("videos")){
        	String title="";
         	if(msg.contains("videos"))
         	{
         		title=msg;
         	    title="";
         		
         	}
              	ArrayList<HashMap<String, String>> songs;
         	ArrayList<HashMap<String, String>> search;
         	FetchVideo s=new FetchVideo();
        		songs= new ArrayList<HashMap<String, String>>();
        		search= new ArrayList<HashMap<String, String>>();
        		boolean flag=false;
        		//songs.clear();
        		songs=s.fetch();
        		
        	
        		for(int i=0;i<songs.size();i++)
        		{
             if(songs.get(i).get("videoTitle").toUpperCase().contains(title.toUpperCase()))
        		{
        		      HashMap<String,String> songsMap= new HashMap<String,String>();
             	    songsMap.put("videoTitle",(String)songs.get(i).get("videoTitle"));
                 	songsMap.put("videoPath",(String)songs.get(i).get("videoPath"));
             		search.add(songsMap);
             		flag=true;
                 
        		}
             
        		}
        	
             if(flag==false){
             	chatArrayAdapter.add(new ChatMessage(true,"Video not Found"));
             	convertTextToSpeech("Video not Found");
             	 chatText.setText("");
             }
             else if(search.size()==1){
             	String path = search.get(0).get("videoPath");
             	String title1=search.get(0).get("videoTitle");
        			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(path));
        			intent.setDataAndType(Uri.parse(path), "video/mp4");
        			chatArrayAdapter.add(new ChatMessage(true, "Opening "+title1));
                	convertTextToSpeech("Opening "+title1);
                	 chatText.setText("");
        			startActivity(intent);
        			
             }else{
             	chatArrayAdapter.add(new ChatMessage(true, "Opening List"));
             	convertTextToSpeech("Opening List");
             	 chatText.setText("");
        			try {
        				Class c = Class.forName("com.example.bebobraina.OpenVideo");
        				Intent in=new Intent(MainActivity.this,c);
         			in.putExtra("videolistoriginal",search);
         			startActivity(in);
        			} catch (ClassNotFoundException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			
             }
        }
        else if(msg.contains("songs")){
        	String title="";
         	if(msg.contains("songs"))
         	{
         		title=msg;
         	    title="";
         		
         	}
         	ArrayList<HashMap<String, String>> songs;
         	ArrayList<HashMap<String, String>> search;
         	FetchSong s=new FetchSong();
     		songs= new ArrayList<HashMap<String, String>>();
     		search= new ArrayList<HashMap<String, String>>();
     		boolean flag=false;
     		//songs.clear();
     		songs=s.fetch();
     		
 		
     		for(int i=0;i<songs.size();i++)
     		{
             if(songs.get(i).get("songTitle").toUpperCase().contains(title.toUpperCase()))
     		{
     		      HashMap<String,String> songsMap= new HashMap<String,String>();
             	    songsMap.put("songTitle",(String)songs.get(i).get("songTitle"));
                 	songsMap.put("songPath",(String)songs.get(i).get("songPath"));
             		search.add(songsMap);
             		flag=true;
                 
     		}
             
     		}
     	
             if(flag==false){
             	chatArrayAdapter.add(new ChatMessage(true,"Song not Found"));
             	convertTextToSpeech("Song not Found");
             	 chatText.setText("");
             }
             else if(search.size()==1){
             	String path = search.get(0).get("songPath");
             	String title1=search.get(0).get("songTitle");
 				Uri audio = Uri.parse("file://"+path);
 				Intent intent = new Intent( Intent.ACTION_VIEW);
 	            intent.setDataAndType(audio, "audio/*");  
 	            chatArrayAdapter.add(new ChatMessage(true, "Opening "+title1));
            	convertTextToSpeech("Opening "+title1);
            	 chatText.setText("");
 	            startActivity(intent);
             }else{
             	chatArrayAdapter.add(new ChatMessage(true, "Opening List"));
             	convertTextToSpeech("Opening List");
             	 chatText.setText("");
					try {
						Class c = Class.forName("com.example.bebobraina.Music");
						Intent in=new Intent(MainActivity.this,c);
         			in.putExtra("songlistoriginal",search);
         			startActivity(in);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
     			
              }
            }

        else
         {
         	sendMessage(msg);
                     
         }
    	    	 

     }
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 		super.onActivityResult(requestCode, resultCode, data);

 		switch (requestCode) {
 		case RESULT_SPEECH: {
 			if (resultCode == RESULT_OK && null != data) {

 				ArrayList<String> text = data
 						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String msg=text.get(0);
                sendChatMessage(msg);
                chat(msg);
 				
 			}
 			break;
 		}

 		}
 	}
    
     @Override
 	public boolean onCreateOptionsMenu(android.view.Menu menu) {
 		// TODO Auto-generated method stub
 		 super.onCreateOptionsMenu(menu);
 		 //fullscreen
 		// requestWindowFeature(Window.FEATURE_NO_TITLE);
 		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
 		 MenuInflater blowUP=getMenuInflater();
 		 blowUP.inflate(R.menu.cool, menu);
 		 return true;
 	}
 	@Override
 	public boolean onOptionsItemSelected(MenuItem item) {
 		// TODO Auto-generated method stub
 		super.onOptionsItemSelected(item);
 		try{
 		switch(item.getItemId())
 		{
 			case R.id.help:
 				Class c1= Class.forName("com.example.bebobraina.Help");
     			Intent i1=new Intent(MainActivity.this,c1);
     			startActivity(i1);
     			break;
 		    case R.id.aboutus:
 		    	Class c2= Class.forName("com.example.bebobraina.About");
     			Intent i2=new Intent(MainActivity.this,c2);
     			startActivity(i2);
 		                   break;
 		    case R.id.developers:
 		    	Class c3= Class.forName("com.example.bebobraina.Developers");
     			Intent i3=new Intent(MainActivity.this,c3);
     			startActivity(i3);
 		                          
 		                   break;  
 		    case R.id.exit:
 		                finish();
 		                break;             
 	    }   
 		
 		}catch(Exception e)
 		{}
 		return false;
 	}
}
