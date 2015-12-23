package ir.rastanco.rastanbarcodescanner.presenter;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
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

/**
 * Created by ParisaRashidhi on 23/12/2015.
 */
public class MainFragmentHandler extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_recycler_view, null);
        init((RecyclerView) v.findViewById(R.id.recycler_view));
        return v;
    }
    @TargetApi(Build.VERSION_CODES.M)
    private void init(RecyclerView recyclerView) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
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
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        recyclerView.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(getContext(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.txt_delete) {
                            touchListener.processPendingDismisses();
                        } else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                        } else { // R.id.txt_data

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
            mDataSet.add("file 9");
            mDataSet.add("file 10");
            mDataSet.add("file 11");
            mDataSet.add("file 12");
            mDataSet.add("file 13");
            mDataSet.add("file 14");
            mDataSet.add("file 15");
            mDataSet.add("file 16");
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


