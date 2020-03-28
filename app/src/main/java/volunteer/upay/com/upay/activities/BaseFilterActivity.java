package volunteer.upay.com.upay.activities;

import androidx.annotation.IdRes;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by amanbansal on 12/05/18.
 */

public abstract class BaseFilterActivity extends BaseActivity {

    @IdRes
    public abstract int getFilterEditTextId();

    public abstract void textChanged(CharSequence text);

    @Override
    protected void onStart() {
        super.onStart();
        int id = getFilterEditTextId();

        if (id > 0) {
            EditText filterText = findViewById(id);
            filterText.addTextChangedListener(tw);
        }
    }


    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            textChanged(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
