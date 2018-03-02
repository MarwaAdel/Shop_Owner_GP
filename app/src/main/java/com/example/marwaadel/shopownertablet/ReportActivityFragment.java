package com.example.marwaadel.shopownertablet;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReportActivityFragment extends Fragment {


    public static final String TAG = ReportActivityFragment.class.getSimpleName();
    static final String DETAIL_REPORT= "DETAIL_REPORT";
    public ReportActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_report, container, false);
    }
}
