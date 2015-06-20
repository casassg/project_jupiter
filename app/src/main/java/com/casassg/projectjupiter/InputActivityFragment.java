package com.casassg.projectjupiter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.casassg.projectjupiter.model.Moment;


/**
 * A placeholder fragment containing a simple view.
 */
public class InputActivityFragment extends Fragment {

    private EditText title;
    private RatingBar rating;

    public InputActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_input, container, false);
        Button capture = (Button) root.findViewById(R.id.capture);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
        rating = (RatingBar) root.findViewById(R.id.ratingBar);
        title = (EditText) root.findViewById(R.id.title);
        return root;
    }

    private void insertData() {
        Moment moment = extractMoment();
        Utility.saveMoment(moment, getActivity());
        getActivity().finish();
    }

    private Moment extractMoment() {
        Moment ret = new Moment();
        ret.setRating(rating.getRating());
        ret.setTitle(title.getText().toString());
        return ret;
    }
}
