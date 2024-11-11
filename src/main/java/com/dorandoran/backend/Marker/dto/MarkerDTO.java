package com.dorandoran.backend.Marker.dto;

import com.dorandoran.backend.Member.domain.MemberAddress;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@Data
public class MarkerDTO {

    /**
     * 마커 생성시 필요한 DTO
     */

    @NotNull(message = "제목은 필수입니다.")
    private String title;

    @NotNull(message = "내용은 필수입니다.")
    private String content;

    private MarkerAddress address;


    public MarkerDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
