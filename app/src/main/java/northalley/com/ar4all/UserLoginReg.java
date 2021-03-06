package northalley.com.ar4all;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;



public class UserLoginReg extends FragmentActivity {

    private String pass;
    private int yes;
    DBHelper db = new DBHelper(this);
    UserData ud = new UserData(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*First we are setting empty container*/
        setContentView(R.layout.activity_user_login_reg);
        /*we are creating instance of the login fragment*
        and it is always loaded when Activity is started/
         */
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        LoginFragmnet login = new LoginFragmnet();
        login.setArguments(getIntent().getExtras());
       // Fragment is loaded into the container
        getSupportFragmentManager().beginTransaction().add(R.id.user_reg_login,login).commit();



    }
    public void goToReg(View v)
    {
      RegisterFragment register = new RegisterFragment();
        register.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.user_reg_login,register).addToBackStack(null).commit();


    }
    public  void goToLog(View v)
    {
        final EditText usrnm = (EditText)findViewById(R.id.username_text);
        final EditText eml_reg =(EditText)findViewById(R.id.email_id_text);
        final EditText pwd_reg=(EditText)findViewById(R.id.password_reg_text);
        final EditText re_pwd_reg=(EditText)findViewById(R.id.renetr_password_text);
        final EditText ph_num=(EditText)findViewById(R.id.phone_number_text);
        String username= usrnm.getText().toString();
        String email =eml_reg.getText().toString();
        String password=pwd_reg.getText().toString();
        String reenter_password=re_pwd_reg.getText().toString();
        String phone= ph_num.getText().toString();
        String[] param = {username,email,phone,password};
        if(username.isEmpty()||email.isEmpty()||password.isEmpty()||reenter_password.isEmpty()||phone.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Make Sure You Have Entered All Fields",Toast.LENGTH_SHORT).show();
        }
       else if(!password.equals(reenter_password))
        {
            Toast.makeText(getApplicationContext(),"The passwords you entered doesnot match",Toast.LENGTH_SHORT).show();
        }
        else if(!isValidEmail(email))
        {
            Toast.makeText(getApplicationContext(),"Please enter a valid email ID",Toast.LENGTH_LONG).show();
        }
        else {
            //addToDb(username,email,password,reenter_password,phone);
            /*ud.startConnection();
            ud.insertTODb(username,email,phone,password);
            ud.closeConnection();*/
            MyAsyncTaskReg myar = new MyAsyncTaskReg();
            myar.execute(param);
        }
    }
    public void goToMain(View v)
    {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
       final EditText pwd = (EditText)findViewById(R.id.password_text);
       final EditText eml =(EditText)findViewById(R.id.emailid_text);
       String paswd = pwd.getText().toString();
       String emal = eml.getText().toString();
        String[] param = {emal,paswd};
        if(checkConnection()) {
            if (paswd.isEmpty() || emal.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill in the details", Toast.LENGTH_SHORT).show();
            } else {
               /* ud.startConnection();
                yes = ud.fromDb(emal, paswd);
                ud.closeConnection();*/
                MyAsyncTask mya = new MyAsyncTask();
                mya.execute(param);

            }
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Please Make Sure You Are Connected to the Internet",Toast.LENGTH_SHORT).show();
        }

    }
   /* public void addToDb(String user_name,String email,String pass,String re_pass,String pho)
    {
        SQLiteDatabase dB = db.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put("username",user_name);
        val.put("phonenumber",pho);
        val.put("email",email);
        val.put("password",pass);
        val.put("repassword",re_pass);
        try {
            dB.insert(DBHelper.TABLE_NAME,null,val);
            Toast.makeText(getApplicationContext(), "you are successfully registered", Toast.LENGTH_SHORT).show();
        }catch (SQLException ex){ex.printStackTrace();}
    }*/
    /*public  boolean readFromDb(String em, String ps)
    {
        SQLiteDatabase dB = db.getReadableDatabase();
        String[] projection = {DBHelper.COLUMN_PASSWORD};
        String selection = DBHelper.COLUMN_EMAIL+" = ?";
        String[] selectionArgs ={em};
        Cursor cursor;

        try
        {
          cursor = dB.query(DBHelper.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
            cursor.moveToFirst();
            Log.d("place","In database");
            pass = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PASSWORD));
            Log.d("password",pass);
            cursor.close();
        }catch (SQLException ex){ex.printStackTrace();}
        if(pass.equals(ps))
        {
            return true;
        }
        else
        {
            return false;
        }
    }*/
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        db.close();
    }
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
    @SuppressWarnings("deprecation")
    public boolean checkConnection()
    {
        boolean connected=false;
        ConnectivityManager conn = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = conn.getAllNetworkInfo();
        for (int i = 0; i<info.length; i++){
            if (info[i].getState() == NetworkInfo.State.CONNECTED){
                connected = true;
            }

        }
        if(connected)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    private class MyAsyncTask extends AsyncTask<String,Void,Void>
    {

        @Override
        protected Void doInBackground(String... params)
        {
          ud.startConnection();
          final int y = ud.fromDb(params[0],params[1]);
            ud.closeConnection();
            runOnUiThread(new Runnable()
            {
                public void run()
                {
                    if(y==0) {
                        Toast.makeText(getApplicationContext(), "please enter a valid emailid or Register", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "please enter correct password", Toast.LENGTH_LONG).show();
                }
            });
            return null;
        }

    }
    private class MyAsyncTaskReg extends AsyncTask<String,Void,Void>
    {
        @Override
        protected Void doInBackground(String... params)
        {
            ud.startConnection();
            final int y = ud.insertTODb(params[0],params[1],params[2],params[3]);
            ud.closeConnection();
            runOnUiThread(new Runnable()
            {
                public void run()
                {
                    if(y==0)
                        Toast.makeText(getApplicationContext(), "You are successfully registered", Toast.LENGTH_SHORT).show();

                }
            });
            return null;
        }
        @Override
        protected void onPostExecute(Void param)
        {
            LoginFragmnet login = new LoginFragmnet();
            login.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.user_reg_login, login).commit();
        }
    }

}
