package com.itesm.healthchain.ui.settings;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.itesm.healthchain.R;

public class EmailFragment extends Fragment implements View.OnClickListener{
    private EditText mEditTextTo;
    private EditText mEditTextSubject;
    private EditText mEditTextMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_email, container, false);
        // Inflate the layout for this fragment
        mEditTextTo = root.findViewById(R.id.edit_text_to);
        mEditTextSubject = root.findViewById(R.id.edit_text_subject);
        mEditTextMessage = root.findViewById(R.id.edit_text_message);

        Button buttonSend = root.findViewById(R.id.button_send);
        buttonSend.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View view) {
        sendMail();
    }

    private void sendMail(){
        String recipientList = mEditTextTo.getText().toString();
        String[] recipients = recipientList.split(",");

        String subject = mEditTextSubject.getText().toString();
        String message = mEditTextMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Elegir aplicacion para mandar correo"));
    }
}