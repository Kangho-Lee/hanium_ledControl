package com.example.yj.bluetoothapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.yj.bluetoothapplication.UserType.Master;
import com.example.yj.bluetoothapplication.UserType.User;
import com.example.yj.bluetoothapplication.UserType.UserDBManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 강호 리 on 2016-08-18.
 */
public class logo_Activity extends Activity {

    private static UserDBManager userDBManager;
    private static ArrayList<User> userStaticDB;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_page);

        userDBManager = new UserDBManager(getApplicationContext());


//        subTaskDBManager.deleteTable();
//        subTaskDBManager.createTable();
//        userDBManager.deleteTable();
//        userDBManager.createTable();
//        mainTaskDBManager.deleteTable();
//        mainTaskDBManager.createTable();

        final ArrayList<User> userDB = userDBManager.getAllUserData();
        final User user = new User();
        userDBManager.close();

        final EditText loginId = (EditText)findViewById(R.id.login_id);
        final EditText loginPw = (EditText)findViewById(R.id.login_pw);
        Button loginButton = (Button)findViewById(R.id.login_button);
        Button registerButton = (Button)findViewById(R.id.register_button);

        //로그인 -> 성공하면 메인페이지로 실패하면 다시
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUserId(loginId.getText().toString());
                user.setUserPw(loginPw.getText().toString());
                login(user, userDB); //factory method로 만들 수 있을 듯
            }
        });

        //회원가입 버튼을 누르면 회원가입 페이지로
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
                startActivity(intent);
            }
        });

    }

    //로그인 메소드
    public void login(User user, ArrayList<User> userDB){
        boolean login = false; //로그인 성공 여부
        User loginUser;

        //userDB내에 있는 데이터 탐색
        for(int i = 0; i < userDB.size(); i++){
            loginUser = userDB.get(i);
            //만약 db내에 있는 유저의 것과 입력한 것의 id와 비밀번호가 같으면
            if(user.getUserId().equals(loginUser.getUserId())){

                //패스워드 확인, 일치하면
                if(user.getUserPw().equals(loginUser.getUserPw())){
                    //realuser에 그 데이터를 넣는다. 앞으로는 realuser을 참조해서 사용하면 된다.
                    User userData = userDB.get(i);
                    Master.getInstance().setUserId(userData.getUserId());
                    Master.getInstance().setUserName(userData.getUserName());
                    Master.getInstance().setManager(userData.getManager());
                    Master.getInstance().setUserPw(userData.getUserPw());
                    Master.getInstance().setUserTeamNum(userData.getUserTeamNum());
                    Master.getInstance().setUserSelected(userData.getUserSelected());

                    userStaticDB = parsingMaster(userDB, Master.getInstance());
                    //로그인 성공해서 메세지
                    login = true;
                    Toast.makeText(logo_Activity.this, Master.getInstance().getUserName()+"님, 환영합니다!", Toast.LENGTH_SHORT).show();
                    //로그인 성공해서 main page로 넘어감
                    Intent intent = new Intent(this, buildingChoice_Activity.class);
                    startActivity(intent);
                    finish();
                    break;
                }else{
                    login = true;
                    Toast.makeText(logo_Activity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        //db내에 일치하는 데이터가 없으며
        if(!login)
            Toast.makeText(logo_Activity.this, "Login failed. Please type again", Toast.LENGTH_SHORT).show();
    }

    private ArrayList<User> parsingMaster(ArrayList<User> userDB, Master master){
        ArrayList<User> oldUserDB = userDB;
        ArrayList<User> newUserDB = new ArrayList<>();

        for(int i = 0; i < oldUserDB.size(); i++){
            User user = oldUserDB.get(i);
            if(!user.getUserId().equals(master.getUserId())){
                newUserDB.add(user);
            }
        }
        return newUserDB;
    }

    public static UserDBManager getUserDBManager() {
        return userDBManager;
    }
    public static ArrayList<User> getUserDB() {
        return userStaticDB;
    }


}


