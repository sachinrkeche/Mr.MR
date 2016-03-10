package sabersoftech.mrmr;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.w3c.dom.Document;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class GoogleMapActivity extends AppCompatActivity {


    GPSTracker gpsTracker;

    Document document;
    GMapV2GetRouteDirection v2GetRouteDirection;
    LatLng fromPosition;
    LatLng toPosition;
    private GoogleMap googleMap;
    MarkerOptions markerOptions, markerOptions1;
    ProgressDialog Dialog;

    Float source_lat;
    Float source_long;

    Float des_lat;
    Float des_long;

    // for server response
    ArrayList<String> arrayList_lat;
    ArrayList<String> arrayList_long;
    ArrayList<LatLng> arrayList_latLngs;
    ArrayList<String> arrayList_name;

    // List
    ListView mListView;
    String ClickedLocationName;

    String[] hospitals = {"Gurukrupa Medical ", "Metha Medicals", "Prashali Dental Clinic", "Shree Sai Medical", "Navshakti Ayurvedic Dawakhana", "ANTRANG SONOGRAPHY CENTRE", "Hadapsar X Ray Clinic", "Physiotherapy Clinc", "DRISHTI EYE CARE INSTITUE", "Gurukripa Furniture", "Pragte Medical Stores", "Rajendra Clinic", "Anand Clinic", "Pbms's  H. V. Desai  Eye  Hospital", "Wadia Dr. N M", "Pathfinder Clinic", "Shree Hospital", "Physiotherapy Clinic", "Dog & Cat Clinic", "MANU LABORATORY", "Patanjli Yoga Naturopathy and Slimming Clinic", "HomeoCure Clinics", "Dr Jinkal Vachhani's Homeopathy Clinic", "Dr. Ranjit Vhora Homoeopathy Clinic", "Shree Clinic", "Shree Vishwadev Ayurvedic Clinic", "Sai Clinic", "Arora Dr Sudhir", "Homeopathic Clinic", "Shree Shaym Clinic", "Shri Vishvadai Ayurvedic Clinic", "Ghaisas ENT Hospital"};
    String[] lat = {"18.501146", "18.499939", "18.500325", "18.501146", "18.501146", "18.501567", "18.502639", "18.50985", "18.504965", "18.504338", "18.500641", "18.519544", "18.519615", "18.497735", "18.530568", "18.509817", "18.520382", "18.502308", "18.501146", "18.501391", "18.499789", "18.502329", "18.50119", "18.501146", "18.501146", "18.501146", "18.495494", "18.504338", "18.515806", "18.501146", "18.517225"};
    String[] lang = {"73.924225", "73.936258", "73.943632", "73.924225", "73.924225", "73.930671", "73.92602", "73.928016", "73.901231", "73.902914", "73.939576", "73.932796", "73.906617", "73.941785", "73.877026", "73.928557", "73.856549", "73.929879", "73.924225", "73.934346", "73.902363", "73.849781", "73.929684", "73.937097", "73.924225", "73.924225", "73.924225", "73.87572", "73.902914", "73.927164", "73.924225"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        //getting location from Dashboard

        Intent intent = getIntent();
        source_lat = Float.parseFloat(intent.getStringExtra("lat"));
        source_long = Float.parseFloat(intent.getStringExtra("lng"));

        Toast.makeText(GoogleMapActivity.this, "Latitude : " + source_lat + " Longitude : " + source_long, Toast.LENGTH_SHORT).show();


        arrayList_lat = new ArrayList<String>();
        arrayList_long = new ArrayList<String>();
        arrayList_latLngs = new ArrayList<LatLng>();
        arrayList_name = new ArrayList<String>();


        for (int i = 0; i < hospitals.length - 1; i++) {
            arrayList_lat.add(lat[i]);
            arrayList_long.add(lang[i]);
            arrayList_name.add(hospitals[i]);
            arrayList_latLngs.add(new LatLng(Double.parseDouble(lat[i]), Double.parseDouble(lang[i])));

        }

        mListView = (ListView) findViewById(R.id.locationListView);
        Myadapter adapter = new Myadapter(GoogleMapActivity.this, arrayList_name);
        mListView.setAdapter(adapter);


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                googleMap.clear();

                gpsTracker = new GPSTracker(GoogleMapActivity.this);

                if (gpsTracker.canGetLocation()) {
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();

                    source_lat = Float.parseFloat("" + latitude);
                    source_long = Float.parseFloat("" + longitude);


                    toPosition = arrayList_latLngs.get(position);
                    ClickedLocationName = arrayList_name.get(position);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(toPosition));
//                googleMap.animateCamera(CameraUpdateFactory.zoomTo(12));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(calculateZoomLevel(getScreenWeidth())));
                    GetRouteTask getRoute = new GetRouteTask();
                    getRoute.execute();


                    Toast.makeText(GoogleMapActivity.this, "Latitude " + gpsTracker.getLatitude() + " Longitude " + gpsTracker.getLongitude(), Toast.LENGTH_SHORT).show();
                } else {
                    showSettingAlert();
                }

            }
        });


        Dialog = new ProgressDialog(GoogleMapActivity.this);
        Dialog.setMessage("Loading route...");
