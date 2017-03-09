package com.example.teracotta.cataday;

public class Cat {

    private String submissionTitle;
    private String submissionAuthor;
    private String submissionLink;

    public Cat(String submissionTitle, String submissionAuthor, String submissionLink) {
        this.submissionTitle = submissionTitle;
        this.submissionAuthor = submissionAuthor;
        this.submissionLink = submissionLink;
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
