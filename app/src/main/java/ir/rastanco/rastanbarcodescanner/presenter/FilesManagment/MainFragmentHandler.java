package ir.rastanco.rastanbarcodescanner.presenter.FilesManagment;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.Utility.Configuration;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagment.ListViewHandling.OnItemClickListener;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagment.ListViewHandling.RecyclerViewAdapter;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagment.ListViewHandling.SwipeToDismissTouchListener;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagment.ListViewHandling.SwipeableItemClickListener;
import ir.rastanco.rastanbarcodescanner.dataModel.FileInfo;

/**
 * Created by ParisaRashidhi on 31/12/2015.
 */
public class MainFragmentHandler extends Fragment {

    private ArrayList<FileInfo> allFileInfo;
    private String state = "default";
    private boolean sort_mode = true ;
    private ImageButton sortFiles;
    private ImageButton change_image_sort;
    private Button showFiles;
    private ImageButton checkBox_toolbar;
    private LinearLayout temp_toolbar_checkbox_handler;
    private LinearLayout main_toolbar;
    private ImageButton toolbar_share_btn;
    private ImageButton toolbar_delete_btn;
    private CheckBox toolbar_select_all_checkboxes;
    private TextView textView;

    private final String defaultMode= "default";
    private final String displayExcelFilesOnly = "displayExcelFilesOnly";
    private final String displayTextFilesOnly = "displayTextFilesOnly";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handler, null);
        this.createToolbar(view);
        this.setFont();
        this.showListOfFiles(view);
        return view;
    }

    private void showListOfFiles(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("allFileInfo", allFileInfo);
        init((RecyclerView) view.findViewById(R.id.recycler_view));
    }

    private void createToolbar(View view) {
        checkBox_toolbar = (ImageButton)view. findViewById(R.id.checkbox_toolbar);
        sortFiles = (ImageButton) view.findViewById(R.id.sort_toolbar);
        showFiles = (Button)view.findViewById(R.id.allfiles_toolbar);
        temp_toolbar_checkbox_handler = (LinearLayout)view.findViewById(R.id.temp_linear_checkbox);
        main_toolbar = (LinearLayout)view.findViewById(R.id.main_toolbar);
        toolbar_delete_btn = (ImageButton)view.findViewById(R.id.checkbox_content_layout_delete_btn);
        textView = (TextView)view.findViewById(R.id.temp_toolbar_textview);
        toolbar_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        toolbar_share_btn = (ImageButton)view.findViewById(R.id.checkbox_content_layout_share_btn);
        toolbar_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        toolbar_select_all_checkboxes = (CheckBox)view.findViewById(R.id.select_all_checkboxes);
        sortFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //container.removeAllViewsInLayout();
                if(sort_mode == true){
                    change_image_sort = (ImageButton) v.findViewById(R.id.sort_toolbar);
                    change_image_sort.setImageResource(R.drawable.ic_sort_a_to_z);

                    sort_mode = false ;

                }
                else if(sort_mode == false){

                    change_image_sort.setImageResource(R.drawable.ic_sort_icon);
                    sort_mode = true;
                }
            }
        });

        checkBox_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main_toolbar.setVisibility(View.INVISIBLE);
                temp_toolbar_checkbox_handler.setVisibility(View.VISIBLE);


            }
        });


        showFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (state) {

                    case defaultMode: {
                        showFiles.setText(getResources().getString(R.string.showExcelFilesOnly));
                        state = displayTextFilesOnly;
                        break;
                    }
                    case displayExcelFilesOnly: {
                        showFiles.setText(getResources().getString(R.string.allfiles));
                        state = defaultMode;
                        break;
                    }
                    case displayTextFilesOnly: {
                        showFiles.setText(getResources().getString(R.string.showTextFilesOnly));
                        state = displayExcelFilesOnly;
                        break;
                    }
                }
            }
        });

    }

    private void setFont() {
        Typeface font  = Typeface.createFromAsset(getActivity().getAssets(), "yekan_font.ttf");
        textView.setTypeface(font);
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
                            touchListener.shareCurrentFile();

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
            CheckBox listViewCheckBox;
            MyViewHolder(View view) {

                super(view);
                dataTextView = ((TextView) view.findViewById(R.id.txt_data));
                listViewCheckBox = ((CheckBox)view.findViewById(R.id.checkbox_listview_items));
                listViewCheckBox.setVisibility(View.VISIBLE);
            }
        }
    }

}
