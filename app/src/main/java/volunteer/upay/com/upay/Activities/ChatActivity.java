package volunteer.upay.com.upay.Activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.ArrayList;
import java.util.List;

import volunteer.upay.com.upay.Adapters.ChatRecyclerAdapter;
import volunteer.upay.com.upay.Models.ChatUser;
import volunteer.upay.com.upay.R;

public class ChatActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerViewChat;
    private EditText mETxtMessage;
    private ImageView btnSend;
    private List<ChatUser> chatList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        initDatabase();
    }

    private void initDatabase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mRecyclerViewChat = findViewById(R.id.recycler_view_chat);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerViewChat.setLayoutManager(linearLayoutManager);
        final ChatRecyclerAdapter chatAdapter = new ChatRecyclerAdapter(getApplicationContext(), chatList);
        initViews();

        mDatabase.child("0").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatUser chatUser = dataSnapshot.getValue(ChatUser.class);
                chatList.add(chatUser);
                mRecyclerViewChat.setAdapter(chatAdapter);
                chatAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initViews() {

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
        Log.d("Clicked","True");
        ChatUser chatUser = new ChatUser(sharedPreferences.getString("login_email",""), sharedPreferences.getString("volunteer_name",""), FirebaseInstanceId.getInstance().getToken(), " ", mETxtMessage.getText().toString());
        DatabaseReference newRef =  mDatabase.child("0").push();
        newRef.setValue(chatUser);
        /*mDatabase.child("0").setValue(chatUser);*/
    }
}
