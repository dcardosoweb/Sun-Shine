package com.example.android.sunshine.app;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



public class ViewHolder {

    public final ImageView iconView;
    public final TextView dateView;
    public final TextView lowView;
    public final TextView highView;
    public final TextView weatherDescView;

    public ViewHolder(View view) {
        dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
        iconView = (ImageView)view.findViewById(R.id.list_item_icon);
        lowView = (TextView)view.findViewById(R.id.list_item_low_textview);
        highView = (TextView)view.findViewById(R.id.list_item_high_textview);
        weatherDescView = (TextView)view.findViewById(R.id.list_item_forecast_textview);
    }
}
