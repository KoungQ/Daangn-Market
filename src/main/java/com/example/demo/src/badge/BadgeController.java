package com.example.demo.src.badge;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.badge.model.GetBadgeRes;
import com.example.demo.src.badge.model.PostBadgeReq;
import com.example.demo.src.badge.model.PostBadgeRes;
import com.example.demo.src.user.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@Profile("prod")
@RequestMapping("/app/badge")
public class BadgeController {

    @Autowired
    private final BadgeProvider badgeProvider;
    @Autowired
    private final BadgeService badgeService;
    @Autowired
    private final UserProvider userProvider;

    public BadgeController(BadgeProvider badgeProvider, BadgeService badgeService, UserProvider userProvider) {
        this.badgeProvider = badgeProvider;
        this.badgeService = badgeService;
        this.userProvider = userProvider;
    }

    // memberID == null: 뱃지 목록 조회, memberID != null: 회원이 보유한 뱃지 조회
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

    // 유저 뱃지 획득
    @PostMapping("")
    public BaseResponse<PostBadgeRes> addBadge(@RequestBody PostBadgeReq postBadgeReq) {
        if (postBadgeReq.getBadgeID() == null || postBadgeReq.getMemberID() == null) {
            return new BaseResponse<>(POST_BADGE_BADGEID);
        }

        try {
            if(badgeProvider.getBadgeByBadgeID(postBadgeReq.getBadgeID()).isEmpty()) {
                System.out.println(false);
                return new BaseResponse<>(POST_BADGE_INVALID_BADGEID);
            }

            PostBadgeRes postBadgeRes = badgeService.addBadge(postBadgeReq);
            return new BaseResponse<>(postBadgeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
