package com.juliovaz.condio.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.juliovaz.condio.R;
import com.juliovaz.condio.model.BuildingMessage;
import com.juliovaz.condio.network.ApiMethodsManager;
import com.juliovaz.condio.network.ApiService;
import com.juliovaz.condio.util.ConstantsCondio;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class NewMessageActivity extends AppCompatActivity {

    private int userId;
    private String messageDescription;
    private EditText etMessageDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etMessageDescription = (EditText) findViewById(R.id.et_message_text);
    }

    private void createNewMessage() {
        ApiService service = ApiMethodsManager.getMethodGetService();

        JsonObject buildingMessageChild = new JsonObject();
        JsonObject buildingMessage = new JsonObject();

        buildingMessageChild.addProperty(ConstantsCondio.TAG_BUILDING_MESSAGE_USER_ID, this.userId);
        buildingMessageChild.addProperty(ConstantsCondio.TAG_BUILDING_MESSAGE_DESCRIPTION, this.messageDescription);
        buildingMessage.add(ConstantsCondio.TAG_BUILDING_MESSAGE, buildingMessageChild);


        service.createBuildingMessage(buildingMessage, new Callback<BuildingMessage>() {

            @Override
            public void success(BuildingMessage buildingMessage, Response response) {
                if (response.getStatus() == 201) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Toast.makeText(getApplicationContext(), "Mensagem enviada aos condôminos", Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println("Não foi possível enviar a mensagem! :( ");
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
