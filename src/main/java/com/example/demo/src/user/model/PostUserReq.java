package com.example.demo.src.user.model;

import com.example.demo.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserReq extends BaseTimeEntity {
    private String UserName;
    private String id;
    private String email;
    private String password;
}
