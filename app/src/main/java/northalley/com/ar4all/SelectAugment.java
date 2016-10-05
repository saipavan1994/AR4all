package northalley.com.ar4all;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class SelectAugment extends Activity implements TabLayout.OnTabSelectedListener {
  private Cursor cursor;
    private Cursor vCursor;
    private int columnIndex;
    private int vColumnIndex;
    private GridView sdcardImages;

    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_augment);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tabLayout);
        tablayout.addTab(tablayout.newTab().setText("Images"));
        tablayout.addTab(tablayout.newTab().setText("Videos"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.setOnTabSelectedListener(this);
        String[] projection = {MediaStore.Images.Thumbnails._ID};
        // Create the cursor pointing to the SDCard
        cursor = managedQuery( MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                projection, // Which columns to return
                null,       // Return all rows
                null,
                MediaStore.Images.Thumbnails.IMAGE_ID);
        // Get the column index of the Thumbnails Image ID
        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
        String[] projection1 = {MediaStore.Video.Thumbnails._ID};
        // Create the cursor pointing to the SDCard
        vCursor=managedQuery(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,projection1,null,null,MediaStore.Video.Thumbnails.VIDEO_ID);

        sdcardImages = (GridView) findViewById(R.id.iamge);

        sdcardImages.setAdapter(new ImageAdapter(this));
        // Get the column index of the Thumbnails Video ID
        vColumnIndex=vCursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails._ID);
        // Set up a click listener
        sdcardImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Get the data location of the image
                String[] projection = {MediaStore.Images.Media.DATA};
                cursor = managedQuery( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, // Which columns to return
                        null,       // Return all rows
                        null,
                        null);
                columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToPosition(position);
                // Get image filename
                String imagePath = cursor.getString(columnIndex);
                // Use this path to do further processing, i.e. full screen display
               goTO(imagePath);

            }
        });

    }
    public void goTO(String s)
    {
        Intent intent = new Intent(this,UserLoginReg.class);
        startActivity(intent);
    }
    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        int pos=tab.getPosition();
        switch(pos)
        {
            case 0:
                sdcardImages.setAdapter(null);
                sdcardImages.setAdapter(new ImageAdapter(this));
                break;
            case 1:
                sdcardImages.setAdapter(null);
                sdcardImages.setAdapter(new VideoAdapter(this));
                break;
        }
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab)
    {


    }
    @Override
    public void onTabReselected(TabLayout.Tab tab)
    {

    }
    private class ImageAdapter extends BaseAdapter {

        private Context context;

        public ImageAdapter(Context localContext) {
            context = localContext;
        }

        public int getCount() {
            return cursor.getCount();
        }
        public Object getItem(int position) {
            return position;
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView picturesView;

                picturesView = new ImageView(context);


            /*else {
                picturesView = (ImageView)convertView;
            }*/
            // Move cursor to current position
            cursor.moveToPosition(position);
            // Get the current value for the requested column
            int imageID = cursor.getInt(columnIndex);
            // Set the content of the image based on the provided URI
            picturesView.setImageURI(Uri.withAppendedPath(
                    MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID));
            picturesView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            picturesView.setPadding(5,5, 5, 5);
            picturesView.setLayoutParams(new GridView.LayoutParams(210,210));
            return picturesView;
        }
    }
    private class VideoAdapter extends BaseAdapter{
        private Context context;
        public VideoAdapter(Context localContext)
        {
            context=localContext;
        }
        public int getCount()
        {
            return vCursor.getCount();
        }
        public Object getItem(int position)
        {
            return position;
        }
        public long getItemId(int position)
        {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView videoView;
            if (convertView == null) {
                videoView = new ImageView(context);

            }
            else {
                videoView = (ImageView)convertView;
            }
            // Move cursor to current position
            vCursor.moveToPosition(position);
            // Get the current value for the requested column
            int imageID = vCursor.getInt(vColumnIndex);
            // Set the content of the image based on the provided URI
            videoView.setImageURI(Uri.withAppendedPath(
                    MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, "" + imageID));
           // videoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            videoView.setPadding(5, 5, 5, 5);
            videoView.setLayoutParams(new GridView.LayoutParams(200,200));
            return videoView;
        }
    }

}


