package com.example.teracotta.cataday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.SubredditPaginator;

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



        new Thread(new Runnable()
        {
            final String username = getString(R.string.username);
            final String password = getString(R.string.password);
            final String clientId = getString(R.string.client_id);
            final String clientSecret = getString(R.string.secret);
            final String appId = getString(R.string.app_id);

            @Override
            public void run()
            {
                UserAgent myUserAgent = UserAgent.of("android",
                        appId, "0.0.1", username);
                RedditClient redditClient = new RedditClient(myUserAgent);
                Credentials credentials = Credentials.script(username,
                        password, clientId, clientSecret);
                try {
                    OAuthData authData = redditClient.getOAuthHelper()
                            .easyAuth(credentials);
                    redditClient.authenticate(authData);
                } catch (OAuthException e) {
                    e.printStackTrace();
                }

                SubredditPaginator paginator
                        = new SubredditPaginator(redditClient);
                Listing<Submission> submissions = paginator.next();
                final Submission first = submissions.get(0);

                Log.d("first", first.getTitle());
            }
        }).start();



        return topCat;
    }
}