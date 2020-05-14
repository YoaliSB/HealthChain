package com.itesm.healthchain.nfc;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.FormatException;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.widget.Toast;

import com.itesm.healthchain.R;
import com.itesm.healthchain.models.TagProfile;

import androidx.annotation.CallSuper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public abstract class NfcActivity extends AppCompatActivity implements NfcWriteDialogFragment.NFCTagWriteDialogListener{

    protected Context context;
    protected NfcAdapter nfcAdapter;
    protected NfcReader nfcReader;
    public TagProfile tagProfile;
    protected PendingIntent pendingIntent;
    protected IntentFilter[] writeTagFilters;
    protected Tag myTag;
    protected DialogFragment writeFragment;
    protected boolean isWriting;
 //   protected boolean writeMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        isWriting = false;

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
            finish();
        }

        try {
            nfcReader = new NfcReader(this.context);
            tagProfile = nfcReader.readFromIntent(getIntent());
        } catch (NoSuchAlgorithmException | IOException | ClassNotFoundException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
        writeTagFilters = new IntentFilter[] { tagDetected };

        writeFragment = new NfcWriteDialogFragment();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // Assuming all intents are NFC
        super.onNewIntent(intent);
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
        if(isWriting){
            attemptWriteToTag();
            writeFragment.dismiss();
            isWriting = false;
        } else {
            setIntent(intent);
            tagProfile = nfcReader.readFromIntent(intent);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        writeModeOff();
    }

    @Override
    public void onResume(){
        super.onResume();
        writeModeOn();
    }

    public void confirmTagWrite() {
        isWriting = true;
        writeFragment.show(getSupportFragmentManager(), "writeTag");
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        onNfcWriteCancel();
    }

    protected void attemptWriteToTag(){
        try {
            if(myTag ==null) {
                onNfcWriteError();
            } else {
                nfcReader.writeToTag(tagProfile, myTag);
                onNfcWriteSuccess();
            }
        } catch (IOException | FormatException | IllegalBlockSizeException | InvalidKeyException |
                InvalidAlgorithmParameterException e) {
            onNfcWriteError();
            e.printStackTrace();
        }
    }

    protected void onNfcWriteSuccess() {
        Toast.makeText(context, getResources().getString(R.string.nfc_write_success), Toast.LENGTH_LONG ).show();
    }

    protected void onNfcWriteError() {
        Toast.makeText(context, getResources().getString(R.string.nfc_write_error), Toast.LENGTH_LONG).show();
    }

    @CallSuper
    protected void onNfcWriteCancel() {
        isWriting = false;
    }

    protected void writeModeOn(){
    //    writeMode = true;
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null);
    }

    protected void writeModeOff(){
    //    writeMode = false;
        nfcAdapter.disableForegroundDispatch(this);
    }

}