package ir.rastanco.rastanbarcodescanner.presenter;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import ir.rastanco.rastanbarcodescanner.R;

/*
created by parisaRashidi  on 94/9/27
this class lets user to choose to see just xls files not txt

 */
public class DisplayExcelFilesOnly extends Fragment {
    ImageButton displayTextFiles;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_display_excel_files_only, null);

        return v;

    }

}
