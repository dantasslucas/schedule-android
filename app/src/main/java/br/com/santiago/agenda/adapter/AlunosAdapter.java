package br.com.santiago.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.santiago.agenda.ListaAlunosActivity;
import br.com.santiago.agenda.R;
import br.com.santiago.agenda.modelo.Aluno;

/**
 * Created by lucas-santiago on 25/02/18.
 */

public class AlunosAdapter extends BaseAdapter {
    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos ) {
        this.context = context;
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
    public View getView(int i, View convetView, ViewGroup viewGroup) {
        Aluno aluno = alunos.get(i);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convetView;
        if (view ==null) {
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }
        TextView campoNome =(TextView) view.findViewById(R.id.item_nome);
        TextView campoTelefone =(TextView) view.findViewById(R.id.item_telefone);
        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());

        String caminhoFoto =  aluno.getCaminhoFoto();
        if (caminhoFoto != null){
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap,100,100,true);
            campoFoto.setImageBitmap(bitmapReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
