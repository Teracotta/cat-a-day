package com.example.teracotta.cataday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CatPage extends AppCompatActivity {
    private Cat redditCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_page);

        redditCat = getTopRedditCat();

        setTitleAndAuthorTexts(redditCat.getSubmissionTitle(), redditCat.getSubmissionAuthor());
        setShareButton(redditCat.getSubmissionLink());
    }

    private void setTitleAndAuthorTexts(String submissionTitle, String submissionAuthor) {
        final TextView titleLine = (TextView) findViewById(R.id.submission_title);
        final TextView authorLine = (TextView) findViewById(R.id.submission_author);
        titleLine.setText(submissionTitle);
        authorLine.setText(submissionAuthor);
    }
    private void setShareButton(String submissionLink) {
        final String sharedMessage = "Check out the current top photo on reddit/r/cats! \n\n" + submissionLink;
        final Button shareButton = (Button) findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent linkIntent = new Intent(android.content.Intent.ACTION_SEND);
                linkIntent.setType("text/plain");

                linkIntent.putExtra(Intent.EXTRA_TEXT, sharedMessage);
                startActivity(Intent.createChooser(linkIntent, "Send picture URL using:"));
            }
        });
    }

    private Cat getTopRedditCat() {
        Cat topCat = new Cat("test title", "test author", "http://static.boredpanda.com/blog/wp-content/uploads/2016/08/cute-kittens-4-57b30a939dff5__605.jpg");
        // here retrieve Cat object data
        return topCat;
    }
}