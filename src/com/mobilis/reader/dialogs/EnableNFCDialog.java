package com.mobilis.reader.dialogs;

import com.mobilis.reader.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class EnableNFCDialog extends AlertDialog.Builder
{
	public AlertDialog getDialog()
	{
		return super.create();
	}
	
	public EnableNFCDialog(final Activity Act, final Context Cont)
	{
		super(Cont);
		
		super.setTitle(R.string.lbl_nfcReader);
		super.setMessage(R.string.lbl_requiresNfcEnabled);
		
		super.setPositiveButton(Act.getText(R.string.lbl_enable).toString(), new Dialog.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{				
				Act.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));				
			}			
		});
		
		super.setNegativeButton(Act.getText(R.string.lbl_continue).toString(), new Dialog.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.cancel();
			}			
		});
	}
}
