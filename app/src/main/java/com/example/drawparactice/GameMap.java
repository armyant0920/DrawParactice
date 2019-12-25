package com.example.drawparactice;
//這是參考中國網友的範例弄的,有興趣請去看MainActivity,那邊有各種畫筆功能說明
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GameMap extends AppCompatActivity {

    private int roleWidth,roleHeight;//目前沒用到
    DrawView view;
    Bitmap wall,role;
    Button btn;
    Boolean threadRun=true;


//地圖設定---要省時間請愛用之前給你的EXCEL
    final int CS=50,RoleSize=94,row=19,col=30;//row=15,col=15,CS=95

    private int roleX=6,roleY=6;

    private int count;
    private static final int LEFT=2,RIGHT=3,UP=1,DOWN=0;
    private int direction=UP;
    private Thread threadAnime;
    int[][]map={
//            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,1,1,1,1,1,0,0,0,0,1},
//            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
//            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
//            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
//            {1,0,0,0,0,1,1,0,1,1,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
//            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}

            {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
            {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,3},
            {3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
            {3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
            {3,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
            {3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},
            {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
            {3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
            {3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
            {3,2,2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
            {3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
            {3,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
            {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,2,3},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
            {2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,3},
            {3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_map);

        init();
        threadAnime=new Thread(new AnimationThread());
        threadAnime.start();


    }
    private class AnimationThread extends Thread {
        public void run(){
            while(threadRun=true) {
                Paint p=new Paint();
                count++;
                count=count%3;
                if(roleX<7 &&roleY<7)
                roleX++;
                else if(roleX==7 &&roleY<7)
                    roleY++;
                else if(roleX==7 & roleY==7)
                roleX--;
                else if(roleX<7 & roleY==7)
                roleY--;
                if(direction==UP)
                    direction=RIGHT;
                else if(direction==RIGHT)
                    direction=DOWN;
                else if(direction==DOWN)
                    direction=LEFT;
                else if(direction==LEFT)
                direction=UP;

//                Canvas canvas=new Canvas();
//                Bitmap role=BitmapFactory.decodeResource(getResources(),R.drawable.role);
//                roleHeight=role.getHeight();
//                roleWidth=role.getWidth();
//                Bitmap roleAction=Bitmap.createBitmap(role,roleWidth/3,roleHeight/3,roleWidth/3,roleHeight/3);
//                canvas.drawBitmap(roleAction,roleX*92,roleY*92,p);
                view.invalidate();
                //thread不知為何關不掉,先拿掉Log不然一直洗
                //Log.d("thread",String.valueOf(count));
                try{
                    Thread.sleep(1000); }
                catch (Exception e) {
                    e.printStackTrace(); }
            }
        }
    }

    private void init() {
        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GameMap.this,GuessNum.class);
                finish();
                threadRun=false;
                startActivity(i);


            }
        });
        LinearLayout layout = (LinearLayout) findViewById(R.id.Map);
        wall= BitmapFactory.decodeResource(getResources(),R.drawable.wall_brick);
        role= BitmapFactory.decodeResource(getResources(),R.drawable.role);
        roleWidth=role.getWidth();
        roleHeight=role.getHeight();
        Log.d("roleWidth",String.valueOf(roleWidth));
        Log.d("roleHeight",String.valueOf(roleHeight));
        view = new GameMap.DrawView(this);// final GameMap.DrawView
        //這是畫布大小設定,先大概抓的,手機實際寬高單位不知道是甚麼,寬要到差不多2000才滿屏
        view.setMinimumHeight(2000);
        view.setMinimumWidth(600);
        //通知view组件重绘
        view.invalidate();
        layout.addView(view);

    }

    public class DrawView extends View {

        public DrawView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // 创建画笔
            Paint p = new Paint();
            //這邊其實方向畫反了,而且實際跑出來,因為手機大小關係有一行看不到
            //有空再微調吧
            for (int i=0;i<col;i++) {
                for (int j = 0; j <row; j++) {
                    switch(map[j][i])
                    { case 0:
                            //因為只是畫地板,就直接用預設功能了
                            p.setColor(Color.GRAY);
                            canvas.drawRect(i*CS, j*CS, (i+1)*CS, (j+1)*CS, p);// 正方形
                        break;
                        case 1:
                            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.wall_brick);
                            canvas.drawBitmap(wall, i * CS, j * CS, p);
                        break;

                        case 2:
                            p.setColor(Color.RED);
                            canvas.drawRect(i*CS, j*CS, (i+1)*CS, (j+1)*CS, p);// 正方形
                            break;

                        case 3:
                            p.setColor(Color.GREEN);
                            canvas.drawRect(i*CS, j*CS, (i+1)*CS, (j+1)*CS, p);// 正方形
                            break;

                    }
                }
            }
            //角色等地圖都畫完才畫,不然會被蓋掉
            //因為還沒想要怎麼偵測動作,就先不丟thread處理動畫了

            //Bitmap role=BitmapFactory.decodeResource(getResources(),R.drawable.role);
            // Bitmap role=Bitmap.createBitmap(100,100,R.drawable.role);
            //Bitmap role=((BitmapDrawable)res.getDrawable(R.drawable.role)).getBitmap();
            //寬高目前還沒用,,但理論上我們應該可以不用瞎猜實際寬高,讓系統自己算好
            //我再依據腳色要顯示哪個動作移到對應座標就好

            Bitmap roleAction=Bitmap.createBitmap(role,RoleSize*count, direction*RoleSize,RoleSize,RoleSize);

            canvas.drawBitmap(roleAction,roleX*RoleSize,roleY*RoleSize,p);
          }

        }
    }


