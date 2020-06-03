package com.amiele.myfutureme.activities.main.summary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.media.audiofx.AudioEffect;
import android.os.Bundle;

import com.amiele.myfutureme.R;
import com.amiele.myfutureme.database.entity.Goal;
import com.amiele.myfutureme.database.entity.SubTask;
import com.amiele.myfutureme.database.entity.Tag;
import com.amiele.myfutureme.database.entity.Task;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
               // DisplayBarChart(time,dateName);
                DisplayLineChart(time,dateName);
            }
        });

        mSummaryViewModel.getGoalMediatorLiveData().observe(this, new Observer<List<Goal>>() {
            @Override
            public void onChanged(List<Goal> goals) {
                if (goals == null) return;
                ArrayList<String> tagNames = new ArrayList<>();
                ArrayList<Integer> tagTimes = new ArrayList<>();
                ArrayList<String> goalNames = new ArrayList<>();
                ArrayList<Integer> goalTimes = new ArrayList<>();

                for (Goal goal : goals)
                {

                    int time = 0;
                    if (goal.getTaskList()!=null)
                    {
                        for (Task task:goal.getTaskList())
                            time += task.getMinute();
                    }
                    goalNames.add(goal.getName());
                    goalTimes.add(time);
                    if (goal.getTagList()!=null)
                    {
                        for (int i = 0; i< goal.getTagList().size();i++)
                        {
                            Tag tag= goal.getTagList().get(i);
                            boolean existed = false;
                            for (String tagName: tagNames)
                                if (tagName.equals(tag.getName()))
                                {
                                    existed = true;
                                    tagTimes.set(i, tagTimes.get(i)+time);
                                    break;
                                }
                            if (!existed) {
                                tagNames.add(tag.getName());
                                tagTimes.add(time);
                            }
                        }
                    }
                }

                DisplayBarChart(tagTimes, tagNames);
                DisplayPieChart(goalTimes,goalNames);
            }
        });

    }

    private void DisplayPieChart(ArrayList<Integer> goalTimes, ArrayList<String> goalNames)
    {
        PieChart pieChart = findViewById(R.id.piechart);
        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i = 0; i< goalNames.size(); i++)
            pieEntries.add(new PieEntry(goalTimes.get(i), goalNames.get(i)));


        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Goal Name");
        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData pieData = new PieData(pieDataSet);

        pieChart.animateY(5000);
        pieChart.setData(pieData);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(7);
        pieChart.setTransparentCircleRadius(10);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);
        pieChart.highlightValue(null);
        Description description1 = new Description();
        description1.setText("Hour per Goal");
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

        List<Entry> dataEntries = new ArrayList<>();
        for (int i = 0; i< xAxisLabels.size();i++)
        {
            dataEntries.add(new Entry(i, values.get(i)));
        }

        LineDataSet lineDataSet = new LineDataSet(dataEntries,"abc");
        lineDataSet.setColor(Color.WHITE);
        lineDataSet.setCircleColor(Color.WHITE);
        lineDataSet.setCircleRadius(5f);
        lineDataSet.setCircleHoleRadius(2.5f);
        lineDataSet.setLineWidth(1.75f);
        lineDataSet.setHighLightColor(Color.WHITE);
        lineDataSet.setDrawValues(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLabels));
        LineData lineData =new LineData(dataSets);

        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        lineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(dateName));
        lineChart.setBackgroundColor(Color.BLACK);
        lineChart.setBorderColor(Color.WHITE);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisLeft().setAxisLineColor(Color.WHITE);
        lineChart.getAxisRight().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setAxisLineColor(Color.WHITE);
        lineChart.invalidate();
    }

    private void DisplayBarChart(ArrayList<Integer> times, ArrayList<String> names)
    {
        BarChart barChart  = findViewById(R.id.barchart);
        List<BarEntry> barEntries = new ArrayList<>();

        for (int i = 0; i< names.size();i++)
        {
            barEntries.add(new BarEntry(i, times.get(i)));
        }

        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(names));
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        barChart.getXAxis().setGranularityEnabled(true);
        BarDataSet barDataSet = new BarDataSet(barEntries,"Tag");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.3f);

        barChart.animateY(5000);
        barChart.setData(barData);

        barChart.setVisibleXRangeMaximum(10);
        Description description = new Description();
        description.setText("Hour Per Tag");
        barChart.setDescription(description);
        barChart.invalidate();
    }
}
