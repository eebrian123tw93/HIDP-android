package com.brianlu.exhibitionshoppingcart.Seller.AddProduct;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.asksira.bsimagepicker.BSImagePicker;
import com.asksira.bsimagepicker.Utils;
import com.brianlu.exhibitionshoppingcart.R;
import com.bumptech.glide.Glide;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.util.List;

public class AddProductActivity extends AppCompatActivity implements AddProductView, BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener,
        BSImagePicker.ImageLoaderDelegate {

    private AddProductPresenter presenter;

    private EditText nameEditText;
    private EditText contentEditText;
    private EditText priceEditText;
    private EditText countEditText;
    private ImageView pictureImageView;
    private Button uploadButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);


        nameEditText = findViewById(R.id.name_editText);
        contentEditText = findViewById(R.id.content_editText);
        priceEditText = findViewById(R.id.price_editText);
        countEditText = findViewById(R.id.count_editText);
        pictureImageView = findViewById(R.id.picture_imageView);

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        contentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.setContent(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    presenter.setPrice(Double.parseDouble(charSequence.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                    presenter.setPrice(0.0);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        countEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    presenter.setCount(Integer.parseInt(charSequence.toString()));
                } catch (Exception e) {
                    presenter.setCount(0);
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        uploadButton = findViewById(R.id.upload_button);
        uploadButton.setOnClickListener(view -> {
            presenter.uploadProduct();
        });

        pictureImageView.setOnClickListener(view -> {
            BSImagePicker singleSelectionPicker = new BSImagePicker.Builder("com.brianlu.exhibitionshoppingcart.fileprovider")
                    .setMaximumDisplayingImages(0) //Default: Integer.MAX_VALUE. Don't worry about performance :)
                    .setSpanCount(1) //Default: 3. This is the number of columns

                    .setGridSpacing(Utils.dp2px(2)) //Default: 2dp. Remember to pass in a value in pixel.
                    .setPeekHeight(Utils.dp2px(360)) //Default: 360dp. This is the initial height of the dialog.
                    //Default: show. Set this if you don't want user to take photo.
                    .hideGalleryTile() //Default: show. Set this if you don't want to further let user select from a gallery app. In such case, I suggest you to set maximum displaying images to Integer.MAX_VALUE.
                    .setTag("A request ID") //Default: null. Set this if you need to identify which picker is calling back your fragment / activity.
                    //Default: true. Set this if you do not want the picker to dismiss right after selection. But then you will have to dismiss by yourself.
                    .build();
//            singleSelectionPicker.show(getSupportFragmentManager(), "picker");
            singleSelectionPicker.show(getSupportFragmentManager(), "picker");
        });

        presenter = new AddProductPresenter(this);
    }

    @Override
    public void onSetMessage(String message, int type) {
        FancyToast.makeText(this, message, FancyToast.LENGTH_SHORT, type, false).show();
    }

    @Override
    public void loadImage(File imageFile, ImageView ivImage) {

    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {

    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {
        String path = getRealFilePath(this, uri);
        File file = new File(path);
        presenter.setImageFile(file);
        Glide.with(this).load(file).into(pictureImageView);
    }

    @Override
    public void onSetUploadButtonClickable(boolean clickable) {
        uploadButton.setClickable(clickable);
        uploadButton.setEnabled(clickable);
    }

    @Override
    public void onUploadSuccess() {
        finish();
    }

    //    {
//        "productCount":100,
//            "productDesc":"product description",
//            "productImageUrl":
//        "https://cdn.kingstone.com.tw/cvlife/images/product/30600/3060000014029/3060000014029b.jpg",
//                "productName":"Figurine",
//            "productPrice":9990
//    }
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
