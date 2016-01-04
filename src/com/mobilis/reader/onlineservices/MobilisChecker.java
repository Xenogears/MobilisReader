package com.mobilis.reader.onlineservices;

import android.content.Context;
import android.content.Intent;

import com.mobilis.reader.CardState;
import com.mobilis.reader.MobilisCard;

public abstract class MobilisChecker extends Thread
{
	private Context _Context = null;
	
	public MobilisChecker(Context Context)
	{
		this._Context = Context;
	}
	
	public void notifyRead(MobilisCard Card, CardState State)
	{
		Intent Broadcast = new Intent();
		
		if(State != null)
		{
			Broadcast.putExtra("CardState", State);
			Broadcast.putExtra("MobilisCard", Card);
			Broadcast.setAction("com.mobilis.reader.receivers.CardProcessedOK");
		}
		else Broadcast.setAction("com.mobilis.reader.receivers.CardProcessedERR");

		this._Context.sendBroadcast(Broadcast);
	}	
}
