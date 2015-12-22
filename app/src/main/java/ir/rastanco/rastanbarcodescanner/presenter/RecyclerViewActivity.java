package ir.rastanco.rastanbarcodescanner.presenter;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import ir.rastanco.rastanbarcodescanner.R;
public class RecyclerViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        init((RecyclerView) findViewById(R.id.recycler_view));
    }

    private void init(RecyclerView recyclerView) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        final MyBaseAdapter adapter = new MyBaseAdapter();
        recyclerView.setAdapter(adapter);
        final SwipeToDismissTouchListener<RecyclerViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new RecyclerViewAdapter(recyclerView),
                        new SwipeToDismissTouchListener.DismissCallbacks<RecyclerViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerViewAdapter view, int position) {
                                adapter.remove(position);
                            }
                        });

        recyclerView.setOnTouchListener(touchListener);
        recyclerView.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(this,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.txt_delete) {
                            touchListener.processPendingDismisses();
                        } else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                        }
                    }
                }));
    }

    static class MyBaseAdapter extends RecyclerView.Adapter<MyBaseAdapter.MyViewHolder> {

        private static final int SIZE = 100;

        private final List<String> mDataSet = new ArrayList<>();

        MyBaseAdapter() {
            mDataSet.add("file 1");
            mDataSet.add("file 2");
            mDataSet.add("file 3");
            mDataSet.add("file 4");
            mDataSet.add("file 5");
            mDataSet.add("file 6");
            mDataSet.add("file 7");
            mDataSet.add("file 8");
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.dataTextView.setText(mDataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        public void remove(int position) {
            mDataSet.remove(position);
            notifyItemRemoved(position);
        }

        static class MyViewHolder extends RecyclerView.ViewHolder {

            TextView dataTextView;
            MyViewHolder(View view) {
                super(view);
                dataTextView = ((TextView) view.findViewById(R.id.txt_data));
            }
        }
    }

}
