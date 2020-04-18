package com.itesm.healthchain.nfc;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

class NfcReader {

    // Total de campos que debe contener el tag
    private final int tagLength = 12;

    NfcReader() {
    }

    // LECTURA DE TAGS
    TagProfile readFromIntent(Intent intent) throws NotNfcActionException, EmptyTagException, ImproperTagException{
        try {
            String action = intent.getAction();
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                    || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
                Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
                NdefMessage[] msgs = null;
                if (rawMsgs != null) {
                    msgs = new NdefMessage[rawMsgs.length];
                    for (int i = 0; i < rawMsgs.length; i++) {
                        msgs[i] = (NdefMessage) rawMsgs[i];
                    }
                    return buildProfile(msgs);
                } else throw new EmptyTagException();
            } else throw new NotNfcActionException();
        } catch (Exception ignored) {
            return new TagProfile();
        }
    }

    private TagProfile buildProfile(NdefMessage[] msgs) throws EmptyTagException, ImproperTagException{
        try {
            if (msgs == null || msgs.length == 0) throw new EmptyTagException();
            String[] records = new String[msgs[0].getRecords().length];
//        String tagId = new String(msgs[0].getRecords()[0].getType());
            if(records.length == tagLength) {
                for (int i = 0; i < records.length; i++) {
                    records[i] = buildText(msgs[0].getRecords()[i].getPayload());
                }
                return new TagProfile(records);
            } else throw new ImproperTagException();
        } catch (ImproperTagException e) {
            // Toast for improper tag
            return new TagProfile();
        } catch (EmptyTagException e) {
            return new TagProfile();
        }
    }

    private String buildText(byte[] payload){
        String text = "";
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16"; // Get the Text Encoding
        int languageCodeLength = payload[0] & 0063; // Get the Language Code, e.g. "en"
        // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");

        try {
            // Get the Text
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("UnsupportedEncoding", e.toString());
        }

        return text;
    }




    // ESCRITURA DE TAGS
    void writeToTag(TagProfile profile, Tag tag) throws IOException, FormatException {
        NdefRecord[] records = {
                createRecord(profile.id),
                createRecord(profile.name),
                createRecord(profile.birthDate),
                createRecord(profile.bloodType),
                createRecord(profile.weight),
                createRecord(profile.height),
                createRecord(profile.hospital),
                createRecord(profile.ailments),
                createRecord(profile.allergies),
                createRecord(profile.contactName),
                createRecord(profile.contactPhone),
                createRecord(profile.contactRelationship)
        };
        NdefMessage message = new NdefMessage(records);
        // Get an instance of Ndef for the tag.
        Ndef ndef = Ndef.get(tag);
        // Enable I/O
        ndef.connect();
        // Write the message
        ndef.writeNdefMessage(message);
        // Close the connection
        ndef.close();
    }

    private NdefRecord createRecord(String text) throws UnsupportedEncodingException {
        String lang       = "en";
        byte[] textBytes  = text.getBytes();
        byte[] langBytes  = lang.getBytes(StandardCharsets.US_ASCII);
        int    langLength = langBytes.length;
        int    textLength = textBytes.length;
        byte[] payload    = new byte[1 + langLength + textLength];

        // set status byte (see NDEF spec for actual bits)
        payload[0] = (byte) langLength;

        // copy langbytes and textbytes into payload
        System.arraycopy(langBytes, 0, payload, 1,              langLength);
        System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

        return new NdefRecord(NdefRecord.TNF_WELL_KNOWN,  NdefRecord.RTD_TEXT,  new byte[0], payload);
    }




    private static class NotNfcActionException extends RuntimeException {
        NotNfcActionException() {}
    }

    private static class EmptyTagException extends RuntimeException {
        EmptyTagException() {}
    }

    private static class ImproperTagException extends RuntimeException {
        ImproperTagException() {
            System.out.println("Tag content does not match application requirements.");
        }
    }

}
