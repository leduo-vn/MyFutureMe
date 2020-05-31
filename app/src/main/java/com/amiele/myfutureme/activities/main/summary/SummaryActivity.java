package com.amiele.myfutureme.activities.main.summary;

import androidx.appcompat.app.AppCompatActivity;

import android.media.audiofx.AudioEffect;
import android.os.Bundle;

import com.amiele.myfutureme.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);


        String[] xAxisLables = new String[]{"abc","abd","ade","ere","ffd"};

        PieChart pieChart = findViewById(R.id.piechart);
        BarChart barChart  = findViewById(R.id.barchart);

        List<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i< 5; i++)
            barEntries.add(new BarEntry(i, i*10));



        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLables));
        BarDataSet barDataSet = new BarDataSet(barEntries,"abc");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.9f);

        barChart.animateY(5000);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.setVisibleXRangeMaximum(3);
        Description description = new Description();
        description.setText("abc per");
        barChart.setDescription(description);
        barChart.invalidate();

        // pie chart

        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i< 5; i++)
            pieEntries.add(new PieEntry(i, "abc"));


        PieDataSet pieDataSet = new PieDataSet(pieEntries,"abc");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);

        pieChart.animateY(5000);
        pieChart.setData(pieData);
//        pieChart.setFitBars(true);
//        pieChart.setVisibleXRangeMaximum(3);

        pieChart.setDrawHoleEnabled(true);
        // setHoleRadius
        pieChart.setHoleRadius(7);
        pieChart.setTransparentCircleRadius(10);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
//        pieChart.setUsePercentValues(true);
        pieChart.highlightValue(null);
        Description description1 = new Description();
        description1.setText("abc per");
        pieChart.setDescription(description1);
        pieChart.invalidate();
    }
}
