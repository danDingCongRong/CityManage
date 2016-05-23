package cn.edu.bupt.citymanageapp.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.bupt.citymanageapp.R;
import cn.edu.bupt.citymanageapp.model.Report;

/**
 * Created by chenjun14 on 16/5/17.
 */
public class ReportAdapter extends BaseAdapter {

    private List<Report> reportList;

    private Context context;

    public ReportAdapter(Context context, List<Report> reports) {
        this.context = context;
        this.reportList = reports;
    }

    @Override
    public int getCount() {
        return null != reportList ? reportList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.item_report_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Report report = reportList.get(position);
        viewHolder.tvName.setText(report.getName());
        viewHolder.imageView.setBackgroundResource(report.getImageId());

        return view;
    }

    private class ViewHolder {
        ImageView imageView;

        TextView tvName;
    }
}
