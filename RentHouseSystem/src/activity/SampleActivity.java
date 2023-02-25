package activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import activity.Info;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import slidingwindow.FlyOutContainer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
import database.JSONParser;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nyp.edu.renthousesystem.R;
public class SampleActivity extends Activity {
    private GoogleMap mMap;
	FlyOutContainer root;
	int id;
	private String address;
	private static String url_all_user = "http://192.168.1.35/chenyu/db_readall.php";
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(R.layout.activity_sample, null);		
		this.setContentView(root);
		
		id =Info.ID;
		Log.d("aa",id+"");
		mMap=((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.setMyLocationEnabled(true);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		List<NameValuePair> params = new ArrayList<NameValuePair>();
	    JSONParser jsonParser1 = new JSONParser();
	    String json1 = jsonParser1.makeHttpReques(url_all_user, "GET", params);
	    try {
			JSONObject jSONObject = new JSONObject(json1);
			JSONArray array = jSONObject.getJSONArray("users");
			String place = null;
			  for (int i=0; i<array.length(); i++){
		             double []coordinate={0,0};
		             
		            place =array.getJSONObject(i).getString("place");
		            Log.d("as",place);
	                coordinate =  getLatLongFromAddress(place);
	                Log.d("as",coordinate[0]+"");
	                
		            final LatLng temp = new LatLng(coordinate[0], coordinate[1]);
		        mMap.addMarker(new MarkerOptions().position(temp));
        }}catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		
		CameraUpdate update = CameraUpdateFactory.zoomBy(5);	    
	    mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
	        public boolean onMarkerClick(Marker arg0) {
	            Toast.makeText(getApplicationContext()
	                    , "Remove Marker " + String.valueOf(arg0.getId())
	                    , Toast.LENGTH_SHORT).show();
	            return true;
	        }
	    });
	}
	
	public double [] getLatLongFromAddress(String youraddress) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
	    String uri = null; 
	    try{
	    	
	    	uri= "http://maps.google.com/maps/api/geocode/json?address=" +
	    			URLEncoder.encode(youraddress, "UTF-8")+"&sensor=false";
	    	
	    }
	    catch(UnsupportedEncodingException e){ e.printStackTrace();}
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    JSONParser jsonParser = new JSONParser();
	    String json =  jsonParser.makeHttpReques(uri, "GET", params);

	    try {	
	    	JSONObject jSONObject = new JSONObject(json);
	        double lng = jSONObject.getJSONArray("results").getJSONObject(0)
	            .getJSONObject("geometry").getJSONObject("location")
	            .getDouble("lng");

	        double lat = jSONObject.getJSONArray("results").getJSONObject(0)
	            .getJSONObject("geometry").getJSONObject("location")
	            .getDouble("lat");

	        Log.d("latitude", "" + lat);
	        Log.d("longitude", "" + lng);
	        double[] temp={lat,lng};
	        return temp;
	        
	    } catch (JSONException e) {
	        e.printStackTrace();
	        double[] temp={0,0};
	        return temp;
	    }
	
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sample, menu);
		return true;
	}

	public void toggleMenu(View v){
		this.root.toggleMenu();
	}
	public void search(View v){
		startActivity(new Intent(this,SearchActivity.class));	
	}
	public void control(View v){
		startActivity(new Intent(this,ControlActivity.class));	
	}
	public void message(View v){
		startActivity(new Intent(this,MessageActivity.class));	
	}
}
