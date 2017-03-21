package com.example.teracotta.cataday;

public class Cat {

    private String submissionImageURL;
    private String submissionTitle;
    private String submissionAuthor;
    private String submissionLink;
    private String thumbnailURL;
    private boolean isFavourite;

    public Cat(String submissionImageURL, String submissionTitle, String submissionAuthor, String submissionLink, String thumbnailURL) {
        this.submissionImageURL = submissionImageURL;
        this.submissionTitle = submissionTitle;
        this.submissionAuthor = submissionAuthor;
        this.submissionLink = submissionLink;
        this.thumbnailURL = thumbnailURL;
        this.isFavourite = false;
    }

    public String getSubmissionImageURL() {
        return this.submissionImageURL;
    }

    public String getSubmissionTitle() {
        return this.submissionTitle;
    }

    public String getSubmissionAuthor() {
        return this.submissionAuthor;
    }

    public String getSubmissionLink() {
        return this.submissionLink;
    }

    public String getThumbnailURL() {
        return this.thumbnailURL;
    }

    public boolean getIsFavourite() {
        return this.isFavourite;
    }

    public void setIsFavourite(boolean favourite) {
        this.isFavourite = favourite;
    }
}
