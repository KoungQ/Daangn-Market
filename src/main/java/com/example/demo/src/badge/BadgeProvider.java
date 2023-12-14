package com.example.demo.src.badge;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.badge.model.GetBadgeRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class BadgeProvider {

    private final BadgeDao badgeDao;

    @Autowired
    public BadgeProvider(BadgeDao badgeDao) {
        this.badgeDao = badgeDao;
    }

    @Transactional
    public List<GetBadgeRes> getBadges(int page) throws BaseException {
        try {
            List<GetBadgeRes> getBadgeRes = badgeDao.getBadges(page);
            return getBadgeRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public List<GetBadgeRes> getBadgesByMemberID(int page, Long memberID) throws BaseException {
        try {
            List<GetBadgeRes> getBadgeRes = badgeDao.getBadgesByMemberID(page, memberID);
            return getBadgeRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
