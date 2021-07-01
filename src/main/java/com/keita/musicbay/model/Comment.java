package com.keita.musicbay.model;

import com.keita.musicbay.model.dto.PostedComment;
import com.keita.musicbay.model.dto.TextDTO;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends Text implements Serializable {

    private Integer nbrLike;

    @ManyToOne
    private Music music;

    public Comment() { }

    @Builder
    public Comment(Long id, String content,String sendBy, Integer nbrLike, Music music) {
        super( id,content,sendBy);
        this.nbrLike = nbrLike;
        this.music = music;
    }

    public Comment(PostedComment postedComment, Music music){
        super(postedComment.getId(),postedComment.getContent(), postedComment.getSendBy());
        this.nbrLike = postedComment.getNbrLike();
        this.music = music;
    }
}
