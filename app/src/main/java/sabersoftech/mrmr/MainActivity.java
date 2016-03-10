package sabersoftech.mrmr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.model.HelpLiveo;
import br.liveo.navigationliveo.NavigationLiveo;

public class MainActivity extends NavigationLiveo implements OnItemClickListener {
    private HelpLiveo mHelpLiveo;

    GPSTracker gpsTracker;

    @Override
    public void onInt(Bundle savedInstanceState) {

        // User Information
        this.userName.setText("Ajay Jadhav");
        this.userEmail.setText("ajayjadhav@gmail.com");
        //Loading image from below url into imageView

        Picasso.with(this)
                .load("https://pbs.twimg.com/profile_images/600996201425571840/BPc1P7Yv_400x400.jpg")
                .placeholder(R.mipmap.ic_launcher)           // optional
                .into(this.userPhoto);
//        this.userPhoto.setImageResource(R.drawable.ic_my);
        this.userBackground.setImageResource(R.drawable.ic_user_blue_background_fifth);

        // Creating items navigation
        mHelpLiveo = new HelpLiveo();

        //  mHelpLiveo.addSubHeader(getString(R.string.categories)); //Item subHeader
//        mHelpLiveo.addSeparator(); // Item separator
/*
    <string name="home">Home</string>
    <string name="addparty">Add Customer</string>
    <string name="addorder">Add Order</string>
    <string name="cart">Cart</string>
    <string name="map">Todays Place</string>
    <string name="placeorder">Place Order</string>
*/
        mHelpLiveo.add(getString(R.string.home), R.mipmap.ic_launcher);
        mHelpLiveo.add(getString(R.string.addparty), R.mipmap.ic_addparty);

        mHelpLiveo.addSubHeader(getString(R.string.group_orders));
        mHelpLiveo.add(getString(R.string.cart), R.mipmap.ic_cart);
        mHelpLiveo.add(getString(R.string.addorder), R.mipmap.ic_addorder);
        mHelpLiveo.add(getString(R.string.placeorder), R.mipmap.ic_conforder);

        mHelpLiveo.addSubHeader(getString(R.string.group_plan));
        mHelpLiveo.add(getString(R.string.map), R.mipmap.ic_map);
        /*
        mHelpLiveo.add(getString(R.string.product), R.mipmap.ic_product_black);
        mHelpLiveo.add(getString(R.string.cart), R.mipmap.ic_cart_black);
        mHelpLiveo.add(getString(R.string.profile), R.mipmap.ic_profile_black);
        mHelpLiveo.add(getString(R.string.information), R.mipmap.ic_info_black);
        mHelpLiveo.add(getString(R.string.about), R.mipmap.ic_about_black);
        mHelpLiveo.add(getString(R.string.contact), R.mipmap.ic_contact_black);*/
        /*
        mHelpLiveo.add(getString(R.string.group), R.mipmap.ic_inbox_black_24dp);

        mHelpLiveo.addSubHeader(getString(R.string.messaging));

        mHelpLiveo.add(getString(R.string.single), R.mipmap.ic_inbox_black_24dp);
        mHelpLiveo.add(getString(R.string.bulk), R.mipmap.ic_inbox_black_24dp);
        mHelpLiveo.addSeparator();

        mHelpLiveo.add(getString(R.string.reports), R.mipmap.ic_inbox_black_24dp);*/


        //{optional} - Header Customization - method customHeader
//        View mCustomHeader = getLayoutInflater().inflate(R.layout.custom_header_user, this.getListView(), false);
//        ImageView imageView = (ImageView) mCustomHeader.findViewById(R.id.imageView);

        with((OnItemClickListener) this).startingPosition(0) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())

                        //{optional} - List Customization "If you remove these methods and the list will take his white standard color"
                        //.selectorCheck(R.drawable.selector_check) //Inform the background of the selected item color
                        //.colorItemDefault(R.color.nliveo_blue_colorPrimary) //Inform the standard color name, icon and counter
                        //.colorItemSelected(R.color.nliveo_purple_colorPrimary) //State the name of the color, icon and meter when it is selected
                        //.backgroundList(R.color.nliveo_black_light) //Inform the list of background color
                        //.colorLineSeparator(R.color.nliveo_transparent) //Inform the color of the subheader line

                        //{optional} - SubHeader Customization
                .colorItemSelected(R.color.colorPrimary)
                .colorNameSubHeader(R.color.colorPrimary)
                        //.colorLineSeparator(R.color.nliveo_blue_colorPrimary)

                .footerItem(R.string.logout, R.mipmap.ic_launcher)

                        //{optional} - Header Customization
                        //.customHeader(mCustomHeader)

