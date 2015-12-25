package ir.rastanco.rastanbarcodescanner.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import ir.rastanco.rastanbarcodescanner.dataModel.Barcode;

/**
 * Created by ParisaRashidhi on 22/12/2015.
 */
public class BarcodeDisplayer extends Activity {

    private ArrayList<Barcode> allBarcode;
    private Button btnSave;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        allBarcode=new ArrayList<Barcode>();
        allBarcode= (ArrayList<Barcode>) this.getIntent().getExtras().getSerializable("allBarcode");
        setContentView(R.layout.activity_barcode_display);
        init((RecyclerView) findViewById(R.id.recycler_view));

        btnSave=(Button)findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BarcodeDisplayer.this);
                builder.setTitle(getResources().getString(R.string.type_of_file));
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setItems(new CharSequence[]
                                {getResources().getString(R.string.save_az_txt_file), getResources().getString(R.string.save_az_xls_file)},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    //in both cases we must open ChooseNameActivity
                                    case 0:
                                        //TODO add txt file creator
                                        //startActivity(new Intent(BarcodeDisplayer.this, Confirm.class));
                                        Intent iChooseName=new Intent(BarcodeDisplayer.this,ChooseNameActivity.class);
                                        startActivity(iChooseName);
                                        break;
                                    case 1:
                                        //TODO add xls files creator
                                        //startActivity(new Intent(BarcodeDisplayer.this, Displaying.class));
                                        iChooseName = new Intent(BarcodeDisplayer.this, ChooseNameActivity.class);
                                        startActivity(iChooseName);
                                        break;

                                }
                            }
                        });
                builder.create().show();

            }
        });



    }
    private void init(RecyclerView recyclerView) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(Configuration.activityContext);
        recyclerView.setLayoutManager(mLayoutManager);
        final MyBaseAdapter adapter = new MyBaseAdapter(allBarcode);
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

        MyBaseAdapter(ArrayList<Barcode> allBarcode) {

            for (int i=0;i<allBarcode.size();i++)
                mDataSet.add(allBarcode.get(i).getContent());

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


