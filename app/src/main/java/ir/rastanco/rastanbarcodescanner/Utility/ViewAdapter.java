package ir.rastanco.rastanbarcodescanner.Utility;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

/**
 * created by parisaRashidiNezhad
 */
public interface ViewAdapter {
    Context getContext();
    int getWidth();
    int getChildCount();
    void getLocationOnScreen(int[] locations);
    View getChildAt(int index);
    int getChildPosition(View position);
    void requestDisallowInterceptTouchEvent(boolean disallowIntercept);
    void onTouchEvent(MotionEvent e);
    Object makeScrollListener(AbsListView.OnScrollListener listener);
}
