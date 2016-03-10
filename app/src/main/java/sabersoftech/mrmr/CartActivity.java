package sabersoftech.mrmr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    DataHandler handler;
    GPSTracker gpsTracker;

    TextView counter;
    ListView mListView;
    ArrayList<String> o_id_arrayList;
    ArrayList<String> o_date_arrayList;
    ArrayList<String> p_id_arrayList;

    ArrayList<String> name_arrayList;
    ArrayList<String> mrp_arrayList;
    ArrayList<String> qty_arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }


        o_id_arrayList = new ArrayList<String>();
        o_date_arrayList = new ArrayList<String>();
        p_id_arrayList = new ArrayList<String>();
        name_arrayList = new ArrayList<String>();
        mrp_arrayList = new ArrayList<String>();
        qty_arrayList = new ArrayList<String>();
        mListView = (ListView) findViewById(R.id.listView_conf);
        counter = (TextView) findViewById(R.id.c_count);

        getFromSQLite();
    }

    public void getFromSQLite() {

        o_id_arrayList.clear();
        o_date_arrayList.clear();
        p_id_arrayList.clear();

        name_arrayList.clear();
        mrp_arrayList.clear();
        qty_arrayList.clear();

        handler = new DataHandler(getBaseContext());
        handler.open();
        Cursor cursor = handler.returnData();
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                do {
                    o_id_arrayList.add(cursor.getString(0));
                    o_date_arrayList.add(cursor.getString(1));
                    p_id_arrayList.add(cursor.getString(2));

                    name_arrayList.add(cursor.getString(3));
                    mrp_arrayList.add(cursor.getString(4));
                    qty_arrayList.add(cursor.getString(5));

                } while (cursor.moveToNext());


                counter.setText("Total : " + handler.returnDataCount());
            }
        } catch (CursorIndexOutOfBoundsException e) {
            Toast.makeText(CartActivity.this, "Item not found", Toast.LENGTH_SHORT).show();
        }


        handler.close();

        Myadapter adapter = new Myadapter(CartActivity.this, name_arrayList, mrp_arrayList, qty_arrayList);
        mListView.setAdapter(adapter);
    }


    public void ConfirmOrder(View view) {
        gpsTracker = new GPSTracker(CartActivity.this);

        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            Toast.makeText(CartActivity.this, "Latitude : " + latitude + "\nLongitude : " + longitude, Toast.LENGTH_SHORT).show();
//            ((TextView)findViewById(R.id.tv_location)).setText("Latitude : " + latitude + "\nLongitude : " + longitude);
            handler = new DataHandler(getBaseContext());
            handler.open();
            handler.deleteAll();//delete(o_id_arrayList.get(position), o_date_arrayList.get(position), p_id_arrayList.get(position));
            counter.setText("Toatal : " + handler.returnDataCount());
            handler.close();
            getFromSQLite();
        } else {
            showSettingAlert();

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


    class Myadapter extends ArrayAdapter<String> {
        ArrayList<String> name_arrayList;
        ArrayList<String> mrp_arrayList;
        ArrayList<String> qty_arrayList;
        Context context;

        public Myadapter(Context context, ArrayList<String> name_arrayList, ArrayList<String> mrp_arrayList, ArrayList<String> qty_arrayList) {
            super(context, R.layout.confirm_order_listitem, R.id.tv_searchlist_coname, name_arrayList);
            this.context = context;
            this.name_arrayList = name_arrayList;
            this.mrp_arrayList = mrp_arrayList;
            this.qty_arrayList = qty_arrayList;

        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return super.getCount();
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            String cols = "";

            View row = convertView;
            if (row == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

                row = ((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.confirm_order_listitem, parent, false);
            }

            ((TextView) row.findViewById(R.id.tv_searchlist_coname)).setText(name_arrayList.get(position));
            ((TextView) row.findViewById(R.id.tv_searchlist_comrp)).setText("MRP : " + mrp_arrayList.get(position));
            ((TextView) row.findViewById(R.id.tv_searchlist_coqty)).setText("Qty : " + qty_arrayList.get(position));

            ((ImageView) row.findViewById(R.id.action_detete)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handler = new DataHandler(getBaseContext());
                    handler.open();
                    handler.delete(o_id_arrayList.get(position), o_date_arrayList.get(position), p_id_arrayList.get(position));
                    counter.setText("Total : " + handler.returnDataCount());
                    handler.close();

                    getFromSQLite();
                }
            });

            return row;

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
}
