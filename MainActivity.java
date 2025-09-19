package com.example.listycity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> dataList;
    private ListView cityList;
    private ArrayAdapter<String> cityAdapter;//to convert array to view
    private FloatingActionButton addCityButton; //down right corner add city button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//set your UI to "activity_main"

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);//the top part of the screen

        String[] cities = {};//set the array value to empty, which is not resizable

        dataList = new ArrayList<>(Arrays.asList(cities));//after this you can add value into this array now

        cityList = findViewById(R.id.city_list);
        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        cityList.setAdapter(cityAdapter);

        //when user taps a city in the list
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = dataList.get(position);
                int lastSpace = item.lastIndexOf(' ');
                String city = (lastSpace != -1) ? item.substring(0, lastSpace).trim() : item;
                String province = (lastSpace != -1) ? item.substring(lastSpace + 1).trim() : "";
                showCityDialog(city, province, position);
            }
        });


        //handle the floating button
        addCityButton = findViewById(R.id.button_add_city);

        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityDialog("", "", -1);
            }
        });
    }

    private void showCityDialog(String currentCity, String currentProvince, final int position) {

        //add city dialog
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText cityInput = new EditText(this);
        cityInput.setHint("Enter city name");
        cityInput.setText(currentCity);


        final EditText provinceInput = new EditText(this);
        provinceInput.setHint("Enter province/state (e.g., AB)");
        provinceInput.setText(currentProvince);

        //to linear layout
        layout.addView(cityInput);
        layout.addView(provinceInput);


        String title = "Add/Edit City";
        //pop up dialog with the title
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setView(layout);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            //when "OK" is pressed
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                String cityName = cityInput.getText().toString().trim();
                String province = provinceInput.getText().toString().trim().toUpperCase();


                if (!cityName.isEmpty() && !province.isEmpty()) {
                    String newItem = cityName + " " + province;


                    if (position == -1) {
                        dataList.add(newItem);
                    } else {
                        dataList.set(position, newItem);
                    }

                    cityAdapter.notifyDataSetChanged();
                }
            }
        });


        builder.setNegativeButton("Cancel", null);


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}