package com.example.vizoralejelento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ProfilActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getName();

    Spinner monthsSpinner;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    private RecyclerView recyclerView;
    private ArrayList<PeriodItem> periodItemArrayList;
    private PeriodItemAdapter periodItemAdapter;

    private FirebaseFirestore firestore;
    private CollectionReference collectionReference;

    private NotificationHandler notificationHelper;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        periodItemArrayList = new ArrayList<>();

        periodItemAdapter = new PeriodItemAdapter(this, periodItemArrayList);
        recyclerView.setAdapter(periodItemAdapter);

        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("Items");

        monthsSpinner = findViewById(R.id.monthsSpinner);
        String[] monthsArray = {"Január", "Február", "Március", "Április", "Május", "Június", "Július", "Agusztus", "Szeptember", "Október", "November", "December"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, monthsArray);
        monthsSpinner.setAdapter(arrayAdapter);

        notificationHelper = new NotificationHandler(this);

        queryData();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void queryData() {
        periodItemArrayList.clear();
        collectionReference.orderBy("month").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                PeriodItem item = document.toObject(PeriodItem.class);
                periodItemArrayList.add(item);
            }
            periodItemAdapter.notifyDataSetChanged();
        });

        if (periodItemArrayList.size() == 0) {
            initializeData();
        }
    }

    private void initializeData() {
        String[] month = getResources().getStringArray(R.array.month);
        String[] quantity = getResources().getStringArray(R.array.quantity);

        for (int i = 0; i < month.length; i++) {
            periodItemArrayList.add(new PeriodItem(month[i], quantity[i]));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_button:
                firebaseAuth.signOut();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteQuantity(View view) {
        notificationHelper.send("Még nem lehet adatot törölni.");
    }

    public void sendQuantity(View view) {
        notificationHelper.send("Még nem lehet adatot feltölteni.");
    }
}