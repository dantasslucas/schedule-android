package br.com.santiago.agenda.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.santiago.agenda.ListaAlunosActivity;
import br.com.santiago.agenda.modelo.Aluno;

/**
 * Created by lucas-santiago on 25/02/18.
 */

public class AlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Context contex;

    public AlunosAdapter(Context context, List<Aluno> alunos ) {
        this.contex = context;
        this.alunos = alunos;
    }

    @Override
    //Quantidades de itens do adapter
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int i) {
        return alunos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return alunos.get(i).getId();
    }

    @Override
    //Recebe os itens do adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = new TextView(contex);
        Aluno aluno = alunos.get(i);
        textView.setText(aluno.toString());
        return textView;
    }
}
