package ufpr.dce.appdce;

import java.util.Date;

public class PostRef extends Post {
    private Post postReferenciado;

    public PostRef(String titulo, String assunto, String texto, Date dataPostado, String autor, String[] tags, Post postReferencia) {
        super(titulo, assunto, texto, dataPostado, autor, tags);
        this.postReferenciado = postReferencia;
    }

    public PostRef(String titulo, String assunto, String texto, String autor, String[] tags, Post postReferencia) {
        super(titulo, assunto, texto, autor, tags);
        this.postReferenciado = postReferencia;
    }

    public Post getPostReferenciado() {
        return postReferenciado;
    }
}
