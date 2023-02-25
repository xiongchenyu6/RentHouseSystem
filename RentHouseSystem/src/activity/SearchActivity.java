package activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nyp.edu.renthousesystem.R;

import database.JSONParser;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity {
	
	// List view
	private ListView lv;
	
	// Listview Adapter
	ArrayAdapter<String> adapter;
	
	// Search EditText
	EditText inputSearch;
    private String address;
    private static String url_all_user = "http://192.168.1.28/chenyu/db_readall.php";
	
	// ArrayList for Listview
	ArrayList<HashMap<String, String>> productList;

    @SuppressWarnings("null")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    JSONParser jsonParser1 = new JSONParser();
		    String json1 = jsonParser1.makeHttpReques(url_all_user, "GET", params);
		    try {
				JSONObject jSONObject = new JSONObject(json1);
				JSONArray array = jSONObject.getJSONArray("users");
				String[]address = new String[(array.length())];
				  for (int i=0; i<array.length(); i++){
					  address[i] = array.getJSONObject(i).getString("place");
						lv = (ListView) findViewById(R.id.list_view);
						inputSearch = (EditText) findViewById(R.id.inputSearch);
						adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.address_name,address);
						lv.setAdapter(adapter);
						inputSearch.addTextChangedListener(new TextWatcher()
						{			@Override
							
							
							
							public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
							// When user changed the Text
							SearchActivity.this.adapter.getFilter().filter(cs);	
						}

						@Override
						public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
								int arg3) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub							
						}
						});
				    
				  }
				  lv.setOnItemClickListener(new OnItemClickListener() {
                        
			
						@Override
						public void onItemClick(AdapterView<?> arg0, View view,
								int position, long id) {
						     Intent appInfo = new Intent(SearchActivity.this, ComfirmActivity.class);
						       startActivity(appInfo);

							
						}
				    });}catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


		}
  

}



