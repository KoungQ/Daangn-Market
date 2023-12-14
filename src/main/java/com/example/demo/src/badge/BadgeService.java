package com.example.demo.src.badge;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.badge.model.PostBadgeReq;
import com.example.demo.src.badge.model.PostBadgeRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.DELETE_FAIL_BADGE;

@Service
@Slf4j
public class BadgeService {

    private final BadgeDao badgeDao;

    @Autowired
    public BadgeService(BadgeDao badgeDao) {
        this.badgeDao = badgeDao;
    }

    @Transactional
    public PostBadgeRes addBadge(PostBadgeReq postBadgeReq) throws BaseException {
        try {
            int badgeID = badgeDao.addBadge(postBadgeReq);
            PostBadgeRes postBadgeRes = new PostBadgeRes(badgeID);
            return postBadgeRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteBadgeByMemberID (Long memberID) throws BaseException{
        try {
            int result = badgeDao.deleteBadgeByBadgeID(memberID);
            if(result == 0) {
                throw new BaseException(DELETE_FAIL_BADGE);
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
