package com.example.teracotta.cataday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CatPage extends AppCompatActivity {

    private TextView submissionLinkBlock;
    private String linkToShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_page);

        submissionLinkBlock = (TextView) findViewById(R.id.submission_link);
        linkToShare = submissionLinkBlock.getText().toString();

        final Button shareButton = (Button) findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent linkIntent = new Intent(android.content.Intent.ACTION_SEND);
                linkIntent.setType("text/plain");
                linkIntent.putExtra(Intent.EXTRA_TEXT, linkToShare);
                startActivity(Intent.createChooser(linkIntent, "Send picture using:"));
            }
        });
    }
}