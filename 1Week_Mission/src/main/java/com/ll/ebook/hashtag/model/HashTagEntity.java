package com.ll.ebook.hashtag.model;

import com.ll.ebook.base.entity.BaseEntity;
import com.ll.ebook.post.model.entity.PostEntity;
import com.ll.ebook.keyword.model.KeywordEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@Table(name="hashtags")
public class HashTagEntity extends BaseEntity {

    @ManyToOne
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PostEntity postEntity;

//    @ManyToOne
//    @JoinColumn(name="authorId", referencedColumnName = "author_id", insertable = false, updatable = false)
//    @ToString.Exclude
//    private PostEntity authorId;

    @ManyToOne
    @ToString.Exclude
    @OnDelete(action = OnDeleteAction.CASCADE)
    private KeywordEntity keywordEntity;
}
