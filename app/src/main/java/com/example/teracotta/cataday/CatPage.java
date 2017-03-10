package com.example.teracotta.cataday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.Sorting;
import net.dean.jraw.paginators.SubredditPaginator;

import java.util.UUID;

public class CatPage extends AppCompatActivity {
    Callback catCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_page);

        catCallback = new Callback() {
            @Override
            public void done(final Cat cat) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        setTopCatValues(cat.getSubmissionThumbnail(), cat.getSubmissionTitle(), cat.getSubmissionAuthor());
                        setShareButton(cat.getSubmissionLink());
                    }
                });
            }
        };
        getTopRedditCat(catCallback);
    }

    private void setTopCatValues(String submissionThumbanil, String submissionTitle, String submissionAuthor) {
        final ImageView catThumbnail = (ImageView) findViewById(R.id.submission_thumbnail);
        final TextView titleLine = (TextView) findViewById(R.id.submission_title);
        final TextView authorLine = (TextView) findViewById(R.id.submission_author);
        Picasso.with(this).load(submissionThumbanil).into(catThumbnail);
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

    interface Callback {
        void done(final Cat cat);
    }

    private void getTopRedditCat(final Callback callback) {
        new Thread(new Runnable()
        {
            final String clientId = getString(R.string.client_id);

            @Override
            public void run()
            {
                UserAgent myUserAgent = UserAgent.of("android");
                RedditClient redditClient = new RedditClient(myUserAgent);
                Credentials credentials = Credentials.userlessApp(clientId, UUID.randomUUID());

                try {
                    OAuthData authData = redditClient.getOAuthHelper().easyAuth(credentials);
                    redditClient.authenticate(authData);
                } catch (OAuthException e) {
                    e.printStackTrace();
                }

                SubredditPaginator paginator = new SubredditPaginator(redditClient);
                paginator.setSorting(Sorting.TOP);
                paginator.setSubreddit("cats");

                Listing<Submission> submissions = paginator.next();
                final Submission first = submissions.get(0);

                Cat topCat = new Cat(first.getThumbnail(), first.getTitle(), first.getAuthor(), first.getShortURL());

                callback.done(topCat);
            }
        }).start();
    }
}