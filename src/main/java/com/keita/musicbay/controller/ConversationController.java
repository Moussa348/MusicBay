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
    public ConversationDTO addUserInConversationGroup(@RequestParam("id") Long id, @RequestParam("username") String username){
        return conversationService.addUserInConversationGroup(id,username);
    }

    @DeleteMapping("/removeUserFromConversationGroup")
    public ConversationDTO removeUserFromConversationGroup(@RequestParam("id") Long id, @RequestParam("username") String username){
        return conversationService.removeUserFromConversationGroup(id,username);
    }

    @PatchMapping("/sendMessageInConversation/{conversationId}")
    public SentMessage sendMessageInConversation(@PathVariable Long conversationId, @RequestBody SentMessage sentMessage){
        return conversationService.sendMessageInConversation(conversationId,sentMessage);
    }

    @DeleteMapping("/deleteConversation/{id}")
    public void deleteConversation(@PathVariable Long id){
        conversationService.deleteConversation(id);
    }

    @GetMapping("/getConversation/{id}")
    public ConversationDTO getConversation(@PathVariable Long id){
        return conversationService.getConversation(id);
    }


    @GetMapping("/getLastSentMessages/{username}")
    public List<SentMessage> getLastSentMessages(@PathVariable String username){
        return conversationService.getLastSentMessages(username);
    }
}
