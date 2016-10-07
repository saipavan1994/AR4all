package northalley.com.ar4all;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Cursor vCursor;
    private int vColumnIndex;
    private String vidPath;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.videoview,container,false);
        GridView gridView = (GridView)view.findViewById(R.id.video);
        Button vid_button = (Button)view.findViewById(R.id.vid_next);
        /*We are using a button with name next so that if user unknowingly touches a thumbnail it will not go to next page
        * After clicking the next button the videopath is passed to intent*/
        vid_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(),PreviewActivity.class);
                intent.putExtra("path",vidPath);
                startActivity(intent);
            }
        });
        String[] projection1 = {MediaStore.Video.Thumbnails._ID};
        vCursor= getContext().getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, projection1, null, null, MediaStore.Video.Thumbnails.VIDEO_ID);
               // Get the column index of the Thumbnails Video ID
        vColumnIndex=vCursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails._ID);
        gridView.setAdapter(new VideoAdapter(view.getContext()));
        //Here we are setting the listener for user selection, when user touches a thumbnail its position is read by the
        //listener and with that position we can get the path of video
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // Get the data location of the image
                String[] projection = {MediaStore.Video.Media.DATA};
                vCursor = getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        projection, // Which columns to return
                        null,       // Return all rows
                        null,
                        null);
                vColumnIndex = vCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                vCursor.moveToPosition(position);
                // Get video filename
                vidPath = vCursor.getString(vColumnIndex);
                Toast.makeText(getContext(),"Selected",Toast.LENGTH_LONG).show();
                vCursor.close();
            }
        });
        return view;
    }
        private class VideoAdapter extends BaseAdapter {
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
