package com.flashlightfree;

import com.flashlightfree.R;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Fun3  extends Activity {
	private int adblock=0;
	private AdView adView;
	
	private int[] bgcolor={Color.rgb(255,15,0),Color.rgb(0,0,0),Color.rgb(255,15,0),Color.rgb(0,0,0),Color.rgb(255,15,0),Color.rgb(0,0,0),Color.rgb(0,0,255),Color.rgb(0,0,0),Color.rgb(0,0,255),Color.rgb(0,0,0),Color.rgb(0,0,255),Color.rgb(0,0,0)};
	private int[] bgflashtime={80,50,80,50,80,250,80,50,80,50,80,250};
	private TextView warmingtv;

	private Handler show_handler;
	private Runnable show_runnable;
	private boolean firsttime=true;
	private int warmingcounter=-1;
	private TextView titletv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fun3);
		warmingtv= (TextView) findViewById(R.id.warmingtv);
		titletv= (TextView) findViewById(R.id.titletv);
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
		warmingtv.setBackgroundColor(bgcolor[1]);
		setBrightness((float) 1.0);
		show_handler = new Handler();
		show_runnable = new Runnable() {
			@Override
			public void run() {
				warmingcounter++;
				if(warmingcounter==16)
					titletv.setVisibility(View.INVISIBLE);
				
				warmingtv.setBackgroundColor(bgcolor[warmingcounter%12]);
				show_handler.postDelayed(this,bgflashtime[warmingcounter%12]);
				
			}
		};
	}
	
	@Override
    public void onDestroy() 
	{
        super.onDestroy();
        show_handler.removeCallbacks(show_runnable);
        if(adblock==0)
        	adView.destroy();
    }
	
	@Override
    protected void onResume() {
        super.onResume();
        if(firsttime)
        {
        	firsttime=false;
        	show_handler.postDelayed(show_runnable,50);
        }
	}
	
	public void setBrightness(float f){
		  WindowManager.LayoutParams lp = getWindow().getAttributes();
		  lp.screenBrightness = f;   
		  getWindow().setAttributes(lp);
		 } 
}
