package fisei.uta.app_java_003;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class AllClientActivity extends AppCompatActivity {
    private ListView listViewData;
    ArrayList<String> list;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_client);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listViewData = findViewById(R.id.listViewData);
        //llenar los datos en ListViewer
        list = FillListView();
        //adapter = new ArrayAdapter(this,android.R.)
        //pasar los datos
        listViewData.setAdapter(adapter);
    }
    private ArrayList<String> FillListView() {
        //crear la base
        DataBaseAdmin dataBaseAdmin = new DataBaseAdmin(
                this, "DB_CLIENTS1",null,1);
        //abrir la base
        SQLiteDatabase sql = dataBaseAdmin.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String>();
        String select = "SELECT Code ,Name, LastName, Email , Age " +
                " FROM Clients";
        Cursor cursor = sql.rawQuery(select,null);
        if(cursor.moveToNext()){
            while (cursor.moveToNext()){
                arrayList.add(cursor.getString(0) + " " + cursor.getString(1));
            }
        }
        return arrayList;
    }
}