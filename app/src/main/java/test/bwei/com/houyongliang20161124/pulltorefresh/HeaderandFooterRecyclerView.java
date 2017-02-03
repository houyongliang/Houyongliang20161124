package test.bwei.com.houyongliang20161124.pulltorefresh;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import static android.R.id.list;

/**
 * 1. 作用
 * 2. 作者 侯永亮
 * 3. 时间 2016/11/24.
 */

public class HeaderandFooterRecyclerView extends RecyclerView{
    public HeaderandFooterRecyclerView(Context context) {
        this(context,null);
    }

    public HeaderandFooterRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeaderandFooterRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public class FixedViewInfo {

        public View view;

        public int viewType;
    }

}
