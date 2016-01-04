package com.mobilis.reader;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.mobilis.reader.nfc.NFCACard;
import com.mobilis.reader.tools.Utils;

@SuppressWarnings("serial")
public class MobilisCard implements Serializable
{
	private String _UID = null;
	private ArrayList<CardState> _States = null;
	
	public MobilisCard(String UID)
	{		
		this.setUID(UID);
		this._States = new ArrayList<CardState>();
	}
	
	public MobilisCard(NFCACard Card)
	{	
		this.setUID(Card.getUIDAsString());
		this._States = new ArrayList<CardState>();
	}
	
	public void addCardState(CardState NewState)
	{
		this._States.add(NewState);
	}
	
	public List<CardState> getCardStates(){ return this._States; }
	public String getUID(){ return this._UID; }
	
	@SuppressLint("DefaultLocale")
	public String getCRC()
	{		
		long UID = Long.parseLong(this._UID);
		
		byte[] Octets = new byte[4];
		Octets[0] = (byte)(UID >> 24);
		Octets[1] = (byte)(UID >> 16);
		Octets[2] = (byte)(UID >> 8);
		Octets[3] = (byte)(UID);
		
		byte XOR = (byte)(((Octets[0] ^ Octets[1]) ^ Octets[2]) ^ Octets[3]);
		
		Locale Loc = Locale.getDefault();
		return String.format(Loc, "%02d", (XOR & 0xFF) % 100);
	}
	
	@Override
	public String toString()
	{
		return getUID() + getCRC();
	}
	
	public String toStringWithSeparators()
	{
		String Temp = this.toString();
		return String.format("%s %s %s", Temp.substring(0, 4), Temp.substring(4, 8), Temp.substring(8, 12));
	}
	
	protected void setUID(String UID)
	{
		if(UID.length() > 10)UID = UID.substring(0, 10);
		else if(UID.length() < 10)UID = Utils.padLeft(UID, 10, "0");
	
		this._UID = UID;
	}
}
