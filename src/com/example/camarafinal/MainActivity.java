package com.example.camarafinal;

import android.app.Activity;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity  { 
	
	public int CAMERA_ID = 0;
	int ca;
	Spinner camara;
	String[] camaras = {"Frontal","Trasera"};
	
	private View.OnClickListener Resolution= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	Intent inten = new Intent(MainActivity.this, Resolucion.class);
        	ca=CAMERA_ID;
        	//tam = text;
        	//tam=tamaño(a);
        	inten.putExtra("camara",ca );
			startActivity(inten);
    }
};

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		camara = (Spinner)findViewById(R.id.camera);
		 ArrayAdapter<String> resol =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, camaras);	
		  camara.setAdapter(resol);
		  camara.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					if(position==0){
						CAMERA_ID =	1;	      
					}else if(position==1){
						      CAMERA_ID =	0;
						}
				}
				@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}	
			});	
		 Button main = (Button) findViewById(R.id.btnresolucion);
	        main.setOnClickListener(Resolution);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
	}	
}
