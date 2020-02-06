package eu.ase.moviemanagementapplication.UserAccessManagement;


import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import eu.ase.moviemanagementapplication.Data.FavouriteDBHelper;
import eu.ase.moviemanagementapplication.R;

public class GraphActivity extends AppCompatActivity {

    private BarChart barChart;
    private ArrayList<BarEntry>  barEntryArrayList = new ArrayList<>();
    private BarDataSet barDataSet;
    private FavouriteDBHelper favouriteDBHelper;
    private ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        barChart = findViewById(R.id.barchart);
int i=-1;
        favouriteDBHelper = new FavouriteDBHelper(this);
    Cursor cursor =favouriteDBHelper.movieGraph();
    while(cursor.moveToNext()){
        i++;
        barEntryArrayList.add(new BarEntry(cursor.getInt(1),i));
        int substringSize = cursor.getString(2).trim().length()/2;
        arrayList.add(cursor.getString(2).trim().substring(0,substringSize-1));
        Toast.makeText(getApplicationContext(),cursor.getString(2),Toast.LENGTH_SHORT).show();
    }

        barDataSet = new BarDataSet(barEntryArrayList, "Movies");
        BarData data = new BarData(arrayList, barDataSet);
        barChart.setData(data);
        barChart.setDescription(getString(R.string.nr_times_added_to_fav));
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barChart.animateY(5000);


    }
}
