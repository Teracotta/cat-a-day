package com.example.teracotta.cataday;

public class Cat {

    private String submissionThumbnail;
    private String submissionTitle;
    private String submissionAuthor;
    private String submissionLink;

    public Cat(String submissionThumbnail, String submissionTitle, String submissionAuthor, String submissionLink) {
        this.submissionThumbnail = submissionThumbnail;
        this.submissionTitle = submissionTitle;
        this.submissionAuthor = submissionAuthor;
        this.submissionLink = submissionLink;
    }

    public String getSubmissionThumbnail() {
        return this.submissionThumbnail;
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
