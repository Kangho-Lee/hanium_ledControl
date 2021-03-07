package com.example.yj.bluetoothapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

 import com.example.yj.bluetoothapplication.UserType.User;

import java.util.ArrayList;

public class RegisterPage extends AppCompatActivity {


    public ArrayList<User> userDataList = logo_Activity.getUserDBManager().getAllUserData();
    public User registerUser = new User();

    public EditText registerName;
    public EditText registerId;
    public EditText registerPw;
    public EditText registerPwCheck;
    public RadioButton radioManager;
    public RadioButton radioMember;
    public Button registerConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        registerName = (EditText)findViewById(R.id.register_user_name);
        registerId = (EditText)findViewById(R.id.register_user_id);
        registerPw = (EditText)findViewById(R.id.register_user_pw);
        registerPwCheck = (EditText)findViewById(R.id.register_user_pw_check);
        radioManager = (RadioButton)findViewById(R.id.radio_manager);
        radioMember = (RadioButton)findViewById(R.id.radio_student);
        registerConfirmButton = (Button)findViewById(R.id.register_confirm_button);

        registerConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean repeatedId = false; //아이디 중복이면 true, 아니면 false

                registerUser.setUserName(registerName.getText().toString());
                registerUser.setUserId(registerId.getText().toString());
                registerUser.setUserPw(registerPw.getText().toString());
                String pwCheck = registerPwCheck.getText().toString();

                if(registerUser.getUserPw().equals("")||registerUser.getUserId().equals("")||registerUser.getUserName().equals("")||pwCheck.equals("")||
                        (!radioManager.isChecked()&&!radioMember.isChecked())){ //정보를 덜 입력했을 때
                    Toast.makeText(RegisterPage.this, "입력하지 않은 정보가 있습니다."+"\n"+ "        다시 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{ //정보가 다 입력되었을 때
                    if(registerUser.getUserPw().equals(pwCheck)){ //비밀번호와 비밀번호 확인이 일치할 때
                        for(int i = 0; i < userDataList.size(); i++){ //아이디 중복 확인
                            if(registerUser.getUserId().equals(userDataList.get(i).getUserId())){
                                repeatedId = true; // 아이디 중복이면 true
                                Toast.makeText(RegisterPage.this, "중복된 아이디가 존재합니다.", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }

                        //member, manager 선택 버튼
                        if(radioManager.isChecked()){
                            registerUser.setManager(1);
                        }else if(radioMember.isChecked()){
                            registerUser.setManager(0);
                        }

                        if(!repeatedId){ // 아이디가 중복되지 않으면 계정 생성하고 로그인 화면으로
                            //팀이 없으므로 팀 넘버 -1;
                            registerUser.setUserTeamNum(-1);
                            logo_Activity.getUserDBManager().addUserData(registerUser);
                            Intent intent = new Intent(getApplicationContext(), logo_Activity.class);
                            startActivity(intent);
                        }
                    }else if(!registerUser.getUserPw().equals(pwCheck)){ //비밀번호 확인이 틀렸을 때
                        Toast.makeText(RegisterPage.this, "비밀번호 확인이 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
}
