package com.example.demo.src.badge;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.badge.model.GetBadgeRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app/badge")
public class BadgeController {

    @Autowired
    private final BadgeProvider badgeProvider;
    @Autowired
    private final BadgeService badgeService;

    public BadgeController(BadgeProvider badgeProvider, BadgeService badgeService) {
        this.badgeProvider = badgeProvider;
        this.badgeService = badgeService;
    }

    @GetMapping("")
    public BaseResponse<List<GetBadgeRes>> getBadges(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(required = false) Long memberID) {
        try {

            if (memberID == null) {
                List<GetBadgeRes> getBadgeRes = badgeProvider.getBadges(page);
                return new BaseResponse<>(getBadgeRes);
            }
            List<GetBadgeRes> getBadgeRes = badgeProvider.getBadgesByMemberID(page, memberID);
            return new BaseResponse<>(getBadgeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
