package com.example.teracotta.cataday;

public class Cat {

    private String submissionURL;
    private String submissionTitle;
    private String submissionAuthor;
    private String submissionLink;

    public Cat(String submissionURL, String submissionTitle, String submissionAuthor, String submissionLink) {
        this.submissionURL = submissionURL;
        this.submissionTitle = submissionTitle;
        this.submissionAuthor = submissionAuthor;
        this.submissionLink = submissionLink;
    }

    public String getSubmissionURL() {
        return this.submissionURL;
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
