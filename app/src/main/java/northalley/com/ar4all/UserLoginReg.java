package northalley.com.ar4all;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserLoginReg extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*First we are setting empty container*/
        setContentView(R.layout.activity_user_login_reg);
        /*we are creating instance of the login fragment*
        and it is always loaded when Activity is started/
         */
        LoginFragmnet login = new LoginFragmnet();
        login.setArguments(getIntent().getExtras());
       // Fragment is loaded into the container
        getSupportFragmentManager().beginTransaction().add(R.id.user_reg_login,login).commit();
      final EditText  pwd = (EditText)findViewById(R.id.password_text);
      final EditText  eml =(EditText)findViewById(R.id.emailid_text);

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
        if(username.isEmpty()||email.isEmpty()||password.isEmpty()||reenter_password.isEmpty()||phone.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"Please Make Sure You Have Entered All Fields",Toast.LENGTH_SHORT).show();
        }

        else {
            LoginFragmnet login = new LoginFragmnet();
            login.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.user_reg_login, login).commit();
        }
    }
    public void goToMain(View v)
    {
        Intent intent = new Intent(this,DrawerActivity.class);
        startActivity(intent);
    }

}
