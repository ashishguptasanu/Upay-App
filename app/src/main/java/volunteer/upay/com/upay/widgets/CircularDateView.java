package volunteer.upay.com.upay.widgets;

import android.content.Context;

import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import volunteer.upay.com.upay.R;

public class CircularDateView extends FrameLayout {

    private TextView mDate;
    private TextView mMonth;
    private long timestamp;


    public CircularDateView(Context context) {
        super(context);
        init();
    }

    public CircularDateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularDateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        refreshText();
    }


    private void init() {
        View view = inflate(getContext(), R.layout.circular_date_view, this);
        mDate = view.findViewById(R.id.dateText);
        mMonth = view.findViewById(R.id.monthText);
        refreshText();
    }

    private void refreshText() {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(timestamp);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        int date = cal.get(Calendar.DAY_OF_MONTH);
        mDate.setText(String.valueOf(date));
        mMonth.setText(month);
    }
}
