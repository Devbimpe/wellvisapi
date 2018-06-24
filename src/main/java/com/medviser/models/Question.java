package com.medviser.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

/**
 * Created by Longbridge on 22/11/2017.
 */
@Indexed
@Entity
public class Question extends CommonFields{

    @AnalyzerDef(name = "questionTextAnalyzer",
            tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
            filters = {
                    @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                    @TokenFilterDef(factory = StandardFilterFactory.class)
            })

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

    public String delFlag="N";


    public Question() {
    }

    public Question(String description, String category, Boolean anonymous) {

        this.description = description;
        this.category = category;
        this.anonymous = anonymous;
    }

    @Override
    @JsonIgnore
    public List<String> getDefaultSearchFields() {
        return Arrays.asList("description");
    }


}
