package com.flashlightfree;

import com.flashlightfree.R;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Fun1 extends Activity implements  OnTouchListener,OnGestureListener  {
	
	private Button[] fun_btns;
	private int[] bgcolor={Color.rgb(255,255,255),Color.rgb(255,0,0),Color.rgb(0,255,0),Color.rgb(0,0,255),Color.rgb(255,255,0),Color.rgb(255,192,203),Color.rgb(128,0,128),Color.rgb(255,127,39)};
	private Handler show_handler;
	private Runnable show_runnable;
	private float brightnessnumber=0.70f;
	private int adblock=0;
	private AdView adView;
	private  GestureDetector mGestureDetector; 
	private boolean firsttime=true;
	private TextView titletv;
	public  Fun1()

	 {            

		mGestureDetector =  new  GestureDetector( this );       

	 }  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fun1);
		     
		
		fun_btns=new Button[8];
		fun_btns[0]= (Button) findViewById(R.id.fun1_btn);
		fun_btns[1]= (Button) findViewById(R.id.fun2_btn);
		fun_btns[2]= (Button) findViewById(R.id.fun3_btn);
		fun_btns[3]= (Button) findViewById(R.id.fun4_btn);
		fun_btns[4]= (Button) findViewById(R.id.fun5_btn);
		fun_btns[5]= (Button) findViewById(R.id.fun6_btn);
		fun_btns[6]= (Button) findViewById(R.id.fun7_btn);
		fun_btns[7]= (Button) findViewById(R.id.fun8_btn);
		titletv= (TextView) findViewById(R.id.titletv);

		
		RelativeLayout bglayout = (RelativeLayout) findViewById(R.id.bglayout);
		bglayout.setOnTouchListener( this );           
		bglayout.setFocusable( true );            
		bglayout.setClickable( true );            
		bglayout.setLongClickable( true );
		
		for(int i=0;i<fun_btns.length;i++)
		{
			fun_btns[i].setBackgroundColor(bgcolor[i]);
		}
		
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
		
		setBrightness(brightnessnumber);

		View.OnClickListener VClick=new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				titletv.setVisibility(View.INVISIBLE);
				for(int i=0;i<fun_btns.length;i++)
				{
					if(v.equals(fun_btns[i]))
					{
						RelativeLayout bglayout = (RelativeLayout) findViewById(R.id.bglayout);
						bglayout.setBackgroundColor(bgcolor[i]);
					}
				}
			}
		};

		for(int i=0;i<fun_btns.length;i++)
			fun_btns[i].setOnClickListener(VClick);
		
		show_handler = new Handler();
		show_runnable = new Runnable() {
			@Override
			public void run() {
				brightnessnumber=brightnessnumber+0.05f;
				setBrightness(brightnessnumber);
				if(brightnessnumber<1.0f)
					show_handler.postDelayed(this,100);
			}
		};
		
		mGestureDetector.setIsLongpressEnabled( true );  
	}

	public void setBrightness(float f){
		  WindowManager.LayoutParams lp = getWindow().getAttributes();
		  lp.screenBrightness = f;   
		  getWindow().setAttributes(lp);
		 } 

	@Override
    protected void onResume() 
	{
        super.onResume();
        if(firsttime)
        {
        	firsttime=false;
        	show_handler.postDelayed(show_runnable,100);
        }
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
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		
		return  mGestureDetector.onTouchEvent(arg1);       
	}

	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		
		float movedistance =arg1.getX()-arg0.getX();
		int indenumber=(int) (movedistance/100.0f);
		brightnessnumber=brightnessnumber+(float) indenumber*0.05f;
		if(brightnessnumber>1.0)
			brightnessnumber=1.0f;
		if(brightnessnumber<0.1)
			brightnessnumber=0.1f;
		
		setBrightness(brightnessnumber);
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}
}
