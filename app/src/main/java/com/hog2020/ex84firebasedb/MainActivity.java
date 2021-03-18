package com.hog2020.ex84firebasedb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText et;
    TextView tv;

    //Firebase : 서버쪽 작업을 대신 해주는 사이트

    //Firebase 실시간 Database = MySQL 같은 RDBMS(테이블형태로 저장하는) 와는 다른 방식으로 저장, No-SQL 방식
    // road 라는 개념을 이용해서 값들을 저장
    // 이 앱에 Firebase 라이브러리 적용하기
    //Firebase console 사이트의 작업 절차(워크플로)를 따라 적용

    //Firebase console 에 데이터베이스 만들기

  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et= findViewById(R.id.et);
        tv= findViewById(R.id.tv);
    }

    public void clicksend(View view) {
      //실시간 DB 의 저장할 데이터
        String text=et.getText().toString();

        //Firebase 실시간 데이터베이스에 저장

        //Firebase 실시간 데이터베이스 관리 객체 얻어오기[서버와 연결]
        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();

        //데이터베이스의 최상위(root) 노드 참조객체 가져오기
        DatabaseReference rootRef= firebaseDatabase.getReference();

        //각 노드에 값 대입해보기

        //1. 값 설정
//        rootRef.setValue(text);
//
//        //DB의 값이 변경된 것을 읽어보기 =실시간 DB이기에 리스너를 이용한 [별도의 GET 메소드 없음]
//        rootRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //변경된 순간의 값을 촬영한다는 느낌의 코드
//                //DataSnapshot 객체가 그 순간의 값을 촬영하고 있다
//                //String data = (String)snapshot.getValue();
//
//                //형변환 코드가 지저분해서 싫다면...원하는 자료형 .class 를 미리지정
//                String data =snapshot.getValue(String.class);
//                tv.setText("robin");
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //2)위 1)방식은 값이 계속 변경됨. 누적하여 추가하고 싶다면
        //rootRef 노드의 지식으로 새로운 node 를 추가하며 값을설정
        //DatabaseReference dataRef =rootRef.push(); //새로운 자식노드 추가 되고 그 추가 된 자식노드의 참조객체 리턴
//        DatabaseReference dataRef = rootRef.push();
//        dataRef.setValue(text);
//
//        //데이터가 추가되는 것에 반응하는 리스너
//        rootRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //snapshot 에게 값을 읽어오게하면 에러
//                // 최상위 노드에 값이 있는 것이 아니라
//                //자식노드들이 여러개 있고 그 노드들이 값을 가지고 있음
//
//                StringBuffer buffer= new StringBuffer();
//                for(DataSnapshot  ds :snapshot.getChildren()){
//                    String data = ds.getValue(String.class);
//                    buffer.append(data+"\n");
//                }
//                tv.setText(buffer.toString());
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //3) 최상위 노드 아래에 각 노드들에 식별 키값을 부여하면서 데이터 관리 하기
        //rootRef 아래에 'data'라는 이름의 자식노드하나 추가
//        DatabaseReference dataRef =rootRef.child("data"); //'data' 라는 이름이 없으면 만들고 있다면 그냥 참조
//        dataRef.push().setValue(text); // push() 추가
//
//        //'data' 노드에 리스너 추가
//        dataRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //값들이 여러개 이므로
//                StringBuffer buffer = new StringBuffer();
//                for(DataSnapshot snap: snapshot.getChildren()){
//                    String data = snap.getValue(String.class);
//                    buffer.append(data+"\n");
//                }
//                tv.setText(buffer.toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //4) 하나의 노드에 벨류가 여러개..
//        DatabaseReference memberRef=rootRef.child("members");
//        DatabaseReference itemRef =memberRef.push();//"member"노드 아래에 임의의 식별자를가진 자식노드 생성
//        itemRef.child("name").setValue(text);
//        itemRef.child("age").setValue(20);
//
//        memberRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                StringBuffer buffer =new StringBuffer();
//
//                //push()에 의한 자식노드들에 하나씩 접근하기
//                for(DataSnapshot snap : snapshot.getChildren()){
//                    //각 자식노드마다 'name' , 'age' 라는 자식노드가 또 있으므로
//                    for (DataSnapshot sn : snap.getChildren()){
//                        buffer.append(sn.getKey()+":"+sn.getValue().toString()+"\n");
//                    }
//                    buffer.append("============\n");
//                }
//                tv.setText(buffer.toString());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        //5) 나만의 클래스 객체를 만들어서 한번에 멤버값을 저장하기
        //이름, 나이 , 주소 를 멤버로 가지는 PersonVO 객체
        String name= et.getText().toString();
        int age=20;
        String address="Seoul";

        //저장할 값을 가진 PersonVo 객체 생성
        PersonVO person= new PersonVO(name,age,address);

        //'persons' 라는이름의 자식노드 생성 or 참조
        DatabaseReference personRef =rootRef.child("persons");
        personRef.push().setValue(person); //Person 객체를 통으로 저장 -멤버변수명으로 자식노드를 생김

        //'person'노드에 리스너 추가
        personRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StringBuffer buffer = new StringBuffer();
                for (DataSnapshot sn : snapshot.getChildren()){
                    PersonVO person =sn.getValue(PersonVO.class); //노드 값들을 PersonVO 객체로 얻어오기
                    String name= person.name;
                    int age=person.age;
                    String address=person.address;

                    buffer.append(name+","+age+","+address+"\n");
                }
                tv.setText(buffer.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}