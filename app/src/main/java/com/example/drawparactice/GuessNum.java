package com.example.drawparactice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.util.Random;

public class GuessNum extends AppCompatActivity {

    Button btn_SEND;
    Button btn_Start;
    TextView textRecord,textViewGameCount;
    EditText inputText;
    String Question,Answer;
    String Record;
    char[]Q={},A={};
    Random rnd=new Random();
    Boolean Boolean_Start=false;

    int question,answer,record,countA,countB;
    int gameCount,recordCount;

    //SoundPool soundPool;
    private View.OnClickListener btnListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()){
                //按下開始或重新開始鍵
                case R.id.btn_Start:
                    start();
                break;
                //按下送出鍵
                case R.id.btn_SEND:
                    send();
                    inputText.setFocusable(false);
                break;
                case R.id.inputText:
                    inputText.setFocusable(true);
                    inputText.setFocusableInTouchMode(true);
                    inputText.requestFocus();
                    Log.d("inputTextPress","Y");
                 break;




            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_num);
        init();
    }

private void init(){

    //找到所有元件
    btn_SEND=(Button)findViewById(R.id.btn_SEND);
    btn_Start=(Button)findViewById(R.id.btn_Start);
    textRecord=(TextView)findViewById(R.id.textRecord);
    textViewGameCount=(TextView)findViewById(R.id.textViewGameCount);
    //輸入欄位的細部設定
    inputText=(EditText)findViewById(R.id.inputText);
    inputText.setVisibility(View.INVISIBLE);
    inputText.setFocusable(false);//避免一開始就聚焦





    //加上監聽事件
    btn_Start.setOnClickListener(btnListener);
    btn_SEND.setOnClickListener(btnListener);
    inputText.setOnClickListener(btnListener);


}
private void start(){





    if (Boolean_Start==false){
        btn_SEND.setVisibility(View.VISIBLE);
        gameCount++;
        Boolean_Start=true;
        Record="";
        recordCount=0;
        btn_Start.setText(R.string.GuessNumReStart);
        String s=getText(R.string.GuessNumGameCount).toString()+String.valueOf(gameCount);//
        textViewGameCount.setText(s);//+String.valueOf(gameCount)
        inputText.setVisibility(View.VISIBLE);//如果是開始一局,就把輸出欄位叫出來

        //按下開始或重新開始後,產生4位數題目
        Question="1505";
//        for(int i=1;i<=4;i++) {
//            Question+=(rnd.nextInt(9));
//
//        }
        Q=Question.toCharArray();
        Log.d("Q=",Question);
        Record="";
        textRecord.setText(Record);

    }
    else {
        Boolean_Start=false;
        inputText.setVisibility(View.INVISIBLE);//如果是重置一局,就把輸出欄位隱藏
        textRecord.setText(R.string.GuessNumGameinfo3);
        btn_SEND.setVisibility(View.INVISIBLE);
        btn_Start.setText(R.string.GuessNumStart);

    }

}

private void send(){
        countA=countB=0;
        Answer="";

if(String.valueOf(inputText.getText()).length()!=4) {
    Toast.makeText(GuessNum.this,"輸入的長度有誤",Toast.LENGTH_SHORT).show();
}

else{

    recordCount++;
    String s[]={"?","?","?","?"};//宣告一個區域字串變數s來存已經抓到的數
    Boolean ACheck[]= {false,false,false,false};//為了不重複計算符合的情況,用Boolean紀錄送出的數字每一個位置是否已經被計算完畢
    Answer=inputText.getText().toString();
    Log.d("Answer=",Answer);
    A=Answer.toCharArray();
//完全一樣的先篩完
    for(int x=0;x<4;x++){
        if(Q[x]==A[x]){
            ACheck[x]=true;
            countA++;
            s[x]=String.valueOf(A[x]);
            Log.d("s=",x+"/"+s[x]);


        }
        Log.d("x",x+"/"+String.valueOf(ACheck[x]));
    }
    loop1:
//從回答的值開始推
    for(int j=0;j<4;j++){//這裡是答案的陣列
        if(s[j]=="?")
        s[j]=String.valueOf(A[j]);
        else{
            continue;
        }
        //loop2:
        for(int k=0;k<4;k++) {//這裡是問題的陣列

                if (A[j]==Q[k] ) {//答案陣列中,還沒有確認的位置
                    int count1=0,count2=0;
                    Log.d("A[j]=", String.valueOf(j) + "/" + String.valueOf(A[j]));
                    for(String s1:s){
                        if (s1==String.valueOf(A[j])){
                            count1++;
                        }
                    }
                    for(char c:Q) {
                        if (c == A[j]) {
                            count2++;
                        }
                    }
                    if (count1<count2){
                        countB++;
                        Log.d("countB","countB+");
                    }
//                                    A_recount++;
//                                    Log.d("Q_recount",String.valueOf(A_recount));
//                                }
                    continue loop1;
                    }

                }

                        //下面這包不要開
//                       { if (j == k) {//位置和數字都對
//
//                            countA++;
//                            Log.d("全對", "Q_position:" + String.valueOf(j) + "/value:" + String.valueOf(Q[j]) + "\t"
//                                    + "A_position:" + String.valueOf(k) + "/value:" + String.valueOf(A[k]));
//                            ACheck[k] = true;//如果數值和位置都對,把送出資料的第k個值狀態鎖定住,且這個位置的值不應該再做任何計算
//                            Log.d("被鎖定",k+"/"+A[k]);
//                            //continue loop2;
//                        } else {//數字對 ,位置不對
//                        //首先先獲得這個重複的值,在問題中出現幾次,
//                        //再找出,回答中出現幾次,有沒有已經被找到的}

                           // int Q_recount = 0, A_recount = 0;
                            //for (int y=0;y<4;y++) {
                                }
                            }
//                            for (char c2 : A) {
//                                if (c2 == A[k]) {
//                                    A_recount++;
//                                    Log.d("Q_recount",String.valueOf(A_recount));
//                                }
//                            }
//                            Log.d("Q_recount=", String.valueOf(Q_recount));
//                            Log.d("A_recount=", String.valueOf(A_recount));
//                            //如果回答中出現的次數,已經超過問題的出現數,就不+,反之+
//                            if(A_recount>Q_recount) {
//                                countB+=2;
//                            }
//                                else{
//                                    countB++;
//
//
//                            }
//                                Log.d("countB+=",String.valueOf(A_recount-Q_recount));

//                            if (Q_recount >= A_recount && ACheck[k]==false) {
//                                countB++;
//
//
//                            }
//                    countB++;
//                    ACheck[k]=true;
//                        }
//                        if(recount<countA){
//                            countB++;
//                            Log.d("數字對", "Q_position:" + String.valueOf(j) + "/value:" + String.valueOf(Q[j]) + "\t"
//                                    + "A_position:" + String.valueOf(k) + "/value:" + String.valueOf(A[k]));}
//                        //continue loop2;
//
//                        //break loop2;





    Record+=recordCount+":"+"\t"+Answer+"\t"+countA+"A"+"\t"+countB+"B:"+"\n";
    textRecord.setText(Record);
    inputText.setText("");
    //答對
    if(countA==4){
        Uri uri=Uri.parse("https://www.youtube.com/watch?v=ZEcBBv7QiRM");
        Intent i=new Intent(Intent.ACTION_VIEW, uri);
        startActivity(i);


     }

    }
}





