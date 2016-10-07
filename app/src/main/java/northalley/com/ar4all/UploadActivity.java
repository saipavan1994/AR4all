package northalley.com.ar4all;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


@SuppressWarnings("deprecation")
public class UploadActivity extends AppCompatActivity implements SurfaceHolder.Callback{
    private Camera mCamera;
    SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_upload);



    }
    @Override
    public void onResume()
    {
        super.onResume();
        takeSnapShot();
    }

    @Override
    protected void onPause()
    {
        clearCamera();
        super.onPause();
    }

    private void takeSnapShot()
    {
        if (mCamera == null)
        {
            try { mCamera = Camera.open();
                }
            catch (Exception e) { e.printStackTrace(); }

            SurfaceView surface = (SurfaceView) findViewById(R.id.cam);
            surfaceHolder=surface.getHolder();
            surfaceHolder.addCallback(this);

            try { mCamera.setPreviewDisplay(surface.getHolder()); }
            catch (IOException e) { e.printStackTrace(); }
            mCamera.startPreview();
        }
    }


        @Override
        public void surfaceCreated(SurfaceHolder holder)
        {
            try
            {
                mCamera.setPreviewDisplay(holder);
                mCamera.setDisplayOrientation(90);
                mCamera.startPreview();

            }
            catch (IOException e) { e.printStackTrace(); }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {}

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (holder.getSurface() == null){
                // preview surface does not exist
                return;
            }

            // stop preview before making changes
            try {
                mCamera.stopPreview();
            } catch (Exception e){
                // ignore: tried to stop a non-existent preview
            }

            // set preview size and make any resize, rotate or
            // reformatting changes here

            // start preview with new settings
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();

            } catch (Exception e){
                Log.d("camera", "Error starting camera preview: " + e.getMessage());
            }
        }



    private final Camera.PictureCallback mPictureCallback = new Camera.PictureCallback()
    {
        @Override
        public void onPictureTaken(byte[] data, Camera camera)
        {
            FileOutputStream outStream = null;

            try
            {
                String dir_path = "/storage/sdcard0/DCIM/Camera/";
                File pictureFile = new File(dir_path+"target.jpg");
                outStream = new FileOutputStream(pictureFile);
                Bitmap realImage = BitmapFactory.decodeByteArray(data, 0, data.length);
                ExifInterface exif=new ExifInterface(pictureFile.toString());
                if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("6")){
                    realImage= rotate(realImage, 90);
                } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("8")){
                    realImage= rotate(realImage, 270);
                } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("3")){
                    realImage= rotate(realImage, 180);
                } else if(exif.getAttribute(ExifInterface.TAG_ORIENTATION).equalsIgnoreCase("0")){
                    realImage= rotate(realImage, 90);
                }
                boolean bo = realImage.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.close();
            }
            catch (FileNotFoundException e) { e.printStackTrace(); }
            catch (IOException e) { e.printStackTrace(); }
            finally { clearCamera();
                Intent intent = new Intent(getBaseContext(),SelectAugment.class);
                startActivity(intent);}


        }
    };
    public static Bitmap rotate(Bitmap bitmap, int degree) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Matrix mtx = new Matrix();
        //       mtx.postRotate(degree);
        mtx.setRotate(degree);

        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }
    public void captureImage(View v)
    {
        //mCamera.startPreview();
        mCamera.takePicture(null, null, mPictureCallback);

    }

    private void clearCamera() {
        if (mCamera != null) {
            try {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

}