                        //{optional} - Footer Customization
                        //.footerNameColor(R.color.nliveo_blue_colorPrimary)
                        //.footerIconColor(R.color.nliveo_blue_colorPrimary)
                        //.footerBackground(R.color.nliveo_white)

                .setOnClickUser(onClickPhoto)
                .setOnPrepareOptionsMenu(onPrepare)
                .setOnClickFooter(onClickFooter)
                .build();

        int position = this.getCurrentPosition();
        this.setElevationToolBar(position != 2 ? 15 : 0);

    }

    @Override
    public void onItemClick(int position) {
        Fragment mFragment = null;

        FragmentManager mFragmentManager = getSupportFragmentManager();

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        /*mFragment = Home.newInstance(mHelpLiveo.get(position).getName());
        actionBar.setTitle("Home");*/

        switch (position) {

            case 1:
                startActivity(new Intent(MainActivity.this, AddParty.class));
                break;
//            case 2:break;
            case 3:
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                break;
            case 4:
                startActivity(new Intent(MainActivity.this, PartySelectActivity.class));
                break;
            /*case 5:
                break;*/
//            case 6:break;
            case 7:
                gpsTracker = new GPSTracker(MainActivity.this);

                if (gpsTracker.canGetLocation()) {
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();
//            Toast.makeText(DashboardActivity.this, "Latitude "+gpsTracker.getLatitude()+" Longitude "+gpsTracker.getLongitude(), Toast.LENGTH_SHORT).show();
                    Intent LocationIntent = new Intent(MainActivity.this, GoogleMapActivity.class);
                    LocationIntent.putExtra("lat", "" + latitude);
                    LocationIntent.putExtra("lng", "" + longitude);
                    startActivity(LocationIntent);
                } else {
                    showSettingAlert();
                }

                break;


        /*

            case 1:
                startActivity(new Intent(MainActivity.this, AddParty.class));
//                startActivity(new Intent(MainActivity.this, ProductActivity.class));
              *//*  mFragment = Groups.newInstance(mHelpLiveo.get(position).getName());
                actionBar.setTitle("Group");*//*
//                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                break;

            case 2:
                Toast.makeText(MainActivity.this, "Clicked" + position, Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, CartActivity.class));
               *//* mFragment = new ViewPagerFragment();
                actionBar.setTitle("Messaging");*//*
//                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                break;

            case 3:
                Toast.makeText(MainActivity.this, "Clicked" + position, Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                *//*mFragment = SingleSMS.newInstance(mHelpLiveo.get(position).getName());
                actionBar.setTitle("Single Messaging");*//*
//                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                break;

            case 4:
                Toast.makeText(MainActivity.this, "Clicked" + position, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, CartActivity.class));
//                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                *//*mFragment = BulkSMS.newInstance(mHelpLiveo.get(position).getName());
                actionBar.setTitle("Bulk Messaging");*//*
                break;


            case 5:
                Toast.makeText(MainActivity.this, "Clicked" + position, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, PartySelectActivity.class));
//                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                *//*mFragment = Reports.newInstance(mHelpLiveo.get(position).getName());
                actionBar.setTitle("Reports");*//*
                break;

            case 6:
                Toast.makeText(MainActivity.this, "Clicked" + position, Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, ContactActivity.class));
//                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                *//*mFragment = Reports.newInstance(mHelpLiveo.get(position).getName());
                actionBar.setTitle("Reports");*//*
                break;


            case 9:


                gpsTracker = new GPSTracker(MainActivity.this);

                if (gpsTracker.canGetLocation()) {
                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();
//            Toast.makeText(DashboardActivity.this, "Latitude "+gpsTracker.getLatitude()+" Longitude "+gpsTracker.getLongitude(), Toast.LENGTH_SHORT).show();
                    Intent LocationIntent = new Intent(MainActivity.this, GoogleMapActivity.class);
                    LocationIntent.putExtra("lat", "" + latitude);
                    LocationIntent.putExtra("lng", "" + longitude);
                    startActivity(LocationIntent);
                } else {
                    showSettingAlert();
                }

                break;
*/

            default:
                mFragment = Home.newInstance(mHelpLiveo.get(position).getName());
                actionBar.setTitle("Home");
                Toast.makeText(MainActivity.this, "Clicked" + position, Toast.LENGTH_SHORT).show();
                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
                break;
        }

        if (mFragment != null) {

        }
        setElevationToolBar(position != 2 ? 15 : 0);


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

    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Intent pro = new Intent(getApplicationContext(), ProfileActivity.class);
//            startActivity(pro);
            closeDrawer();
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
//            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            closeDrawer();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
}
