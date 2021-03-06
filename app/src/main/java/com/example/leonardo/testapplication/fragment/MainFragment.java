package com.example.leonardo.testapplication.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.leonardo.testapplication.ContainerActivity;
import com.example.leonardo.testapplication.R;
import com.example.leonardo.testapplication.screenGuide.ScreenGuide;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /* CONST */


    /* */
    private ScreenGuide screenGuide;

    /* View components */
    private View view;

    private Button infoFragmentBtn, notificationFragmentBtn;
    private Button fancyShowBtn, tapTargetBtn, materialTapBtn;


    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ContainerActivity CONTAINER = (ContainerActivity) getActivity();

        view = inflater.inflate(R.layout.fragment_main, container, false);
        infoFragmentBtn = (Button) view.findViewById(R.id.info_btn);
        notificationFragmentBtn = (Button) view.findViewById(R.id.notification_btn);
        fancyShowBtn = (Button) view.findViewById(R.id.fancyshowcase_btn);
        tapTargetBtn = (Button) view.findViewById(R.id.tapTargetView_btn);
        materialTapBtn = (Button) view.findViewById(R.id.materialTapTargetPrompt_btn);

        // Screen guides
        screenGuide = new ScreenGuide(getActivity(), new View[]
                {infoFragmentBtn, notificationFragmentBtn, fancyShowBtn, tapTargetBtn, materialTapBtn});
        // end(Screen guideS)

        infoFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CONTAINER.addFragment(new InfoFragment(), true, true, R.id.content_view);
            }
        });

        notificationFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CONTAINER.addFragment(new NotificationFragment(), true, true, R.id.content_view);
            }
        });

        fancyShowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenGuide.fancyShowCase();
            }
        });

        tapTargetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenGuide.tabTargetView();
            }
        });

        materialTapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screenGuide.materialTapTargetPrompt();
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
