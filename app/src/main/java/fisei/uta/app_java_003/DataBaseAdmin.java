package fisei.uta.app_java_003;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseAdmin extends SQLiteOpenHelper {
    String create_table_clients = "CREATE TABLE Clients " +
            "(Code INTEGER PRIMARY KEY AUTOINCREMENT , Name TEXT, LastName TEXT, Email TEXT, Age INTEGER)";

    String drop_table_clients = "DROP TABLE IF EXISTS Clients";

    public DataBaseAdmin(@Nullable Context context, @Nullable String name,
                         @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la estructura de la base de datos
        db.execSQL(create_table_clients);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Para actualizar la estructura de la base de datos
        //eliminar a o las tablas
        db.execSQL(drop_table_clients);

        //crear la nueva estructura de la tabla o tablas
        db.execSQL(create_table_clients);
}
}