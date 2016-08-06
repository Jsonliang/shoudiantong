package com.flashlightfree;

import android.hardware.Camera;

public class PowerLED {
	boolean m_isOn;
	Camera m_Camera;
	
	public boolean getIsOn() { return m_isOn; }
	
	public PowerLED()
	{
		m_isOn = false;
		m_Camera = Camera.open();
	}
	
	public void turnOn()
	{
		if(!m_isOn)
		{
			m_isOn = true;
			try
			{
				Camera.Parameters mParameters;
				mParameters = m_Camera.getParameters();
				mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				m_Camera.setParameters(mParameters);
			}catch(Exception ex){}
		}
	}
	
	public void turnOff()
	{
		if(m_isOn)
		{
			m_isOn = false;
			try
			{
				Camera.Parameters mParameters;
				mParameters = m_Camera.getParameters();
				mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				m_Camera.setParameters(mParameters);
			}catch(Exception ex){}
		}
	}
	
	public void Destroy()
	{
		m_Camera.release();
	}
	
}
