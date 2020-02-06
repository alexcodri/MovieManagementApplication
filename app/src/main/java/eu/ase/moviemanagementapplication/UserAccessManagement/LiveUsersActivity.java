package eu.ase.moviemanagementapplication.UserAccessManagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import eu.ase.moviemanagementapplication.Model.UserInfo;
import eu.ase.moviemanagementapplication.R;

public class LiveUsersActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ListView userList;
    private ArrayAdapter<String> userAdapter;
    private ArrayList<String> uArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_users);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        userList = findViewById(R.id.userList);
        userAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,uArrayList);
        userList.setAdapter(userAdapter);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue().toString();
                uArrayList.add(value);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
