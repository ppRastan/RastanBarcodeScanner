package ir.rastanco.rastanbarcodescanner.presenter.navigationDrawerManagement;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.Utility.Configuration;
import ir.rastanco.rastanbarcodescanner.dataModel.FileInfo;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagement.ListViewHandling.OnItemClickListener;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagement.ListViewHandling.RecyclerViewAdapter;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagement.ListViewHandling.SwipeToDismissTouchListener;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagement.ListViewHandling.SwipeableItemClickListener;

/**
 * Created by ParisaRashidhi on 31/12/2015.
 */
public class FragmentSentFiles extends Fragment {

    private ArrayList<FileInfo> allFileInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handler,container ,false);
        this.showListOfFiles(view);
        return view;
    }

    private void showListOfFiles(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("allFileInfo", allFileInfo);
        init((RecyclerView) view.findViewById(R.id.recycler_view));
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
        recyclerView.setOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(Configuration.activityContext,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.txt_delete) {
                            touchListener.processPendingDismisses();
                        } else if (view.getId() == R.id.txt_undo) {
                            touchListener.undoPendingDismiss();
                        } else {

                            //TODO send files
                        }
                    }
                }));
    }

    static class MyBaseAdapter extends RecyclerView.Adapter<MyBaseAdapter.MyViewHolder> {


        private final List<String> mDataSet = new ArrayList<>();

        MyBaseAdapter() {
            mDataSet.add("shared file 1");
            mDataSet.add("shared file 2");
            mDataSet.add("shared file 3");
            mDataSet.add("shared file 4");
            mDataSet.add("shared file 5");
            mDataSet.add("shared file 6");
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
            final TextView dataTextView;
            CheckBox listViewCheckBox;
            final TextView textShare;

            MyViewHolder(View view) {

                super(view);
                dataTextView = ((TextView) view.findViewById(R.id.txt_data));
                textShare = (TextView) view.findViewById(R.id.txt_share);
                textShare.setVisibility(View.VISIBLE);
            }
        }
    }

}
