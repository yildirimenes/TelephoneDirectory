package com.project.telephonedirectory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    private RecyclerView rec;
    private FloatingActionButton btn_floating;
    private ArrayList<Persons> personsArrayList;
    private PersonsAdapter adapter;
    private Database db;
    //private Persons persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        rec = findViewById(R.id.rec);
        btn_floating = findViewById(R.id.btn_floating);

        db = new Database(this);


        toolbar.setTitle("My Phone Directory");
        setSupportActionBar(toolbar);

        rec.setHasFixedSize(true);
        rec.setLayoutManager(new LinearLayoutManager(this));

        personsArrayList = new PersonsDao().allPersons(db);

        adapter = new PersonsAdapter(this,personsArrayList,db);
        rec.setAdapter(adapter);

        btn_floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertShow();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        personsArrayList = new PersonsDao().personSearch(db,query);
        adapter = new PersonsAdapter(this,personsArrayList,db);
        rec.setAdapter(adapter);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        personsArrayList = new PersonsDao().personSearch(db,newText);
        adapter = new PersonsAdapter(this,personsArrayList,db);
        rec.setAdapter(adapter);
        return false;
    }


    private void alertShow() {
        LayoutInflater layout = LayoutInflater.from(this);
        View design = layout.inflate(R.layout.alert_design,null);

        final EditText editTextName = design.findViewById(R.id.editTextName);
        final EditText editTextPhoneNumber = design.findViewById(R.id.editTextPhoneNumber);
        final EditText editTextEmail = design.findViewById(R.id.editTextEmail);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Kişi Ekle");
        builder.setView(design);

        builder.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String person_name = editTextName.getText().toString().trim();
                String person_phoneNumber = editTextPhoneNumber.getText().toString().trim();
                String person_email = editTextEmail.getText().toString().trim();

                new PersonsDao().personAdd(db,person_name,person_phoneNumber,person_email);

                personsArrayList = new PersonsDao().allPersons(db);
                adapter = new PersonsAdapter(MainActivity.this,personsArrayList,db);
                rec.setAdapter(adapter);

            }
        });

        builder.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();

    }

}