package com.mobilis.reader.nfc;

import android.content.Context;
import android.content.Intent;

public class NFCProcess extends Thread
{
	private Intent _Intent = null;
	private Context _Context = null;
	
	public NFCProcess(Intent Intent, Context Context)
	{
		this._Intent = Intent;		
		this._Context = Context;
	}
	
	public void run()
	{		
		NFCIntent NFCInt = new NFCIntent(_Intent);
		
		try
		{
			NFCACard Card = NFCInt.readCard();			
			Intent Broadcast = new Intent();
			
			if(Card != null)
			{
				Broadcast.putExtra("NFCACard", Card);
				Broadcast.setAction("com.mobilis.reader.receivers.CardReadedOK");
			}
			else Broadcast.setAction("com.mobilis.reader.receivers.CardReadedERR");

			this._Context.sendBroadcast(Broadcast);
		}
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
}
