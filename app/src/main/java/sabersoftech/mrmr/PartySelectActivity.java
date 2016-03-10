package sabersoftech.mrmr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.ArrayList;

public class PartySelectActivity extends AppCompatActivity {
    boolean searchFlag = false;
    ListView mListView;
    EditText mEditText;
    int textlength = 0;


    String[] name = {"Hiralal Mehata &Co. Sadhashivpeth, Pune – 30.", "Pharamcea Link Shankarsheth Rd. Pune -42.", "Grant Medical Foundation Drug Stores, Ruby Hall Clinic.Sasoon Rd, Pune -1", "Pharmaceutico Vanavdi, Pune.", "Epaca Laboratories Ltd. Vadaki, Tal. Haveli, Pune.", "Natco Pharma Ltd. Saswad Rd, Vadaij, Pune.", "Chordiya Health Care. Vadaki, Tal. Haveli, Pune.", "Swastik Medical Stores. Sadashivpeth, Pune – 30.", "Deenarath Medical Stores.Deenanath Mangeshkar Hospital, Erantavne,Pune -4.", "Kundan Pharmacon. Saswad Rd, Vadaij, Pune.", "Poena Hospital Medical Stores.Navipeth, Pune – 30.", "Vardhaman MedicalCentre.Ravivarpeth, Pune – 2.", "Sadhu Vasvani Mishan Medical Complex.Intecks & Budharani Hospital, Koregaon Park,Pune.", "Neetin Egency. Sadashivpeth, Pune – 30.", "Rohit Medical & General Stores.Gondhatenagar, Saswad Rd., Hadapsar, Pune.", "Prem Medico. Hadapsar, Pune.", "Rahul Medical & General Stores.Vatdawadi, Hadapsar, Pune.", "Parekh Pharma. Manchar, Tal. Ambegaon.", "Parekh Medical &General Stores.Manchar, Tal. Ambegaon.", "Medipoint Drug Stores. Aundh, Pune", "Medicare Chemist. Asholnagar, Shivajinagar, Pune.", "Tapadiya Life Sciences. Sadashivpeth, Pune – 30.", "Tapadiya Distributores. Sadashivpeth, Pune – 30.", "Cipla Ltd. Uralidevachi, Pune.", "Prabhat Medico. Law College Rd, Pune.", "Dev Medical. Sadashivpeth, Pune – 30.", "Mediplus Chemist. Wanavdi, Pune.", "Nootan Pharmaceuticals. Sadashivpeth, Pune – 30.", "Kundan Medico. Chinchawad, Pune.", "Laxmi Medical & GeneralStores.Lonikand, Pune.", "Pooja Medico Pimpri, Pune.", "Mahesh Medicals. Bhosari, Pune.", "Dvijay Pharma. Sadashivpeth, Pune – 30.", "Shri Dattaguru Medical & General Stores.Dhankawadi, Pune.", "Dev Pandel MedicalStores.Camp, Pune.", "Subhtrata Medical. Shirue, Dist. Pune.", "Chnamani Medicals. Saswad, Tal. Purandar, Pune.", "Rahul Medicals. Nasarapur-Bhor, Pune.", "Lockmanya Hospital Medical Stores.Ngadi, Pune.", "Dr.D.Y. Patil Hospital Medical Stores.Pimpri, Pune", "Khandelval Medicals. Lonavala, Tal. Maval, Pune.", "Vardhaman Medicals. Baramati, Dist. Pune.", "Deshi Abhyankar Chaganlal.Baramati, Dist. Pune.", "Shri. Vardhaman Egencies.Baramati, Dist. Pune.", "Yogesh Egency. Bhor, Dist. Pune.", "Mohanlal Sadharam & Co.Bhor, Dist. Pune.", "Sanjeevani Medical & General Stores.Indapur, Dist. Pune.", "Suvidha Medical & General Stores.Indapur, Dist. Pune.", "Vandana Medical & General Stores.Chakan Khed, Pune.", "Niramay Drug Stores. Chinchawad, Pune.", "Apollo Pharmacy. Kondhava, Pune.", "Apollo Pharmacy. Chinchawad, Pune.", "Nobel Chemist. Hadapsar, Pune.", "Shashi Enterprises. Chinchawad, Pune."};
    ArrayList<String> name_arrayList;
    ArrayList<String> search_name_arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_select);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        name_arrayList = new ArrayList<String>();
        search_name_arrayList = new ArrayList<String>();
        mListView = (ListView) findViewById(R.id.searchProductListView);
        mEditText = (EditText) findViewById(R.id.input_search_user_name);
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
                search_name_arrayList.clear();

                for (int i = 0; i < name_arrayList.size(); i++) {
                    if (textlength <= name_arrayList.get(i).length()) {
                        if (mEditText.getText().toString().equalsIgnoreCase(
                                (String) name_arrayList.get(i).subSequence(0, textlength))) {
                            search_name_arrayList.add(name_arrayList.get(i));
                        }
                    }
                }
                Myadapter adapter = new Myadapter(PartySelectActivity.this, search_name_arrayList);
                mListView.setAdapter(adapter);
            }
        });


        for (int i = 0; i < name.length; i++) {
            name_arrayList.add(name[i]);
        }


        Myadapter adapter = new Myadapter(PartySelectActivity.this, name_arrayList);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PartySelectActivity.this, "" + position, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(PartySelectActivity.this, ProductSelectActivity.class);
                if (searchFlag) {
                    intent.putExtra("cust_name", search_name_arrayList.get(position));
                } else {
                    intent.putExtra("cust_name", name_arrayList.get(position));
                }
                startActivity(intent);
                finish();
            }
        });
    }

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
