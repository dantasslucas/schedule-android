package br.com.santiago.agenda.helper;

import android.app.Activity;
import android.widget.EditText;
import android.widget.RatingBar;

import br.com.santiago.agenda.FormularioActivity;
import br.com.santiago.agenda.R;
import br.com.santiago.agenda.modelo.Aluno;

/**
 * Created by lucas-santiago on 19/02/18.
 */

public class FormularioHelper {
    private  EditText campoNome;
    private  EditText campoEndereco;
    private  EditText campoTelefone;
    private  EditText campoSite;
    private  RatingBar campoNota;
    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity){
        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
        aluno =new Aluno();
    }

    public Aluno pegaAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));
        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoNota.setProgress(aluno.getNota().intValue());
        this.aluno = aluno;
    }
}