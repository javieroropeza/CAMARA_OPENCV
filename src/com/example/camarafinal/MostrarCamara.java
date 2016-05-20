package com.example.camarafinal;

import java.util.ArrayList;
import java.util.List;


import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.*;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import org.opencv.core.Point;


public class MostrarCamara extends Activity implements CvCameraViewListener2{

	private CameraBridgeViewBase mCameraView;
	private MetodoEfectos mOpenCvCameraView;
	Mat rgba;
	MostrarCamara lol;
	
	private List<android.hardware.Camera.Size> mSupportedImageSizes;
	private Camera.Size element;
	private Mat mIntermediateMat;
	boolean bol=false;
	int camara,efecto;
	String pef;
	
	 //protected Camera mCamera;
	 public static final int VIEW_MODE_RGBA = 0;
	 public static final int VIEW_MODE_SEPIA = 3;
	 public static final int      VIEW_MODE_PIXELIZE  = 6;
	 public static final int      VIEW_MODE_POSTERIZE = 7;
	 
	 private Size                 mSize0;

	   private Mat                  mMat0;
	    private MatOfInt             mChannels[];
	    private MatOfInt             mHistSize;
	    private int                  mHistSizeNum = 25;
	    private MatOfFloat           mRanges;
	    private Scalar               mColorsRGB[];
	    private Scalar               mColorsHue[];
	    private Scalar               mWhilte;
	    private Point                mP1;
	    private Point                mP2;
	    private float                mBuff[];
	    private Mat                  mSepiaKernel;
	    private Mat                  mSepiaKerne,mSepiaKern;
	    
	    public static int viewMode = VIEW_MODE_RGBA;
	 
	 private static final String TAG = "OCVSample::Activity";
	private static final int CV_16S = 0;

	
	ArrayList<String> al = new ArrayList<String>();
	
