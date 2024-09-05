package com.java.web.estateagency.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.java.web.estateagency.entity.Room;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Schema(
        name = "RoomDto",
        description = "Room to hold Room information"
)
public class CreateRoomRequest {
    @Schema(
            description = "Rooms Id Rooms of Estateagency Rooms", example = "4"
    )
    private Long id;

    @Schema(
            description = "Rooms name of Estateagency Rooms", example = "Room A22"
    )
    @NotEmpty(message = "Rooms name can not be a null or empty")
    private String name;

    @Schema(
            description = "Rooms price of Estateagency Rooms", example = "4000000"
    )
    @NotEmpty(message = "Rooms price can not be a null or empty")
    private Float price;

    @Schema(
            description = "Rooms address of Estateagency Rooms", example = "Dinh Cong"
    )
    @NotEmpty(message = "Rooms address can not be a null or empty")
    private String address;

    @Schema(
            description = "Rooms describe of Estateagency Rooms", example = "Ok"
    )
    @NotEmpty(message = "Rooms describe can not be a null or empty")
    private String describe;

    @Schema(
            description = "Rooms state of Estateagency Rooms", example = "Waiting"
    )
    @NotEmpty(message = "Rooms state can not be a null or empty")
    private String state;

    @Schema(
            description = "Rooms Image of Estateagency Rooms"
    )
    private MultipartFile img;

    @Schema(
            description = "Rooms enabled of Estateagency Rooms", example = "true"
    )
    @NotEmpty(message = "Rooms enabled can not be a null or empty")
    private boolean enabled;

    @Schema(
            description = "Agent Id Rooms of Estateagency User", example = "4"
    )
    @NotEmpty(message = "Agent Id can not be a null or empty")
    private Long idAgent;
}
