package com.longtek.bluetooth_control;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class CanSettings extends Activity {

	private Button CANSettings;
	private TextView CanRPM;
	private	TextView CanSpeed;
	private TextView CanThrottle;
	private TextView CcgFile;
	private ToggleButton m_SpyOnOff;

	private View.OnClickListener OnCANSettings = new View.OnClickListener() {
		
		@Override
		public void onClick(View view) {
			Intent intent= new Intent("android.intent.action.GET_CONTENT");
		    intent.setType("*/*");
		    intent.addCategory("android.intent.category.OPENABLE");
		    CanSettings.this.startActivityForResult(Intent.createChooser(intent, "Choose Ccg file"), 2);
		}
	};
	
	public void init()
	{
		this.CANSettings = (Button) findViewById(R.id.ButtonBrowseCcgCan);
		this.CanRPM = (TextView) findViewById(R.id.textViewRPMCan);
		this.CanSpeed = (TextView) findViewById(R.id.textViewSpeedCan);
		this.CanThrottle = (TextView) findViewById(R.id.textViewThrottleCan);
		this.CcgFile = (TextView) findViewById(R.id.TextCcgFileCan);
		this.m_SpyOnOff = (ToggleButton) findViewById(R.id.buttonSpyOnOffCan);
		this.m_SpyOnOff.setTextOff(getResources().getString(R.string.SpyOff));
		this.m_SpyOnOff.setTextOn(getResources().getString(R.string.SpyOn));
		this.m_SpyOnOff.setChecked(false);
	}
	
	public void ActivityFinish()
	{
		finish();
	}
	
	public void GoHome()
	{
		finish();
	}
	
	public void Launch_Connection()
	{
		startActivity(new Intent(this, Connect.class));
		ActivityFinish();
	}
	
	public void Launch_CanSettings()
	{
		
	}
	public void Launch_BoxSettings()
	{
		startActivity(new Intent(this, BoxSettings.class));
		ActivityFinish();
	}
	
	public void Launch_Demo()
	{
		startActivity(new Intent(this, Demo.class));
		ActivityFinish();
	}
	
	public void Launch_Help()
	{
		startActivity(new Intent(this, Help.class));
		ActivityFinish();
	}
	
	public void Launch_Logs()
	{
		startActivity(new Intent(this, Logs.class));
		ActivityFinish();
	}
	
	public void Launch_About()
	{
		startActivity(new Intent(this, About.class));
		ActivityFinish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cansettings);
		
		init();
		this.CANSettings.setOnClickListener(this.OnCANSettings);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		//����ʱ������Menu��ʵ����MenuBuilder����  
        Log.d("MainActivity", "menu--->" + menu);  
          
        /*���÷�����Ƶ���MenuBuilder��setOptionalIconsVisible��������mOptionalIconsVisibleΪtrue�� 
         * ���˵�����ͼ��ʱ�ſɼ� 
         */  
        setIconEnable(menu, true);
          
        return super.onCreateOptionsMenu(menu);  
	}
	
	//enableΪtrueʱ���˵����ͼ����Ч��enableΪfalseʱ��Ч��4.0ϵͳĬ����Ч 
	private void setIconEnable(Menu menu, boolean enable)  
    {  
        try   
        {  
            Class<?> clazz = Class.forName("com.android.internal.view.menu.MenuBuilder");  
            Method method = clazz.getDeclaredMethod("setOptionalIconsVisible", boolean.class);  
            method.setAccessible(true);  
              
            //MenuBuilderʵ��Menu�ӿڣ������˵�ʱ����������menu��ʵ����MenuBuilder����(java�Ķ�̬����)  
            method.invoke(menu, enable);  
              
        } catch (Exception e)   
        {  
            e.printStackTrace();  
        }  
    }  

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	 
		//ͨ���˵����ID��Ӧÿ���˵�
				switch (item.getItemId())
				{
				case R.id.menu_home:
					GoHome();
					break;
				case R.id.menu_connnection:
					Launch_Connection();
					break;
				case R.id.memu_demo:
					Launch_Demo();
					break;
				case R.id.menu_boxsettings:
					Launch_BoxSettings();
					break;
				case R.id.menu_cansettings:
					Launch_CanSettings();
					break;
				case R.id.menu_help:
					Launch_Help();
					break;
				case R.id.menu_about:
					Launch_About();
					break;
				case R.id.menu_logs:
					Launch_Logs();
					break;
				default:
					return super.onOptionsItemSelected(item);		//��û�д�����¼��������ദ��
				}
				return true;		//����true��ʾ������˵���ĵ���¼�������Ҫ���¼�����
				
	}
	
}
