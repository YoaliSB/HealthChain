package com.itesm.healthchain.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

/*
    USAGE
        Encoder encoder = Encoder.getEncoder();
        String string = encoder.toString( new SomeClass() );
        SomeClass some = ( SomeClass ) encoder.fromString( string );
 */

@RequiresApi(api = Build.VERSION_CODES.O)
public class Encoder {

    private static Encoder encoder = null;

    public static Encoder getEncoder(){
        if(encoder == null){
            encoder = new Encoder();
        }
        return encoder;
    }

    public Object fromString( String s ) throws IOException ,
            ClassNotFoundException {
        byte [] data = Base64.getDecoder().decode( s );
        ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    public String toString( Serializable o ) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream( baos );
        oos.writeObject( o );
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

}
