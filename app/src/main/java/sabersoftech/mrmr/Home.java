package sabersoftech.mrmr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment implements View.OnClickListener {
    private boolean mSearchCheck;
    private static final String TEXT_FRAGMENT = "TEXT_FRAGMENT";


    public static Home newInstance(String text) {
        Home mFragment = new Home();
        Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);


       /* inflater.inflate(R.menu.menu, menu);

        //Select search item
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        menuItem.setVisible(true);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(this.getString(R.string.search));

        ((EditText) searchView.findViewById(R.id.search_src_text))
                .setHintTextColor(getResources().getColor(R.color.nliveo_white));
        searchView.setOnQueryTextListener(onQuerySearchView);

        //menu.findItem(R.id.profile).setVisible(true);*/

        mSearchCheck = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub

        switch (item.getItemId()) {
        /*

            case R.id.profile:
                Intent intent = new Intent(getActivity(), Profile.class);
                startActivity(intent);
                Toast.makeText(getActivity(), R.string.profile, Toast.LENGTH_SHORT).show();
                break;

            case R.id.account:
                Intent Accountintent = new Intent(getActivity(), Account.class);
                startActivity(Accountintent);
                Toast.makeText(getActivity(), R.string.account, Toast.LENGTH_SHORT).show();
                break;

            case R.id.rate:
                Toast.makeText(getActivity(), R.string.rate, Toast.LENGTH_SHORT).show();
                break;

            case R.id.help:
                Intent Helpintent = new Intent(getActivity(), Help.class);
                startActivity(Helpintent);
                Toast.makeText(getActivity(), R.string.help, Toast.LENGTH_SHORT).show();
                break;

            case R.id.logout:
                Toast.makeText(getActivity(), R.string.logout, Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_search:
                mSearchCheck = true;
                Toast.makeText(getActivity(), R.string.search, Toast.LENGTH_SHORT).show();
                break;*/
        }
        return true;
    }

    private SearchView.OnQueryTextListener onQuerySearchView = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            if (mSearchCheck) {
                // implement your search here
            }
            return false;
        }
    };

    @Override
    public void onClick(View v) {


        switch (v.getId()) {/*

            case R.id.bulk_messagebtnsend: {

                if (!Validator.isEmpty(message)) {
                    message.setError("Missing Text");
                } else {
                    if (validator.getStatus()) {
                        sendingDetails();
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), "Fails", Toast.LENGTH_SHORT).show();
                }


            }
            break;

            case R.id.bulk_message_cancel: {

            }
            break;

            case R.id.bulk_message_date: {

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");

            }
            break;

            case R.id.bulk_message_time: {


                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "timePicker");


            }
            break;*/

        }

    }

/*
    public void sendingDetails() {
        Map<String, String> m = new HashMap<String, String>();
        m.put("group_name", group_nm);
        m.put("message", message.getText().toString());

        DataToServer server = new DataToServer();
        server.send(Validator.getBaseUrl() + "test.php", "post", m, "", getActivity());
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String selecteddata = "" + day + "/" + (month + 1) + "/" + year;
            date.setText(selecteddata);
        }
    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            String timedata = "" + hourOfDay + " : " + minute;
            smstime.setText(timedata);

        }
    }*/
}
