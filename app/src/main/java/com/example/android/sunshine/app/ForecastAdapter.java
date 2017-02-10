package com.example.android.sunshine.app;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.sunshine.app.data.WeatherContract;

import static com.example.android.sunshine.app.Utility.getArtResourceForWeatherCondition;
import static com.example.android.sunshine.app.Utility.getIconResourceForWeatherCondition;

/**
 * {@link ForecastAdapter} exposes a list of weather forecasts
 * from a {@link android.database.Cursor} to a {@link android.widget.ListView}.
 */
public class ForecastAdapter extends CursorAdapter {

    private static final int VIEW_TYPE_TODAY = 0;
    private static final int VIEW_TYPE_FUTURE_DAY = 1;
    private static final int VIEW_TYPE_COUNT = 2;
    private boolean mUseTodayLayout = true;

    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public void setUseTodayLayout(boolean useTodayLayout) {
        mUseTodayLayout = useTodayLayout;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Choose the layout type
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;
        layoutId = viewType == VIEW_TYPE_TODAY ? R.layout.list_item_forecast_today: R.layout.list_item_forecast;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // our view is pretty simple here --- just a text view
        // we'll keep the UI functional with a simple (and slow!) binding.
        int weatherId = cursor.getInt(ForecastFragment.COL_WEATHER_ID);

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        // Read weather icon ID from cursor
        int viewType = getItemViewType(cursor.getPosition());
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
                // Get weather icon
                viewHolder.iconView.setImageResource(Utility.getArtResourceForWeatherCondition(
                        cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));
                break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
                // Get weather icon
                viewHolder.iconView.setImageResource(Utility.getIconResourceForWeatherCondition(
                        cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));
                break;
            }
        }


        long date = cursor.getLong(ForecastFragment.COL_WEATHER_DATE);
        viewHolder.dateView.setText(Utility.getFriendlyDayString(context,date));

        String weather = cursor.getString(ForecastFragment.COL_WEATHER_DESC);
        viewHolder.weatherDescView.setText(weather);

        viewHolder.iconView.setContentDescription(weather);
        // Read user preference for metric or imperial temperature units
        boolean isMetric = Utility.isMetric(context);

        // Read high temperature from cursor
        double high = cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        viewHolder.highView.setText(Utility.formatTemperature(context,high, isMetric));

        double low = cursor.getDouble(ForecastFragment.COL_WEATHER_MIN_TEMP);
        viewHolder.lowView.setText(Utility.formatTemperature(context,low, isMetric));
    }

    public static class ViewHolder {

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
}