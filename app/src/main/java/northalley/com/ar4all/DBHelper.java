package northalley.com.ar4all;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

 class DBHelper extends SQLiteOpenHelper{

    protected static final String ROW_ID="id";
    protected static final String COLUMN_USER_NAME="username";
    protected static final String COLUMN_PHONE_NUMBER="phonenumber";
    protected static final String COLUMN_EMAIL="email";
    protected static final String COLUMN_PASSWORD="password";
    protected static final String COLUMN_RE_PASSWORD="repassword";
    protected  static final String DATABASE_NAME="targert.db";
    protected static final String TABLE_NAME="userdet";
    private static final int DATABASE_VERSION = 2;



    private static final String TABLE_CREATE="CREATE TABLE "+TABLE_NAME+"("+ROW_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                             COLUMN_USER_NAME+" TEXT NOT NULL,"+COLUMN_PHONE_NUMBER+" TEXT NOT NULL,"+COLUMN_EMAIL+
                                             " TEXT NOT NULL,"+COLUMN_PASSWORD+" TEXT NOT NULL,"+COLUMN_RE_PASSWORD+" TEXT NOT NULL"+");";

    protected DBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db)
    {
       try{
           db.execSQL(TABLE_CREATE);
       } catch(SQLException ex){ex.printStackTrace();}
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
       db.execSQL("DROP IF TABLE EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db,oldVersion,newVersion);
    }
    public void open()
    {
        getWritableDatabase();
    }



}