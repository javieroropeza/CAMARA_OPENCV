package com.example.camarafinal;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.*;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class Resolucion extends Activity{
	
	
	int camara,a,l,p;
	private List<Size> mSupportedImageSizes;
	ListView lista;
	ArrayList<String> al = new ArrayList<String>();
	
	private View.OnClickListener TomarFoto= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	Intent inten = new Intent(Resolucion.this, MostrarCamara.class);
        	
        	inten.putExtra("camara",camara );
        	inten.putExtra("alto",a);
        	inten.putExtra("ancho",l );
        	inten.putExtra("pos",p );
			startActivity(inten);
    }
};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.resolucion);
		
		Bundle datos = this.getIntent().getExtras();
		camara = datos.getInt("camara");
		a = datos.getInt("alto");
		l = datos.getInt("ancho");
		p = datos.getInt("pos");
		
		final Camera camera;
		camera = Camera.open(camara);
		final Parameters parameters = camera.getParameters();
		camera.release();
		
		mSupportedImageSizes =parameters.getSupportedPreviewSizes();
		
		ListIterator<Size> resolucionItr = mSupportedImageSizes.listIterator();
		while(resolucionItr.hasNext()){
			Camera.Size element =  resolucionItr.next();
			al.add(element.height + "x" + element.width);
		}
		lista = (ListView) findViewById(R.id.listresolucion);
		 ArrayAdapter<String> res =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, al);
		 lista.setAdapter(res);
		 
		/* setListAdapter (new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, al));
		 ListView lv = getListView();
		 lv.setTextFilterEnabled(true);
		 lv.setOnItemClickListener(new OnItemClickListener(){*/
		 lista.setOnItemClickListener( new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				 Toast.makeText(getApplicationContext(), "Ha pulsado el item " + position, Toast.LENGTH_SHORT).show();
					if(position == 0){	 
    					p=position;
    				}
    				if(position == 1){
    					p=position;
    				}
    				if(position == 2){
    					p=position;
    				}
    				if(position == 3){	 
    					p=position;
    				}
    				if(position == 4){ 
    					p=position;
    				}
    				if(position == 5){ 
    					p=position;
    				}
    				if(position == 6){ 
    					p=position;
    				}
    				if(position == 7){ 
    					p=position;
    				}
    				if(position == 8){ 
    					p=position;
    				}
    				if(position == 9){ 
    					p=position;
    				}
			}
		 });
		
		 Button abrircamara = (Button) findViewById(R.id.btntakephoto);
	        abrircamara.setOnClickListener(TomarFoto);
		
	}
	
}
