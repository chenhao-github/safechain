package com.code.safechain.ui.ecotope;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.code.safechain.R;

/**
 * @Auther: hchen
 * @Date: 2020/7/4 0004
 * @Description:
 */
public class EcotopeFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ecotope, null);
        return view;
    }
}
