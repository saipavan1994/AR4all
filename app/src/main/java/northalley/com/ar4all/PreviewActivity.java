package northalley.com.ar4all;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaCodec;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview2);
        //decoding the target image using bitmapFactory for imageview
        Bitmap myBitmap = BitmapFactory.decodeFile("/storage/sdcard0/DCIM/Camera/target.jpg");

        ImageView myImage = (ImageView) findViewById(R.id.target);
        //setting the bitmap for imageview
        myImage.setImageBitmap(myBitmap);
        /* The path which is sent by previous activity through put extra is retrieved here
        * using getIntent() and getExtras() we are storing the path to string*/

        Bundle extras;
        extras=getIntent().getExtras();
        String augPath = extras.getString("path");
        /* We are having two types of augments i.e. video and audio
        * Whenever user selects one of them we will get the path and we need to differentiate between video and image
         * so we use split() to get the extension and comparing the extension for further processing*/
        try {
            String[] ext = augPath.split("\\.");
            Log.d("path",ext[0]);
            Log.d("path",augPath);
            //If the path is image we need to populate imageview for augment else need to go for video view
            if (ext[1].equals("jpg") || ext[1].equals("png")) {
                Log.d("path", augPath);
                ImageView augView = (ImageView) findViewById(R.id.augment);
                augView.setImageBitmap(BitmapFactory.decodeFile(augPath));
            } else {
                /* We cannot directly place video view in the .xml file so we dynamically place it
                when there is a video file*/
                RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_preview2);
                VideoView augView = new VideoView(this);
                RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(200,200);
                param.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
                augView.setLayoutParams(param);
                layout.addView(augView);
                augView.setVideoPath(augPath);
                augView.setMediaController(new MediaController(this));
                augView.requestFocus();
                augView.start();
            }
        }catch (NullPointerException np){np.printStackTrace();}
    }
}
