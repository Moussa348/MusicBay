package com.keita.musicbay.service;

import com.keita.musicbay.model.dto.PostedComment;
import com.keita.musicbay.model.entity.Comment;
import com.keita.musicbay.model.entity.LikedBy;
import com.keita.musicbay.model.entity.Music;
import com.keita.musicbay.model.enums.NotificationEvent;
import com.keita.musicbay.repository.CommentRepository;
import com.keita.musicbay.repository.MusicRepository;
import com.keita.musicbay.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private NotificationService notificationService;


    public PostedComment postComment(PostedComment postedComment,String musicTitle){
        Music music = musicRepository.findByTitle(musicTitle).get();
        return new PostedComment(commentRepository.save(new Comment(postedComment,music)));
    }

    public PostedComment increaseLike(Long id,String username){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Comment " + id + " not found"));

        comment.setNbrLike(comment.getNbrLike()+1);
        comment.getLikedByList().add(new LikedBy(username,comment));

        notificationService.saveNotification(username, NotificationEvent.LIKING_COMMENT, Arrays.asList(comment.getSendBy()));

        return new PostedComment(commentRepository.save(comment));
    }

    public PostedComment decreaseLike(Long id,String username){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Comment " + id + " not found"));

        comment.setNbrLike(comment.getNbrLike()-1);
        comment.getLikedByList().removeIf(likedBy -> likedBy.getUsername().equals(username));

        return new PostedComment(commentRepository.save(comment));
    }

    public List<PostedComment> getListCommentOfMusic(String title,Integer noPage){
        return commentRepository.getAllByMusicTitle(title, PageRequest.of(noPage,10, Sort.by("date").descending()))
        .stream().map(PostedComment::new).collect(Collectors.toList());
    }

    public Integer getNbrOfPage(String title){
        return (int) (Math.ceil(commentRepository.countAllByMusicTitle(title)/10));
    }

}
