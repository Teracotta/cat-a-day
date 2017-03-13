package com.example.teracotta.cataday;

public class Cat {

    private String submissionImageURL;
    private String submissionTitle;
    private String submissionAuthor;
    private String submissionLink;

    public Cat(String submissionImageURL, String submissionTitle, String submissionAuthor, String submissionLink) {
        this.submissionImageURL = submissionImageURL;
        this.submissionTitle = submissionTitle;
        this.submissionAuthor = submissionAuthor;
        this.submissionLink = submissionLink;
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
}
