package com.example.drawparactice;
//這是參考中國網友的範例弄的,有興趣請去看MainActivity,那邊有各種畫筆功能說明
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class GameMap extends AppCompatActivity {

    private int roleWidth,roleHeight;//目前沒用到

//地圖設定---要省時間請愛用之前給你的EXCEL
    final int CS=85,row=15,col=15;
    int[][]map={
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,1,1,1,1,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,0,0,0,1,0,0,0,0,1},
            {1,0,0,0,0,1,1,0,1,1,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
            {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_map);

        init();

    }

    private void init() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.Map);
        final GameMap.DrawView view = new GameMap.DrawView(this);
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
            for (int i=0;i<row;i++) {
                for (int j = 0; j < col; j++) {
                    switch(map[i][j])
                    { case 0:
                            //因為只是畫地板,就直接用預設功能了
                            p.setColor(Color.GRAY);
                            canvas.drawRect(i*CS, j*CS, (i+1)*CS, (j+1)*CS, p);// 正方形
                        break;
                        case 1:
                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.wall);
                            canvas.drawBitmap(bitmap, i * CS, j * CS, p);
                        break;


                    }

                }
            }
            //角色等地圖都畫完才畫,不然會被蓋掉
            //因為還沒想要怎麼偵測動作,就先不丟thread處理動畫了

            Bitmap role=BitmapFactory.decodeResource(getResources(),R.drawable.role);
            // Bitmap role=Bitmap.createBitmap(100,100,R.drawable.role);
            //Bitmap role=((BitmapDrawable)res.getDrawable(R.drawable.role)).getBitmap();
            //寬高目前還沒用,,但理論上我們應該可以不用瞎猜實際寬高,讓系統自己算好
            //我再依據腳色要顯示哪個動作移到對應座標就好
            roleWidth=role.getWidth();
            roleHeight=role.getHeight();
            Bitmap roleAction=Bitmap.createBitmap(role,0,0,100,100);
            canvas.drawBitmap(roleAction,7*CS,7*CS,p);
        }
    }
}

