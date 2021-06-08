package com.example.clounema;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class MovieDetails extends AppCompatActivity {

    private Button pilihJadwal;
    private int toggler = 0;
    private ImageView mvDposter;
    private VideoView videoView;
    private StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private StorageReference posterTemp;

//    private DatabaseReference dbRef;
//    private TextView mdTitle, mdYear, mdSino, mdDirector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        videoView = findViewById(R.id.mvTrailer);
        String vidPath = "android.resource://" + getPackageName() + "/" + R.raw.avengers_trailer;

        Uri uri = Uri.parse(vidPath);
        videoView.setVideoURI(uri);
        // videoView.start();

        MediaController mvController = new MediaController(this);
        videoView.setMediaController(mvController);
        mvController.setAnchorView(videoView);

        posterTemp = storageRef.child("Movie/avengers.jpg");

        mvDposter = findViewById(R.id.mvDposter);
        posterTemp = storageRef.child("Movie/avengers.jpg");
        try {
            final File localPoster = File.createTempFile("poster_1", "jpg");
            posterTemp.getFile(localPoster).addOnSuccessListener(taskSnapshot -> {
                /** Toast.makeText(HomeActivity.this, "Poster Retrieved",
                 Toast.LENGTH_SHORT).show();*/
                Bitmap bitmap = BitmapFactory.decodeFile(localPoster.getAbsolutePath());
                mvDposter.setImageBitmap(bitmap);
            }).addOnFailureListener(e -> Toast.makeText(MovieDetails.this, "Poster Not Found",
                    Toast.LENGTH_SHORT).show());
        } catch (IOException e) {
            e.printStackTrace();
        }

        pilihJadwal = findViewById(R.id.btn_pilih_jadwal);
        pilihJadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetails.this, PilihJadwal.class);
                startActivity(intent);
            }
        });
    }
}