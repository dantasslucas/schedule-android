package br.com.santiago.agenda.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.santiago.agenda.WebClient;
import br.com.santiago.agenda.converter.AlunoConverter;
import br.com.santiago.agenda.dao.AlunoDAO;
import br.com.santiago.agenda.modelo.Aluno;

/**
 * Created by lucas-santiago on 27/02/18.
 */

public class EnviarAlunosTask extends AsyncTask<Void,Void,String>{
    private ProgressDialog dialog;

    public EnviarAlunosTask(Context context) {
        this.context = context;
    }

    private Context context;

    public EnviarAlunosTask() {

    }

    @Override
    protected void onPreExecute() {
         dialog = ProgressDialog.show(context,"Aguarde","Enviando alunos ...",
                true,true);
    }

    @Override
    protected String doInBackground(Void... objects) {
        AlunoDAO dao =new AlunoDAO(context);
        List<Aluno> alunos= dao.buscaAlunos();
        dao.close();
        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converterParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context,  resposta,Toast.LENGTH_LONG).show();
        super.onPostExecute(resposta);
    }
}
