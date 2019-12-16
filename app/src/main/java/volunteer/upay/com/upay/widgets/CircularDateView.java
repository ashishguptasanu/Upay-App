package volunteer.upay.com.upay.widgets;

import android.content.Context;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

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
    private static int count = 0;

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
        view.setBackgroundResource(getRandomRes());
        mDate = view.findViewById(R.id.dateText);
        mMonth = view.findViewById(R.id.monthText);
        refreshText();
    }

    @DrawableRes
    public int getRandomRes() {
        count = count % 4;
        switch (count++) {
            case 0: {
                return R.drawable.circle_bg_blue;
            }
            case 1: {
                return R.drawable.circle_bg_blue_dark;
            }
            case 2: {
                return R.drawable.circle_bg_green;
            }
            case 3: {
                return R.drawable.circle_bg_red;
            }
            default: {
                return R.drawable.circle_bg_blue;
            }
        }

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
