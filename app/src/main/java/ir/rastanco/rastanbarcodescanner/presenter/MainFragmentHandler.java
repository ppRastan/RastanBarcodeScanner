package ir.rastanco.rastanbarcodescanner.presenter;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.Utility.Configuration;
import ir.rastanco.rastanbarcodescanner.Utility.OnItemClickListener;
import ir.rastanco.rastanbarcodescanner.Utility.RecyclerViewAdapter;
import ir.rastanco.rastanbarcodescanner.Utility.SwipeToDismissTouchListener;
import ir.rastanco.rastanbarcodescanner.Utility.SwipeableItemClickListener;
import ir.rastanco.rastanbarcodescanner.dataModel.FileInfo;

/**
 * Created by ParisaRashidhi on 23/12/2015.
 */
public class MainFragmentHandler extends Fragment {

    private ArrayList<FileInfo> allFileInfo;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        allFileInfo=new ArrayList<FileInfo>();
        allFileInfo= (ArrayList<FileInfo>) getArguments().getSerializable("allFileInfo");

        View v = inflater.inflate(R.layout.activity_recycler_view, null);
        init((RecyclerView) v.findViewById(R.id.recycler_view));
        return v;
    }
    private void init(RecyclerView recyclerView) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Configuration.activityContext);
        recyclerView.setLayoutManager(mLayoutManager);
        final MyBaseAdapter adapter = new MyBaseAdapter(allFileInfo);
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


        private final List<String> mDataSet = new ArrayList<>();
        private final List<Integer> typeFile=new ArrayList<>();

        MyBaseAdapter(ArrayList<FileInfo> allFileInfo) {

            for (int i=0;i<allFileInfo.size();i++) {
                mDataSet.add(allFileInfo.get(i).getFileName());
                if(allFileInfo.get(i).getFileType().equals(".docx"))
                    typeFile.add(R.drawable.ic_word);
                else if(allFileInfo.get(i).getFileType().equals(".xlsx"))
                    typeFile.add(R.drawable.ic_excel);
            }

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false));
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.dataTextView.setText(mDataSet.get(position));
            holder.iconImageView.setImageResource(typeFile.get(position));
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
            ImageView iconImageView;
            MyViewHolder(View view) {
                super(view);
                dataTextView = ((TextView) view.findViewById(R.id.txt_data));
                iconImageView=(ImageView)view.findViewById(R.id.img_icon);
            }
        }
    }

}


