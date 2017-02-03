package test.bwei.com.houyongliang20161124.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;

/**
 * RecyclerView中的所有方法都可以在此类中设置，暴露出去以供调用
 */
public class PullRecyclerView extends PullBaseView<RecyclerView> {
  public AdapterView.OnItemClickListener listener;

    public PullRecyclerView(Context context) {
        this(context, null);
    }

    public PullRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected RecyclerView createRecyclerView(Context context, AttributeSet attrs) {
        return new RecyclerView(context, attrs);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        mRecyclerView.setLayoutManager(manager);
    }

    public void addOnItemTouchListener(RecyclerView.OnItemTouchListener listener){
        mRecyclerView.addOnItemTouchListener(listener);
    }
    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }


}
