package com.amiele.myfutureme.activities.main.summary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.media.audiofx.AudioEffect;
import android.os.Bundle;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.database.entity.SubTask;
import com.amiele.myfutureme.helpers.DateConverter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
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
import java.util.Date;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {
    private SummaryViewModel mSummaryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        mSummaryViewModel = new ViewModelProvider(this).get(SummaryViewModel.class);
        int userId = Integer.parseInt(getIntent().getStringExtra("user_id"));
        mSummaryViewModel.setUserId(userId);
        mSummaryViewModel.loadAllLoad();

        mSummaryViewModel.getSubTaskLiveData().observe(this, new Observer<List<SubTask>>() {
            @Override
            public void onChanged(List<SubTask> subTasks) {
                int[] time = new int[100];
                String[] dateName = new String[100];
                Date d = new Date();
                String currentDate= DateConverter.ConvertFromDateToString(d);
                for (SubTask subTask:subTasks) {
                    long dateDiff = DateConverter.getDaysDifferentFromStringDate(currentDate, subTask.getDate());
                    if (dateDiff >= 0 && dateDiff < 100) {
                        time[(int) dateDiff] += subTask.getMinute();
                        dateName[(int) dateDiff] = subTask.getDate();
                    }
                }
                DisplayBarChart(time,dateName);
            }
        });



        String[] xAxisLables = new String[]{"abc","abd","ade","ere","ffd"};

        PieChart pieChart = findViewById(R.id.piechart);







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

    private void DisplayLineChart(int[]time , String[] dateName)
    {
        LineChart lineChart= findViewById(R.id.linechart);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        ArrayList<String> xAxisLabels = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        List<BarEntry> barEntries = new ArrayList<>();
        boolean stored = false;
        for (int i=99; i>=0;i--) {
            if (time[i] > 0) stored = true;
            if (stored) {
                if (dateName[i]==null) xAxisLabels.add("");
                else
                    xAxisLabels.add(dateName[i]);
                values.add(time[i]);
            }
        }

        for (int i = 0; i< xAxisLabels.size();i++)
        {
            barEntries.add(new BarEntry(i, values.get(i)));
        }
    }

    private void DisplayBarChart(int[] time, String[] dateName)
    {
        BarChart barChart  = findViewById(R.id.barchart);
        ArrayList<String> xAxisLabels = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        List<BarEntry> barEntries = new ArrayList<>();
        boolean stored = false;
        for (int i=99; i>=0;i--) {
            if (time[i] > 0) stored = true;
            if (stored) {
                if (dateName[i]==null) xAxisLabels.add("");
                else
                    xAxisLabels.add(dateName[i]);
                values.add(time[i]);
            }
        }

        for (int i = 0; i< xAxisLabels.size();i++)
        {
            barEntries.add(new BarEntry(i, values.get(i)));
        }

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));
        BarDataSet barDataSet = new BarDataSet(barEntries,"Date");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.3f);

        barChart.animateY(5000);
        barChart.setData(barData);
        barChart.setFitBars(true);

        barChart.setVisibleXRangeMaximum(10);
        Description description = new Description();
        description.setText("Hour Per Date");
        barChart.setDescription(description);
        barChart.invalidate();
    }
}
