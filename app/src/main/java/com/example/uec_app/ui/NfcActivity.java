package com.example.uec_app.ui;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.widget.TextView;

import com.example.uec_app.R;

public class NfcActivity extends Activity {

    NfcAdapter nfcAdapter;
    TextView promt;
    byte a[]={(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc);
        promt = (TextView) findViewById(R.id.promt);
        // ��ȡĬ�ϵ�NFC������
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            promt.setText("�豸��֧��NFC��");
            finish();
            return;
        }
        if (!nfcAdapter.isEnabled()) {
            promt.setText("����ϵͳ������������NFC���ܣ�");
            finish();
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //�õ��Ƿ��⵽ACTION_TECH_DISCOVERED����
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
            //�����intent
            processIntent(getIntent());
        }
    }
    //�ַ�����ת��Ϊ16�����ַ���
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            //System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    private void processIntent(Intent intent) {
        //ȡ����װ��intent�е�TAG
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        for (String tech : tagFromIntent.getTechList()) {
            //  System.out.println(tech);
        }
        boolean auth = false;
        //��ȡTAG
        MifareClassic mfc = MifareClassic.get(tagFromIntent);
        try {
            String metaInfo = "";
            //Enable I/O operations to the tag from this TagTechnology object.
            mfc.connect();
            int type = mfc.getType();//��ȡTAG������
            int sectorCount = mfc.getSectorCount();//��ȡTAG�а�����������
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;
            }
            metaInfo += "��Ƭ���ͣ�" + typeS + "\n��" + sectorCount + "������\n��"
                    + mfc.getBlockCount() + "����\n�洢�ռ�: " + mfc.getSize() + "B\n";
            for (int j = 0; j < sectorCount; j++) {
                //Authenticate a sector with key A.
                // auth = mfc.authenticateSectorWithKeyA(j, MifareClassic.KEY_DEFAULT);
                auth = mfc.authenticateSectorWithKeyA(j, a);
                // System.out.println("xxh+"+bytesToHexString(MifareClassic.KEY_DEFAULT));
                int bCount;
                int bIndex;
                if (auth) {
                    metaInfo += "Sector " + j + ":��֤�ɹ�\n";
                    // ��ȡ�����еĿ�
                    bCount = mfc.getBlockCountInSector(j);
                    bIndex = mfc.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {
                        byte[] data = mfc.readBlock(bIndex);
                        metaInfo += "Block " + bIndex + " : "
                                + bytesToHexString(data) + "\n";
                        bIndex++;
                    }
                } else {
                    metaInfo += "Sector " + j + ":��֤ʧ��\n";
                }
            }
            promt.setText(metaInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
