package com.example.mapjournal.view;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mapjournal.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends ActionBarActivity implements
    GooglePlayServicesClient.ConnectionCallbacks,
    GooglePlayServicesClient.OnConnectionFailedListener {
  /*
   * Define a request code to send to Google Play services
   * This code is returned in Activity.onActivityResult
   */
  private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
  
  // Drawer components
  private String[] drawerItems;
  private DrawerLayout drawerLayout;
  private ListView drawerList;
  private ActionBarDrawerToggle drawerToggle;
  
  // Map components
  private GoogleMap map;
  private LocationClient locationClient;
  private boolean moveCameraToCurrentLocationFlag;
  private Marker currentLocation;
  
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
    drawerList.setAdapter(new DrawerArrayAdapter(this, drawerItems));
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
    
    locationClient = new LocationClient(this, this, this);
    
    // Add the map to the content frame
    if (findViewById(R.id.content_frame) != null) {
      // Don't create the map if resuming from a saved state
      if (savedInstanceState != null) {
        return;
      }
      
      GoogleMapOptions options = new GoogleMapOptions();      
      MapFragment mapFragment = MapFragment.newInstance(
          options.zoomControlsEnabled(false));
      getFragmentManager().beginTransaction()
          .add(R.id.content_frame, mapFragment, "map").commit();
      map = mapFragment.getMap();
    }
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
  }
  
  /*
   * Called when the Activity becomes visible.
   */
  @Override
  protected void onStart() {
      super.onStart();
      
      // Set flag to center on location after location client is connected
      moveCameraToCurrentLocationFlag = true; 
      // Connect the client.
      locationClient.connect();      
  }
  
  /*
   * Called when the Activity is no longer visible.
   */
  @Override
  protected void onStop() {
      // Disconnecting the client invalidates it.
      locationClient.disconnect();
      super.onStop();
  }
  
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu items for use in the action bar
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.activity_main_actions, menu);
    return super.onCreateOptionsMenu(menu);
  }
  
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Pass the event to ActionBarDrawerToggle, if it returns
    // true, then it has handled the app icon touch event
    if (drawerToggle.onOptionsItemSelected(item)) {
      return true;
    }
    
    switch (item.getItemId()) {
      case R.id.action_current_location:
        moveCameraToCurrentLocation();
    }
    return super.onOptionsItemSelected(item);
  }
  
  /*
   * Called by Location Services when the request to connect the
   * client finishes successfully. At this point, you can
   * request the current location or start periodic updates
   */
  @Override
  public void onConnected(Bundle dataBundle) {
    // Display the connection status
    Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
    if (moveCameraToCurrentLocationFlag) {
      moveCameraToCurrentLocation();
    }
  }

  /*
   * Called by Location Services if the connection to the
   * location client drops because of an error.
   */
  @Override
  public void onDisconnected() {
    // Display the connection status
    Toast.makeText(this,
                   "Disconnected. Please re-connect.",
                   Toast.LENGTH_SHORT).show();
  }
  
  /*
   * Called by Location Services if the attempt to
   * Location Services fails.
   */
  @Override
  public void onConnectionFailed(ConnectionResult connectionResult) {
      /*
       * Google Play services can resolve some errors it detects.
       * If the error has a resolution, try sending an Intent to
       * start a Google Play services activity that can resolve
       * error.
       */
      if (connectionResult.hasResolution()) {
          try {
              // Start an Activity that tries to resolve the error
              connectionResult.startResolutionForResult(
                      this,
                      CONNECTION_FAILURE_RESOLUTION_REQUEST);
              /*
               * Thrown if Google Play services canceled the original
               * PendingIntent
               */
          } catch (IntentSender.SendIntentException e) {
              // Log the error
              e.printStackTrace();
          }
      } else {
        /*
         * If no resolution is available, display a dialog to the
         * user with the error.
         */
        showErrorDialog(connectionResult.getErrorCode());
      }
  }
  
  
  /*
   * Handle results returned to the Activity by Google Play services
   */
  @Override
  protected void onActivityResult(int requestCode,
                                  int resultCode,
                                  Intent data) {
    // Decide what to do based on the original request code
    switch (requestCode) {
      case CONNECTION_FAILURE_RESOLUTION_REQUEST :
        /*
         * If the result code is Activity.RESULT_OK, try
         * to connect again
         */
        switch (resultCode) {
          case Activity.RESULT_OK :
            // Try the request again
            break;
        }
    }
  }
  
  private void moveCameraToCurrentLocation() {
    moveCameraToCurrentLocationFlag = false;
    
    setUpMapIfNeeded();
    
    Location l = locationClient.getLastLocation();
    LatLng currentCoords = new LatLng(l.getLatitude(), l.getLongitude());
    
    if(currentLocation == null) {
      currentLocation = map.addMarker(new MarkerOptions()
                            .position(currentCoords)
                            .title("You are here"));
    } else {
      currentLocation.setPosition(currentCoords);
    }
    
    CameraPosition p = new CameraPosition.Builder()
        .target(currentCoords)
        .zoom(15)
        .build();
    map.animateCamera(CameraUpdateFactory.newCameraPosition(p));
  }
  
  private void setUpMapIfNeeded() {
    // Do a null check to confirm that we have not already instantiated the map.
    if (map == null) {
      map = ((MapFragment) getFragmentManager().findFragmentByTag("map"))
          .getMap();
      // Check if we were successful in obtaining the map.
      if (map != null) {
        // The Map is verified. It is now safe to manipulate the map.
      }
    }
}
  
  private void showErrorDialog(int errorCode) {
    // Get the error dialog from Google Play services
    Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
        errorCode,
        this,
        CONNECTION_FAILURE_RESOLUTION_REQUEST);

    // If Google Play services can provide an error dialog
    if (errorDialog != null) {
      // Create a new DialogFragment for the error dialog
      ErrorDialogFragment errorFragment = new ErrorDialogFragment();
      // Set the dialog in the DialogFragment
      errorFragment.setDialog(errorDialog);
      // Show the error dialog in the DialogFragment
      errorFragment.show(getFragmentManager(), "Location Updates");
    }
  }
  
  private boolean servicesConnected() {
    // Check that Google Play services is available
    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    // If Google Play services is available
    if (ConnectionResult.SUCCESS == resultCode) {
      // In debug mode, log the status
      Log.d("Location Updates", "Google Play services is available.");
      // Continue
      return true;
    // Google Play services was not available for some reason
    } else {
      showErrorDialog(resultCode);
      return false;
    }
  }
  
  // Define a DialogFragment that displays the error dialog
  public static class ErrorDialogFragment extends DialogFragment {
    // Global field to contain the error dialog
    private Dialog mDialog;
    // Default constructor. Sets the dialog field to null
    public ErrorDialogFragment() {
      super();
      mDialog = null;
    }
    // Set the dialog to display
    public void setDialog(Dialog dialog) {
      mDialog = dialog;
    }
    // Return a Dialog to the DialogFragment.
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      return mDialog;
    }
  }
}
