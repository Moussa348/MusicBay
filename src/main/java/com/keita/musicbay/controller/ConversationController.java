package com.keita.musicbay.controller;

import com.keita.musicbay.model.dto.ConversationDTO;
import com.keita.musicbay.model.dto.SentMessage;
import com.keita.musicbay.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;

@RestController
@RequestMapping("/conversation")
@CrossOrigin(origins = "http://localhost:5001")
public class ConversationController {

    @Autowired
    private ConversationService conversationService;

    @PostMapping("/createConversation")
    public ConversationDTO createConversation(@RequestBody ConversationDTO conversationDTO){
        return conversationService.createConversation(conversationDTO);
    }


    @PatchMapping("/addUserInConversationGroup")
    public void addUserInConversationGroup(@RequestParam("id") Long id, @RequestParam("username") String username){
         conversationService.addUserInConversationGroup(id,username);
    }

    @DeleteMapping("/removeUserFromConversationGroup")
    public void removeUserFromConversationGroup(@RequestParam("id") Long id, @RequestParam("username") String username){
         conversationService.removeUserFromConversationGroup(id,username);
    }

    @PatchMapping("/sendMessageInConversation/")
    public SentMessage sendMessageInConversation(@RequestBody SentMessage sentMessage){
        return conversationService.sendMessageInConversation(sentMessage);
    }

    @DeleteMapping("/deleteConversation/{id}")
    public void deleteConversation(@PathVariable Long id){
        conversationService.deleteConversation(id);
    }

    @GetMapping("/getMessagesFromConversation/")
    public List<SentMessage> getMessagesFromConversation(@RequestParam("id")Long id,@RequestParam("noPage")Integer noPage){
        return conversationService.getMessagesFromConversation(id,noPage);
    }


    @GetMapping("/getLastSentMessages")
    public List<SentMessage> getLastSentMessages(@RequestParam("username") String username,@RequestParam("noPage")Integer noPage){
        return conversationService.getLastSentMessages(username,noPage);
    }
}
