package com.example.vitorpereira.livrariafirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapterLivro extends ArrayAdapter<Livro> {

    private final Context context;
    private final List<Livro> elementos;

    FirebaseStorage storage;
    StorageReference storageReference;

    public ArrayAdapterLivro(Context context, List<Livro> elementos){
        super(context,R.layout.list_home,elementos);
        this.context = context;
        this.elementos = elementos;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_home, parent, false);

        ImageView imagem = (ImageView) rowView.findViewById(R.id.imageHome);
        TextView titulo = (TextView) rowView.findViewById(R.id.tituloHome);
        TextView autor = (TextView) rowView.findViewById(R.id.autorHome);
        TextView editora = (TextView) rowView.findViewById(R.id.editorahome);


        Livro livro = new Livro();


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

//        StorageReference gsReference = storage.getReferenceFromUrl("gs://livraria-40419.appspot.com/images/"+ livro.getUid());

//        Picasso.with(context).load(storageReference.child("imagens"+ livro.getUid())).into(imagem);

//        Glide.with(getContext()).load(gsReference).into(imagem);
//        Glide.with(getContext()).load(storageReference.getDownloadUrl().toString()).into(imagem);
//        Glide.with(getContext()).load(storageReference.child("imagens/"+ livro.getUid())).into(imagem);
        imagem.setImageResource(elementos.get(position).getImagem());
        titulo.setText(elementos.get(position).getTitulo());
        autor.setText(elementos.get(position).getAutor());
        editora.setText(elementos.get(position).getEditora());

        return rowView;
    }
}
