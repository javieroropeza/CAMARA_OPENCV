package com.example.camarafinal;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class efectos extends Activity{
int camara,a,l,p,poseefec;
String  text;
CharSequence cs, pefecto;
ListView efect;
ArrayList<String> efec = new ArrayList<String>();
private MenuItem[] mEffectMenuItems;
private SubMenu mColorEffectsMenu;

private View.OnClickListener TomarFoto= new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    	Intent inten = new Intent(efectos.this, MostrarCamara.class);
    	
    	inten.putExtra("camara",camara );
    	inten.putExtra("alto",a);
    	inten.putExtra("ancho",l );
    	inten.putExtra("pos",p );
    	inten.putExtra("posefec",pefecto );
		startActivity(inten);
}
};

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.efecto);
	
	Bundle datos = this.getIntent().getExtras();
	camara = datos.getInt("camara");
	a = datos.getInt("alto");
	l = datos.getInt("ancho");
	p = datos.getInt("pos");
	poseefec = datos.getInt("pefecto");
	//pefecto = datos.getInt("posefec");
	//pefecto = datos.getString("posefec");
	
	
	final Camera camera;
	camera = Camera.open();
	List<String> effects = camera.getParameters().getSupportedColorEffects();
	camera.release();
	
	ListIterator<String> effectItr = effects.listIterator();
    while(effectItr.hasNext()) {
       String element = effectItr.next();
       efec.add(element);
    }
	
	efect = (ListView) findViewById(R.id.listefect);
	 ArrayAdapter<String> res =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, efec);
	 efect.setAdapter(res);
	 
	 efect.setOnItemClickListener( new OnItemClickListener(){
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			//text = efect.getItem(position);
			 text = efect.getItemAtPosition(position).toString();
			 cs = text; 
					
			 Toast.makeText(getApplicationContext(), " " + text, Toast.LENGTH_SHORT).show();
				if(position == 0){	 
					poseefec=position;
					//pefecto = position
				}
				if(position == 1){
					poseefec=position;
				}
				if(position == 2){
					poseefec=position;
				}
				if(position == 3){	 
					poseefec=position;
				}
				if(position == 4){ 
					poseefec=position;
				}
				if(position == 5){ 
					poseefec=position;
				}
				if(position == 6){ 
					poseefec=position;
				}
				if(position == 7){ 
					poseefec=position;
				}
				if(position == 8){ 
					poseefec=position;
				}
		}
	 });
	 
	 Button abrircamara = (Button) findViewById(R.id.btntakephoto);
        abrircamara.setOnClickListener(TomarFoto);
	
}
/*@Override
public boolean onCreateOptionsMenu(Menu menu) {
	final Camera camera;
	camera = Camera.open();
	List<String> effects = camera.getParameters().getSupportedColorEffects();
	camera.release();
	 mColorEffectsMenu = menu.addSubMenu("Color Effect");
	    mEffectMenuItems = new MenuItem[effects.size()];
	
	int idx = 0;
    ListIterator<String> effectItr = effects.listIterator();
    while(effectItr.hasNext()) {
       String element = effectItr.next();
       mEffectMenuItems[idx] = mColorEffectsMenu.add(1, idx, Menu.NONE, element);
       idx++;
    }    
   
    return true;
}

public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getGroupId() == 1)
    {
        
    	Toast.makeText(getApplicationContext(), "Ha pulsado el item " + item, Toast.LENGTH_SHORT).show();
    }
   

    return true;
}*/
}
