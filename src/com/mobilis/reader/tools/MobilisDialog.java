package com.mobilis.reader.tools;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public class MobilisDialog
{
	private static AlertDialog _CurrentDialog = null;
	
	public static void ShowAlert(Context Cont, String Message, String Title, String ButtonText, final DialogInterface.OnClickListener OnClick)
	{
		if(MobilisDialog._CurrentDialog != null)MobilisDialog._CurrentDialog.cancel();
		
		MobilisDialog._CurrentDialog = new AlertDialog.Builder(Cont)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle(Title)
	        .setMessage(Message)
	        .setOnCancelListener(new DialogInterface.OnCancelListener()
	        {		
				public void onCancel(DialogInterface dialog)
				{
					if(OnClick != null)OnClick.onClick(dialog, 0);
					MobilisDialog._CurrentDialog = null;
				}					
			})
	        .setPositiveButton(ButtonText, new DialogInterface.OnClickListener()
	        {			
				public void onClick(DialogInterface dialog, int which)
				{						
					if(OnClick != null)OnClick.onClick(dialog, which);	
					MobilisDialog._CurrentDialog = null;
				}
			})	        
	        .show();			      
	}
	
	public static void ShowConfirm(Context Cont, String Title, String Message, final DialogInterface.OnClickListener OnPositiveClick, String PositiveButtonText, final DialogInterface.OnClickListener OnNegativeClick, String NegativeButtonText)
	{
		if(MobilisDialog._CurrentDialog != null)
			MobilisDialog._CurrentDialog.cancel();
		
		MobilisDialog._CurrentDialog = new AlertDialog.Builder(Cont)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle(Title)
	        .setMessage(Message)
	        .setOnCancelListener(new DialogInterface.OnCancelListener()
	        {		
				public void onCancel(DialogInterface dialog)
				{
					if(OnNegativeClick != null)OnNegativeClick.onClick(dialog, 0);
					MobilisDialog._CurrentDialog = null;
				}					
			})
	        .setPositiveButton(PositiveButtonText, new DialogInterface.OnClickListener()
	        {			
				public void onClick(DialogInterface dialog, int which)
				{						
					if(OnPositiveClick != null)OnPositiveClick.onClick(dialog, which);	
					MobilisDialog._CurrentDialog = null;
				}
			})	 
			.setNegativeButton(NegativeButtonText, new DialogInterface.OnClickListener()
	        {			
				public void onClick(DialogInterface dialog, int which)
				{						
					if(OnNegativeClick != null)OnNegativeClick.onClick(dialog, which);	
					MobilisDialog._CurrentDialog = null;
				}
			})	 
	        .show();			      
	}

	public static void ShowPrompt(Context Cont, String Title, String Message, final EditText Input, final DialogInterface.OnClickListener OnPositiveClick, String PositiveButtonText, final DialogInterface.OnClickListener OnNegativeClick, String NegativeButtonText)
	{
		if(MobilisDialog._CurrentDialog != null)MobilisDialog._CurrentDialog.cancel();			
		
		MobilisDialog._CurrentDialog = new AlertDialog.Builder(Cont)
	        .setIcon(android.R.drawable.ic_dialog_info)
	        .setTitle(Title)
	        .setMessage(Message)
	        .setView(Input)
	        .setOnCancelListener(new DialogInterface.OnCancelListener()
	        {		
				public void onCancel(DialogInterface dialog)
				{
					if(OnNegativeClick != null)OnNegativeClick.onClick(dialog, 0);
					MobilisDialog._CurrentDialog = null;
				}					
			})
	        .setPositiveButton(PositiveButtonText, new DialogInterface.OnClickListener()
	        {			
				public void onClick(DialogInterface dialog, int which)
				{														
					if(OnPositiveClick != null)OnPositiveClick.onClick(dialog, which);	
					MobilisDialog._CurrentDialog = null;
				}
			})	 
			.setNegativeButton(NegativeButtonText, new DialogInterface.OnClickListener()
	        {			
				public void onClick(DialogInterface dialog, int which)
				{						
					if(OnNegativeClick != null)OnNegativeClick.onClick(dialog, which);	
					MobilisDialog._CurrentDialog = null;
				}
			})	 
	        .show();			      
	}
	
	public static boolean CloseCurrentDialog()
	{
		if(MobilisDialog._CurrentDialog != null)
		{
			MobilisDialog._CurrentDialog.cancel(); 
			return true;
		}
		else return false;
	}
}
