package com.example.mapjournal.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mapjournal.R;

public class MainActivity extends ActionBarActivity {
  private String[] drawerItems;
  private DrawerLayout drawerLayout;
  private ListView drawerList;
  private ActionBarDrawerToggle drawerToggle;
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    // Populate the navigation drawer data
    drawerItems = getResources().getStringArray(R.array.drawer_array);
    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawerList = (ListView) findViewById(R.id.left_drawer);
    
    // Add the shadow over the main frame
    drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    
    // Set up the drawer's list view
    drawerList.setAdapter(new ArrayAdapter<String>(this,
        R.layout.drawer_row, drawerItems));
    drawerList.setOnItemClickListener(new DrawerItemClickListener());

    // Enable action bar icon to toggle navigation drawer
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);
    
    // Set up ActionBar toggler
    drawerToggle = new ActionBarDrawerToggle(this,
                                             drawerLayout,
                                             R.drawable.ic_drawer,
                                             R.string.drawer_open,
                                             R.string.drawer_close);
    drawerLayout.setDrawerListener(drawerToggle);
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
  }
  
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Pass the event to ActionBarDrawerToggle, if it returns
    // true, then it has handled the app icon touch event
    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    
    
    return super.onOptionsItemSelected(item);
  }
}
