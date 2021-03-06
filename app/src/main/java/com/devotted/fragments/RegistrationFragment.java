package com.devotted.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.devotted.R;
import com.devotted.activities.SplashActivity;
import com.devotted.utils.Constants;
import com.devotted.utils.DialogUtils;
import com.devotted.utils.LocalStorage;
import com.devotted.utils.RuntimePermissionUtils;
import com.devotted.utils.StaticUtils;
import com.devotted.utils.views.CustomEditText;
import com.devotted.utils.views.CustomTextView;

import java.util.ArrayList;
import java.util.List;

public class RegistrationFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private SplashActivity splashActivity;
    private CustomTextView txtRegister;
    private CustomEditText edtPassword, edtEmailAddress, edtName, edtMobileNumber, edtConfirmPassword;
    private ImageView imgAddProfileIcon;
    private RelativeLayout cardProfile;
    private Uri IMAGE_CAPTURE_URI;
    private Spinner spinnerCountry;
    private String[] countryCodesArray;
    private ImageView imgBack;

    private View.OnClickListener cameraClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            openCamera();
        }
    };

    private View.OnClickListener galleryClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clickOnGallery();
        }
    };

    public RegistrationFragment() {
    }

    public static RegistrationFragment newInstance(Bundle bundle) {
        RegistrationFragment categoryDetailsFragment = new RegistrationFragment();
        categoryDetailsFragment.setArguments(bundle);
        return categoryDetailsFragment;
    }

    public static RegistrationFragment newInstance() {
        RegistrationFragment categoryDetailsFragment = new RegistrationFragment();
        categoryDetailsFragment.setArguments(new Bundle());
        return categoryDetailsFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        initComponents();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashActivity = (SplashActivity) getActivity();
    }

    private void initComponents() {
        countryCodesArray = new String[]{"+91", "+1"};
        setReferences();
        setAdapter();
        setListeners();
    }

    private void setReferences() {
        cardProfile = rootView.findViewById(R.id.cardProfile);
        edtEmailAddress = rootView.findViewById(R.id.edtEmailAddress);
        edtName = rootView.findViewById(R.id.edtName);
        edtMobileNumber = rootView.findViewById(R.id.edtMobileNumber);
        edtPassword = rootView.findViewById(R.id.edtPassword);
        edtConfirmPassword = rootView.findViewById(R.id.edtConfirmPassword);
        txtRegister = rootView.findViewById(R.id.txtRegister);
        imgAddProfileIcon = rootView.findViewById(R.id.imgAddProfileIcon);
        spinnerCountry = rootView.findViewById(R.id.spinnerCountry);
        imgBack = rootView.findViewById(R.id.imgBack);
    }

    private void setListeners() {
        txtRegister.setOnClickListener(this);
        cardProfile.setOnClickListener(this);
        imgAddProfileIcon.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        spinnerCountry.setSelection(0, true);
        edtEmailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    edtPassword.setEnabled(true);
                    edtConfirmPassword.setEnabled(true);
                } else {
                    edtPassword.setEnabled(false);
                    edtConfirmPassword.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPassword.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
        edtConfirmPassword.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public void onDestroyActionMode(ActionMode mode) {
            }

            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }
        });
    }

    private void setAdapter() {
        ArrayAdapter spinnerAdapter = new ArrayAdapter(splashActivity, android.R.layout.simple_spinner_item, countryCodesArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountry.setAdapter(spinnerAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtRegister:
                String message = checkValidations();
                if (!TextUtils.isEmpty(message)) {
                    StaticUtils.showToast(splashActivity, message);
                } else {
                    confirmMobileNumber();
                }
//                navigateToVerificationScreen();
                break;
            case R.id.imgAddProfileIcon:
            case R.id.cardProfile:
                if (isStoragePermissionAvailable()) {
                    openImagePickerDialog();
                }
                break;
            case R.id.imgBack:
                splashActivity.popBackStack();
                break;
            default:
                break;
        }
    }

    private void confirmMobileNumber() {
        DialogUtils.showConfirmMobileNumberDialog(splashActivity, spinnerCountry.getSelectedItem().toString() + " - " + edtMobileNumber.getText().toString().trim(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForSmsReadingPermission();
            }
        });
    }

    private void navigateToVerificationScreen() {
        LocalStorage.getInstance(splashActivity).putString(LocalStorage.PREF_USER_NAME, edtName.getText().toString().trim());
        splashActivity.replaceFragment(VerificationCodeFragment.newInstance());
    }

    private String checkValidations() {
        String message = "";
        if (TextUtils.isEmpty(edtName.getText().toString().trim())) {
            edtName.requestFocus();
            return getString(R.string.please_enter_fullname);
        }
        String emailId = edtEmailAddress.getText().toString().trim();
//        if (TextUtils.isEmpty(emailId)) {
//            edtEmailAddress.requestFocus();
//            return getString(R.string.please_enter_email_address);
//        }
        if (!TextUtils.isEmpty(emailId)) {
            if (!StaticUtils.isValidEmail(emailId)) {
                edtEmailAddress.requestFocus();
                return getString(R.string.please_enter_a_valid_email_address);
            }
        }
        String mobileNumber = edtMobileNumber.getText().toString().trim();
        if (TextUtils.isEmpty(mobileNumber)) {
            edtMobileNumber.requestFocus();
            return getString(R.string.please_enter_mobile_number);
        }
        if (mobileNumber.length() < 10) {
            edtMobileNumber.requestFocus();
            return getString(R.string.please_enter_a_valid_mobile_number);
        }
        String password = edtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            edtPassword.requestFocus();
            return getString(R.string.please_enter_password);
        }
        if (password.length() < 6) {
            edtPassword.requestFocus();
            return getString(R.string.please_enter_a_valid_password);
        }

        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(confirmPassword)) {
            edtConfirmPassword.requestFocus();
            return getString(R.string.please_enter_confirm_password);
        }
        if (confirmPassword.length() < 6) {
            edtConfirmPassword.requestFocus();
            return getString(R.string.please_enter_a_valid_password);
        }
        if (!password.equals(confirmPassword)) {
            edtConfirmPassword.requestFocus();
            return getString(R.string.new_password_and_confirm_password_should_match);
        }

        return message;
    }

    private boolean isStoragePermissionAvailable() {
        List<String> neededPermissions = new ArrayList<>();

        if (RuntimePermissionUtils.checkPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            neededPermissions.add(Manifest.permission.CAMERA);
        }
        if (RuntimePermissionUtils.checkPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            neededPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (RuntimePermissionUtils.checkPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            neededPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        final String[] permissions = neededPermissions.toArray(new String[neededPermissions.size()]);

        if (neededPermissions.size() > 0) {
            requestForPermission(permissions, Constants.STORAGE_AND_CAMERA);
            return false;
        } else return true;
    }

    private void checkForSmsReadingPermission() {
        if (RuntimePermissionUtils.checkPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestForPermission(Manifest.permission.RECEIVE_SMS, Constants.READ_SMS_CODE);
        } else {
            StaticUtils.showToast(splashActivity, getString(R.string.otp_sent_to_that_mobile_number_for_verification));
            navigateToVerificationScreen();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.STORAGE_AND_CAMERA:
                if (StaticUtils.isAllPermissionsGranted(grantResults)) {
                    openImagePickerDialog();
                } else {
//                    StaticUtils.showToast(splashActivity, getString(R.string.permission_is_mandatory));
                    StaticUtils.showToast(splashActivity, getString(R.string.permission_is_mandatory));
//                    isStoragePermissionAvailable();
                }
                break;
            case Constants.READ_SMS_CODE:
                if (RuntimePermissionUtils.checkPermission(getActivity(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                    StaticUtils.showToast(splashActivity, getString(R.string.please_enter_otp_manually_since_you_have_denied_permission_for_reading_sms));
                }
                navigateToVerificationScreen();
                break;
            default:
                break;
        }
    }

    private void openImagePickerDialog() {
        DialogUtils.imagePickerDialog(splashActivity, cameraClick, galleryClick, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgAddProfileIcon.setImageResource(R.drawable.ic_placeholder);
            }
        });
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(splashActivity.getPackageManager()) != null) {
            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            IMAGE_CAPTURE_URI = StaticUtils.getOutputMediaFileUri(splashActivity);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, IMAGE_CAPTURE_URI);
            startActivityForResult(takePictureIntent, Constants.REQUEST_IMAGE_CAPTURE);
        }
    }

    private void clickOnGallery() {
        try {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            photoPickerIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, Constants.REQUEST_IMAGE_GALLERY);
        } catch (Exception e) {
            e.printStackTrace();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, Constants.REQUEST_IMAGE_GALLERY);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == Constants.REQUEST_IMAGE_CAPTURE) {
//                    Bitmap bitmap1 = StaticUtils.getImageFromCamera(splashActivity, IMAGE_CAPTURE_URI);
//                    File cameraFile = StaticUtils.bitmapToFile(bitmap1);
                    notifyNewImage(IMAGE_CAPTURE_URI.getPath());
                    setImageAttachment(IMAGE_CAPTURE_URI);
                } else if (requestCode == Constants.REQUEST_IMAGE_GALLERY) {
                    try {
                        if (data != null) {
                            Uri _uri = data.getData();
                            if (_uri != null) {
                                setImageAttachment(_uri);
//                                Cursor cursor = splashActivity.getContentResolver().query(_uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
//                                if (cursor != null) {
//                                    cursor.moveToFirst();
//                                    try {
//                                        String imagePath = cursor.getString(0);
//                                        profilePicBitmap = StaticUtil.getResizeImage(chatActivity, StaticUtil.PROFILE_IMAGE_SIZE, StaticUtil.PROFILE_IMAGE_SIZE, ScalingUtilities.ScalingLogic.CROP, true, imagePath, _uri);
//                                        File file = new File(String.valueOf(StaticUtil.bitmapToFile(profilePicBitmap)));
//                                        sendImageAttachment(file);
//                                    } catch (Exception e) {
//                                        e.getStackTrace();
//                                    }
//                                    cursor.close();
//                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
//                statusMessage.setText(String.format(getString(R.string.barcode_error),
//                        CommonStatusCodes.getStatusCodeString(resultCode)));
//                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyNewImage(String imagePath) {
        MediaScannerConnection.scanFile(splashActivity, new String[]{imagePath}, null, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
            }
        });
    }

    private void setImageAttachment(Uri cameraFile) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_placeholder)
                .fitCenter()
                .error(R.drawable.ic_placeholder);

//        RequestOptions optionsStretch = new RequestOptions()
//                .placeholder(R.mipmap.ic_launcher)
//                .fitCenter()
//                .error(R.mipmap.ic_launcher);

        Glide.with(this)
                .load(cameraFile)
                .apply(options)
                .into(imgAddProfileIcon);

//        imgBanner.setImageURI(cameraFile);
//        Glide.with(this).load(cameraFile).apply(optionsStretch).into(imgBanner);

    }

}
