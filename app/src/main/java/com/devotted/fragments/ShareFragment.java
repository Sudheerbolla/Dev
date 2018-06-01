package com.devotted.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devotted.R;
import com.devotted.activities.MainActivity;
import com.devotted.utils.ShareUtils;
import com.devotted.utils.views.CustomTextView;

public class ShareFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private MainActivity mainActivity;
    private ImageView imgWhatsapp, imgEmail, imgFb, imgTwitter;
    private CustomTextView txtOthers;

    public ShareFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_share, container, false);
        initComponents();
        return rootView;
    }

    private void initComponents() {
        txtOthers = rootView.findViewById(R.id.txtOthers);
        imgEmail = rootView.findViewById(R.id.imgEmail);
        imgWhatsapp = rootView.findViewById(R.id.imgWhatsapp);
        imgTwitter = rootView.findViewById(R.id.imgTwitter);
        imgFb = rootView.findViewById(R.id.imgFb);

        txtOthers.setOnClickListener(this);
        imgTwitter.setOnClickListener(this);
        imgFb.setOnClickListener(this);
        imgEmail.setOnClickListener(this);
        imgWhatsapp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgWhatsapp:
                shareText(0);
                break;
            case R.id.imgEmail:
                shareText(1);
                break;
            case R.id.imgTwitter:
                shareText(2);
                break;
            case R.id.imgFb:
                shareText(3);
                break;
            case R.id.txtOthers:
                shareText(4);
                break;
            default:
                break;
        }
    }

    private void shareText(int method) {
        switch (method) {
            case 0:
                ShareUtils.shareWhatsapp(mainActivity, getString(R.string.social_share_text), ShareUtils.getAppPlaystoreLink(mainActivity));
                break;
            case 1:
                ShareUtils.shareViaEmail(mainActivity, getString(R.string.social_share_subject), getString(R.string.social_share_text) + ShareUtils.getAppPlaystoreLink(mainActivity));
                break;
            case 2:
                ShareUtils.shareTwitter(mainActivity, getString(R.string.social_share_text), ShareUtils.getAppPlaystoreLink(mainActivity), "", "");
                break;
            case 3:
                ShareUtils.shareFacebook(mainActivity, getString(R.string.social_share_text), ShareUtils.getAppPlaystoreLink(mainActivity));
                break;
            case 4:
                ShareUtils.shareViaIntent(mainActivity, getString(R.string.social_share_subject), getString(R.string.social_share_text) + ShareUtils.getAppPlaystoreLink(mainActivity));
                break;
            default:
                ShareUtils.shareWhatsapp(mainActivity, getString(R.string.social_share_text), ShareUtils.getAppPlaystoreLink(mainActivity));
                break;
        }
    }
}
