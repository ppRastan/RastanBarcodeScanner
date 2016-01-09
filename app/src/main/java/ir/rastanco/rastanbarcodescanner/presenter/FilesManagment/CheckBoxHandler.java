package ir.rastanco.rastanbarcodescanner.presenter.FilesManagment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.dataModel.FileInfo;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagment.ListViewHandling.OnItemClickListener;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagment.ListViewHandling.RecyclerViewAdapter;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagment.ListViewHandling.SwipeToDismissTouchListener;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagment.ListViewHandling.SwipeableItemClickListener;

import static android.widget.Toast.LENGTH_SHORT;

public class CheckBoxHandler extends Activity {


    private ImageButton toolbarShareButton;
    private ImageButton toolbarDeleteButton;
    private CheckBox toolbarSelectAllCheckboxes;
    private TextView selectAllCheckBoxesTextView;
    private ImageButton toolbarFinishedChecking;
    private ArrayList<FileInfo> allFileInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_handler);
        this.addToolbar();
        this.setFont();
        this.showListOfFiles();

    }

    private void setFont() {
        selectAllCheckBoxesTextView = (TextView)findViewById(R.id.select_all_text_view);
        Typeface font  = Typeface.createFromAsset(getAssets(), "yekan_font.ttf");
        selectAllCheckBoxesTextView.setTypeface(font);
    }

    private void showListOfFiles() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("allFileInfo", allFileInfo);
        init((RecyclerView) findViewById(R.id.recycler_view));
    }
    private void addToolbar() {

        toolbarFinishedChecking = (ImageButton)findViewById(R.id.appbar_barcode_displayer_check_btn);
        toolbarShareButton = (ImageButton)findViewById(R.id.checkbox_content_layout_share_btn);
        toolbarDeleteButton = (ImageButton)findViewById(R.id.checkbox_content_layout_delete_btn);
        toolbarSelectAllCheckboxes = (CheckBox)findViewById(R.id.select_all_checkboxes);
        toolbarFinishedChecking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        toolbarShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Toast.makeText(getApplicationContext(),"share button clicked",Toast.LENGTH_SHORT).show();
            }
        });

        toolbarDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        toolbarSelectAllCheckboxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        toolbarFinishedChecking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(CheckBoxHandler.this,MainActivity.class));
            }
        });
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
                        else{
                            //TODO handle send files
                            Toast.makeText(getApplicationContext(),"share button",Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
    }

    static class MyBaseAdapter extends RecyclerView.Adapter<MyBaseAdapter.MyViewHolder> {


        private final List<String> mDataSet = new ArrayList<>();

        MyBaseAdapter() {

            mDataSet.add("file 1");
            mDataSet.add("file 2");
            mDataSet.add("file 3");
            mDataSet.add("file 4");
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
            CheckBox checkBox;
            TextView checkBoxHandlertextShare;
            MyViewHolder(View view) {
                super(view);
                checkBoxHandlertextShare = (TextView)view.findViewById(R.id.txt_share);
                checkBox = (CheckBox)view.findViewById(R.id.checkbox_listview_items);
                dataTextView = ((TextView) view.findViewById(R.id.txt_data));
                checkBox.setVisibility(View.VISIBLE);
                checkBoxHandlertextShare.setVisibility(View.VISIBLE);
            }
        }
    }

}
