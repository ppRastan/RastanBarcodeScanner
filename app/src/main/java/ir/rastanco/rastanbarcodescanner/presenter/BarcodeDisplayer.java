package ir.rastanco.rastanbarcodescanner.presenter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.rastanco.rastanbarcodescanner.R;
import ir.rastanco.rastanbarcodescanner.Utility.ListViewAdapter;
import ir.rastanco.rastanbarcodescanner.Utility.SwipeToDismissTouchListener;

/**
 * Created by ParisaRashidhi on 22/12/2015.
 */
public class BarcodeDisplayer extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment_handler, null);
        init((ListView) v.findViewById(R.id.list_view));
        return v;
    }
    private void init(ListView listView)
    {
        final MyBaseAdapter adapter = new MyBaseAdapter();
        listView.setAdapter(adapter);
        final SwipeToDismissTouchListener<ListViewAdapter> touchListener =
                new SwipeToDismissTouchListener<>(
                        new ListViewAdapter(listView),
                        new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListViewAdapter view, int position) {
                                adapter.remove(position);
                            }
                        });
        listView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                }
            }
        });
    }

    static class MyBaseAdapter extends BaseAdapter {

        private static final int SIZE = 100;

        private final List<String> mDataSet = new ArrayList<>();

        MyBaseAdapter() {
            //TODO add files from database to listview by mDataSet.addAll();
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
            mDataSet.add("barcode 17");
            mDataSet.add("barcode 18");
            mDataSet.add("barcode 19");
            mDataSet.add("barcode 20");
            mDataSet.add("barcode 21");
        }

        @Override
        public int getCount() {
            return mDataSet.size();
        }

        @Override
        public String getItem(int position) {
            return mDataSet.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void remove(int position) {
            mDataSet.remove(position);
            notifyDataSetChanged();
        }

        static class ViewHolder {
            TextView dataTextView;
            ViewHolder(View view) {
                dataTextView = ((TextView) view.findViewById(R.id.txt_data));
                view.setTag(this);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder = convertView == null
                    ? new ViewHolder(convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false))
                    : (ViewHolder) convertView.getTag();

            viewHolder.dataTextView.setText(mDataSet.get(position));
            return convertView;
        }
    }
}

