package com.longtek.bluetooth_control;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

/**�����������裺
 * 1.ʹ��������ӦȨ��
 * 2.���ñ�������������
 * 3.���������豸
 * 4.����SocketͨѶ
 * 	a.�ͻ���ʵ��
 * 	b.��������ʵ��
 * 	c.���ӹ�������ͨѶ��
 * */
public class Fac_Manager {
	
	private static Fac_Manager m_UniqueInstance;
	public Fac_BroadcastReceiver bReceiver;
	private Fac_com m_com;
	private PC_Data m_Data;
	private Fac_Handler m_handler;
	private BluetoothAdapter myBluetoothAdapter;
	private Context m_context;
	private Context m_Connect;
	
	public Fac_Manager(Context context)
	{
		this.m_context = context;
		this.m_Data = new PC_Data();
		this.bReceiver = new Fac_BroadcastReceiver(this);
		this.m_handler = new Fac_Handler(this);
		this.m_com = new Fac_com(this.m_handler);
		this.myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); 
		
		//��鱾�������Ƿ��
		if (!this.myBluetoothAdapter.isEnabled())
		{
			this.myBluetoothAdapter.enable();
			Log.d("Fac_Manager", "Bluetooth is not active!");
		}
		//����registerReceiver����ע��㲥��������ʵ��ɨ�������豸�ͷ��ط��ֵ��豸
		this.m_context.registerReceiver(this.bReceiver, new IntentFilter("android.bluetooth.device.action.FOUND"));
		this.m_context.registerReceiver(this.bReceiver, new IntentFilter("android.bluetooth.adapter.action.DISCOVERY_FINISHED"));
		m_UniqueInstance = this;
	}
	
	//ɨ����Χ�������豸
		public void ScanDevice()
		{
			if (this.myBluetoothAdapter.isDiscovering()) {
				this.myBluetoothAdapter.cancelDiscovery();
			}
			ClearDevices();
			if (!this.myBluetoothAdapter.startDiscovery()) {
				Log.d("Fac_Manager", "Scaning SoundCreadtor devices.");
			}
		}
		
		public void ClearDevices()
		{
			this.m_Data.clearDevices();
		}
		
		public void DiscoveryFinished()
		{
			if (this.m_Connect != null) {
				((Connect)this.m_Connect).HideProgressBar();
			}
		}
		//��⵽�µ������豸
		public void NewDeviceDetected(BluetoothDevice device)
		{
			this.m_Data.addDevice(device);
			if (this.m_Connect != null) {
				((Connect)this.m_Connect).NewDeviceDetected(device);
			}
		}
		  
		public boolean Connect(int indext)
		{
			BluetoothDevice device = this.m_Data.getDevice(indext);
			if (this.myBluetoothAdapter.isDiscovering()) {
				this.myBluetoothAdapter.cancelDiscovery();
			}
			for (;;)
			{
				if (!this.myBluetoothAdapter.isDiscovering())
				{
					boolean bool = this.m_com.connect(device);
					if ((this.m_Connect != null) && (!bool)) {
						((Connect)this.m_Connect).ConnectionDone(false);
					}
					return bool;
				}
				try
				{
					Thread.sleep(100L);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	  
		public void ConnectionDone(byte[] ArrayOfByte)
		{
			String str = new String();
			if (this.m_Connect != null) {
				((Connect)this.m_Connect).ConnectionDone(str.equals("y"));
			}
			((MainActivity)this.m_context).ConnectionDone(str.equals("y"));
			if (str.equals("y")) {
				this.m_com.getSoundCreatorVersion();
			}
		}	
		
		public boolean IsConnected()
		{
			return this.m_com.IsConnected();
		}
		
		public void disconnect()
		{
			this.m_com.disconnect();
			this.m_Data.setM_ListBox(null);
			((MainActivity)this.m_context).ConnectionDone(false);
			if (this.m_Connect != null) {
				((Connect)this.m_Connect).DisconnectionDone(true);
			}
		}
		
		public PC_Data getM_Data()
		{
			return this.m_Data;
		}
		
//	  public void Previous()
//	  {
//		  if (!IsConnected())
//		  {
//			  ((MainActivity)this.m_context).PleaseDoConnection();
//			  return;
//		  }
//		  this.m_Data.BoxCurrentDecrease();
//		  this.m_com.LoadBoxCmd();
//	  }

}
