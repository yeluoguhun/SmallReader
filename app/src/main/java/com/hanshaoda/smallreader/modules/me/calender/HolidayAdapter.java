package com.hanshaoda.smallreader.modules.me.calender;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanshaoda.smallreader.R;

import butterknife.BindView;

/**
 * author: hanshaoda
 * created on: 2017/10/14 上午11:53
 * description:
 */
public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.MyHolder> {

    private Context context;
    private String[] mHolidaysNameArray;
    private String[] mHolidaysInfoArray;

    private OnItemClickListener mItemClickListener;

    public void setmItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public HolidayAdapter(Context context, String[] mHolidaysNameArray, String[] mHolidaysInfoArray) {

        this.context = context;
        this.mHolidaysInfoArray = mHolidaysInfoArray;
        this.mHolidaysNameArray = mHolidaysNameArray;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.holiday_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.nameHolidayItem.setText(mHolidaysNameArray[position]);
        holder.infoHolidayItem.setText(mHolidaysInfoArray[position]);
    }

    @Override
    public int getItemCount() {
        return mHolidaysNameArray.length;
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameHolidayItem;
        TextView infoHolidayItem;

        public MyHolder(View itemView) {
            super(itemView);
            nameHolidayItem=itemView.findViewById(R.id.name_holiday_item);
            infoHolidayItem=itemView.findViewById(R.id.info_holiday_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getLayoutPosition(), nameHolidayItem.getText().toString());
            }
        }
    }
}
