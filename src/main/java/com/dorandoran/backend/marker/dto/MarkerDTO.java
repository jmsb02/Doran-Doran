package com.dorandoran.backend.marker.dto;

import com.dorandoran.backend.marker.domain.MarkerAddress;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;


/**
 * 마커 생성시 필요한 DTO
 */

@Getter @Setter
@NoArgsConstructor
@Data
@Schema(description = "마커 생성에 필요한 데이터")
public class MarkerDTO {

    @NotNull(message = "제목은 필수입니다.")
    @Schema(description = "마커 제목", example = "더벤티")
    private String title;

    @NotNull(message = "내용은 필수입니다.")
    @Schema(description = "마커 내용", example = "더벤티 논현역점")
    private String content;

    @Schema(description = "마커 주소")
    private MarkerAddress address;


    public MarkerDTO(String title, String content, MarkerAddress address) {
        this.title = title;
        this.content = content;
        this.address = address;
    }
}
