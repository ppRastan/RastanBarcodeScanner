package ir.rastanco.rastanbarcodescanner.presenter;

import android.app.Fragment;
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
import ir.rastanco.rastanbarcodescanner.Utility.Configuration;
import ir.rastanco.rastanbarcodescanner.Utility.OnItemClickListener;
import ir.rastanco.rastanbarcodescanner.Utility.RecyclerViewAdapter;
import ir.rastanco.rastanbarcodescanner.Utility.SwipeToDismissTouchListener;
import ir.rastanco.rastanbarcodescanner.Utility.SwipeableItemClickListener;
import ir.rastanco.rastanbarcodescanner.dataModel.Barcode;

/**
 * Created by ParisaRashidhi on 22/12/2015.
 */
public class BarcodeDisplayer extends Fragment {
    public ArrayList<Barcode> allBarcode;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_recycler_view, null);
        init((RecyclerView) v.findViewById(R.id.recycler_view));
        allBarcode=new ArrayList<Barcode>();
        allBarcode= (ArrayList<Barcode>) getArguments().getSerializable("allBarcode");
        System.out.println("بار کد در فرگمنت:" + allBarcode.get(0).getContent());
        return v;
    }
    private void init(RecyclerView recyclerView) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Configuration.activityContext);
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
        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(Configuration.activityContext,
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

            mDataSet.add("barcode 1");
            mDataSet.add("barcode 2");
            mDataSet.add("barcode 3");
            mDataSet.add("barcode 4");
            mDataSet.add("barcode 5");
            mDataSet.add("barcode 6");
            mDataSet.add("barcode 7");
            mDataSet.add("barcode 8");
            mDataSet.add("barcode 9");
            mDataSet.add("barcode 10");
            mDataSet.add("barcode 11");
            mDataSet.add("barcode 12");
            mDataSet.add("barcode 13");
            mDataSet.add("barcode 14");
            mDataSet.add("barcode 15");
            mDataSet.add("barcode 16");
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


