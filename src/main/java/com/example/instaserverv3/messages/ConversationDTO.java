package com.example.instaserverv3.messages;


import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ConversationDTO(UUID userId, List<Message> messages) {
}
