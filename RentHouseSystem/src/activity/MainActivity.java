 package activity;
import java.util.ArrayList;
import java.util.List;
import activity.Info;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.nyp.edu.renthousesystem.R;
import database.JSONParser;


public class MainActivity extends Activity {
    EditText usernameTxt;
    EditText passwordTxt;
    private String db_username="";
    private int db_password=0;
    private static String url_all_user = "http://192.168.1.35/chenyu/db_readall.php";
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.sample, menu);
		return true;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_call:
	            String number = "tel:" + 82325895;
	            Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number)); 
	            startActivity(callIntent);
	            return true;
	        case R.id.action_email:
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	public void onsignClick(View v) throws JSONException {
     	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    		StrictMode.setThreadPolicy(policy); 
  	   	 usernameTxt = (EditText)findViewById(R.id.editusername);
 	   passwordTxt = (EditText)findViewById(R.id.editpassword);

       String username = usernameTxt.getText().toString();
       String password = passwordTxt.getText().toString();
       Log.d("test",password+"" );
       int j=0;
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    
	    JSONParser jsonParser1 = new JSONParser();
	    String json1 = jsonParser1.makeHttpReques(url_all_user, "GET", params);
	    try {
			JSONObject jSONObject = new JSONObject(json1);
			JSONArray array = jSONObject.getJSONArray("users");
			  for (int i=0; i<array.length(); i++){
				  db_username = array.getJSONObject(i).getString("username");
				  Log.d("test",db_username+"" );
				  db_password =array.getJSONObject(i).getInt("password");
					Log.d("test",db_password+"" );
			    if(username.compareTo(db_username)==0&&Integer.parseInt(password)==db_password)
			    {
			    	Info.ID=i+1;
			    	startActivity(new Intent(this,SampleActivity.class));	
		            break;
			    }
			    else
			    {
			    	j++;
			    	
			    }
			    
			  }
			  if(j==array.length())
			  {
				  Handler handler = new Handler(Looper.getMainLooper()); 

				  handler.postDelayed(new Runnable() {
				    @Override
				  public void run() {
				   Toast.makeText(getApplicationContext(), "stupid user", Toast.LENGTH_LONG).show();				
				    }
				  }, 1000 );
				  					
				  
				  
			  }
	    }
			  catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
	public void onregClick(View v) {
		startActivity(new Intent(this,RegActivity.class));		
		
	}	 
 

}

	
	
	
