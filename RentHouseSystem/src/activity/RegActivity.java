package activity;
 
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
 




import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 


import com.nyp.edu.renthousesystem.R;

import database.JSONParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
 
public class RegActivity extends Activity {
 
    // Progress Dialog
    private ProgressDialog pDialog; 
    JSONParser jsonParser = new JSONParser();
    EditText usernameTxt;
    EditText passwordTxt;
    EditText ageTxt;
	RadioButton sexTxt;
	EditText hobbyTxt ;
	EditText plcaeTxt;
	RadioButton typeTxt;
	EditText numberTxt;
	EditText emailTxt;
    // url to create new product
    private static String url_create_user = "http://192.168.1.28/chenyu/db_create.php?";
    private static String url_all_user = "http://192.168.1.28/chenyu/db_readall.php";
    String connection;
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
	private String db_username="";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
 
        // Edit Text

 
        // Create button
        Button btnCreateProduct = (Button) findViewById(R.id.btnUpload);
 
        // button click event
        btnCreateProduct.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // creating new product in background thread
       	   	 usernameTxt = (EditText)findViewById(R.id.editUsername);
      	   passwordTxt = (EditText)findViewById(R.id.editPassword);
      	    ageTxt = (EditText)findViewById(R.id.editAge);
      	   	RadioGroup sexGroup = (RadioGroup)findViewById(R.id.rdSex);
      	   	 sexTxt = (RadioButton) findViewById(sexGroup.getCheckedRadioButtonId());
      	   hobbyTxt = (EditText)findViewById(R.id.editHobby);
      	    plcaeTxt = (EditText)findViewById(R.id.editPlace);
      		RadioGroup typeGroup = (RadioGroup)findViewById(R.id.rdType);
      		 typeTxt = (RadioButton) findViewById(typeGroup.getCheckedRadioButtonId());
      	   	 numberTxt = (EditText)findViewById(R.id.editNumber);
      	    emailTxt = (EditText)findViewById(R.id.editEmail);
      		Log.d("test", "adding");
                new CreateNewUser().execute();
            }
        });
    }
 public void test (View v) 
 {
	
	 
 }
    /**
     * Background Async Task to Create new product
     * */
    class CreateNewUser extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(RegActivity.this);
            pDialog.setMessage("Creating Product..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
 
        /**
         * Creating product
         * @return 
         * */
        protected String doInBackground(String... args){
        	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    		StrictMode.setThreadPolicy(policy); 
            String username = usernameTxt.getText().toString();
            String password = passwordTxt.getText().toString();
            String age = ageTxt.getText().toString();
            String sex = sexTxt.getText().toString();
            String hobby = hobbyTxt.getText().toString();
            String place = plcaeTxt.getText().toString();
            String type = typeTxt.getText().toString();
            String number = numberTxt .getText().toString();
            String email = emailTxt .getText().toString();
           
            int j=0;
    	    List<NameValuePair> params = new ArrayList<NameValuePair>();
    	    JSONParser jsonParser1 = new JSONParser();
    	    String json1 = jsonParser1.makeHttpReques(url_all_user, "GET", params);
		    try {
				JSONObject jSONObject = new JSONObject(json1);
				JSONArray array = jSONObject.getJSONArray("users");
				  for (int i=0; i<array.length(); i++){
					  db_username = array.getJSONObject(i).getString("username");
				    if(username.compareTo(db_username)==0)
				    {
				    	pDialog.dismiss();
			            break;
				    }
				    else
				    {
				    	j++;
				    	
				    }
				    
				  }
					
				  if(j==array.length())
				  {
			            try {
							connection=url_create_user +"username="+URLEncoder.encode(username, "UTF-8")+"&password"+URLEncoder.encode
		(password,"UTF-8")+"&age="+URLEncoder.encode(age,"UTF-8")+"&sex="+URLEncoder.encode(sex,"UTF-8")
									+"&hobby="+URLEncoder.encode(hobby,"UTF-8")+"&place="+URLEncoder.encode(place,"UTF-8")+"&type="+
						URLEncoder.encode(type,"UTF-8")+"&number="+URLEncoder.encode(number,"UTF-8")+"&email="
									+URLEncoder.encode(email,"UTF-8");
							Log.d("test",connection);
							
						} catch (UnsupportedEncodingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			            		;
			            		Log.d("Create Response", connection);
			          /*  params.add(new BasicNameValuePair("username", username));
			            params.add(new BasicNameValuePair("password", password));
			            params.add(new BasicNameValuePair("age", age));
			            params.add(new BasicNameValuePair("sex", sex));
			            params.add(new BasicNameValuePair("hobby", hobby));
			            params.add(new BasicNameValuePair("plcae", plcae));
			            params.add(new BasicNameValuePair("type", type));
			            params.add(new BasicNameValuePair("number", number));
			            params.add(new BasicNameValuePair("email", email));*/
			          
			            // getting JSON Object
			            // Note that create product url accepts POST method
			            JSONParser jsonParser = new JSONParser();
			            JSONObject json =jsonParser.makeHttpRequest(connection,
			            		"POST", params);
			 
			            finish();
					  
					  
				  }
				  else {Handler handler = new Handler(Looper.getMainLooper()); 

handler.postDelayed(new Runnable() {
  @Override
public void run() {
 Toast.makeText(getApplicationContext(), getString(R.string.idleness_toast), Toast.LENGTH_LONG).show();				
  }
}, 1000 );
					
				  }
			    
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
            // Building Parameters

 
        
    return "good";    
    }
}}
