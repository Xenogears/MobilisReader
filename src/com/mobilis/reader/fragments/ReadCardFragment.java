package com.mobilis.reader.fragments;

import com.mobilis.reader.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ReadCardFragment extends mobFragment
{
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_read_card, container, false);
		
		return rootView;		
	}
}
