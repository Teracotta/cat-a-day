package com.example.teracotta.cataday;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
    static boolean isCatStillFavourite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_page);

        catCallback = new Callback() {
            @Override
            public void done(final Cat cat) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        setTopCatValues(cat.getSubmissionImageURL(), cat.getSubmissionTitle(), cat.getSubmissionAuthor());
                        setShareButton(cat.getSubmissionLink());
                        setGotoButton(cat.getSubmissionLink());
                        isCatStillFavourite = cat.getIsFavourite();
                        cat.setIsFavourite(setFavouriteButton());
                        setFavouritePageButton();

                        final LinearLayout loadingLayout = (LinearLayout) findViewById(R.id.loadingLayout);
                        loadingLayout.setVisibility(LinearLayout.GONE);
                    }
                });
            }
        };
        getTopRedditCat(catCallback);
    }

    private void setTopCatValues(String submissionImageURL, String submissionTitle, String submissionAuthor) {
        final ImageView catImage = (ImageView) findViewById(R.id.submission_image);
        final TextView titleLine = (TextView) findViewById(R.id.submission_title);
        final TextView authorLine = (TextView) findViewById(R.id.submission_author);
        Glide.with(this).load(submissionImageURL).into(catImage);
        catImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
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
                startActivity(Intent.createChooser(linkIntent, "Share via"));
            }
        });
    }

    private void setGotoButton(final String submissionLink) {
        findViewById(R.id.goto_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent linkIntent = new Intent(Intent.ACTION_VIEW);
                linkIntent.setData(Uri.parse(submissionLink));
                startActivity(linkIntent);
            }
        });
    }

    private boolean setFavouriteButton() {
        final Button favouriteButton = (Button) findViewById(R.id.add_to_favourites_button);
        favouriteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isCatStillFavourite) {
                    AlertDialog alertDialog = new AlertDialog.Builder(CatPage.this).create();
                    alertDialog.setTitle("Warning");
                    alertDialog.setMessage("Are you sure you want to remove this post from Favourites?");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    favouriteButton.setText(R.string.add_to_favourites_button_text);
                                    dialog.dismiss();
                                    isCatStillFavourite = false;
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    isCatStillFavourite = true;
                                }
                            });
                    alertDialog.show();
                } else {
                    Toast.makeText(CatPage.this, "The post was added to Favourites",
                            Toast.LENGTH_LONG).show();
                    isCatStillFavourite = true;
                    favouriteButton.setText(R.string.remove_from_favourites_button_text);
                }
            }
        });
        return isCatStillFavourite;
    }

    private void setFavouritePageButton() {
        // check if the cat is favourite
            // check if it exists in json
            // add if it doesn't
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

                while(NSFW || mourningTag || (imageUrl.equals(""))) {
                    topSubmission = submissions.get(counter);

                    Thumbnails thumbs = topSubmission.getThumbnails();
                    tag = topSubmission.getSubmissionFlair().getText();

                    if (!topSubmission.isNsfw()) {
                        NSFW = false;
                    }

                    if (tag == null || !tag.equals("Mourning/Loss")) {
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
                                        topSubmission.getShortURL(),
                                        topSubmission.getThumbnail());

                callback.done(topCat);
            }
        }).start();
    }

    public void goToAnActivity(View view) {
        Intent Intent = new Intent(this, FavouritesCatPage.class);
        startActivity(Intent);
    }
}