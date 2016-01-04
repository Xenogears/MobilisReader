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

public class MetroValenciaChecker extends MobilisChecker
{
	public static final String REQUEST_URI = "http://www.metrovalencia.es/tools_consulta_tsc.php";
	private MobilisCard _Card = null;
	
	public MetroValenciaChecker(Context Context, MobilisCard Card)
	{
		super(Context);
		
		this._Card = Card;
	}
	
	public CardState processResponse(HttpResponse Response)
	{
		CardState State = new CardState(this._Card, OnlineServices.METRO_VALENCIA);
		
		try
		{
			String Data = EntityUtils.toString(Response.getEntity());
			
			ArrayList<String> P = Utils.matchRegex(Data, "</form>(\\r*\\s*.*)*</body>");
			String[] Lines = Utils.splitRegex(P.get(0), "<br>|<br/>");
			
			for(int i = 0; i < Lines.length; i++)
			{
				Lines[i] = Utils.removeHTML(Lines[i]);			
				
				if(Lines[i].contains(":"))
				{
					String[] Pair = Utils.splitRegex(Lines[i], ":");
					if(Pair[0].equals("Título"))State.setTitle(Utils.fullTrim(Pair[1]));
					else if(Pair[0].equals("Saldo de viajes"))
					{
						Pair[1] = Pair[1].trim();
						
						int AvailableTickets = Integer.parseInt(Utils.fullTrim(Pair[1].substring(0, Pair[1].indexOf(" "))));
						State.setAvailableTickets(AvailableTickets);
					}
				}
				else
				{
					State.addMessage(Lines[i]);
				}
			}			
		}
		catch (Exception e){ e.printStackTrace(); }
		
		return State;
	}
	
	public void run()
	{
		try
		{
			HttpClient Client = new DefaultHttpClient();
			HttpPost Post = new HttpPost(MetroValenciaChecker.REQUEST_URI);
			
			List<NameValuePair> Parameters = new ArrayList<NameValuePair>(1);
			Parameters.add(new BasicNameValuePair("tsc", this._Card.getUID()));
			
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
