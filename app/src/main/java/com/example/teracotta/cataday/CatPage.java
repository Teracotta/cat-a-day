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
import net.dean.jraw.models.Thumbnails;
import net.dean.jraw.paginators.Sorting;
import net.dean.jraw.paginators.SubredditPaginator;

import java.util.UUID;

public class CatPage extends AppCompatActivity {
    Callback catCallback;
    Submission topSubmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_page);

        catCallback = new Callback() {
            @Override
            public void done(final Cat cat) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        setTopCatValues(cat.getSubmissionURL(), cat.getSubmissionTitle(), cat.getSubmissionAuthor());
                        setShareButton(cat.getSubmissionLink());
                    }
                });
            }
        };
        getTopRedditCat(catCallback);
    }

    private void setTopCatValues(String submissionImageURL, String submissionTitle, String submissionAuthor) {
        final ImageView catImage = (ImageView) findViewById(R.id.submission_thumbnail);
        final TextView titleLine = (TextView) findViewById(R.id.submission_title);
        final TextView authorLine = (TextView) findViewById(R.id.submission_author);
        Picasso.with(this).load(submissionImageURL).into(catImage);
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
                startActivity(Intent.createChooser(linkIntent, "Send via"));
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
                paginator.setLimit(10);

                Listing<Submission> submissions = paginator.next();
                boolean NSFW = true, mourningTag = true;
                int counter = 0;
                String imageUrl = "", tag;

                while(NSFW && mourningTag && (imageUrl.equals(""))) {
                    topSubmission = submissions.get(counter);

                    Thumbnails thumbs = topSubmission.getThumbnails();
                    tag = topSubmission.getSubmissionFlair().getText();

                    if (!topSubmission.isNsfw()) {
                        NSFW = false;
                    }

                    if (tag == null || !tag.equals("MOURNING/LOSS")) {
                        mourningTag = false;
                    }

                    if (thumbs != null) {
                        Thumbnails.Image source = thumbs.getSource();
                        imageUrl = source.getUrl();
                    }
                    counter++;
                }

                Cat topCat = new Cat(   imageUrl,
                                        topSubmission.getTitle(),
                                        topSubmission.getAuthor(),
                                        topSubmission.getShortURL());

                callback.done(topCat);
            }
        }).start();
    }
}