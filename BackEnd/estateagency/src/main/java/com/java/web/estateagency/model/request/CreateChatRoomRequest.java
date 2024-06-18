package com.java.web.estateagency.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatRoomRequest {
    private Long idfirstUserName;
    private Long idsecondUserName;
}
