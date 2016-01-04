package com.mobilis.reader.receivers;

import com.mobilis.reader.CardState;
import com.mobilis.reader.activities.CardInfoActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CardProcessedReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context Context, Intent I)
	{	
		CardState State = (CardState)I.getSerializableExtra("CardState");
		
		Intent Int = new Intent(Context, CardInfoActivity.class);
		Int.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Int.putExtra("CardState", State);
		Context.startActivity(Int);		
	}
}
