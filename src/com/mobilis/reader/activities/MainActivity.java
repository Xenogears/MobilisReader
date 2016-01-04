package com.mobilis.reader.activities;

import java.util.Locale;

import com.mobilis.reader.R;
import com.mobilis.reader.dialogs.EnableNFCDialog;
import com.mobilis.reader.fragments.AboutAppFragment;
import com.mobilis.reader.fragments.ReadCardFragment;
import com.mobilis.reader.fragments.TypeCodeFragment;
import com.mobilis.reader.fragments.mobTab;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	private NfcAdapter NfcAdapt = null;
	private PendingIntent readIntent = null;
	
	private BroadcastReceiver ChangeTabReceiver = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				 
		//NFC INTENT
 		this.readIntent = PendingIntent.getActivity(this, 0, new Intent(this, LoadingActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP).addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY), 0);

		//ACTION BAR
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager)findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
				actionBar.setSelectedNavigationItem(position);
			}
		});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++)
		{
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}	
	}
	
	protected void onDestroy()
	{
		super.onDestroy();
		
		unregisterReceiver(this.ChangeTabReceiver);
	}
	
	public void onPause()
	{
 		super.onPause();
	    
	    if(this.NfcAdapt != null)
	    	this.NfcAdapt.disableForegroundDispatch(this);
	}

    public void onResume()
    {
        super.onResume();
        
        if(this.NfcAdapt == null)
        	this.NfcAdapt = NfcAdapter.getDefaultAdapter(this); //PRIMERA BUSQUEDA

        if(this.NfcAdapt != null)
        {
        	if(this.NfcAdapt.isEnabled())
        		this.NfcAdapt.enableForegroundDispatch(this, this.readIntent, null,  null);
        	else
        	{
        		EnableNFCDialog Dialog = new EnableNFCDialog(MainActivity.this, this);
        		Dialog.getDialog().show();
        	}
        }
    }
	
	//MENU
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return false;
	}
	 
	//TABS
	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
	
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction)
	{
		
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{
		public mobTab[] Tabs = null;
		
		public SectionsPagerAdapter(FragmentManager fm)
		{
			super(fm);
			
			Locale Loc = Locale.getDefault();
			this.Tabs = new mobTab[]
			{
					new mobTab(getString(R.string.title_section1).toUpperCase(Loc), new ReadCardFragment()),
					new mobTab(getString(R.string.title_section2).toUpperCase(Loc), new TypeCodeFragment()),
					new mobTab(getString(R.string.title_section3).toUpperCase(Loc), new AboutAppFragment())
			};
		}

		@Override
		public Fragment getItem(int position)
		{
			return this.Tabs[position].getFragment();
		}

		@Override
		public int getCount()
		{
			return this.Tabs.length;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return this.Tabs[position].getName();
		}
	}
}
