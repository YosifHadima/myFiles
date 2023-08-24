package com.foru.mainfarahy.ui.home;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.foru.mainfarahy.R;
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
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RetriveActivity extends AppCompatActivity {
    private boolean isImageExpanded = false;
    private LinearLayout layout;
    private FirebaseAuth mAuth;
    int position=0;

    private ProgressBar progressBar;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private static final int CALL_PERMISSION_REQUEST_CODE = 3;

    private Uri imageUri;
    private StorageReference mStorageRef;
    private ImageView profileButton;
    private ImageView profileImageView;
    String currentUser;

    private TextView displayTextView;


    private ImageView editeDescription;

    EditText phoneNumber;
    EditText facebookLink;
    EditText instgramLink;

    TextView phoneNumberonPage;
    String facebooklinkTemp;
    String InstgramLinkTemp;
    TextView businessName;
    TextView storeName;
    String storeNameTemp;
    private String selectedCategory = ""; // Store the selected category

    //private PrefManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrive);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

       // prefManager = new PrefManager(this);
        //   if (!prefManager.isFirstTimeLaunch()) {
    //    prefManager.setFirstTimeLaunch(false);
        //  }
        // Get the Intent that started this activity

        Intent intent = getIntent();

        // Get the string extra from the Intent
        String receivedData = intent.getStringExtra("STRING_EXTRA");
        //mAuth = FirebaseAuth.getInstance();
        currentUser=receivedData ; // take input from prev activity
        // Initialize the ProgressBar
        progressBar = findViewById(R.id.progressBar);
        layout = findViewById(R.id.imageLayout);
        profileButton=findViewById(R.id.changeProfile_id);
        profileImageView=findViewById(R.id.profileImageView);
        displayTextView=findViewById(R.id.Description_Id);
        editeDescription=findViewById(R.id.editeDescription_id);
        phoneNumberonPage=findViewById(R.id.phoneNumer_id);
        businessName=findViewById(R.id.BusinessName_id);
        storeName=findViewById(R.id.StoreName_id);

        //  DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads/"+currentUser.getEmail());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads/"+currentUser);
        //  Log.e("my joe id ", currentUser.getUid());
        progressBar.setVisibility(ProgressBar.VISIBLE);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                layout.removeAllViews();




                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //  Log.e("my joe ", snapshot.getKey() );
                    //  Log.e("my joe ch ", snapshot.toString());

                    String text = snapshot.child("text").getValue(String.class);


                    Iterable<DataSnapshot> imageUrls = snapshot.child("imageUrls").getChildren();


                    if (text != null && imageUrls != null) {

                        View view = getLayoutInflater().inflate(R.layout.item_upload, null);

                        TextView textView = view.findViewById(R.id.textView);
                        ImageView imageView = view.findViewById(R.id.imageView);


                        textView.setText(text);

                        RequestOptions requestOptions = new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL);

                        for (DataSnapshot imageUrlSnapshot : imageUrls) {

                            String imageUrl = imageUrlSnapshot.getValue(String.class);

                            if (imageUrl != null) {
/*
                                 imageView = new ImageView(RetrieveActivity.this);
                               // imageView.setLayoutParams(new LinearLayout.LayoutParams(
                                 //       LinearLayout.LayoutParams.WRAP_CONTENT,
                                   //     LinearLayout.LayoutParams.WRAP_CONTENT));
                                 imageView.setLayoutParams(new LinearLayout.LayoutParams(
                                      400,
                                     500));
                                Glide.with(RetrieveActivity.this)
                                        .load(imageUrl)
                                        .apply(requestOptions)
                                        .into(imageView);
                                layout.addView(imageView);
                                */
                                //  for (int i = 0; i < 2; i++) {
                                View dynamicView = createDynamicView(position ,  imageUrl, requestOptions);
                                layout.addView(dynamicView);
                                position++;
                                // }
                            }
                        }

                        //layout.addView(view);

                        //////////////////////////////////////


                    }
                }




            }
            // Method to create dynamic views (you can customize this as per your requirements)
            private View createDynamicView(final int position ,  String imageUrl ,RequestOptions requestOptions) {

                ImageView imageView=new ImageView(getApplicationContext()) ;
                if (imageUrl != null) {

                    imageView = new ImageView(RetriveActivity.this);
                    // imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    //       LinearLayout.LayoutParams.WRAP_CONTENT,
                    //     LinearLayout.LayoutParams.WRAP_CONTENT));
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(
                            800,
                            1000));
                    Glide.with(getApplicationContext())
                            .load(imageUrl)
                            .apply(requestOptions)
                            .into(imageView);
                    //   layout.addView(imageView);
                }

                // Log.e("my joe url name ",  getImageNameFromURL(imageUrl));
                TextView dynamicView = new TextView(getApplicationContext());
                // Set layout parameters and other properties for the dynamic view
                // ...
                // dynamicView.setText("Dynamic View " + position);
                ImageView finalImageView = imageView;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //showEnlargedImage(finalImageView, imageUrl);

                    }




                });



                imageView.setPadding(10,10,10,10);
                return imageView;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });



        //mStorageRef= FirebaseStorage.getInstance().getReference("uploads").child(currentUser.getEmail()).child("profile_images.jpg");
        // Load the profile picture from Firebase and set it in the ImageView
        loadProfilePicture();
        // Load the text from Firebase and set it in the TextView
        loadText();
        LoadPhoneNumber();
        loadFacebookLink();
        loadInstgramLink();
        loadbusinessName();
        loadStoreName();
    }


    public void onProfileImageClicked(View view) {
        // Implement the logic to capture an image or pick from the gallery
        // For simplicity, I'll show an example for picking from the gallery

        Intent pickImageIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImageIntent, REQUEST_IMAGE_PICK);
    }


    public String getImageNameFromURL(String inputString){
        // String inputString = "This%2Fis%2Fa%2Fsample%2Fstring";
        String patternString = ".com%2F(.*?).image%2F";

        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(inputString);

        while (matcher.find()) {
            String match = matcher.group(1);
            return match;
        }
        return patternString;
    }


    private void loadFacebookLink(){

        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads/"+currentUser+"/"+currentUser);

        mDatabaseRef.child("facebookLink")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String text = dataSnapshot.getValue(String.class);
                        if (text != null) {
                            // facebookLink.setText(text);
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                            facebooklinkTemp=text;
                        }



                        // Change this delay to suit your loading duration
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error if the database retrieval is canceled.
                        // For simplicity, we'll set a default text.

                    }
                });
    }

    private void loadInstgramLink(){
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads/"+currentUser+"/"+currentUser);

        mDatabaseRef.child("instgramLink")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String text = dataSnapshot.getValue(String.class);
                        if (text != null) {
                            //instgramLink.setText(text);
                            InstgramLinkTemp=text;
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }



                        // Change this delay to suit your loading duration
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error if the database retrieval is canceled.
                        // For simplicity, we'll set a default text.
                        //  instgramLink.setText("Failed to load text");
                    }
                });
    }

    private void loadStoreName(){
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads/"+currentUser+"/"+currentUser);

        mDatabaseRef.child("StoreName")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String text = dataSnapshot.getValue(String.class);
                        if (text != null) {
                            //instgramLink.setText(text);
                            storeNameTemp=text;
                            storeName.setText(storeNameTemp);
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }



                        // Change this delay to suit your loading duration
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error if the database retrieval is canceled.
                        // For simplicity, we'll set a default text.
                        //  instgramLink.setText("Failed to load text");
                    }
                });
    }

    private void loadProfilePicture() {
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads/"+currentUser+"/"+currentUser);

        mDatabaseRef.child("profileImage")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String imageUrl = dataSnapshot.getValue(String.class);
                        if (imageUrl != null) {
                            Glide.with(getApplicationContext())
                                    .load(imageUrl)
                                    .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                                    .error(R.drawable.common_google_signin_btn_icon_dark)
                                    .into(profileImageView);
                        } else {
                            // If the image URL is not available, you can set a default image or show an error message.
                            // For simplicity, we'll set a default image.
                            profileImageView.setImageResource(R.drawable.common_google_signin_btn_icon_dark);
                        }
                        progressBar.setVisibility(ProgressBar.INVISIBLE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error if the database retrieval is canceled.
                        // For simplicity, we'll set a default image.
                        profileImageView.setImageResource(R.drawable.common_google_signin_btn_icon_dark);
                    }
                });
    }

    private void loadText() {
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads/"+currentUser+"/"+currentUser);

        mDatabaseRef.child("text")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String text = dataSnapshot.getValue(String.class);
                        if (text != null) {
                            displayTextView.setText(text);
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                        } else {
                            // If the text is not available, you can set a default text or show an error message.
                            // For simplicity, we'll set a default text.
                            displayTextView.setText("No text available");
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }



                        // Change this delay to suit your loading duration
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error if the database retrieval is canceled.
                        // For simplicity, we'll set a default text.
                        displayTextView.setText("Failed to load text");
                    }
                });


        /*

        mDatabaseRef.child("facebookLink")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String text = dataSnapshot.getValue(String.class);
                        if (text != null) {
                            displayTextView.setText(text);
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                        } else {
                            // If the text is not available, you can set a default text or show an error message.
                            // For simplicity, we'll set a default text.
                            displayTextView.setText("No text available");
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }



                        // Change this delay to suit your loading duration
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error if the database retrieval is canceled.
                        // For simplicity, we'll set a default text.
                        displayTextView.setText("Failed to load text");
                    }
                });

        mDatabaseRef.child("instgramLink")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String text = dataSnapshot.getValue(String.class);
                        if (text != null) {
                            displayTextView.setText(text);
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                        } else {
                            // If the text is not available, you can set a default text or show an error message.
                            // For simplicity, we'll set a default text.
                            displayTextView.setText("No text available");
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }



                        // Change this delay to suit your loading duration
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error if the database retrieval is canceled.
                        // For simplicity, we'll set a default text.
                        displayTextView.setText("Failed to load text");
                    }
                });
        */
    }
    public void LoadPhoneNumber(){
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads/"+currentUser+"/"+currentUser);

        mDatabaseRef.child("phoneNumber")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String text = dataSnapshot.getValue(String.class);
                        if (text != null) {
                            phoneNumberonPage.setText(text);
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                        } else {
                            // If the text is not available, you can set a default text or show an error message.
                            // For simplicity, we'll set a default text.
                            phoneNumberonPage.setText("No text available");
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }



                        // Change this delay to suit your loading duration
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error if the database retrieval is canceled.
                        // For simplicity, we'll set a default text.
                        displayTextView.setText("Failed to load text");
                    }
                });
    }
    public void loadbusinessName(){
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads/"+currentUser+"/"+currentUser);

        mDatabaseRef.child("BusinessName")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String text = dataSnapshot.getValue(String.class);
                        if (text != null) {
                            businessName.setText(text);
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                        } else {
                            // If the text is not available, you can set a default text or show an error message.
                            // For simplicity, we'll set a default text.
                            businessName.setText("No text available");
                            // Hide the ProgressBar after loading is complete
                            //  progressBar.setVisibility(ProgressBar.INVISIBLE);
                        }



                        // Change this delay to suit your loading duration
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Handle the error if the database retrieval is canceled.
                        // For simplicity, we'll set a default text.
                        displayTextView.setText("Failed to load text");
                    }
                });
    }



    public void updateBusinessName( String newText) {
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads/"+currentUser+"/"+currentUser);

        newText = newText.trim();
        if (!newText.isEmpty()) {
            // Update the text in Firebase
            mDatabaseRef.child("BusinessName")
                    .setValue(newText)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),
                                    "BusinessName added successfully",
                                    Toast.LENGTH_SHORT).show();
                            loadbusinessName();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Failed to add BusinessName information",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Toast.makeText(this, "Please enter some text to update", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profileImageView.setImageURI(imageUri);
            uploadProfilePicture();
        }
    }

    public void uploadProfilePicture() {
        DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads/"+currentUser+"/"+currentUser);
        if (imageUri != null) {
            mStorageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Image uploaded successfully. Now get the image URL and store it in the database.
                            mStorageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        String imageUrl = task.getResult().toString();
                                        mDatabaseRef
                                                .child("profileImage").setValue(imageUrl)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(getApplicationContext(),
                                                                    "Profile picture uploaded successfully",
                                                                    Toast.LENGTH_SHORT).show();
                                                        } else {
                                                            Toast.makeText(getApplicationContext(),
                                                                    "Failed to upload profile picture",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "Failed to get image URL",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),
                                    "Failed to upload image",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show();
        }
    }
