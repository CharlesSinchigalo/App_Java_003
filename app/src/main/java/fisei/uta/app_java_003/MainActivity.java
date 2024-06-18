package fisei.uta.app_java_003;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextID;
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextID = findViewById(R.id.editTextID);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAge = findViewById(R.id.editTextAge);
    }


    public void onclickInsert(View view) {
        //crear la base
        DataBaseAdmin dataBaseAdmin = new DataBaseAdmin(
                this, "DB_CLIENTS1",null,1);
        //abrir la base
        SQLiteDatabase sql = dataBaseAdmin.getWritableDatabase();
        //INSERTAR UN REGISTRO EN LA BASE DE DATOS
        //String code = editTextID.getText().toString();
        String name = editTextName.getText().toString();
        String lastname = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        String age = editTextAge.getText().toString();

        //Primera forma no recomendada
        //sql.execSQL("INSERT INTO CLients (Name,LastName,Email,Age)" +
          //      "VALUES ("+name+","+lastname+", "+email+","+age+")");
        ContentValues values = new ContentValues();
        values.put("Name",name);
        values.put("LastName",lastname);
        values.put("Email",email);
        values.put("Age",age);
        //enviar
        sql.insert("Clients",null,values);
         //cerrar la base de datos
        sql.close();
        Toast.makeText(this,"Cliente Creado",
                Toast.LENGTH_SHORT).show();
        cleanControls();
    }

    private void cleanControls() {
        //Limpiar los controles
        editTextID.setText("");
        editTextName.setText("");
        editTextLastName.setText("");
        editTextEmail.setText("");
        editTextAge.setText("");
    }
    public void onclickSearch(View view) {
        //crear la base
        DataBaseAdmin dataBaseAdmin = new DataBaseAdmin(
                this, "DB_CLIENTS1",null,1);
        SQLiteDatabase sql = dataBaseAdmin.getReadableDatabase();
        String code = editTextID.getText().toString();
        //String select = "SELECT Name , LastName , Email , Age FROM Clients WHERE Code =";
        Cursor cursor = sql.rawQuery("SELECT Name , LastName , Email , Age " +
                "FROM Clients WHERE Code =" + code,null);
        //mover el cursor
        if (cursor.moveToFirst()){
            editTextName.setText(cursor.getString(0));
            editTextLastName.setText(cursor.getString(1));
            editTextEmail.setText(cursor.getString(2));
            editTextAge.setText(cursor.getString(3));
            //Toast.makeText(this,"ingresa", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,
                    "No existe el cliente indicado",Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        sql.close();
    }
    public void onclickDelete(View view) {
        //crear la base
        DataBaseAdmin dataBaseAdmin = new DataBaseAdmin(
                this, "DB_CLIENTS1",null,1);

        //abrir la base
        SQLiteDatabase sql = dataBaseAdmin.getWritableDatabase();

        //realizar
        String code = editTextID.getText().toString();

        // metoso borrar
        int count= sql.delete("Clients","Code=" + code, null);

        //cerrar la base
        sql.close();

        if (count==1){
            cleanControls();
            Toast.makeText(this,"Registro eliminado",
                    Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this,"No se encontro el Registro",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onclickUpdate(View view){
        //crear la base
        DataBaseAdmin dataBaseAdmin = new DataBaseAdmin(
                this, "DB_CLIENTS1",null,1);
        //abrir la base
        SQLiteDatabase sql = dataBaseAdmin.getWritableDatabase();
        //Actualizar un registro de la DB
        String code = editTextID.getText().toString();
        String name = editTextName.getText().toString();
        String lastname = editTextLastName.getText().toString();
        String email = editTextEmail.getText().toString();
        String age = editTextAge.getText().toString();
        ContentValues values = new ContentValues();
        values.put("Name",name);
        values.put("LastName",lastname);
        values.put("Email",email);
        values.put("Age",age);
        int count = sql.update("Clients",values,
                "Code=" + code,null);
        //cerrar la base
        sql.close();
        if (count==1){
            cleanControls();
            Toast.makeText(this,"Registro actualizado",
                    Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this,"No se encontro el Registro",
                    Toast.LENGTH_SHORT).show();
        }
        cleanControls();
    }

    public void onClickSelectAll(View view){
        Intent intent = new Intent(this,AllClientActivity.class);
        startActivity(intent);
    }


}