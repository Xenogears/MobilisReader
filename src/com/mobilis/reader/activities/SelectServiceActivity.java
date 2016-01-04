package com.mobilis.reader.activities;

import com.mobilis.reader.MobilisCard;
import com.mobilis.reader.R;
import com.mobilis.reader.onlineservices.EMTValenciaChecker;
import com.mobilis.reader.onlineservices.MetroValenciaChecker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;

public class SelectServiceActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_service);
	
		final MobilisCard Card = (MobilisCard)this.getIntent().getSerializableExtra("MobilisCard");
		
		Button BtMetro = (Button)findViewById(R.id.bt_service_metro_valencia);
		Button BtEMT = (Button)findViewById(R.id.bt_service_emt_valencia);
		TextView lblCardID = (TextView)findViewById(R.id.tv_cardid);
		lblCardID.setText(Card.toStringWithSeparators());
		
		final Context Ctx = this;
		
		BtMetro.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ProgressDialog Dialog = new ProgressDialog(Ctx);
				Dialog.setIndeterminate(true);
				Dialog.setTitle(R.string.lbl_loading);
				Dialog.setMessage(String.format(getText(R.string.lbl_fetchingFromServer).toString(), getText(R.string.lbl_metrovalencia).toString()));
				Dialog.show();
				
				new MetroValenciaChecker(SelectServiceActivity.this, Card).start();
			}			
		});		
		
		BtEMT.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ProgressDialog Dialog = new ProgressDialog(Ctx);
				Dialog.setIndeterminate(true);
				Dialog.setTitle(R.string.lbl_loading);
				Dialog.setMessage(String.format(getText(R.string.lbl_fetchingFromServer).toString(), getText(R.string.lbl_emtvalencia).toString()));
				Dialog.show();
				
				new EMTValenciaChecker(SelectServiceActivity.this, Card).start();
			}			
		});	
	}
}
