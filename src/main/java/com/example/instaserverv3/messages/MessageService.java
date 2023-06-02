package com.example.instaserverv3.messages;


import com.example.instaserverv3.conversations.ConversationRepository;
import com.example.instaserverv3.conversations.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationService conversationService;

    public UUID conversationCheck(Message input) {
        return conversationService.startNewConversation(input.userIdA, input.userIdB);
    }
    public Message createAndSendMessage(Message input) {
        UUID uuid = UUID.randomUUID();
        Message newMessage = new Message();
        UUID safeConvoId = conversationCheck(input);

        switch (input.messageType) {
            case IsSingleMessage -> {
                Message message = Message.builder()
                        .message(input.message)
                        .userIdA(input.userIdA)
                        .userIdB(input.userIdB)
                        .conversationId(safeConvoId)
                        .fromUser(input.userIdA)
                        .messageId(uuid)
                        .postId(null)
                        .timeStamp(new Timestamp(System.currentTimeMillis()))
                        .acknowledged(false)
                        .messageType(MessageType.IsSingleMessage)
                        .build();
                newMessage = messageRepository.save(message);
            }
            case IsPostRedirect -> {
                Message message = Message.builder()
                        .message(input.message)
                        .userIdA(input.userIdA)
                        .userIdB(input.userIdB)
                        .fromUser(input.userIdA)
                        .messageId(uuid)
                        .conversationId(safeConvoId)
                        .postId(input.postId)
                        .postSrcUrl(input.postSrcUrl)
                        .timeStamp(new Timestamp(System.currentTimeMillis()))
                        .acknowledged(false)
                        .messageType(MessageType.IsPostRedirect)
                        .build();
                newMessage =  messageRepository.save(message);
            }
        }

        return newMessage;
    }

    public Optional<List<Message>> getUsersMessages(UUID userIdA) {
        return messageRepository.findMessageByUserIdA(userIdA);
    }

    // want to find all the conversations that a user has, find all distinct user_id_b, order them by time_stamp

    public HashMap<UUID, List<Message>> findConversationsService(UUID userIdA) {
       List<Message> messageList = messageRepository.findMessageByUserIdAOrderByUserIdB(userIdA);

          HashMap<UUID, List<Message>> conversationMap = new HashMap<>();

          for (int i = 0; i < messageList.size(); i++) {
              UUID key = messageList.get(i).getConversationId();
              boolean keyCheck = conversationMap.containsKey(key);

              if (!keyCheck) {
                  List<Message> messages = new ArrayList<>();
                  messages.add(messageList.get(i));
                  conversationMap.put(key, messages);
              } else {
                  List<Message> currConv = conversationMap.get(key);
                  currConv.add(messageList.get(i));
                  conversationMap.put(key, currConv);
              }
          }

          System.out.println(conversationMap);

          return conversationMap;
      }
}
