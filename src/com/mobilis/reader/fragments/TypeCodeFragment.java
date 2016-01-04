package com.mobilis.reader.fragments;

import com.mobilis.reader.MobilisCard;
import com.mobilis.reader.R;
import com.mobilis.reader.activities.SelectServiceActivity;
import com.mobilis.reader.tools.MobilisDialog;

import android.content.Intent;
import android.os.Bundle; 
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class TypeCodeFragment extends mobFragment
{
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_type_card_number, container, false);
	
		final EditText CardCodeView = (EditText)rootView.findViewById(R.id.tb_card_code);
		
		final Button SearchCardCodeButton = (Button)rootView.findViewById(R.id.bt_searchCardCode);
		SearchCardCodeButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String ID = CardCodeView.getText().toString();
				
				MobilisCard Mobilis = new MobilisCard(ID);
				if(ID.length() == 12 && Mobilis.getCRC().equals(ID.substring(10)))
				{							
					CardCodeView.setText(null);
					CardCodeView.clearFocus();
					
					Intent ServiceSelector = new Intent(inflater.getContext(), SelectServiceActivity.class);
					ServiceSelector.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_HISTORY);
					ServiceSelector.putExtra("MobilisCard", Mobilis);
				
					inflater.getContext().startActivity(ServiceSelector);
				}
				else MobilisDialog.ShowAlert(inflater.getContext(), getText(R.string.lbl_typedCodeIsWrong).toString(), getText(R.string.lbl_errorCheckingCard).toString(), getText(R.string.lbl_continue).toString(), null);				
			}			
		});
		
		return rootView;		
	}
}
