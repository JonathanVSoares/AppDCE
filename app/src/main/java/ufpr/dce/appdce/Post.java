package ufpr.dce.appdce;

import java.util.Date;

public class Post {
    private String titulo;
    private String assunto;
    private String texto;
    private Date dataPostado;
    private String autor;
    private String[] tags;


    public Post(String titulo, String assunto, String texto, Date dataPostado, String autor, String[] tags) {
        this.titulo = titulo;
        this.assunto = assunto;
        this.texto = texto;
        this.autor = autor;
        this.dataPostado = dataPostado;
        this.tags = tags;
    }

    public Post(String titulo, String assunto, String texto, String autor, String[] tags) {
        this.titulo = titulo;
        this.assunto = assunto;
        this.texto = texto;
        this.autor = autor;
        this.dataPostado = new Date();
        this.tags = tags;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAssunto() {
        return assunto;
    }

    public String getTexto() {
        return texto;
    }

    public String getAutor() {
        return autor;
    }

    public Date getDataPostado() {
        return dataPostado;
    }

    public String[] getTags() {
        return tags;
    }
}
