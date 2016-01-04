package com.mobilis.reader.fragments;

public class mobTab
{
	private String _Name = null;
	private mobFragment _Fragment = null;
	
	public mobTab(String Name, mobFragment Frag)
	{
		this._Name = Name;
		this._Fragment = Frag;
	}
	
	public String getName(){ return this._Name; }
	public mobFragment getFragment(){ return this._Fragment; }
}
