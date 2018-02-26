package br.com.santiago.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.santiago.agenda.R;
import br.com.santiago.agenda.dao.AlunoDAO;
import br.com.santiago.agenda.modelo.Aluno;

/**
 * Created by lucas-santiago on 26/02/18.
 */

public class SMSReceiver extends BroadcastReceiver{
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");
        SmsMessage sms =SmsMessage.createFromPdu(pdu,formato);
        String telefone = sms.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);
        if (dao.ehAluno(telefone)){
        Toast.makeText(context,"Chegou SMS de aluno",Toast.LENGTH_SHORT).show();
        MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
        mp.start();
        }
        dao.close();
    }
}
