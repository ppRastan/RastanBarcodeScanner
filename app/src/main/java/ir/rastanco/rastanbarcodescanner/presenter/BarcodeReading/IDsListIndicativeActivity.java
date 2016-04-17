package ir.rastanco.rastanbarcodescanner.presenter.BarcodeReading;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.Utility.Configuration;
import ir.rastanco.rastanbarcodescanner.dataModel.Barcode;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagement.ListViewHandling.OnItemClickListener;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagement.ListViewHandling.RecyclerViewAdapter;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagement.ListViewHandling.SwipeToDismissTouchListener;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagement.ListViewHandling.SwipeableItemClickListener;
import ir.rastanco.rastanbarcodescanner.presenter.FilesManagement.MainActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ParisaRashidhi on 22/12/2015.
 */
public class IDsListIndicativeActivity extends Activity {

    private ArrayList<Barcode> listOfIDs;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_displayer);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        this.createPage();


    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new CalligraphyContextWrapper(newBase));
    }

    private void createPage() {
        ImageButton homeButton = (ImageButton) findViewById(R.id.choose_name_activity_home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IDsListIndicativeActivity.this, MainActivity.class));
            }
        });
        ChooseNameActivity chooseNameActivity = new ChooseNameActivity();
        ImageButton saveButton = (ImageButton) findViewById(R.id.appbar_barcode_Indicative_check_btn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Todo save barCodes in file
                startActivity(new Intent(IDsListIndicativeActivity.this, ChooseNameActivity.class));

            }
        });

        listOfIDs = new ArrayList<>();
        listOfIDs = (ArrayList<Barcode>) this.getIntent().getExtras().getSerializable("cameraBarcodeReaderListOfIDs");
        chooseNameActivity.setListOfIDs(listOfIDs);
        init((RecyclerView) findViewById(R.id.recycler_view));
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(IDsListIndicativeActivity.this, CameraBarcodeReader.class));

    }


    private void init(RecyclerView recyclerView) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Configuration.activityContext);
        recyclerView.setLayoutManager(mLayoutManager);
        final MyBaseAdapter adapter = new MyBaseAdapter(listOfIDs);
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
        recyclerView.addOnScrollListener((RecyclerView.OnScrollListener) touchListener.makeScrollListener());
        recyclerView.addOnItemTouchListener(new SwipeableItemClickListener(Configuration.activityContext,
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

        private final List<String> mDataSet = new ArrayList<>();

        MyBaseAdapter(ArrayList<Barcode> allBarcode) {

            for (int i = 0; i < allBarcode.size(); i++)
                mDataSet.add(allBarcode.get(i).getContent());
//// TODO: 11/01/2016  replace below code instead of above so that user can see number of barCodes scanned
//            for (int i = 0; i < allBarcode.size(); i++)
//                mDataSet.add(i,allBarcode.get(i).getContent() + i);

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int position) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.dataTextView.setText(mDataSet.get(position));
            holder.iconImageView.setImageResource(R.drawable.ic_action);
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
            final ImageView iconImageView;

            MyViewHolder(View view) {
                super(view);
                dataTextView = ((TextView) view.findViewById(R.id.txt_data));
                iconImageView = (ImageView) view.findViewById(R.id.img_icon);
            }
        }
    }
}