/*
    private void showEnlargedImage(ImageView ExpandImage , String imageUrl) {
        // Create a custom dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.image_expander, null);

        // Get references to the views in the custom dialog layout
        ImageView dialogImageView = dialogView.findViewById(R.id.dialogImageView);
        Button deleteButton = dialogView.findViewById(R.id.deleteButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        // Set up the image and other views in the dialog as needed
        // For example, you can load a different image into dialogImageView
        // dialogImageView.setImageResource(R.drawable.your_another_image);
        dialogImageView.setImageDrawable(ExpandImage.getDrawable());
        // Set up click listeners for the buttons




        // Set the custom view for the dialog
        builder.setView(dialogView);

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle cancel button click here
                // For example, dismiss the dialog without performing any action
                dialog.dismiss();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete button click here
                // For example, delete the image or perform any other action
// Get the position index of the clicked view
                int clickedPosition = layout.indexOfChild(v);
                // Log.e("my joe url ", imageUrl);
                // Use the clickedPosition as needed
                // Toast.makeText(getApplicationContext(), "Clicked Position: " + clickedPosition, Toast.LENGTH_SHORT).show();


                ////////////////////////////////// delete from storage/////////////
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference folderRef = storage.getReference("uploads").child(currentUser.getEmail()).child(getImageNameFromURL(imageUrl)+".image");
                //StorageReference fileRef = storageRef.child(getImageNameFromURL(imageUrl)+".image");

                // Step 2: List all items within the folder
                folderRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        List<StorageReference> items = listResult.getItems();

                        // Step 3: Iterate through the items and delete each file
                        for (StorageReference item : items) {
                            item.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // File deletion successful

                                    // Step 4: Remove the folder reference to delete the empty folder
                                    folderRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Folder deletion successful
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Folder deletion failed

                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // File deletion failed
                                }
                            });
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to list items within the folder
                    }
                });

                //////////////////DELETE IMAGE////////////////////////////
                /////////////////////////////////////////////////////////
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference nodeToDeleteRef = databaseReference.child("uploads/"+currentUser.getUid()+"/"+currentUser.getUid()+"/imageUrls");
                nodeToDeleteRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<Object> arrayValues = new ArrayList<>();
                        for (DataSnapshot valueSnapshot : dataSnapshot.getChildren()) {
                            Object value = valueSnapshot.getValue();
                            arrayValues.add(value);
                        }

                        // Remove the desired value from the array
                        arrayValues.remove(imageUrl); // Replace "value2" with the value you want to delete

                        // Update the modified array back to the database
                        nodeToDeleteRef.setValue(arrayValues).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Deletion successful
                                    Toast.makeText(getApplicationContext(), "Image have been deleted: " , Toast.LENGTH_SHORT).show();

                                } else {
                                    // Deletion failed
                                    Toast.makeText(getApplicationContext(), "Failed to Delete image: " , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                        ////////////////////////////////////////////////////////
                        ////////////////////END OF DELETE section///////////////
                        ////////////////////////////////////////////////////////


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Error occurred while fetching data
                    }
                });

                dialog.dismiss();
            }
        });
    }
*/
    public void onFacebookImageClicked(View view){
        // Replace 'your_facebook_link' with the actual link you want to open
        String facebookLink = facebooklinkTemp;

        if (isValidUrl(facebookLink)) {
            openAPPLink(facebookLink);
        } else {
            // Handle invalid URL gracefully (e.g., show a toast message)
            // You can use a library like Toast or Snackbar for this purpose
            // Example with Toast:
            Toast.makeText(RetriveActivity.this, "Invalid URL", Toast.LENGTH_SHORT).show();
        }
    }

    public void onInstgramImageClicked(View view){
        String instgramLink = InstgramLinkTemp;

        if (isValidUrl(instgramLink)) {
            openAPPLink(instgramLink);
        } else {
            // Handle invalid URL gracefully (e.g., show a toast message)
            // You can use a library like Toast or Snackbar for this purpose
            // Example with Toast:
            Toast.makeText(RetriveActivity.this, "Invalid URL", Toast.LENGTH_SHORT).show();
        }
    }

    public void onCallImageClicked(View view){
        // Replace "phoneNumber" with the actual phone number you want to call
        String phoneNumber = phoneNumberonPage.getText().toString();

        // Specify the SIM slot index (0 for SIM1, 1 for SIM2)
        int simSlotIndex = 0;

        // Check if the device is running Android 10 or earlier
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.Q) {
            makeCallUsingSimSlot(phoneNumber, simSlotIndex);
        } else {
            // For Android 11 and later, open the dialer with the number filled in
            openDialer(phoneNumber);
        }
    }

    private void makeCallUsingSimSlot(String phoneNumber, int simSlotIndex) {
        Intent intent = new Intent(Intent.ACTION_CALL);

        Uri uri = Uri.fromParts("tel", phoneNumber, null);
        intent.setData(uri);

        // Specify the SIM slot index as an extra (0 for SIM1, 1 for SIM2)
        intent.putExtra("com.android.phone.force.slot", true);
        intent.putExtra("com.android.phone.extra.slot", simSlotIndex);

        startActivity(intent);
    }

    private void openDialer(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri uri = Uri.fromParts("tel", phoneNumber, null);
        intent.setData(uri);
        startActivity(intent);
    }


    private boolean isValidUrl(String url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }

    private void openAPPLink(String link) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        try {
            // Check if the Facebook app is installed, if not, open in a web browser
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(webIntent);
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., ActivityNotFoundException) gracefully
            e.printStackTrace();
        }
    }


}
