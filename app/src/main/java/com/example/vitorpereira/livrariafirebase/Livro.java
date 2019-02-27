package com.example.vitorpereira.livrariafirebase;

public class Livro {

    private String uid;
    private int imagem;
    private String urlImagem;
    private String titulo;
    private String autor;
    private String editora;

    public Livro() {
    }

    @Override
    public String toString() {
        return "Livro{" +
                "uid='" + uid + '\'' +
                ", imagem=" + imagem +
                ", urlImagem='" + urlImagem + '\'' +
                ", titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", editora='" + editora + '\'' +
                '}';
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public int getImagem() { return imagem; }

    public void setImagem(int imagem) { this.imagem = imagem; }

    public String getUid() { return uid; }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }
}
