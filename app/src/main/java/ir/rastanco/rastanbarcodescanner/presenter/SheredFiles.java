package ir.rastanco.rastanbarcodescanner.presenter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.rastanco.rastanbarcodescanner.R;
/*
created by parisaRashidi  on 94/9/27
this class let user to see all shere files and can remove them from list
 */

public class SheredFiles extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shered_files, null);
        return v;
    }
}