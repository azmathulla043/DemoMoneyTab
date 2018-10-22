package com.example.demo.moneyTab;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by anju on 22-10-2018.
 */

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView profileImageView = (ImageView) findViewById(R.id.profileImageView);
        TextView userNameTextView = (TextView) findViewById(R.id.usernameTextView);
        ImageButton shareProfile = (ImageButton) findViewById(R.id.shareProfile);
        TextView developerUrl = (TextView) findViewById(R.id.developerUrl);


        Intent intent = getIntent();
        final String title = intent.getStringExtra(JsonDataAdapter.TITLE);
        final String source = intent.getStringExtra(JsonDataAdapter.SOURCE);
        final String description = intent.getStringExtra(JsonDataAdapter.DESCRIPTION);


        Picasso.with(this)
                .load(source)
                .into(profileImageView);

        userNameTextView.setText(title);
        developerUrl.setText(description);

        developerUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = source;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


        shareProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome developer " + title +
                        ", " + description);
                Intent chooser = Intent.createChooser(shareIntent, "Share via");
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                }
            }
        });


    }
}

