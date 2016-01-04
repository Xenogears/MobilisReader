package com.mobilis.reader.nfc;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;

public class NFCIntent
{
	private Tag ExtraTag = null;
	private Tag ReadedTag = null;	
	
	public NFCIntent(Intent Intent)
	{
		this.ExtraTag = Intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	}
	
	private boolean isNfcA()
	{
		if(this.ExtraTag == null)return false;
		
		for(String Tech : this.ExtraTag.getTechList())
		{   	
			if(Tech.equals(NfcA.class.getName()))
				return true;
		}
		
		return false;
	}

	public NFCACard readCard()
	{
		if(isNfcA())
		{
			NfcA NFCA = null;
			NFCACard Card = null;
			
	        try
	        {
	        	NFCA = NfcA.get(this.ExtraTag);
	        	NFCA.connect();
	        	
	        	this.ReadedTag = NFCA.getTag();	
	        	
	        	byte[] ATQA = NFCA.getAtqa();
	        	short SAK = NFCA.getSak();      	        	
	        	byte[] UID = this.ReadedTag.getId();

	        	Card = new NFCACard(ATQA, SAK, UID);
	        }
	        catch (Exception e){ e.printStackTrace(); }
	        finally
	        {
	            try{ NFCA.close(); }
	            catch(Exception e){ e.printStackTrace(); }
	        }			
			
			return Card;
		}
		else return null;
	}
}
