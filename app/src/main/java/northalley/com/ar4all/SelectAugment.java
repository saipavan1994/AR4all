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

public class SelectAugment extends FragmentActivity implements TabLayout.OnTabSelectedListener {


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
        ImageFragment image = new ImageFragment();
        image.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.grid_view,image).commit();
        // Create the cursor pointing to the SDCard

        // Set up a click listener


    }

    @Override
    public void onTabSelected(TabLayout.Tab tab)
    {
        int pos=tab.getPosition();
        switch(pos)
        {
            case 0:
                ImageFragment image = new ImageFragment();
                image.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().replace(R.id.grid_view,image).commit();
                break;
            case 1:
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


}


