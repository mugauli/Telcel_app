package net.grapesoft.www.telcel;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import Utitilies.AlertDialogManager;
import Utitilies.Comunication;
import Utitilies.ConnectionDetector;
import Utitilies.SessionManagement;

public class ActualizarActivity extends ActionBarActivity {

    SessionManagement session;

    public String tokenCTE = "";

    public ActualizarActivity (Context cxt) {

        tokenCTE = cxt.getString(R.string.tokenXM);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);

        Spinner spinner_Region = (Spinner) findViewById(R.id.spnRegion);

        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.region , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Region.setAdapter(spinner_adapter);

        Spinner spinner_Direccion = (Spinner) findViewById(R.id.spnDireccion);

        ArrayAdapter spinner_adapterD = ArrayAdapter.createFromResource( this, R.array.direccion , android.R.layout.simple_spinner_item);
        spinner_adapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_Direccion.setAdapter(spinner_adapterD);

    }
}
