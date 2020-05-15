package com.example.androidsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.security.acl.Permission;

public class MainActivity extends AppCompatActivity {

    int izinKontrol;
    private EditText editTextTelefon, editTextMesaj;
    private Button buttonGonder;
    private TextView textViewKayan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        izinKontrol = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);

        editTextMesaj = findViewById(R.id.editTextMesajGonder);
        editTextTelefon = findViewById(R.id.editTextTelefonNo);
        buttonGonder = findViewById(R.id.buttonGonder);
        textViewKayan = findViewById(R.id.textViewKayan);
        textViewKayan.setSelected(true);


        if (izinKontrol != PackageManager.PERMISSION_GRANTED) {
            // İSTENEN İZİN VERİLMEDİYSE
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);

        } else {
            //  İSTENEN İZİN VERİLDİYSE


            buttonGonder.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {
                    String gonderilecekMesaj = editTextMesaj.getText().toString();
                    String telefonNo = editTextTelefon.getText().toString();

                    // 1) UYGULAMA AÇARAK MESAJ GÖNDERME
                    /*
                    Uri uri = Uri.parse("smsto:" + telefonNo);
                    Intent mesajGonder = new Intent(Intent.ACTION_SENDTO, uri);
                    mesajGonder.putExtra("sms_body", gonderilecekMesaj);
                    startActivity(mesajGonder);
                    */


                    // 2) UYGULAMA AÇMADAN MESAJ GÖNDERME

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(telefonNo, null, gonderilecekMesaj, null, null);

                    Toast.makeText(getApplicationContext(), "Telefon no: " + telefonNo + "\n" + "Mesaj:" + gonderilecekMesaj, Toast.LENGTH_LONG).show();



                    editTextTelefon.setText("");
                    editTextMesaj.setText("");


                }
            });


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        // İZİN DİYALOĞU ÇIKTIĞINDA KARŞINA ÇIKAN ALLOW VE DENY SEÇENEKLERİNİ DİNLER. KULLANICININ CEVABINA GÖRE HAREKET EDİP İŞLEM YAPAR
        if (requestCode == 100) {
            izinKontrol = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS);

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // KULLANICI ALLOW BASTI İZİN VERİLDİ
                Toast.makeText(getApplicationContext(), "KULLANICI SMS İZNİNİ VERDİ", Toast.LENGTH_LONG).show();


            } else {
                // KULLANICI DENY BASTI İZİN VERMEDİ. KULLANICI UYGULAMAYA DEVAM EDEBİLİR FAKAT UYGULAMADA SMS ÖZELLİĞİNİ KULLANAMAZ
                textViewKayan.setText("SMS İZNİ VERİLMEDİ. UYGULAMANIZDA SMS ÖZELLİĞİ KULLANILMAYACAKTIR");
                Toast.makeText(getApplicationContext(), "İZİN VERİLMEDİ!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