	private BaseLoaderCallback opencvCharger = new BaseLoaderCallback(this){
		@Override
		public void onManagerConnected(final int status){
			if(status == LoaderCallbackInterface.SUCCESS){
				mCameraView.enableView();
				Log.i(TAG, "OpenCV loaded successfully");
			}else
				super.onManagerConnected(status);
		}
	};
	public void ImageManipulationsActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final Window window = getWindow();
		window.addFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.mostrarcamara);
		 
		Bundle datos = this.getIntent().getExtras();
		camara = datos.getInt("camara");
		int p = datos.getInt("pos");
		
		final Camera camera;
		camera = Camera.open(camara);
		final Parameters parameters = camera.getParameters();
		camera.release();
		
		mSupportedImageSizes =parameters.getSupportedPreviewSizes();
		element = mSupportedImageSizes.get(p);
		mCameraView = new JavaCameraView(this, camara);
		mCameraView.setMaxFrameSize(element.width, element.height);
		mCameraView.setCvCameraViewListener(this);
		setContentView(mCameraView);
		
        
		
	/*	mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
		  mOpenCvCameraView.setMaxFrameSize(element.width, element.height);
		// mOpenCvCameraView.getEffect(camara);
		 // mOpenCvCameraView.setBackgroundColor(cam);
	      mOpenCvCameraView.setCvCameraViewListener(this);*/
	  	//setContentView(mOpenCvCameraView);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		switch(id){
		case R.id.Normal:
			efecto = 1;
			return true;
		case R.id.Negativo:
			efecto = 2;
			return true;
		case R.id.Sepia:{
			efecto = 3;
			return true;
		}
		case R.id.Canal:{
			efecto = 4;
			return true;
		}
		case R.id.bordes:{
			efecto =5;
			return true;
		}case R.id.Blur:{
			efecto =6;
			return true;
		}case R.id.Laplacian:{
			efecto =7;
			return true;
		}
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onCameraViewStarted(int width, int height) {
		
		 mIntermediateMat = new Mat();
	        mSize0 = new Size();
	        mChannels = new MatOfInt[] { new MatOfInt(0), new MatOfInt(1), new MatOfInt(2) };
	        mBuff = new float[mHistSizeNum];
	        mHistSize = new MatOfInt(mHistSizeNum);
	        mRanges = new MatOfFloat(0f, 256f);
	        mMat0  = new Mat();
	        mColorsRGB = new Scalar[] { new Scalar(200, 0, 0, 255), new Scalar(0, 200, 0, 255), new Scalar(0, 0, 200, 255) };
	        mColorsHue = new Scalar[] {
	                new Scalar(255, 0, 0, 255),   new Scalar(255, 60, 0, 255),  new Scalar(255, 120, 0, 255), new Scalar(255, 180, 0, 255), new Scalar(255, 240, 0, 255),
	                new Scalar(215, 213, 0, 255), new Scalar(150, 255, 0, 255), new Scalar(85, 255, 0, 255),  new Scalar(20, 255, 0, 255),  new Scalar(0, 255, 30, 255),
	                new Scalar(0, 255, 85, 255),  new Scalar(0, 255, 150, 255), new Scalar(0, 255, 215, 255), new Scalar(0, 234, 255, 255), new Scalar(0, 170, 255, 255),
	                new Scalar(0, 120, 255, 255), new Scalar(0, 60, 255, 255),  new Scalar(0, 0, 255, 255),   new Scalar(64, 0, 255, 255),  new Scalar(120, 0, 255, 255),
	                new Scalar(180, 0, 255, 255), new Scalar(255, 0, 255, 255), new Scalar(255, 0, 215, 255), new Scalar(255, 0, 85, 255),  new Scalar(255, 0, 0, 255)
	        };
	        mWhilte = Scalar.all(255);
	        mP1 = new Point();
	        mP2 = new Point();

	        // Fill sepia kernel
	        mSepiaKernel = new Mat(4, 4, CvType.CV_32F);
	        mSepiaKernel.put(0, 0, /* R */0.189f, 0.769f, 0.393f, 0f);
	        mSepiaKernel.put(1, 0, /* G */0.168f, 0.686f, 0.349f, 0f);
	        mSepiaKernel.put(2, 0, /* B */0.131f, 0.534f, 0.272f, 0f);
	        mSepiaKernel.put(3, 0, /* A */0.000f, 0.000f, 0.000f, 1f);
	        
	        mSepiaKerne = new Mat(4, 4, CvType.CV_32F);
	        mSepiaKerne.put(0, 0, /* R */0.255, 0.200, 0.100, 0);
	        mSepiaKerne.put(1, 0, /* G */0.134, 0.100, 0.155, 0);
	        mSepiaKerne.put(2, 0, /* B */0.124, 0.514, 0.242, 1);
	        mSepiaKerne.put(3, 0, /* A */0.280, 0.200, 0.300, 1);
	        
	        mSepiaKern = new Mat(4, 4, CvType.CV_32F);
	        mSepiaKern.put(0, 0, /* R */0.255, 0.200, 0.100, 0);
	        mSepiaKern.put(1, 0, /* G */0.134, 0.100, 0.155, 0);
	        mSepiaKern.put(2, 0, /* B */0.124, 0.514, 0.242, 0);
	        mSepiaKern.put(3, 0, /* A */0.280, 0.200, 0.300, 1);

	}

	@Override
	public void onCameraViewStopped() {
	 if (mIntermediateMat != null)
        mIntermediateMat.release();
    mIntermediateMat = null;
		
	}
	
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		Mat rgba = inputFrame.rgba();
		
		//esto es para los efectos
		org.opencv.core.Size sizeRgba = rgba.size();
        int rows = (int) sizeRgba.height;
        int cols = (int) sizeRgba.width;

        int   left = cols / 960;
        int  top = rows / 960;

        int width = cols * 4 / 4;
        int height = rows * 4 / 4;
		
		//esto es para el borde
		Mat rgbaInnerWindow;
 
		if(efecto==1){
		}
		if(efecto==2){
			if(camara ==1){
				
				  Mat gray = inputFrame.gray();
		            Mat grayInnerWindow = gray.submat(top, top + height, left, left + width);
		            rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
		            Imgproc.Sobel(grayInnerWindow, mIntermediateMat, CvType.CV_8U, 1, 1);
		            Core.convertScaleAbs(mIntermediateMat, mIntermediateMat, 10, 0);
		            Imgproc.cvtColor(mIntermediateMat, rgbaInnerWindow, Imgproc.COLOR_GRAY2BGRA, 4);
		            grayInnerWindow.release();
			}
			if(camara ==0){
 				  left = cols / 960;
 			         top = rows / 960;

 			         width = cols * 820 / 820;
 			        height = rows * 820 / 820;
 			        
 			       rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
 		            Core.transform(rgbaInnerWindow, rgbaInnerWindow, mSepiaKern);
 		            rgbaInnerWindow.release();			}
		}
		if(efecto==3){
			if(camara ==1){
				viewMode = VIEW_MODE_SEPIA;
				rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
	            Core.transform(rgbaInnerWindow, rgbaInnerWindow, mSepiaKernel);
	            rgbaInnerWindow.release();
			}
	            if(camara ==0){
	   				  left = cols / 960;
	   			       top = rows / 960;

	   			       width = cols * 820 / 820;
	   			      height = rows * 820 / 820;
	   			   
	   			     viewMode = VIEW_MODE_SEPIA;
	   				 rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
	   		         Core.transform(rgbaInnerWindow, rgbaInnerWindow, mSepiaKernel);
	   		         rgbaInnerWindow.release();
	   			  }
			
		}
		if(efecto==4){
			if(camara ==1){
				// sin el canal rojo
				
				rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
				Core.transform(rgbaInnerWindow, rgbaInnerWindow, mSepiaKerne);
				rgbaInnerWindow.release();
			}
   			if(camara ==0){
   				  left = cols / 960;
   			         top = rows / 960;

   			         width = cols * 820 / 820;
   			        height = rows * 820 / 820;
   			        
   			     rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
 				Core.transform(rgbaInnerWindow, rgbaInnerWindow, mSepiaKerne);
 				rgbaInnerWindow.release();   			}
		}
		if(efecto==5){
			if(camara ==1){
				
				  rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
		            Imgproc.Canny(rgbaInnerWindow, mIntermediateMat, 80, 90);
		            Imgproc.cvtColor(mIntermediateMat, rgbaInnerWindow, Imgproc.COLOR_GRAY2BGRA, 4);
		            rgbaInnerWindow.release();   			
		            }
   			if(camara ==0){
   				  left = cols / 960;
   			         top = rows / 960;

   			         width = cols * 820 / 820;
   			        height = rows * 820 / 820;
   				
   			     rgbaInnerWindow = rgba.submat(top, top + height, left, left + width);
		            Imgproc.Canny(rgbaInnerWindow, mIntermediateMat, 80, 90);
		            Imgproc.cvtColor(mIntermediateMat, rgbaInnerWindow, Imgproc.COLOR_GRAY2BGRA, 4);
		            rgbaInnerWindow.release();   			
		            }
		}
		if(efecto==6){
			if(camara ==1){
				Mat src= rgba ;
				Mat dst=rgba ;
				Size s = new Size(10,10);
				   dst=new Mat();
						Imgproc.blur(src, dst, s);
						return dst;
		            }
   			if(camara ==0){
   				Mat src= rgba ;
				Mat dst= rgba ;
				Size s = new Size(10,10);
				   dst=new Mat();
						Imgproc.blur(src, dst, s);
						return dst;
   			}
		}
   			if(efecto==7){
   				if(camara ==1){
   					Mat dst=rgba ;
   					Mat src = inputFrame.gray();
   					
					int ksize = 3;
					int scale = 1;
					int delta = 0;
					   dst=new Mat();
							Imgproc.Laplacian(src, dst, CvType.CV_8UC1, ksize, scale, delta);
							return dst;
   			            }
   	   			if(camara ==0){
   	   			Mat src= rgba ;
				Mat dst=rgba ;
				int ksize = 10;
				int scale = 1;
				int delta = 0;
				int ddepth = CvType.CV_8UC4;
				   dst=new Mat();
						Imgproc.Laplacian(src, dst, ddepth, ksize, scale, delta);
						return dst;
   	   			}
   			}
		return rgba;
	}

	@Override
	public void onPause() {
		if (mCameraView != null) {
			mCameraView.disableView();
		}
		if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
		super.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, opencvCharger);
	}
	@Override
	public void onDestroy() {
		if (mCameraView != null) {
			mCameraView.disableView();
		}
		 if (mOpenCvCameraView != null)
	            mOpenCvCameraView.disableView();
		super.onDestroy();
	}
}
