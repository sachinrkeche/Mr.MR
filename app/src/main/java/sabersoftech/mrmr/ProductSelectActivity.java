package sabersoftech.mrmr;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductSelectActivity extends AppCompatActivity {
    boolean searchFlag = false;
    int textlength = 0;
    DataHandler handler;

    ListView mListView;
    EditText mEditText;
    TextView cartCount;

    ArrayList<String> o_id_arrayList;
    ArrayList<String> o_date_arrayList;
    ArrayList<String> p_id_arrayList;
    ArrayList<String> name_arrayList;
    ArrayList<String> mrp_arrayList;
    ArrayList<String> qty_arrayList;

    ArrayList<String> search_o_id_arrayList;
    ArrayList<String> search_o_date_arrayList;
    ArrayList<String> search_p_id_arrayList;
    ArrayList<String> search_name_arrayList;
    ArrayList<String> search_mrp_arrayList;
    ArrayList<String> search_qty_arrayList;


    /*String[] o_id = {"101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112", "113", "114", "115", "116", "117", "118", "119", "120", "121", "122", "123", "124", "125", "126", "127", "128", "129", "130", "131", "132", "133", "134", "135", "136", "137", "138", "139", "140", "141", "142", "143", "144", "145", "146", "147", "148", "149", "150"};
    String[] p_id = {"201", "202", "203", "204", "205", "206", "207", "208", "209", "210", "211", "212", "213", "214", "215", "216", "217", "218", "219", "220", "221", "222", "223", "224", "225", "226", "227", "228", "229", "230", "231", "232", "233", "234", "235", "236", "237", "238", "239", "240", "241", "242", "243", "244", "245", "246", "247", "248", "249", "250"};
    String[] o_date = {"12-1-2015", "13-1-2015", "14-1-2015", "15-1-2015", "16-1-2015", "17-1-2015", "18-1-2015", "19-1-2015", "20-1-2015", "21-1-2015", "22-1-2015", "23-1-2015", "24-1-2015", "25-1-2015", "26-1-2015", "27-1-2015", "28-1-2015", "29-1-2015", "30-1-2015", "31-1-2015", "32-1-2015", "33-1-2015", "34-1-2015", "35-1-2015", "36-1-2015", "37-1-2015", "38-1-2015", "39-1-2015", "40-1-2015", "41-1-2015", "42-1-2015", "43-1-2015", "44-1-2015", "45-1-2015", "46-1-2015", "47-1-2015", "48-1-2015", "49-1-2015", "50-1-2015", "51-1-2015", "52-1-2015", "53-1-2015", "54-1-2015", "55-1-2015", "56-1-2015", "57-1-2015", "58-1-2015", "59-1-2015", "60-1-2015", "61-1-2015"};
    String[] name = {"Aminophylline : 225 mg", "Vit E : 100 mg", "Vit E : 200 mg", "Vit E : 268 mg", "Vit E : 134 mg", "Vit E : 400 mg", "Vit E : 600 mg", "Vit C : 100 mg", "Analgin : 500 mg", "Aminophylline : 100 mg", "Vit C : 500 mg", "Vit C : 100 mg", "Amodiaquine : 200 mg", "Analgin : 500 mg", "Analgin : 500 mg", "Aspirin : 50 mg", "Aspirin : 75 mg", "Aspirin : 80 mg", "Aspirin : 100 mg", "Aspirin : 150 mg", "Aspirin : 162.5 mg", "Aspirin : 500 mg", "Aspirin : 60 mg", "Aspirin : 325 mg", "Bacampicillin : 200 mg", "Bacampicillin : 400 mg", "Vit C : 500 mg", "Betamethasone Sodium Phosphate : 0.5 mg", "Vit C : 200 mg", "Vit C : 100 mg", "Vit C : 50 mg", "Betamethasone : 1 mg", "Riboflavin : 10 mg", "Betamethasone Sodium Phosphate : 4 mg", "Vit B1 : 75 mg", "Betamethasone : 4 mg", "Vit A : 5000 iu", "Betamethasone : 0.1%", "Vit A : 100000 iu", "Betamethasone : 0.5 mg", "Vit A : 50000 iu", "Vit A : 50000 iu", "Verapamil : 40 mg", "Verapamil : 80 mg", "Verapamil : 120 mg", "Verapamil : 240 mg", "Verapamil : 2.5 mg", "Tolnaftate : 10 mg", "Tolnaftate : 10 mg", "Tolnaftate : 10 mg"};
    String[] mrp = {"5.09", "5.34", "8.88", "12.82", "7.7", "15.25", "21.8", "11.65", "4.54", "0.93", "2.71", "1.73", "4.62", "8.27", "29.44", "1.1", "1.22", "1.36", "1.28", "1.72", "2.2", "8.53", "2.44", "8.66", "5.82", "10.62", "6.36", "3.44", "26.98", "17.62", "11.74", "6.3", "1.5", "4.4", "2.98", "7.27", "3.88", "2.53", "3.02", "9.28", "6.2", "5.96", "6.24", "11.9", "19.64", "37.5", "2.26", "3.86", "5.2", "10.34"};
    String[] qty = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50"};
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_select);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        Intent intent = getIntent();
        ((TextView) findViewById(R.id.name)).setText(intent.getStringExtra("cust_name"));

        o_id_arrayList = new ArrayList<String>();
        o_date_arrayList = new ArrayList<String>();
        p_id_arrayList = new ArrayList<String>();
        name_arrayList = new ArrayList<String>();
        mrp_arrayList = new ArrayList<String>();
        qty_arrayList = new ArrayList<String>();

        search_o_id_arrayList = new ArrayList<String>();
        search_o_date_arrayList = new ArrayList<String>();
        search_p_id_arrayList = new ArrayList<String>();
        search_name_arrayList = new ArrayList<String>();
        search_mrp_arrayList = new ArrayList<String>();
        search_qty_arrayList = new ArrayList<String>();

        mListView = (ListView) findViewById(R.id.searchProductListView);
        mEditText = (EditText) findViewById(R.id.input_search_product_name);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                textlength = mEditText.length();
                if (textlength == 0) {
                    searchFlag = false;
                } else {
                    searchFlag = true;
                }
                search_o_id_arrayList.clear();
                search_o_date_arrayList.clear();
                search_p_id_arrayList.clear();
                search_name_arrayList.clear();
                search_mrp_arrayList.clear();
                search_qty_arrayList.clear();

                for (int i = 0; i < name_arrayList.size(); i++) {
                    if (textlength <= name_arrayList.get(i).length()) {
                        if (mEditText.getText().toString().equalsIgnoreCase(
                                (String) name_arrayList.get(i).subSequence(0, textlength))) {
                            search_o_id_arrayList.add(name_arrayList.get(i));
                            search_o_date_arrayList.add(o_date_arrayList.get(i));
                            search_p_id_arrayList.add(p_id_arrayList.get(i));
                            search_name_arrayList.add(name_arrayList.get(i));
                            search_mrp_arrayList.add(mrp_arrayList.get(i));
                            search_qty_arrayList.add(qty_arrayList.get(i));
                        }
                    }
                }

                Myadapter adapter = new Myadapter(ProductSelectActivity.this, search_name_arrayList, search_mrp_arrayList, search_qty_arrayList);
                mListView.setAdapter(adapter);

            }
        });


        cartCount = (TextView) findViewById(R.id.cart_count);


        //Here is the webservice call

        ServerDetails();







      /*  for (int i = 0; i < name.length; i++) {
            o_id_arrayList.add(o_id[i]);
            o_date_arrayList.add(o_date[i]);
            p_id_arrayList.add(p_id[i]);
            name_arrayList.add(name[i]);
            mrp_arrayList.add(mrp[i]);
            qty_arrayList.add(qty[i]);
        }*/
//        getFromSQLite();


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    if (searchFlag) {
                        ADD(search_o_id_arrayList.get(position), search_o_date_arrayList.get(position), search_p_id_arrayList.get(position), search_name_arrayList.get(position), search_mrp_arrayList.get(position), search_qty_arrayList.get(position));
                    } else {
                        ADD(o_id_arrayList.get(position), o_date_arrayList.get(position), p_id_arrayList.get(position), name_arrayList.get(position), mrp_arrayList.get(position), qty_arrayList.get(position));
                    }

                } catch (SQLiteException e) {
                    Toast.makeText(ProductSelectActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    public void ServerDetails() {

        o_id_arrayList.clear();
        o_date_arrayList.clear();
        p_id_arrayList.clear();
        name_arrayList.clear();
        mrp_arrayList.clear();
        qty_arrayList.clear();

        String base = "http://192.168.0.101/Mr.MR/product.php";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();


        client.post(base, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject root = new JSONObject(response);
                    JSONArray arr = root.getJSONArray("results");

                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.optJSONObject(i);
                        o_id_arrayList.add(obj.optString("1"));
                        o_date_arrayList.add(obj.optString("2"));
                        p_id_arrayList.add(obj.optString("p_id"));//p_id
                        name_arrayList.add(obj.optString("p_name"));//p_name
                        mrp_arrayList.add(obj.optString("p_price"));
                        qty_arrayList.add(obj.optString("2"));
                        /*p_id
p_cat
p_subcat
p_type
Feed
p_name
p_price
p_desc
p_barocde*/
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Myadapter adapter = new Myadapter(ProductSelectActivity.this, name_arrayList, mrp_arrayList, qty_arrayList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int statusCode, Throwable error, String content) {
                // TODO Auto-generated method stub

                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                } else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    // goto cart
    public void showCart(View view) {
        startActivity(new Intent(ProductSelectActivity.this, CartActivity.class));
    }

    //SQLite Function

    public void ADD(String ORDER_ID, String ORDER_DATE, String PRODUCT_ID, String PRODUCT_NAME, String PRODUCT_MRP, String PRODUCT_QTY) {

        handler = new DataHandler(getBaseContext());
        handler.open();


        if (handler.check(ORDER_ID, ORDER_DATE, PRODUCT_ID) == 0) {
            handler.InsertIntoCart(ORDER_ID, ORDER_DATE, PRODUCT_ID, PRODUCT_NAME, PRODUCT_MRP, PRODUCT_QTY);
            Toast.makeText(getApplicationContext(), "inserted data", Toast.LENGTH_SHORT).show();
            getFromSQLite();
            handler.close();

        } else {
            handler.close();
            Toast.makeText(ProductSelectActivity.this, "Entry Already presnt", Toast.LENGTH_SHORT).show();
        }
    }

    public void getFromSQLite() {
        handler = new DataHandler(getBaseContext());
        handler.open();
        cartCount.setText("" + handler.returnDataCount());

        handler.close();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getFromSQLite();
    }

    ///Barcode Read
    public void BarcodeClick(View view) {
        Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        intent.putExtra("com.google.zxing.client.android.SCAN.SCAN_MODE", "QR_CODE_MODE");
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                ((EditText) findViewById(R.id.input_search_product_name)).setText("" + contents);
                Log.i("xZing", "contents: " + contents + " format: " + format); // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) { // Handle cancel
                Log.i("xZing", "Cancelled");
            }
        }
    }


    class Myadapter extends ArrayAdapter<String> {
        ArrayList<String> name_arrayList;
        ArrayList<String> mrp_arrayList;
        ArrayList<String> qty_arrayList;
        Context context;

        public Myadapter(Context context, ArrayList<String> name_arrayList, ArrayList<String> mrp_arrayList, ArrayList<String> qty_arrayList) {
            super(context, R.layout.product_list_item, R.id.tv_search_pname, name_arrayList);
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
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            String cols = "";

            View row = convertView;
            if (row == null) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

                row = ((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.product_list_item, parent, false);
            }

            ((TextView) row.findViewById(R.id.tv_search_pname)).setText("Name : " + name_arrayList.get(position));
            ((TextView) row.findViewById(R.id.tv_search_pmrp)).setText("MRP : " + mrp_arrayList.get(position));
            ((TextView) row.findViewById(R.id.tv_search_pqty)).setText("Qty : " + qty_arrayList.get(position));


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
