package cn.edu.bupt.citymanageapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.bupt.citymanageapp.R;
import cn.edu.bupt.citymanageapp.model.Report;

/**
 * Created by chenjun14 on 16/5/17.
 */
public class InspectionReportActivity extends Activity {

    private int[] imageId = {
            R.drawable.sample_report, R.drawable.check_report,
            R.drawable.check_task, R.drawable.component_census,
            R.drawable.draft, R.drawable.send,
            R.drawable.call_name, R.drawable.repid_report};

    private Button btnBack;

    private GridView gridView;

    private ReportAdapter adapter;

    private List<Report> reportList;

    private Context context = InspectionReportActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.inspecte_report_activity);

        initData();
        initView();
        bindEvents();
    }

    private void initView() {
        btnBack = (Button) findViewById(R.id.btnBack);
        gridView = (GridView) findViewById(R.id.gridView);

        adapter = new ReportAdapter(this, reportList);
        gridView.setAdapter(adapter);
    }

    private void bindEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                gotoTestPage();
            }
        });
    }

    private void gotoTestPage() {
        Intent intent = new Intent(context, TestActivity.class);
        startActivity(intent);
    }

    private List<Report> initData() {
        reportList = new ArrayList<Report>();

        String[] reports = getResources().getStringArray(R.array.report);
        for (int i = 0; i < reports.length; ++i) {
            Report report = new Report();
            report.setImageId(imageId[i]);
            report.setName(reports[i]);

            reportList.add(report);
        }

        return reportList;
    }

}
