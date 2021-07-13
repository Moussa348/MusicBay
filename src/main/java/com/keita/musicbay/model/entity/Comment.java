package com.keita.musicbay.model.entity;

import com.keita.musicbay.model.dto.PostedComment;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends Text implements Serializable {

    private Integer nbrLike;

    @ManyToOne
    private Music music;

    @OneToMany(mappedBy = "comment",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<LikedBy> likedByList = new ArrayList<>();

    public Comment() { }

    @Builder
    public Comment(Long id, String content,String sendBy, Integer nbrLike, Music music) {
        super( id,content,sendBy);
        this.nbrLike = nbrLike;
        this.music = music;
    }

    public Comment(PostedComment postedComment, Music music){
        super(postedComment.getId(),postedComment.getContent(), postedComment.getSendBy());
        super.setDate(LocalDateTime.now());
        this.nbrLike = postedComment.getNbrLike();
        this.music = music;
    }
}
