package com.example.demo.src.badge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
public class GetBadgeRes {
    Long badgeID;
    String badgeName;
    String icon;
    String badgeInfo;
}
