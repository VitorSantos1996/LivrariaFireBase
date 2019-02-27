package com.example.vitorpereira.livrariafirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ArrayAdapterLivro extends ArrayAdapter<Livro> {

    private final Context context;
    private final List<Livro> elementos;

    private StorageReference storageReference;

    public ArrayAdapterLivro(Context context, List<Livro> elementos){
        super(context,R.layout.list_home,elementos);
        this.context = context;
        this.elementos = elementos;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_home, parent, false);

        Livro livro = new Livro();

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageStorage = storageReference.child("imagens/"+ elementos.get(position).getUid());
        ImageView imagem = (ImageView) rowView.findViewById(R.id.imageHome);
        TextView titulo = (TextView) rowView.findViewById(R.id.tituloHome);
        TextView autor = (TextView) rowView.findViewById(R.id.autorHome);
        TextView editora = (TextView) rowView.findViewById(R.id.editorahome);


        GlideApp.with(getContext()).load(imageStorage).into(imagem);

        imagem.setImageResource(elementos.get(position).getImagem());
        titulo.setText(elementos.get(position).getTitulo());
        autor.setText(elementos.get(position).getAutor());
        editora.setText(elementos.get(position).getEditora());

        return rowView;
    }
}
