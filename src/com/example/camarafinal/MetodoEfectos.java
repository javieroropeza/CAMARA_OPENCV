package com.example.camarafinal;

import java.util.List;

import org.opencv.android.JavaCameraView;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.util.AttributeSet;
import android.util.Log;

public class MetodoEfectos extends JavaCameraView implements PictureCallback{
	//private static final String TAG = "Sample::MetodoEfectos";
    
	public MetodoEfectos(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	/* public List<String> getEffectList() {
	        return mCamera.getParameters().getSupportedColorEffects();
	    }*/
	 
	  public String getEffect() {
		
		  return mCamera.getParameters().getColorEffect();
	    }

	    public void setEffect(String effect) {
	    	  Camera.Parameters params = mCamera.getParameters();
	          params.setColorEffect(effect);
	          mCamera.setParameters(params);
	    }
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			
		}

		public String getEffec() {
			Camera coco ;
			coco = Camera.open();
			  return coco.getParameters().getColorEffect();
			
		}
}
