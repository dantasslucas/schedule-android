package br.com.santiago.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Browser;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.santiago.agenda.adapter.AlunosAdapter;
import br.com.santiago.agenda.dao.AlunoDAO;
import br.com.santiago.agenda.modelo.Aluno;

public class ListaAlunosActivity extends AppCompatActivity {


    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);


        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        //Click item lista
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long l) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent intentVaiFormulario = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                intentVaiFormulario.putExtra("aluno", aluno);
                startActivity(intentVaiFormulario);
            }
        });

        Button btnNovoAluno = (Button) findViewById(R.id.listaAlunoNovoAluno);

        btnNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent navega entre as telas
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });
        //Ativar Menu de Contexto
        registerForContextMenu(listaAlunos);
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        //Responsável em converter o Array de String para view
        AlunosAdapter adapter = new AlunosAdapter(this,alunos);
        listaAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);
        //Ligar
        MenuItem itemLigar = menu.add("Ligar");
        //Pedindo permissão user
        itemLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this,Manifest.permission.CALL_PHONE)
                        !=PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},123);
                }else{
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getTelefone()));
                    startActivity(intentLigar);
                }


                return false;
            }
        });



        //Visualizar Mapa
        MenuItem itemMapa = menu.add("Visualizar no Mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        //uri = identificador universal de recursos
        intentMapa.setData(Uri.parse("geo:0,0?q="+aluno.getEndereco()));
        itemMapa.setIntent(intentMapa);

        //Mandar SMS
        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:"+aluno.getTelefone()));
        itemSMS.setIntent(intentSMS);


        //Visitar site
        MenuItem itemSite = menu.add("Visitar Site");
        //Usando intent para o android descobrir ação a ser feita
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        String site = aluno.getSite();
        if (!site.startsWith("http://")){
            site = "http://"+site;
        }
        //Passando Url a ser chamada
        intentSite.setData(Uri.parse(site));
        itemSite.setIntent(intentSite);

        //Deletar número
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deletaAluno(aluno);
                dao.close();

                carregaLista();
                Toast.makeText(ListaAlunosActivity.this,"Aluno  "+aluno.getNome()+" deletado",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


}
