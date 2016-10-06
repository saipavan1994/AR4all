package northalley.com.ar4all;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview2);
        Bitmap myBitmap = BitmapFactory.decodeFile("/storage/sdcard0/DCIM/Camera/target.jpg");

        ImageView myImage = (ImageView) findViewById(R.id.target);
        myImage.setImageBitmap(myBitmap);
        Bundle extras;
        extras=getIntent().getExtras();
        String augPath = extras.getString("path");
        Log.d("path",augPath);
        ImageView augView = (ImageView)findViewById(R.id.augment);
        augView.setImageBitmap(BitmapFactory.decodeFile(augPath));
    }
}
