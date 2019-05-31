package com.example.booky.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booky.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class DangNhap_activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener,FirebaseAuth.AuthStateListener {
   Button btnDangNhapGoogle ;
   TextView txtDangKyMoi;
   Button btnDangNhap;
   TextView txtQuenMK;
   EditText edemail, edpassword;
   GoogleApiClient apiClient;
   public static int REQUESTCODE_DNHAP_GOOGLE =3;
    public static int KIEMTRA_PROVIDER_DANGNHAP =0;
    FirebaseAuth firebaseAuth;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("kiemtradn","aaaaaaaaa");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);
        Log.d("kiemtradn","bbb");
        Log.d("kiemtradn","signout");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        btnDangNhapGoogle = (Button) findViewById(R.id.btnDangNhapGoogle);
        txtDangKyMoi = (TextView) findViewById(R.id.txtDangKyMoi);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        edemail = (EditText) findViewById(R.id.edEmailDN);
        edpassword = (EditText) findViewById(R.id.edPasswordDN);
        txtQuenMK = (TextView) findViewById(R.id.txtQuenMatKhau);

        txtQuenMK.setOnClickListener(this);
        txtDangKyMoi.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);
        btnDangNhapGoogle.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("luudangnhap",MODE_PRIVATE);
        TaoClientDangNhapGoogle();
    }
    private void TaoClientDangNhapGoogle(){
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    private void DangNhapGoogle(GoogleApiClient apiClient){
        KIEMTRA_PROVIDER_DANGNHAP = 1;
        Intent iDNGoogle = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(iDNGoogle,REQUESTCODE_DNHAP_GOOGLE);
    }
    private void ChungThucDangNhapFireBase(String tokenID){
        if(KIEMTRA_PROVIDER_DANGNHAP == 1){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID,null);
            firebaseAuth.signInWithCredential(authCredential);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE_DNHAP_GOOGLE){
            if(resultCode == RESULT_OK){
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount account = signInResult.getSignInAccount();
                String tokenID = account.getIdToken();
                ChungThucDangNhapFireBase(tokenID);
            }
        }else{
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"Vui lòng xem lại kết nối mạng",Toast.LENGTH_SHORT).show();

    }

    private void DangNhap(){
        String email = edemail.getText().toString();
        String pass = edpassword.getText().toString();
        if(email.length() == 0 || pass.length() ==0){Toast.makeText(this,"Vui lòng nhập đầy đủ các trường!",Toast.LENGTH_SHORT).show();}
        else {
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(DangNhap_activity.this, getString(R.string.thongbaodangnhapthatbai), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch ((id)){
            case R.id.btnDangNhapGoogle:
                DangNhapGoogle(apiClient);
                break;
            case R.id.txtDangKyMoi:
                Intent iDangKy = new Intent(DangNhap_activity.this,DangKyActivity.class);
                startActivity(iDangKy);
                break;
            case  R.id.btnDangNhap:
                DangNhap();
                break;
            case R.id.txtQuenMatKhau:
                Intent iquenmk = new Intent(DangNhap_activity.this,KhoiPhucMatKhauActivity.class);
                startActivity(iquenmk);
                break;

        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null ){
            //Toast.makeText(this,"Dang nhap thanh cong!",Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("mauser",user.getUid());
            editor.commit();
            Intent iTrangChu = new Intent(this,homepage_activity.class);
            Log.d("chuyentranghomepage","aaaaa");
            startActivity(iTrangChu);
        }
    }
}
