package com.mobilis.reader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.mobilis.reader.onlineservices.OnlineServices;

@SuppressWarnings("serial")
public class CardState implements Serializable
{
	private Date _Date = null;
	private String _Title = null;
	private int _AvailableTickets = 0;
	private ArrayList<String> _Messages = null;
	private OnlineServices _Service = OnlineServices.UNKNOWN;
	private MobilisCard _Card = null;
	
	public CardState(MobilisCard Card, OnlineServices Service)
	{
		this._Messages = new ArrayList<String>();	
		this._Date = new Date();
		
		this._Service = Service;
		this._Card = Card;
	}
	
	public void addMessage(String Message)
	{
		this._Messages.add(Message);
	}
	
	public void setTitle(String Title){ this._Title = Title; }
	public void setAvailableTickets(int Tickets){ this._AvailableTickets = Tickets; }
	
	public MobilisCard getCard(){ return this._Card; }
	public OnlineServices getOnlineService(){ return this._Service; }
	public Date getDate(){ return this._Date; }
	public String getTitle(){ return this._Title; }
	public int getAvailableTickets(){ return this._AvailableTickets; }
	public ArrayList<String> getMessages(){ return this._Messages; }
	public int getMessagesLength(){ return this._Messages.size(); }
}
