package com.mobilis.reader.activities;

import com.mobilis.reader.CardState;
import com.mobilis.reader.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ImageView;	
import android.widget.TextView;

public class CardInfoActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_info);
	
		ImageView IV_Service = (ImageView)this.findViewById(R.id.iv_service);
		TextView TV_Service = (TextView)this.findViewById(R.id.tv_service_name);
		TextView TV_CardID = (TextView)this.findViewById(R.id.tv_cardid);
		TextView TV_Ticket = (TextView)this.findViewById(R.id.tv_ticket_name);
		TextView TV_TicketsAvailable = (TextView)this.findViewById(R.id.tv_tickets_available_name);
		TextView TV_Messages = (TextView)this.findViewById(R.id.tv_messages_name);
			
		CardState State = (CardState)this.getIntent().getExtras().get("CardState");
		if(State == null)
			finish();
		
		switch(State.getOnlineService())
		{
			case EMT:
				IV_Service.setImageResource(R.drawable.logo_emt);
				TV_Service.setText(R.string.lbl_emtvalencia);
				break;
				
			case METRO_VALENCIA:
				IV_Service.setImageResource(R.drawable.logo_metro);
				TV_Service.setText(R.string.lbl_metrovalencia);
				break;
				
			default:
				finish();
				return;
		}
		
		TV_CardID.setText(State.getCard().toStringWithSeparators());
		
		if(State.getTitle() != null)
		{
			
			TV_Ticket.setText(State.getTitle());
			TV_TicketsAvailable.setText(String.format("%d",State.getAvailableTickets()));
						
			if(State.getMessagesLength() > 0)
			{
				StringBuilder Builder = new StringBuilder();
				for(int i = 0; i < State.getMessagesLength(); i++)
				{
					Builder.append(State.getMessages().get(i));
					Builder.append("\n");
				}
				
				TV_Messages.setText(Builder.toString());
			}
			else
			{
				TV_Messages.setText(R.string.lbl_noMessages);
				TV_Messages.setTypeface(null, Typeface.ITALIC);	
			}
		}
		else
		{
			TV_Ticket.setText(R.string.lbl_notAvailable);
			TV_TicketsAvailable.setText("-");

			TV_Messages.setText(R.string.lbl_noMessages);
			TV_Messages.setTypeface(null, Typeface.ITALIC);
		}
	}
}
