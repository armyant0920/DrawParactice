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
    TextView textRecord, textViewGameCount;
    EditText inputText;
    String Question, Answer;
    String Record;
    char[] Q = {}, A = {};
    Random rnd = new Random();
    Boolean Boolean_Start = false;

    int question, answer, record, countA, countB;
    int gameCount, recordCount;

    //SoundPool soundPool;
    private View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
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
                    Log.d("inputTextPress", "Y");
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

    private void init() {

        //找到所有元件
        btn_SEND = (Button) findViewById(R.id.btn_SEND);
        btn_Start = (Button) findViewById(R.id.btn_Start);
        textRecord = (TextView) findViewById(R.id.textRecord);
        textViewGameCount = (TextView) findViewById(R.id.textViewGameCount);
        //輸入欄位的細部設定
        inputText = (EditText) findViewById(R.id.inputText);
        inputText.setVisibility(View.INVISIBLE);
        inputText.setFocusable(false);//避免一開始就聚焦


        //加上監聽事件
        btn_Start.setOnClickListener(btnListener);
        btn_SEND.setOnClickListener(btnListener);
        inputText.setOnClickListener(btnListener);


    }

    private void start() {



        if (Boolean_Start == false) {
            btn_SEND.setVisibility(View.VISIBLE);
            textRecord.setText(R.string.GuessNumGameinfo3);
            gameCount++;
            Boolean_Start = true;
            Record = "";
            recordCount = 0;
            btn_Start.setText(R.string.GuessNumReStart);
            String s = getText(R.string.GuessNumGameCount).toString() + String.valueOf(gameCount);//
            textViewGameCount.setText(s);//+String.valueOf(gameCount)
            inputText.setVisibility(View.VISIBLE);//如果是開始一局,就把輸出欄位叫出來

            //按下開始或重新開始後,產生4位數題目
            Question="";
            for(int i=0;i<4;i++) {
                Question+=(rnd.nextInt(9));

        }
            Q = Question.toCharArray();
            Log.d("Q=", Question);
            Record = "";
            textRecord.setText(Record);

        } else {
            Boolean_Start = false;
            inputText.setVisibility(View.INVISIBLE);//如果是重置一局,就把輸出欄位隱藏

            btn_SEND.setVisibility(View.INVISIBLE);
            btn_Start.setText(R.string.GuessNumStart);

        }

    }

    private void send() {
        countA = countB = 0;
        Answer = "";

        if (String.valueOf(inputText.getText()).length() != 4) {
            Toast.makeText(GuessNum.this, "輸入的長度有誤", Toast.LENGTH_SHORT).show();
        }
        else {
            recordCount++;
            String s[] = {"?", "?", "?", "?"};//宣告一個區域字串變數s來存已經抓到的數
            Boolean ACheck[] = {false, false, false, false};//為了不重複計算符合的情況,用Boolean紀錄送出的數字每一個位置是否已經被計算完畢
            Answer = inputText.getText().toString();
            Log.d("Answer=", Answer);
            A = Answer.toCharArray();
            //完全一樣的先篩完
            for (int x = 0; x < 4; x++) {
                if (Q[x] == A[x]) {
                    ACheck[x] = true;
                    countA++;
                    s[x] = String.valueOf(A[x]);
                    Log.d("s=", x + "/" + s[x]);
                }
                Log.d("x", x + "/" + String.valueOf(ACheck[x]));
            }
            loop1:
           //從回答的值開始推
            for (int j = 0; j < 4; j++) {//這裡是答案的陣列
                if (s[j] == "?")
                    s[j] = String.valueOf(A[j]);
                else {
                    continue;
                }

                //loop2:
                for (int k = 0; k < 4; k++) {//這裡是問題的陣列

                    if (A[j] == Q[k]) {//答案陣列中,還沒有確認的位置
                        int count1 = 0, count2 = 0;
                        Log.d("A[j]=", String.valueOf(j) + "/" + String.valueOf(A[j]));
                        for (String s1 : s) {
                            Log.d("s=",s1);
                            if (s1 .equals( String.valueOf(A[j]))) {
                                count1++;
                                Log.d("紀錄的字串中,出現的值和次數","s1="+s1+"/times:"+String.valueOf(count1));
                            }
                        }
                        for (char c : Q) {
                            if (c == A[j]) {
                                count2++;
                                Log.d("題目的字串中,該值總共有幾次","c="+String.valueOf(c)+"/times:"+String.valueOf(count2));
                            }
                        }
                        if (count1 <= count2) {
                            countB++;
                            Log.d("countB", "countB+");
                        }

                        continue loop1;
                    }

                }

            }

                Record += recordCount + ":" + "\t" + Answer + "\t" + countA + "A" + "\t" + countB + "B:" + "\n";
                textRecord.setText(Record);
                inputText.setText("");
                //答對
                if (countA == 4) {
                    Boolean_Start=true;
                    Toast.makeText(this,"恭喜答對了,請等一下喔",Toast.LENGTH_SHORT).show();
                    start();
                    Thread thread=new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            finally {
                                Uri uri = Uri.parse("https://www.youtube.com/watch?v=ZEcBBv7QiRM");
                                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(i);
                            }


                        }
                    });
                    thread.start();




                }

            }

        }
    }






