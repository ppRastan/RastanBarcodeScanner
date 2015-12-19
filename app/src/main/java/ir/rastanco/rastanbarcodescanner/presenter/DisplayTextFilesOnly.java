package ir.rastanco.rastanbarcodescanner.presenter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.rastanco.rastanbarcodescanner.R;

/*
created by parisaRashidi  on 94/9/27
this class let user to choose only txt files not to see xls files

 */
public class DisplayTextFilesOnly extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/
        View v = inflater.inflate(R.layout.fragment_display_text_files_only, null);
        return v;
    }}
