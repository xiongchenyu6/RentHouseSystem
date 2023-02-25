package activity;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import services.MusicService;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.BarGraphView;
import com.jjoe64.graphview.CustomLabelFormatter;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.nyp.edu.renthousesystem.R;

import database.JSONParser;

public class ControlActivity extends Activity {
	private Runnable mTimer1;
	private GraphView graphView;
	private GraphViewSeries exampleSeries;
	private final Handler mHandler = new Handler();
    private int light=0;
    private int door=0;
    private double[] temp=new double[7];
    private Long[] date=new Long[7];
    private String[] datetemp=new String[7];
	Random rand = new Random();
	int size = 7;
	String myDate;
	GraphViewData[] data = new GraphViewData[size];
    ToggleButton tgblight;
    ToggleButton tgbdoor;
    private static String url_control = "http://192.168.1.26/chenyu/status_read.php";
    private static String url_set = "http://192.168.1.26/chenyu/status_updates.php?door=";
    private static String url_temp = "http://192.168.1.26/chenyu/status_temp.php";
	private boolean mIsBound = false;
	private MusicService mServ;
	private ServiceConnection Scon =new ServiceConnection(){

		public void onServiceConnected(ComponentName name, IBinder
	     binder) {
		mServ = ((MusicService.ServiceBinder)binder).getService();
		}

		public void onServiceDisconnected(ComponentName name) {
			mServ = null;
		}
		};

		void doBindService(){
	 		bindService(new Intent(this,MusicService.class),
					Scon,Context.BIND_AUTO_CREATE);
			mIsBound = true;
		}

		void doUnbindService()
		{
			if(mIsBound)
			{
				unbindService(Scon);
	      		mIsBound = false;
			}
		}

		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_control);
			Intent music = new Intent();
			music.setClass(this,MusicService.class);
			startService(music);
			readtempreture();			
			data = new GraphViewData[size];
			for (int i=0; i<size; i++) {
				data[i] = new GraphViewData(date[6-i], temp[6-i]); // next day
			}
			exampleSeries = new GraphViewSeries(data);

			
			
				graphView = new LineGraphView(
						this
						, "tempreture"
				);
				exampleSeries.getStyle().color = Color.CYAN;
			graphView.addSeries(exampleSeries); // data

			/*
			 * date as label formatter
			 */
			final SimpleDateFormat dateFormat = new SimpleDateFormat("MM d");
			graphView.setCustomLabelFormatter(new CustomLabelFormatter() {
				@Override
				public String formatLabel(double value, boolean isValueX) {
					if (isValueX) {
						Date d = new Date((long) value);
						return dateFormat.format(d);
					}else
					{
						
					}
					return null; // let graphview generate Y-axis label for us
				}
			});

			LinearLayout layout = (LinearLayout) findViewById(R.id.llgraph);
			layout.addView(graphView);
		}
       public void readtempreture()
       {
    	   
	     	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy); 
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
    	    JSONParser jsonParser1 = new JSONParser();
			tgbdoor=(ToggleButton)findViewById(R.id.tgbdoor);
			tgblight=(ToggleButton)findViewById(R.id.tgblight);
			String json = jsonParser1.makeHttpReques(url_temp, "GET", params);
		    String json1 = jsonParser1.makeHttpReques(url_control, "GET", params);
		    try {
		    	JSONObject jSONObject = new JSONObject(json);

				JSONObject jSONObject1 = new JSONObject(json1);
				JSONArray array = jSONObject.getJSONArray("users");
				JSONArray array1 = jSONObject1.getJSONArray("control");
			  	  for (int i=0; i<array.length(); i++){
			  		  temp[i]=array.getJSONObject(i).getDouble("tempreture");
			  		 Log.d("fds",temp[i]+"");
			  		  datetemp[i]=array.getJSONObject(i).getString("time");
			  		 
			  		String myDate = new String(datetemp[i]);
			  		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			  		Date datesd = format.parse(myDate);
			  		date[i] = datesd.getTime();
			  	  }
				door = array1.getJSONObject(0).getInt("door");
				light =array1.getJSONObject(0).getInt("light");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(door==0)
			{tgbdoor.setChecked(false);}else
			{tgbdoor.setChecked(true);}
			if(light==0)
			{	tgblight.setChecked(false);}else
			{	tgblight.setChecked(true);}
    	   
    	   
       }

		@Override
		protected void onPause() {
			mHandler.removeCallbacks(mTimer1);
			super.onPause();
		}
		protected void onResume() {
			super.onResume();
			mTimer1 = new Runnable() {
				@Override
				public void run() {
					readtempreture();
					exampleSeries.resetData(new GraphViewData[] {
						  new GraphViewData(date[6], temp[6])
							,new GraphViewData(date[5], temp[6])
							,
							new GraphViewData(date[4], temp[4])
							, new GraphViewData(date[3], temp[3])
							, new GraphViewData(date[2], temp[2]) // another frequency
							, new GraphViewData(date[1], temp[1])
							, new GraphViewData(date[0], temp[0])
					});
	
					mHandler.postDelayed(this, 1000);
				}
			};
			mHandler.postDelayed(mTimer1, 1000);
		}
	
		public void light(View v)
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy); 
			    String uri = null; 
				if (tgblight.isChecked()==(true))
				{
					light = 1;
					
				}else{
				light =0;	
				}
try{
				    	
				    	uri= url_set +
				    			URLEncoder.encode(String.valueOf(door) , "UTF-8")+"&light="+URLEncoder.encode(String.valueOf(light), "UTF-8");
				} catch(UnsupportedEncodingException e){ e.printStackTrace();}
List<NameValuePair> params = new ArrayList<NameValuePair>();
JSONParser jsonParser1 = new JSONParser();
String json1 = jsonParser1.makeHttpReques(uri, "GET", params);   
			
		}
		public void door(View v)
		{	     StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy); 
		    String uri = null; 
		 
			if (	tgbdoor.isChecked()==(true))
			{
				door = 3;
				
			}else{
				door =0;	
			}
			   try{
			    	
			    	uri= url_set +
			    			URLEncoder.encode(String.valueOf(door) , "UTF-8")+"&light="+URLEncoder.encode(String.valueOf(light), "UTF-8");
			} catch(UnsupportedEncodingException e){ e.printStackTrace();}
	    	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    	    JSONParser jsonParser1 = new JSONParser();
	    	    String json1 = jsonParser1.makeHttpReques(uri, "GET", params);						
		}
	

	
}
	
	

