package com.medviser.models;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Longbridge on 22/11/2017.
 */
@Entity
public class Question extends CommonFields{
    @ManyToOne
    public User user;
    public String description;
    public String category;
    public Boolean anonymous;

    public String evidenceName;

    public int trendingCount=0;

    @OneToMany(mappedBy = "question",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public List<Comments> comments;
    @OneToMany(mappedBy = "question",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public List<Likes> likes;

    @OneToMany(mappedBy = "question",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public List<Flag> flags;

    @OneToMany(mappedBy = "question",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    public List<BookMark> bookMarks;

    public Question() {
    }

    public Question(String description, String category, Boolean anonymous) {

        this.description = description;
        this.category = category;
        this.anonymous = anonymous;
    }


}
