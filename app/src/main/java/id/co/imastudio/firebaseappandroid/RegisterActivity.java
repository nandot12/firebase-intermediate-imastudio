package id.co.imastudio.firebaseappandroid;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.registerEmail)
    EditText registerEmail;
    @BindView(R.id.registerPassword)
    EditText registerPassword;
    @BindView(R.id.registerPasswordConfirm)
    EditText registerPasswordConfirm;
    @BindView(R.id.btnregister)
    Button btnregister;
    @BindView(R.id.btnlogin)
    Button btnlogin;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    //1
    private FirebaseAuth mAuth;

    Boolean status ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        //2
        mAuth = FirebaseAuth.getInstance();

    }

    @OnClick({R.id.btnregister, R.id.btnlogin, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnregister:

                status = false ;

                registerPasswordConfirm.setVisibility(View.VISIBLE);

                break;
            case R.id.btnlogin:

                status = true ;
                registerPasswordConfirm.setVisibility(View.GONE);
                break;
            case R.id.btnSubmit:
                //TODO

                //3


                if(status == false){

                    signUp();
                }
                else{

                    login();
                }
                break;
        }
    }

    private void login() {
    }

    private void signUp() {

        String email  = registerEmail.getText().toString() ;
        String password = registerPassword.getText().toString() ;
        String confirm = registerPasswordConfirm.getText().toString();


        //kita pastikan user ngisi password lebih 6 karakter
        if(registerPassword.getText().toString().length() < 6 ){

            Toast.makeText(this, "pokoknya lebih dari 6 ",
                    Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(confirm)){

            Toast.makeText(this, "not match", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                               // Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                registerPasswordConfirm.setVisibility(View.GONE);
                                status = true;

                               // updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                              //  Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                              //  updateUI(null);
                            }

                            // ...
                        }
                    });

        }
    }
}
