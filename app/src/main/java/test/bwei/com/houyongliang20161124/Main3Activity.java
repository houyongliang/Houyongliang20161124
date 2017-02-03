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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
public class Main3Activity extends AppCompatActivity {
    private String TAG = "MainActivity";/*获取标识*/
//    private RecyclerView ryView;/*ryview视图对象*/
    private PullRecyclerView ryView;/*ryview视图对象*/
    private Dialog alertDialog;
    private MyAdapter adapter;
    private LinearLayoutManager manager;


    private List<Bean.ResultBean.DataBean> listAll;
    private LinkedList<Bean.ResultBean.DataBean> list;
//    private String url = "http://japi.juhe.cn/joke/content/list.from?key= 874ed931559ba07aade103eee279bb37 &page=2&pagesize=10&sort=asc&time=1418745237";

    private int page=2;/*定义页数*/
    private int pagesize=10;/*定义每页展示条数*/
    private String url="http://japi.juhe.cn/joke/content/list.from";

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what){
                case 0:
                    reflashAdapter(0);/*刷新adpter*/
                break;
                case 1:
                    reflashAdapter(1);/*刷新adpter*/
                    ryView. onHeaderRefreshComplete();
                    break;
                case 2:
                    reflashAdapter(2);/*刷新adpter*/
                    ryView. onFooterRefreshComplete();
                    break;


            }
        }
    };




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
        getList(0);

    }
    private  void reflashAdapter(int what){
        if(what!=2){
            list.clear();
        }
        list.addAll(listAll);
        if (adapter == null) {
            adapter = new MyAdapter(Main3Activity.this, list);
            ryView.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }
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

//    private String getUrl(int page, int pagesize) {
//        String ul = "http://japi.juhe.cn/joke/content/list.from?key= 874ed931559ba07aade103eee279bb37 &page=" + page + "&pagesize=" + pagesize + "&sort=asc&time=1418745237";
//        return ul;
//    }

    private void getList(final int what) {
//        String url=getUrl(page, pagesize);
//        Log.e(TAG, "getList: +url"+url );
//        OkHttp.getAsync(url, new OkHttp.DataCallBack() {
//
//            /*获取数据失败*/
//            @Override
//            public void requestFailure(Request request, IOException e) {
//                Toast.makeText(Main3Activity.this, "亲，请求失败", Toast.LENGTH_SHORT).show();
//            }
//
//            /*成功获取数据*/
//            @Override
//            public void requestSuccess(String result) throws Exception {
//               Log.e(TAG, "requestSuccess: "+result);
//                Bean bean = Utils.parseJsonWithGson(result, Bean.class);
//                /*获取数据集合*/
//                List<Bean.ResultBean.DataBean> data = bean.getResult().getData();
//                listAll = data;
//                mHandler.sendEmptyMessageDelayed(what,2000);
//
//            }
//        });
        Map<String,String> map=new HashMap<String,String>();

        map.put("key"," 874ed931559ba07aade103eee279bb37 ");
        map.put("page",page+"");
        map.put("pagesize",pagesize+"");
        map.put("sort","asc");
        map.put("time",""+1418745237);
        OkHttp.postAsync(url, map, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Toast.makeText(Main3Activity.this, "亲，请求失败", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.e(TAG, "requestSuccess: "+result);
                Bean bean = Utils.parseJsonWithGson(result, Bean.class);
                /*获取数据集合*/
                List<Bean.ResultBean.DataBean> data = bean.getResult().getData();
                listAll = data;
                mHandler.sendEmptyMessageDelayed(what,2000);
            }
        });
    }

    private void initView() {
        list = new LinkedList<Bean.ResultBean.DataBean>();/*初始化数据*/

//        ryView = (RecyclerView) findViewById(R.id.ryView_main);
        ryView = (PullRecyclerView) findViewById(R.id.ryView_main);

        ryView.setLayoutManager(new LinearLayoutManager(Main3Activity.this,LinearLayoutManager.VERTICAL,false));

        /*条目点击事件*/
        ryView.addOnItemTouchListener(new RecyclerViewClickListener(Main3Activity.this, ryView.getRecyclerView(), new RecyclerViewClickListener.OnItemClickListener() {
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
        ryView.setOnHeaderRefreshListener(new PullBaseView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullBaseView view) {
                page++;
                getList(1);


            }
        });
         /* 加载更多*/
        ryView.setOnFooterRefreshListener(new PullBaseView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullBaseView view) {
                page++;
                getList(2);

            }
        });

    }


}
