package com.amiele.myfutureme.activities.main.goal.motivation;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Quote {

    @SerializedName("qotd_date")
    private String date;

    @SerializedName("quote")
    private Detail detail;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public class Detail{

        private String id;
        private String dialogue;

        @SerializedName("private")
        private String privateStatus;

        private ArrayList<String> tags;
        private String url;
        private String favorites_count;
        private String downvotes_count;
        private String author;
        private String author_permalink;
        private String body;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDialogue() {
            return dialogue;
        }

        public void setDialogue(String dialogue) {
            this.dialogue = dialogue;
        }

        public String getPrivateStatus() {
            return privateStatus;
        }

        public void setPrivateStatus(String privateStatus) {
            this.privateStatus = privateStatus;
        }

        public ArrayList<String> getTags() {
            return tags;
        }

        public void setTags(ArrayList<String> tags) {
            this.tags = tags;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getFavorites_count() {
            return favorites_count;
        }

        public void setFavorites_count(String favorites_count) {
            this.favorites_count = favorites_count;
        }

        public String getDownvotes_count() {
            return downvotes_count;
        }

        public void setDownvotes_count(String downvotes_count) {
            this.downvotes_count = downvotes_count;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthor_permalink() {
            return author_permalink;
        }

        public void setAuthor_permalink(String author_permalink) {
            this.author_permalink = author_permalink;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

}

