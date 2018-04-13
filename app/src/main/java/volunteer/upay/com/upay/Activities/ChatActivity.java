package volunteer.upay.com.upay.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import volunteer.upay.com.upay.R;

public class ChatActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initDatabase();
    }

    private void initDatabase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        initViews();
    }

    private void initViews() {
    }
}
