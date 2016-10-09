package northalley.com.ar4all;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectAugment extends FragmentActivity implements TabLayout.OnTabSelectedListener {


    @Override
    @SuppressWarnings("deprecation")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_augment);
        TabLayout tablayout = (TabLayout) findViewById(R.id.tabLayout);
        /*In the below code we are adding Tabs to the tablayout and
        * giving those tabs a name*/
        tablayout.addTab(tablayout.newTab().setText("Images"));
        tablayout.addTab(tablayout.newTab().setText("Videos"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tablayout.setOnTabSelectedListener(this);
        //On opening of this activity we need to display the Image thumbnails because these are
        //image is the first tab and need to be selected
        ImageFragment image = new ImageFragment();
        image.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.grid_view,image).commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        int pos=tab.getPosition();
        switch(pos)
        {
            case 0:
                //for selecting image tab wee are displaying image thumbnails
                ImageFragment image = new ImageFragment();
                image.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().replace(R.id.grid_view,image).commit();
                break;
            case 1:
                //for selecting videotab we are displaying video thumbnails
                VideoFragment video1 = new VideoFragment();
                video1.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().replace(R.id.grid_view,video1).commit();
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
    @Override
    public void onPause()
    {
        super.onPause();
    }
    @Override
    public void onResume()
    {
        super.onResume();
    }
    public void goBack(View v)
    {
     Intent intent = new Intent(this,UploadActivity.class);
        startActivity(intent);
    }
}