//        Dialog.show();

        /*
  sourse 18.508920, 73.926026
(4:04:38 PM) dest 19.580410, 74.465907
*/
       /* source_lat = Float.parseFloat("18.508920");
        source_long = Float.parseFloat("73.926026");*/

       /* des_lat = Float.parseFloat("19.580410");
        des_long = Float.parseFloat("74.465907");*/

        v2GetRouteDirection = new GMapV2GetRouteDirection();
        initilizeMap();

        // Changing map type
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        // Showing / hiding your current location
        googleMap.setMyLocationEnabled(true);

        // Enable / Disable zooming controls
        googleMap.getUiSettings().setZoomControlsEnabled(false);

        // Enable / Disable my location button
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Enable / Disable Compass icon
        googleMap.getUiSettings().setCompassEnabled(true);

        // Enable / Disable Rotate gesture
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setAllGesturesEnabled(true);
        googleMap.setTrafficEnabled(true);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(source_lat, source_long)));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(8));
        markerOptions = new MarkerOptions();
        markerOptions1 = new MarkerOptions();

        fromPosition = new LatLng(source_lat, source_long);
//        toPosition = new LatLng(des_lat, des_long);

        /*GetRouteTask getRoute = new GetRouteTask();
        getRoute.execute();*/

        getAll();

    /*    for (int j = 0; j < arrayList_latLngs.size(); j++) {
            googleMap.addMarker(new MarkerOptions().position(arrayList_latLngs.get(j))
                    .title(arrayList_name.get(j)));

        }*/

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(source_lat, source_long)));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(7));


    }


    public void showAllLocations(View view) {
        getAll();
    }

    private void getAll() {
        for (int j = 0; j < arrayList_latLngs.size(); j++) {
            googleMap.addMarker(new MarkerOptions().position(arrayList_latLngs.get(j))
                    .title(arrayList_name.get(j)));

        }
    }

    public void showSettingAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS Settings");
        builder.setMessage("GPS is not active,Want you settings ? ");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        builder.show();
    }


    public int getScreenWeidth() {
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        return width;
    }

    private int calculateZoomLevel(int screenWidth) {
        double equatorLength = 40075004; // in meters
        double widthInPixels = screenWidth;
        double metersPerPixel = equatorLength / 256;
        int zoomLevel = 1;
        while ((metersPerPixel * widthInPixels) > 2000) {
            metersPerPixel /= 2;
            ++zoomLevel;
        }
        Log.i("ADNAN", "zoom level = " + zoomLevel);
        return zoomLevel;
    }

    private class GetRouteTask extends AsyncTask<String, Void, String> {

        String response = "";

        @Override
        protected void onPreExecute() {
            Dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            //Get All Route values
            document = v2GetRouteDirection.getDocument(fromPosition, toPosition, GMapV2GetRouteDirection.MODE_DRIVING);
            response = "Success";
            return response;

        }

        @Override
        protected void onPostExecute(String result) {
            if (response.equalsIgnoreCase("Success")) {
                ArrayList<LatLng> directionPoint = v2GetRouteDirection.getDirection(document);
                PolylineOptions rectLine = new PolylineOptions().width(15).color(getApplication().getResources().getColor(R.color.colorPrimaryDark));
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < directionPoint.size(); i++) {
                    rectLine.add(directionPoint.get(i));
                }
                // Adding route on the map
                googleMap.addPolyline(rectLine);
                markerOptions1.position(fromPosition);
                markerOptions1.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .title("You")
                        .snippet("Lat : " + fromPosition.latitude + " Lng : " + fromPosition.longitude);
                markerOptions1.draggable(true);
                googleMap.addMarker(markerOptions1);

                markerOptions.position(toPosition);
                markerOptions.icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        .title(ClickedLocationName)
                        .snippet("Lat : " + toPosition.latitude + " Lng : " + toPosition.longitude);
                markerOptions.draggable(true);
                googleMap.addMarker(markerOptions);

            }

            Dialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @SuppressLint("NewApi")
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            //	googleMap.setOnMarkerClickListener(this);

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // List Adapter

    class Myadapter extends ArrayAdapter<String> {
        ArrayList<String> name_arrayList;
        Context context;

        public Myadapter(Context context, ArrayList<String> name_arrayList) {
            super(context, R.layout.single_tv_list_item, R.id.tv_item, name_arrayList);
            this.context = context;
            this.name_arrayList = name_arrayList;

        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return super.getCount();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            String cols = "";

            View row = convertView;
            if (row == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

                row = ((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.single_tv_list_item, parent, false);
            }

            ((TextView) row.findViewById(R.id.tv_item)).setText(name_arrayList.get(position));
            return row;

        }

    }
}
