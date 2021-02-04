package com.mristudio.massnundoa.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import com.mristudio.massnundoa.R;
import com.mristudio.massnundoa.adapter.Add_Slider__ImageAdapter;
import com.mristudio.massnundoa.model.ImageModel;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class HomeBannerActivity extends AppCompatActivity implements Add_Slider__ImageAdapter.ImageClick {
    private static final String TAG = "mainActivy";
    private static final Integer SELECT_PDF = 111;
    private ImageView thumbCoverImage;
    private Button btnchoosethumImage, button_clear, buttonSave;

    private RecyclerView catagoryListRechyclerView;
    ProgressDialog progress;
    private Boolean status = false;
    private String coverimageDownloadUrl = null;
    private ArrayList<ImageModel> catlist = new ArrayList<>();
    private String imageTableId = null;
    private Add_Slider__ImageAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    private static StorageReference mstorageReference;
    private static StorageTask storageTask;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference rootRer;
    private DatabaseReference allSlider;
    private DatabaseReference deleteRef;
    private Uri uri;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_banner);
//
        dataBaseRaperance();
        setView();
        fetchMainCatagoryData();

        btnchoosethumImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(HomeBannerActivity.this);

            }
        });
    }

    private void dataBaseRaperance() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        rootRer = FirebaseDatabase.getInstance().getReference();
        allSlider = rootRer.child("AllSlider");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                Log.e(TAG, "urlsdfs = =" + result.getUri());
                Picasso.get().load(uri).into(thumbCoverImage);

            }
        }


    }

    private void fetchMainCatagoryData() {
        allSlider.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    catlist.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        ImageModel images = ds.getValue(ImageModel.class);
                        catlist.add(images);
                    }
                    Log.e(TAG, "catagory Size = " + catlist.size());
                    layoutManager = new GridLayoutManager(HomeBannerActivity.this, 1);
                    catagoryListRechyclerView.setLayoutManager(layoutManager);
                    catagoryListRechyclerView.setFocusable(false);
                    adapter = new Add_Slider__ImageAdapter(catlist, HomeBannerActivity.this);
                    catagoryListRechyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "fetchDataError :" + databaseError.getMessage());
            }
        });

    }

    //Save file from Storage
    private void saveImageIntoStorage(Uri path) {

        final String[] dUrl = {""};
        if (path != null) {
            progressDialog();
            mstorageReference = FirebaseStorage.getInstance().getReference("Pdf_Image_File");
            final StorageReference imageRefarence = mstorageReference.child(System.currentTimeMillis() + "." + getFileExtension(path));

            storageTask = imageRefarence.putFile(path)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.e(TAG,"error taskSnapshot : "+taskSnapshot.toString());
                            imageRefarence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.e(TAG, "+" + uri.toString());

                                    coverimageDownloadUrl = uri.toString();
                                    saveImageToDataBase(coverimageDownloadUrl);


                                }
                            });

                        }
                    })
                    .addOnFailureListener(this, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Toast.makeText(AddHomeCatagory.this, "Error: "+e, Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "erro:" + e.getMessage());

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            if (taskSnapshot.getTask().isSuccessful()) {
                                Log.e(TAG,"error task :"+taskSnapshot.toString());
                            }
                        }
                    });


        }
    }

    //saveImageToDatabase
    private void saveImageToDataBase(String path) {
        final String catId = allSlider.push().getKey();
        ImageModel images = new ImageModel(catId, path);
        allSlider.child(catId).setValue(images).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toasty.success(HomeBannerActivity.this, "Successfully Saved.").show();
                    progress.dismiss();
                    fetchMainCatagoryData();
                    clear();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toasty.success(HomeBannerActivity.this, "" + e.getMessage()).show();
                progress.dismiss();
            }
        });
    }

    //delete existing file
    private void deleteexistingPdf(String imageurl, final Uri path) {

        FirebaseStorage imageReference = FirebaseStorage.getInstance();
        StorageReference imageRef = imageReference.getReferenceFromUrl(imageurl);


        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                saveImageIntoStorage(path);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "" + e.getMessage());
            }
        });
    }

    //get Making Image Url
    private String getFileExtension(Uri uri) {
        ContentResolver cR = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void setView() {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Top Home Banner");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        thumbCoverImage = findViewById(R.id.thumbCoverImage);
        btnchoosethumImage = findViewById(R.id.btnchoosethumImage);
        button_clear = findViewById(R.id.button_clear);
        buttonSave = findViewById(R.id.buttonSave);
        catagoryListRechyclerView = findViewById(R.id.sliderImageRV);
        catagoryListRechyclerView.setHasFixedSize(true);
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status) {
                    if (deleteTable()) {
                        Toasty.success(HomeBannerActivity.this, "Successfully Deleted.").show();
                        clear();
                    } else {
                        Toasty.warning(HomeBannerActivity.this, "Something was wrong.").show();
                    }
                } else {
                    clear();
                }
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coverimageDownloadUrl != null) {
                    deleteexistingPdf(coverimageDownloadUrl, uri);

                } else {
                    saveImageIntoStorage(uri);
                }
            }
        });

    }

    //delete pdf Dta from child
    private boolean deleteTable() {
        if (imageTableId != null) {
            deleteRef = allSlider.child(imageTableId);
            deleteRef.removeValue();
        }
        return true;
    }

    void clear() {
        Picasso.get().load(R.drawable.ima).into(thumbCoverImage);
        coverimageDownloadUrl = null;
        imageTableId = null;
        buttonSave.setText("Save");
        button_clear.setText("Clear All");
        status = false;
    }

    public void progressDialog() {
        progress = new ProgressDialog(this);
        progress.setMessage("Please Wait.....");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    @Override
    public void sliderimageClick(ImageModel images) {
        Picasso.get().load(images.getImageUrl()).resize(100, 100).centerCrop().into(thumbCoverImage);
        coverimageDownloadUrl = images.getImageUrl();
        imageTableId = images.getImageId();
        buttonSave.setText("Update");
        button_clear.setText("Delete");
        status = true;
    }
}