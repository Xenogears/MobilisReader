package com.mobilis.reader.activities;

import com.mobilis.reader.MobilisCard;
import com.mobilis.reader.R;
import com.mobilis.reader.nfc.NFCACard;
import com.mobilis.reader.nfc.NFCProcess;
import com.mobilis.reader.tools.MobilisDialog;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class LoadingActivity extends Activity
{
	public BroadcastReceiver ReadedReceiver = new BroadcastReceiver()
	{ 
		@Override
		public void onReceive(Context Cont, Intent Inte)
		{	
			NFCACard Card = (NFCACard)Inte.getSerializableExtra("NFCACard");
			if(Card == null)
			{
				MobilisDialog.ShowAlert(Cont, getText(R.string.lbl_errorReadingCard).toString(), getText(R.string.lbl_errorProcessingCard).toString(), getText(R.string.lbl_back).toString(), new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						finish();
					}						
				});
			}
			else
			{
				if(Card.getATQA()[0] == 4 && Card.getATQA()[1] == 0 && Card.getSAK() == 8) //MOBILIS
				{
					MobilisCard Mobilis = new MobilisCard(Card);
					
					Intent ServiceSelector = new Intent(Cont, SelectServiceActivity.class);
					ServiceSelector.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_HISTORY);
					ServiceSelector.putExtra("MobilisCard", Mobilis);
					Cont.startActivity(ServiceSelector);
				}
				else
				{
					MobilisDialog.ShowAlert(Cont, getText(R.string.lbl_errorNotAMobilisCard).toString(), getText(R.string.lbl_errorReadingCard).toString(), getText(R.string.lbl_back).toString(), new OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							finish();
						}						
					});
				}
			}
		}		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		
		IntentFilter Filter = new IntentFilter();
		Filter.addAction("com.mobilis.reader.receivers.CardReadedOK");
		Filter.addAction("com.mobilis.reader.receivers.CardReadedERR");
        super.registerReceiver(this.ReadedReceiver, Filter);
       
        new NFCProcess(super.getIntent(), this).start();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		super.unregisterReceiver(this.ReadedReceiver);
	}
}
