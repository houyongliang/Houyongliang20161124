package test.bwei.com.houyongliang20161124;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import test.bwei.com.houyongliang20161124.utils.OkHttp;
import test.bwei.com.houyongliang20161124.utils.Utils;

/**
 * 类名： MainActivity
 * 作用： 处理主页面数据，展示数据
 * autour: 侯永亮
 * date: 2016/11/24 8:55 
 * update: 2016/11/24
 */
public class MainActivity extends AppCompatActivity {
    private String TAG="MainActivity";/*获取标识*/
    private RecyclerView ryView;/*ryview视图对象*/

    private  List<Bean.ResultBean.DataBean> list;
    private String url="http://japi.juhe.cn/joke/content/list.from?key= 874ed931559ba07aade103eee279bb37 &page=2&pagesize=10&sort=asc&time=1418745237";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*初始化控件*/
        initView();
        /*初始化数据*/
        initData();
    }

    private void initData() {
        OkHttp.getAsync(url, new OkHttp.DataCallBack() {
            /*获取数据失败*/
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(MainActivity.this, "亲，请求失败", Toast.LENGTH_SHORT).show();
            }
            /*成功获取数据*/
            @Override
            public void requestSuccess(String result) throws Exception {
               /* Log.e(TAG, "requestSuccess: "+result);*/
                Bean bean = Utils.parseJsonWithGson(result, Bean.class);
                List<Bean.ResultBean.DataBean> data = bean.getResult().getData(); /*获取数据集合*/

            }
        });
    }

    private void initView() {
        list=new ArrayList<Bean.ResultBean.DataBean>();/*初始化数据*/
        ryView= (RecyclerView) findViewById(R.id.ryView_main);

    }
}
