package com.ufam.smartaquarium;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.ufam.smartaquarium.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    LineChart mpLineChart;
    BarChart barChart;
    DatabaseReference mPostReference;
    ValueEventListener valueEventListener;
    LineDataSet lineDataSet;
    LineChart lineChart;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("temperatura");
        DatabaseReference myRefTDS = database.getReference("tdsValue");
        DatabaseReference myRefPH = database.getReference("ph");


        final TextView mTemperaturatextView = root.findViewById(R.id.textView2);
        //return inflater.inflate(R.layout.fragment_home, container, false);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue().toString();
                mTemperaturatextView.setText(value+"ºC");
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        final TextView mTDStextView = root.findViewById(R.id.tds);
        //return inflater.inflate(R.layout.fragment_home, container, false);

        // Read from the database
        myRefTDS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                String value = dataSnapshot.getValue().toString();

                mTDStextView.setText(value+"\nppm");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        final TextView mPHtextView = root.findViewById(R.id.ph);
        //return inflater.inflate(R.layout.fragment_home, container, false);

        // Read from the database
        myRefPH.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue().toString();
                mPHtextView.setText(value);
                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        //final BarChart barChart = root.findViewById(R.id.barChart);
        //final FirebaseDatabase data = FirebaseDatabase.getInstance();
        /*
        DatabaseReference refGraph = data.getReference("https://smart-aquarium-data-default-rtdb-firebaseio-com/sensores");
        refGraph.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                yData.clear();
                float i = 0;
                int conta = 0;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    sv = ds.child("temp").getValue().toString();
                    //yData.add(new BarEntry(sv));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/
        barChart = root.findViewById(R.id.barChart);
        BarDataSet barDataSet = new BarDataSet(tempValuesGraph(), "Temperatura");
        BarDataSet barDataSet1 = new BarDataSet(tdsValuesGrapf(), "TDS");
        BarDataSet barDataSet2 = new BarDataSet(phValuesGraph(), "pH");

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

        barChart.setBackgroundColor(Color.WHITE);
        barChart.setNoDataText("Não há dados disponíveis!");
        barChart.setNoDataTextColor(Color.BLACK);


        barDataSet.setColor(Color.parseColor("#63BDE0"));
        barDataSet1.setColor(Color.parseColor("#e4b17d"));
        barDataSet2.setColor(Color.parseColor("#07f9a2"));

        // legend
        Legend legend = barChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(10);
        legend.setForm(Legend.LegendForm.LINE);

        // description
        Description description = new Description();
        description.setText("24 Horas");
        description.setTextColor(Color.BLACK);
        description.setTextSize(10);
        barChart.setDescription(description);

        BarData data = new BarData(dataSets);
        barChart.setData(data);
        barChart.invalidate();


        return root;
    }

    public ArrayList<BarEntry> tempValuesGraph() {
        ArrayList<BarEntry> tempVals = new ArrayList<>();
        tempVals.add(new BarEntry(0,25));

        return tempVals;
    }

    public ArrayList<BarEntry> tdsValuesGrapf() {
        ArrayList<BarEntry> tdsVals = new ArrayList<>();
        tdsVals.add(new BarEntry(1, 40));

        return tdsVals;
    }

    public ArrayList<BarEntry> phValuesGraph() {
        ArrayList<BarEntry> phVals = new ArrayList<>();
        phVals.add(new BarEntry(2, 7));

        return phVals;
    }


}

/*
 mpLineChart = root.findViewById(R.id.grafico);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Temperatura");
        LineDataSet lineDataSet2 = new LineDataSet(dataValues2(), "TDS");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet2);

        mpLineChart.setBackgroundColor(Color.WHITE);
        mpLineChart.setNoDataText("Não há dados disponíveis!");
        mpLineChart.setNoDataTextColor(Color.BLACK);

        // legend
        Legend legend = mpLineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(10);
        legend.setForm(Legend.LegendForm.CIRCLE);

        // description
        Description description = new Description();
        description.setText("");
        description.setTextColor(Color.WHITE);
        description.setTextSize(5);
        mpLineChart.setDescription(description);


        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();
 */