package com.mobilis.reader.nfc;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NFCACard implements Serializable
{
	private byte[] _ATQA = null;
	private short _SAK = 0;
	private byte[] _UID = null;
	
	public NFCACard(byte[] ATQA, short SAK, byte[] UID)
	{
		this._ATQA = ATQA;
		this._SAK = SAK;
		this._UID = UID;
	}
	
	public byte[] getATQA(){ return this._ATQA; }
	public short getSAK(){ return this._SAK; }

	public byte[] getUID(){ return this._UID; }
	public int getUIDAsInteger(){ return ((this._UID[3] & 0xFF) << 24) | ((this._UID[2] & 0xFF) << 16) | ((this._UID[1] & 0xFF) << 8) | (this._UID[0] & 0xFF); }
	public String getUIDAsString(){ return Long.toString(this.getUIDAsInteger() & 0xFFFFFFFFL); }
}
