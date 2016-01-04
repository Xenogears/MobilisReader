package com.mobilis.reader.onlineservices;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import com.mobilis.reader.CardState;
import com.mobilis.reader.MobilisCard;
import com.mobilis.reader.tools.Utils;

public class EMTValenciaChecker extends MobilisChecker
{
	public static final String REQUEST_URI = "http://www.emtvalencia.es/ciudadano/modules/mod_saldo/busca_saldo.php";
	private MobilisCard _Card = null;
	
	public EMTValenciaChecker(Context Context, MobilisCard Card)
	{
		super(Context);
		
		this._Card = Card;
	}
	
	public CardState processResponse(HttpResponse Response)
	{
		CardState State = new CardState(this._Card, OnlineServices.EMT);
		
		try
		{
			String Data = EntityUtils.toString(Response.getEntity());
			
			String Info = Utils.matchRegex(Data, "[0-9]+\\sviajes\\s(.*)?</b>").get(0);
			String[] Parts = Utils.splitRegex(Info, "viajes");
						
			State.setAvailableTickets(Integer.parseInt(Utils.removeHTML(Utils.fullTrim(Parts[0]))));
			State.setTitle(Utils.removeHTML(Utils.fullTrim(Parts[1])));						
		}
		catch (Exception e){ e.printStackTrace(); }
		
		return State;
	}
	
	public void run()
	{
		try
		{
			HttpClient Client = new DefaultHttpClient();
			HttpPost Post = new HttpPost(EMTValenciaChecker.REQUEST_URI);
			
			List<NameValuePair> Parameters = new ArrayList<NameValuePair>(1);
			Parameters.add(new BasicNameValuePair("numero", this._Card.toString()));
			Parameters.add(new BasicNameValuePair("idioma", "es"));			
			Post.setEntity(new UrlEncodedFormEntity(Parameters));
			
			HttpResponse Response = Client.execute(Post);
			
			if(Response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) //200
			{
				CardState State = this.processResponse(Response);
				super.notifyRead(_Card, State);
			}
		}
		catch (Exception e){ e.printStackTrace(); }
	}
}
