package com.flashlightfree;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import com.flashlightfree.R;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity {
	
	private static String Setting_Info = "setting_infos";
	protected static final String Ad_Block="adblock";
	private static final String Auto_Start_LED = "autostartled";
	
	private MediaPlayer mMediaPlayer;
	private AdView adView;
	private int adblock=0;
	private Button powerbtn;
	private TextView batterytv;
	private TextView titletv;
	
	private PowerLED powerled; 
	private int[] batterystatusimgs={R.drawable.battery1,R.drawable.battery2,R.drawable.battery3};
	private int[] batterystatuspercent={75,30,0};
	private int[] gift_imgs={R.drawable.giftbox1,R.drawable.giftbox2,R.drawable.giftbox3};
	private int[] title_color={Color.rgb(51, 51, 51),Color.rgb(48, 210, 255)};
	
	private String adappname;
	private String adpackname;
	private String adappdesc;
	private String adappicon;
	private String adappimage;
	
	private Button fun1_btn;
	private Button fun2_btn;
	private Button fun3_btn;
	private Button fun4_btn;
	private Button fun5_btn;
	private Button gift_btn;
	private SharedPreferences settings_info;
	private int autostarled;
	private CheckBox autocheck;
	private int userrate=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		powerbtn= (Button) findViewById(R.id.powerbtn);
		batterytv= (TextView) findViewById(R.id.batterytv);
		titletv= (TextView) findViewById(R.id.titletv);
		mMediaPlayer=new MediaPlayer();
		
		autocheck= (CheckBox) findViewById(R.id.autocheck);
		
		fun1_btn= (Button) findViewById(R.id.fun1_btn);
		fun2_btn= (Button) findViewById(R.id.fun2_btn);
		fun3_btn= (Button) findViewById(R.id.fun3_btn);
		fun4_btn= (Button) findViewById(R.id.fun4_btn);
		fun5_btn= (Button) findViewById(R.id.fun5_btn);
		
		gift_btn= (Button) findViewById(R.id.gift_btn);
		
		Typeface fonttype = Typeface.createFromAsset(this.getAssets(),"fonts/justusitalic.ttf");
		titletv.setTypeface(fonttype);
		settings_info = getSharedPreferences(Setting_Info, 0);
		adblock= settings_info.getInt(Ad_Block,0);
		autostarled= settings_info.getInt(Auto_Start_LED,1);
		
		if(autostarled==1)
			playSounds(R.raw.turnon);
		
		powerled=new PowerLED();
		
		registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		
		if(adblock==1)
     	{
     		LinearLayout layout = (LinearLayout) findViewById(R.id.admainLayout);
     		layout.setVisibility(View.GONE);
     	}
     	else
     	{
     		adView = new AdView(this, AdSize.BANNER, "a151b8cb70e64fd");
    		LinearLayout layout = (LinearLayout) findViewById(R.id.admainLayout);
    		layout.addView(adView);
    		adView.loadAd(new AdRequest());
     	}
		
		titletv.setTextColor(title_color[0]);
		if(!powerled.m_isOn&&autostarled==1)
		{
			autocheck.setChecked(true);
			powerled.turnOn();
			powerbtn.setBackgroundResource(R.drawable.poweron);
			titletv.setTextColor(title_color[1]);
		       Animation animation = AnimationUtils.loadAnimation(
						Main.this, R.anim.gradually);
		       powerbtn.setAnimation(animation);
		}
		else
		{
			Animation animation = AnimationUtils.loadAnimation(
					Main.this, R.anim.turnoff);
			animation.setFillAfter(true);
	        powerbtn.setAnimation(animation);
		}
		
		View.OnClickListener VClick=new View.OnClickListener() {
			
			private Intent it;

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				switch(v.getId())
				{
					case R.id.powerbtn:
						playSounds(R.raw.click);
						if(!powerled.m_isOn)
						{
							powerled.turnOn();
							powerbtn.setBackgroundResource(R.drawable.poweron);
							titletv.setTextColor(title_color[1]);
							
							 Animation animation = AnimationUtils.loadAnimation(
										Main.this, R.anim.turnon);
						       powerbtn.setAnimation(animation);
						}
						else
						{
							powerled.turnOff();
							powerbtn.setBackgroundResource(R.drawable.poweroff);
							titletv.setTextColor(title_color[0]);
							Animation animation = AnimationUtils.loadAnimation(
									Main.this, R.anim.turnoff);
							animation.setFillAfter(true);
					        powerbtn.setAnimation(animation);
						}
					break;
					
					case R.id.gift_btn:
						  String applink2="market://details?id="+adpackname;
						  Uri uri2 = Uri.parse(applink2);  
						  it = new Intent(Intent.ACTION_VIEW, uri2);  
						  startActivity(it);
						break;
					
					case R.id.fun1_btn:
						  it = new Intent(Main.this, Fun1.class);  
						  startActivity(it);
						break;
						
					case R.id.fun2_btn:
						  it = new Intent(Main.this, Fun2.class);  
						  startActivity(it);
						break;
						
					case R.id.fun3_btn:
						  it = new Intent(Main.this, Fun3.class);  
						  startActivity(it);
						break;
						
					case R.id.fun4_btn:
						 if(powerled.m_isOn)
						 {
						   powerled.turnOff();
						   powerbtn.setBackgroundResource(R.drawable.poweroff);
						   titletv.setTextColor(title_color[0]);
						   
						   Animation animation = AnimationUtils.loadAnimation(
									Main.this, R.anim.turnoff);
							animation.setFillAfter(true);
					        powerbtn.setAnimation(animation);
						 }
						 powerled.Destroy();
						 it = new Intent(Main.this, Fun4.class);  
						 startActivityForResult(it,4);
						break;
					case R.id.autocheck:
						if(autocheck.isChecked())
							settings_info.edit().putInt(Auto_Start_LED, 1).commit();
						else
							settings_info.edit().putInt(Auto_Start_LED, 0).commit();
						break;
						
					case R.id.fun5_btn:
						break;
				}
			}
		};
		
		powerbtn.setOnClickListener(VClick);
		gift_btn.setOnClickListener(VClick);
		fun1_btn.setOnClickListener(VClick);
		fun2_btn.setOnClickListener(VClick);
		fun3_btn.setOnClickListener(VClick);
		fun4_btn.setOnClickListener(VClick);
		fun5_btn.setOnClickListener(VClick);
		autocheck.setOnClickListener(VClick);
		
	}

	@Override
    public void onDestroy() 
	{
        super.onDestroy();
        if(adblock==0)
        	adView.destroy();
        powerled.Destroy();
        unregisterReceiver(mBatInfoReceiver);
	}

	  private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver()
	  {
		  @Override
	    public void onReceive(Context context, Intent intent)
	    {
	      String action = intent.getAction();
	      if (Intent.ACTION_BATTERY_CHANGED.equals(action))
	      {
	        int intLevel = intent.getIntExtra("level", 0);
	        int intScale = intent.getIntExtra("scale", 100);
	        onBatteryInfoReceiver(intLevel, intScale);
	      }
	    }
	  };
	
	  
	  public void onBatteryInfoReceiver(int intLevel, int intScale)
	  {
		int bp=intLevel * 100 / intScale;
	    batterytv.setText( bp + "%");
	    
	    if(bp>=batterystatuspercent[2])
	    {
	    	batterytv.setBackgroundResource(batterystatusimgs[2]);
	    }
	    
	    
	    if(bp>=batterystatuspercent[1])
	    {
	    	batterytv.setBackgroundResource(batterystatusimgs[1]);
	    }
	    
	    if(bp>=batterystatuspercent[0])
	    {
	    	batterytv.setBackgroundResource(batterystatusimgs[0]);
	    }
	    
	  }
	  
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	AudioManager audioManager=null;
    	audioManager=(AudioManager)getSystemService(Service.AUDIO_SERVICE);

    if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN){
    	//Toast.makeText(main.this, "Down", Toast.LENGTH_SHORT).show();
    	audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, 
                AudioManager.ADJUST_LOWER, 
                AudioManager.FLAG_SHOW_UI);
        	return true;
    }else if(keyCode==KeyEvent.KEYCODE_VOLUME_UP)
    {
    	audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, 
                AudioManager.ADJUST_RAISE, 
                AudioManager.FLAG_SHOW_UI);    
    		return true;
    }else if(keyCode==KeyEvent.KEYCODE_BACK)
    {
    	powerled.turnOff();
		powerbtn.setBackgroundResource(R.drawable.poweroff);
		titletv.setTextColor(title_color[0]);
		
    	return super.onKeyDown(keyCode, event);    
    }else{
    return super.onKeyDown(keyCode, event);    
    }
    }
	
	
	
	private boolean isRunApp(String packageName) {  
        PackageInfo pi;  
        try {  
            pi = getPackageManager().getPackageInfo(packageName, 0);  
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);  
            // resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);   
            resolveIntent.setPackage(pi.packageName);  
            PackageManager pManager = getPackageManager();  
            List<ResolveInfo> apps = pManager.queryIntentActivities(  
                    resolveIntent, 0);  
  
            ResolveInfo ri = apps.iterator().next();  
            if (ri != null) {  
                return true;
            }  
            else
            {
            	return false;
            }
            
        } catch (NameNotFoundException e) {  
            // TODO Auto-generated catch block   
            e.printStackTrace();
            return false;
        }  
    }
	
	@Override
    protected void onResume() {
        super.onResume();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

  	  super.onActivityResult(requestCode, resultCode, data);
  	  if (data != null) {
  		  if (requestCode == 4)
  		  {
  			powerled=new PowerLED();
  		  }
  	  }
  	}
	

	private void playSounds(int sid) {

		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.release();
			mMediaPlayer = null;
		}

		mMediaPlayer = MediaPlayer.create(Main.this, sid);

		/* 准备播放 */
		// mMediaPlayer.prepare();
		/* 开始播放 */
		mMediaPlayer.start();
	}
}
