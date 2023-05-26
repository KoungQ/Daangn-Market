package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserReq {
    private Long userIdx;
    private String status;

    public DeleteUserReq(Long userIdx) {
        this.userIdx = userIdx;
    }
}
