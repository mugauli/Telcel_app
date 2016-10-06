package net.grapesoft.www.telcel;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

public class activity_trivias_swipe extends SwipeBackActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivias_swipe);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
    }
}
