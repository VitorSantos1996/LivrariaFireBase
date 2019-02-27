package com.example.vitorpereira.livrariafirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {


    DatabaseReference dataBaseReference;

    StorageReference storageReference;


    private List<Livro> listaLivro = new ArrayList<Livro>();
    ArrayAdapter<Livro> ArrayAdapterLivro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        inicializeFirebase();

        listaDados();
    }

    private void listaDados() {
        dataBaseReference.child("Livros").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaLivro.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    Livro livro = dataSnapshot1.getValue(Livro.class);
                    listaLivro.add(livro);
                }

                ListView lista = (ListView) findViewById(R.id.lvMain);

                ArrayAdapterLivro adapter = new ArrayAdapterLivro(MainActivity.this, listaLivro);
                lista.setAdapter(adapter);;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void inicializeFirebase() {
        FirebaseApp.initializeApp(this);
        dataBaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == R.id.icon_add){
            novoCadastro();
        }
        return true;
    }

    private void novoCadastro() {
        Intent intent = new Intent(this,CadastrarProduto.class);
        startActivity(intent);
    }

}
