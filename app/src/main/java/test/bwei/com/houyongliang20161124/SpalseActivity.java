package test.bwei.com.houyongliang20161124;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import static android.R.attr.start;

/**
 * 类名： SpalseActivity
 * 作用： 闪屏页面  倒计时三秒后跳转
 * autour: 侯永亮
 * date: 2016/11/24 8:57
 * update: 2016/11/24
 */
public class SpalseActivity extends AppCompatActivity {
    private ImageView iv_spalse;/*展示log 图片*/
    private TextView tv_spalse_backtime;/*倒计时展示*/

    private int time=3;/*初始化时间*/

    /*倒计时采用handler 处理*/
    private Handler mHanlder= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                if(time>0){
                    time--;
                    tv_spalse_backtime.setText("倒计时"+time+"s");
                    mHanlder.sendEmptyMessageDelayed(0,1000);
           }else{
            /*跳转主页面*/
                    startActivity(new Intent(SpalseActivity.this,MainActivity.class));
                    time=3;/*数据重置*/
                    finish();/*关闭SpalseActivity*/
                }

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalse);
        /*初始化控件*/
        initView();
        /*初始化事件*/
        initEvent();
    }

    private void initEvent() {
        /*handler 延时发送*/
        tv_spalse_backtime.setText("倒计时"+time+"s");
        mHanlder.sendEmptyMessageDelayed(0,1000);

    }

    private void initView() {
        iv_spalse= (ImageView) findViewById(R.id.iv_spalse);
        tv_spalse_backtime= (TextView) findViewById(R.id.tv_spalse_backtime);
    }
}
