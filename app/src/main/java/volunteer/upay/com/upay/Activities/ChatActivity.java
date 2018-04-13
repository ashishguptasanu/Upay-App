package volunteer.upay.com.upay.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import volunteer.upay.com.upay.R;

public class ChatActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerViewChat;
    private EditText mETxtMessage;
    private ImageView btnSend;

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
        mRecyclerViewChat = findViewById(R.id.recycler_view_chat);
        mETxtMessage = findViewById(R.id.edit_text_message);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
    }
}
