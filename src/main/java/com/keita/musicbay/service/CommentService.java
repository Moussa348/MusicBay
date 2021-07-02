package com.keita.musicbay.service;

import com.keita.musicbay.model.Comment;
import com.keita.musicbay.model.Message;
import com.keita.musicbay.model.Music;
import com.keita.musicbay.model.User;
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

    public PostedComment increaseLike(Long id){
        Comment comment = (Comment) textRepository.findById(id).get();
        comment.setNbrLike(comment.getNbrLike()+1);
        return new PostedComment(textRepository.save(comment));
    }

    public List<PostedComment> getListCommentOfMusic(String title){
        Music music = musicRepository.findByTitle(title).get();
        return music.getComments().stream().map(PostedComment::new).collect(Collectors.toList());
    }


    /*

    public List<TextDTO> getLastSentMessageWithEveryUser(String username){
        User user = userRepository.findByUserName(username).get();
        List<Message> lastSentMessageWithEveryUser = user.getMessages()
                .stream().filter(message -> message)

        return lastSentMessageWithEveryUser.stream().map(TextDTO::new).collect(Collectors.toList());
    }
     */

}
