package test.bwei.com.houyongliang20161124;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Request;
import test.bwei.com.houyongliang20161124.adpter.MyAdapter;
import test.bwei.com.houyongliang20161124.utils.OkHttp;
import test.bwei.com.houyongliang20161124.utils.RecyclerViewClickListener;
import test.bwei.com.houyongliang20161124.utils.Utils;
import test.bwei.com.houyongliang20161124.view.PullBaseView;
import test.bwei.com.houyongliang20161124.view.PullRecyclerView;

/**
 * 类名： MainActivity
 * 作用： 处理主页面数据，展示数据
 * autour: 侯永亮
 * date: 2016/11/24 8:55
 * update: 2016/11/24
 */
public class Main2Activity extends AppCompatActivity implements PullRecyclerView.OnFooterRefreshListener, PullBaseView.OnHeaderRefreshListener {
    private String TAG = "MainActivity";/*获取标识*/
    private PullRecyclerView ryView;/*ryview视图对象*/
    private Dialog alertDialog;
    private MyAdapter adapter;
    private List<Bean.ResultBean.DataBean> list;
    private int page = 2;/*定义页数*/
    private int pageNum = 1;/*定义每页展示条数*/
    private int pageSize=10;



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
         /*获取数据*/
        getList(page, pageNum*pageSize);
//        adapter.notifyDataSetChanged();/

    }


    private void showAlertDiog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确定删除？");
        builder.setMessage("您确定删除该条信息吗？");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();

            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private String getUrl(int page, int pagesize) {
        String ul = "http://japi.juhe.cn/joke/content/list.from?key= 874ed931559ba07aade103eee279bb37 &page=" + page + "&pagesize=" + pagesize + "&sort=asc&time=1418745237";
        return ul;
    }

    private void getList(int page, int pagesize) {
        String url = getUrl(page, pagesize);
        Log.e(TAG, "getList: +url" + url);
        OkHttp.getAsync(url, new OkHttp.DataCallBack() {

            /*获取数据失败*/
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(Main2Activity.this, "亲，请求失败", Toast.LENGTH_SHORT).show();
            }

            /*成功获取数据*/
            @Override
            public void requestSuccess(String result) throws Exception {
                Log.e(TAG, "requestSuccess: " + result);
                Bean bean = Utils.parseJsonWithGson(result, Bean.class);
                /*获取数据集合*/
                List<Bean.ResultBean.DataBean> data = bean.getResult().getData();
                list = data;

                    adapter = new MyAdapter(Main2Activity.this, list);
                    ryView.setAdapter(adapter);


            }
        });
    }

    private void initView() {
     /*   list = new LinkedList<Bean.ResultBean.DataBean>();*//*初始化数据*/


        ryView = (PullRecyclerView) findViewById(R.id.ryView_main);

        ryView.setLayoutManager(new LinearLayoutManager(Main2Activity.this, LinearLayoutManager.VERTICAL, false));

        /*条目点击事件*/
        ryView.addOnItemTouchListener(new RecyclerViewClickListener(Main2Activity.this, ryView.getRecyclerView(), new RecyclerViewClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            /*设置条目长按事件*/
            @Override
            public void onItemLongClick(View view, int position) {
                        /*展示 alertdiogler*/
                showAlertDiog(position);
            }
        }));
        /*下拉刷新*/
        ryView.setOnHeaderRefreshListener(this);
         /* 加载更多*/
        ryView.setOnFooterRefreshListener(this);

    }


    @Override
    public void onFooterRefresh(PullBaseView view) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                page++;
                pageNum++;
                getList(page, pageNum*pageSize);
                adapter.notifyDataSetChanged();
                ryView.onFooterRefreshComplete();
            }
        }, 2000);

    }

    @Override
    public void onHeaderRefresh(PullBaseView view) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNum=1;
                page++;
                getList(page, pageNum*pageSize);
                adapter.notifyDataSetChanged();
                ryView.onHeaderRefreshComplete();

            }
        }, 2000);

    }
}
