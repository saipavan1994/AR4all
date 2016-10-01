package northalley.com.ar4all;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserLoginReg extends FragmentActivity{

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
        /*Fragment is loaded into the container*/
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
        LoginFragmnet login = new LoginFragmnet();
        login.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.user_reg_login,login).commit();
    }

}
