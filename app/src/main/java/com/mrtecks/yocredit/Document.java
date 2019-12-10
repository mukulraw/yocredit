package com.mrtecks.yocredit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mrtecks.yocredit.updatePOJO.Data;
import com.mrtecks.yocredit.updatePOJO.updateBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Document extends AppCompatActivity {
    Button submit;

    EditText amount , tenover;
    CircleImageView panpic , afpic , abpic , ccpic;
    Button upload1 , upload2 , upload3 , upload4;
    ProgressBar progress;

    Uri uri1 , uri2 , uri3 , uri4;
    File f1 , f2 , f3 , f4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        submit = findViewById(R.id.submit);
        progress = findViewById(R.id.progressBar);
        amount = findViewById(R.id.amount);
        tenover = findViewById(R.id.tenover);
        panpic = findViewById(R.id.pan_pic);
        afpic = findViewById(R.id.afpic);
        abpic = findViewById(R.id.abpic);
        ccpic = findViewById(R.id.ccpic);
        upload1 = findViewById(R.id.upload1);
        upload2 = findViewById(R.id.upload2);
        upload3 = findViewById(R.id.upload3);
        upload4 = findViewById(R.id.upload4);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Document.this , Status.class);
                startActivity(intent);
                finishAffinity();

            }
        });


        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {
                        "Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Document.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f1 = new File(file);
                            Log.d("abso", f1.getAbsolutePath());
                            try {
                                f1.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri1 = FileProvider.getUriForFile(Objects.requireNonNull(Document.this), BuildConfig.APPLICATION_ID + ".provider", f1);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 11);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 12);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });

        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {
                        "Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Document.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f2 = new File(file);
                            Log.d("abso", f2.getAbsolutePath());
                            try {
                                f2.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri2 = FileProvider.getUriForFile(Objects.requireNonNull(Document.this), BuildConfig.APPLICATION_ID + ".provider", f2);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri2);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 21);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 22);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });


        upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {
                        "Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Document.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f3 = new File(file);
                            Log.d("abso", f3.getAbsolutePath());
                            try {
                                f3.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri3 = FileProvider.getUriForFile(Objects.requireNonNull(Document.this), BuildConfig.APPLICATION_ID + ".provider", f3);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri3);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 31);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 32);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });


        upload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {
                        "Take Photo from Camera",
                        "Choose from Gallery",
                        "Cancel"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Document.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo from Camera")) {
                            final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Folder/";
                            File newdir = new File(dir);
                            try {
                                newdir.mkdirs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String file = dir + DateFormat.format("yyyy-MM-dd_hhmmss", new Date()).toString() + ".jpg";


                            f4 = new File(file);
                            Log.d("abso", f4.getAbsolutePath());
                            try {
                                f4.createNewFile();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            uri4 = FileProvider.getUriForFile(Objects.requireNonNull(Document.this), BuildConfig.APPLICATION_ID + ".provider", f4);

                            Intent getpic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            getpic.putExtra(MediaStore.EXTRA_OUTPUT, uri4);
                            getpic.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            startActivityForResult(getpic, 41);
                        } else if (items[item].equals("Choose from Gallery")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 42);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = amount.getText().toString();
                String t = tenover.getText().toString();

                if (uri1 != null)
                {
                    if (uri2 != null)
                    {
                        if (uri3 != null)
                        {
                            if (uri4 != null)
                            {
                                if (a.length() > 0)
                                {
                                    if (t.length() > 0)
                                    {


                                        MultipartBody.Part body1 = null;

                                        try {

                                            RequestBody reqFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), f1);
                                            body1 = MultipartBody.Part.createFormData("pan", f1.getName(), reqFile1);


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        MultipartBody.Part body2 = null;

                                        try {

                                            RequestBody reqFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), f2);
                                            body2 = MultipartBody.Part.createFormData("aadhar1", f2.getName(), reqFile2);


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        MultipartBody.Part body3 = null;

                                        try {

                                            RequestBody reqFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), f3);
                                            body3 = MultipartBody.Part.createFormData("aadhar2", f3.getName(), reqFile3);


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        MultipartBody.Part body4 = null;

                                        try {

                                            RequestBody reqFile4 = RequestBody.create(MediaType.parse("multipart/form-data"), f4);
                                            body4 = MultipartBody.Part.createFormData("passbook", f4.getName(), reqFile4);


                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                        progress.setVisibility(View.VISIBLE);

                                        Bean b = (Bean) getApplicationContext();

                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.baseurl)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                        Call<updateBean> call = cr.update_document(
                                                SharePreferenceUtils.getInstance().getString("id") ,
                                                a ,
                                                t ,
                                                body1 ,
                                                body2 ,
                                                body3 ,
                                                body4
                                        );

                                        call.enqueue(new Callback<updateBean>() {
                                            @Override
                                            public void onResponse(Call<updateBean> call, Response<updateBean> response) {

                                                if (response.body().getStatus().equals("1"))
                                                {
                                                    Data item = response.body().getData();
                                                    SharePreferenceUtils.getInstance().saveString("pan" , item.getPan());
                                                    SharePreferenceUtils.getInstance().saveString("aadhar1" , item.getAadhar1());
                                                    SharePreferenceUtils.getInstance().saveString("aadhar2" , item.getAadhar2());
                                                    SharePreferenceUtils.getInstance().saveString("passbook" , item.getPassbook());
                                                    SharePreferenceUtils.getInstance().saveString("amount" , item.getAmount());
                                                    SharePreferenceUtils.getInstance().saveString("tenover" , item.getTenover());
                                                    Toast.makeText(Document.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(Document.this , Status.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else
                                                {
                                                    Toast.makeText(Document.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                                progress.setVisibility(View.GONE);

                                            }

                                            @Override
                                            public void onFailure(Call<updateBean> call, Throwable t) {
                                                progress.setVisibility(View.GONE);
                                            }
                                        });


                                    }
                                    else
                                    {
                                        Toast.makeText(Document.this, "Invalid tenover", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(Document.this, "Invalid amount", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(Document.this, "Please upload Cancelled check or Pass book", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Document.this, "Please upload your Aadhar pic", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Document.this, "Please upload your Aadhar pic", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Document.this, "Please upload your PAN pic", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 12 && resultCode == RESULT_OK && null != data) {
            uri1 = data.getData();

            Log.d("uri", String.valueOf(uri1));

            String ypath = getPath(Document.this, uri1);
            assert ypath != null;
            f1 = new File(ypath);

            Log.d("path", ypath);


            ImageLoader loader = ImageLoader.getInstance();

            Bitmap bmp = loader.loadImageSync(String.valueOf(uri1));

            Log.d("bitmap", String.valueOf(bmp));

            panpic.setImageBitmap(bmp);

        } else if (requestCode == 11 && resultCode == RESULT_OK) {
            panpic.setImageURI(uri1);
        }


        if (requestCode == 22 && resultCode == RESULT_OK && null != data) {
            uri2 = data.getData();

            Log.d("uri", String.valueOf(uri2));

            String ypath = getPath(Document.this, uri2);
            assert ypath != null;
            f2 = new File(ypath);

            Log.d("path", ypath);


            ImageLoader loader = ImageLoader.getInstance();

            Bitmap bmp = loader.loadImageSync(String.valueOf(uri2));

            Log.d("bitmap", String.valueOf(bmp));

            afpic.setImageBitmap(bmp);

        } else if (requestCode == 21 && resultCode == RESULT_OK) {
            afpic.setImageURI(uri2);
        }



        if (requestCode == 32 && resultCode == RESULT_OK && null != data) {
            uri3 = data.getData();

            Log.d("uri", String.valueOf(uri3));

            String ypath = getPath(Document.this, uri3);
            assert ypath != null;
            f3 = new File(ypath);

            Log.d("path", ypath);


            ImageLoader loader = ImageLoader.getInstance();

            Bitmap bmp = loader.loadImageSync(String.valueOf(uri3));

            Log.d("bitmap", String.valueOf(bmp));

            abpic.setImageBitmap(bmp);

        } else if (requestCode == 31 && resultCode == RESULT_OK) {
            abpic.setImageURI(uri3);
        }


        if (requestCode == 42 && resultCode == RESULT_OK && null != data) {
            uri4 = data.getData();

            Log.d("uri", String.valueOf(uri4));

            String ypath = getPath(Document.this, uri4);
            assert ypath != null;
            f4 = new File(ypath);

            Log.d("path", ypath);


            ImageLoader loader = ImageLoader.getInstance();

            Bitmap bmp = loader.loadImageSync(String.valueOf(uri4));

            Log.d("bitmap", String.valueOf(bmp));

            ccpic.setImageBitmap(bmp);

        } else if (requestCode == 41 && resultCode == RESULT_OK) {
            ccpic.setImageURI(uri4);
        }

    }

    private static String getPath(final Context context, final Uri uri) {

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static String getDataColumn(Context context, Uri uri, String selection,
                                        String[] selectionArgs) {

        final String column = "_data";
        final String[] projection = {
                column
        };
        try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                null)) {
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        }
        return null;
    }

}
