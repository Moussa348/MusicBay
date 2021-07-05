package com.keita.musicbay.service;

import com.keita.musicbay.model.*;
import com.keita.musicbay.model.dto.PostedComment;
import com.keita.musicbay.repository.MusicRepository;
import com.keita.musicbay.repository.TextRepository;
import com.keita.musicbay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MusicRepository musicRepository;


    public PostedComment postComment(PostedComment postedComment,String musicTitle){
        Music music = musicRepository.findByTitle(musicTitle).get();
        return new PostedComment(textRepository.save(new Comment(postedComment,music)));
    }

    public PostedComment increaseLike(Long id,String username){
        Comment comment = (Comment) textRepository.findById(id).get();

        comment.setNbrLike(comment.getNbrLike()+1);
        comment.getLikedByList().add(new LikedBy(username,comment));
        return new PostedComment(textRepository.save(comment));
    }

    public List<PostedComment> getListCommentOfMusic(String title){
        Music music = musicRepository.findByTitle(title).get();
        return music.getComments().stream().map(PostedComment::new).collect(Collectors.toList());
    }

}
