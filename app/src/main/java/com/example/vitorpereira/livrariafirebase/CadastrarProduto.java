package com.example.vitorpereira.livrariafirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class CadastrarProduto extends AppCompatActivity {

    private Button btnCadastrar;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private ImageButton btnImageButton;

    EditText tituloP, autorP, editoraP;

    FirebaseStorage storage;
    StorageReference storageReference;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dataBaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto);

        Button btnCadastrar = (Button)findViewById(R.id.cpBtnCadastrar);
        EditText cpTitulo = (EditText)findViewById(R.id.cpTitulo);
        EditText cpAutor = (EditText)findViewById(R.id.cpAutor);
        EditText cpEditora = (EditText)findViewById(R.id.cpEditora);
        btnImageButton = (ImageButton) findViewById(R.id.btnImageButton);

        cpTitulo.setOnClickListener(onClickView);
        cpAutor.setOnClickListener(onClickView);
        cpEditora.setOnClickListener(onClickView);
        btnCadastrar.setOnClickListener(onClickView);

        btnImageButton.setOnClickListener(onClickView);

        inicializeFirebase();
    }

    private void inicializeFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        dataBaseReference = firebaseDatabase.getReference();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    private View.OnClickListener onClickView = new View.OnClickListener() {

        public void onClick(View view){

            tituloP = findViewById(R.id.cpTitulo);
            autorP = findViewById(R.id.cpAutor);
            editoraP = findViewById(R.id.cpEditora);

            String titulo = tituloP.getText().toString();
            String autor = autorP.getText().toString();
            String editora = editoraP.getText().toString();

            switch (view.getId()) {
                case R.id.cpBtnCadastrar: {
                    if (titulo.equals("") || autor.equals("") || editora.equals("")) {
                        valida();
                    } else {
                        Livro livro = new Livro();

                        livro.setUid(UUID.randomUUID().toString());
                        livro.setTitulo(titulo);
                        livro.setAutor(autor);
                        livro.setEditora(editora);

                        dataBaseReference.child("Livros").child(livro.getUid()).setValue(livro);

                        uploadImage();

                        Toast.makeText(getApplicationContext(), "Adicionado com Sucesso", Toast.LENGTH_SHORT).show();

                        limpaCampos();
                    }
                    break;
                }
                case R.id.btnImageButton:{

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                btnImageButton.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage(){
        if(filePath != null)
        {
            final ProgressBar simpleProgressBar=(ProgressBar) findViewById(R.id.progressBar);

            StorageReference ref = storageReference.child("imagens/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            simpleProgressBar.setVisibility(View.GONE);
                            Toast.makeText(CadastrarProduto.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            simpleProgressBar.setVisibility(View.GONE);
                            Toast.makeText(CadastrarProduto.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            simpleProgressBar.setVisibility(View.VISIBLE);
                        }
                    });
        }

    }

    private void limpaCampos() {
        tituloP.setText("");
        autorP.setText("");
        editoraP.setText("");
    }

    private void valida() {

        String titulo = tituloP.getText().toString();
        String autor = autorP.getText().toString();
        String editora = editoraP.getText().toString();

        if (titulo.equals("")) {
            tituloP.setError("Campo requerido");
        }
        if (autor.equals("")) {
            autorP.setError("Campo requerido");
        }
        if (editora.equals("")) {
            editoraP.setError("Campo requerido");
        }
    }

}
