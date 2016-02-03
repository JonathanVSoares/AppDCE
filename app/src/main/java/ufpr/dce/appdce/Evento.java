package ufpr.dce.appdce;

import java.util.Date;

public class Evento extends Post {
    private Date dataInicio;
    private Date dataFim;

    public Evento(String titulo, String assunto, String texto, Date dataPostado, String autor, String[] tags, Date dataInicio) {
        super(titulo, assunto, texto, dataPostado, autor, tags);
        this.dataInicio = dataInicio;
    }

    public Evento(String titulo, String assunto, String texto, Date dataPostado, String autor, String[] tags, Date dataInicio, Date dataFim) {
        super(titulo, assunto, texto, dataPostado, autor, tags);
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Evento(String titulo, String assunto, String texto, String autor, String[] tags, Date dataInicio) {
        super(titulo, assunto, texto, autor, tags);
        this.dataInicio = dataInicio;
    }

    public Evento(String titulo, String assunto, String texto, String autor, String[] tags, Date dataInicio, Date dataFim) {
        super(titulo, assunto, texto, autor, tags);
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }
}
