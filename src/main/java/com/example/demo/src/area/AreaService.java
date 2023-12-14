package com.example.demo.src.area;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.area.model.PatchAreaReq;
import com.example.demo.src.area.model.PostAreaReq;
import com.example.demo.src.area.model.PostAreaRes;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
@Slf4j
public class AreaService {

    private final AreaDao areaDao;

    @Autowired
    public AreaService(AreaDao areaDao) {
        this.areaDao = areaDao;
    }

    @Transactional
    public void modifyArea(PatchAreaReq patchAreaReq) throws BaseException {
        try {
            System.out.println(patchAreaReq.getAreaID() + " " + patchAreaReq.getMemberID());
            int result = areaDao.modifyArea(patchAreaReq);
            System.out.println(result);
            if(result == 0)
                throw new BaseException(MODIFY_FAIL_AREA);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public PostAreaRes addArea(PostAreaReq postAreaReq) throws BaseException {
        try {
            int memberAreaID = areaDao.addArea(postAreaReq);
            PostAreaRes postAreaRes = new PostAreaRes(memberAreaID);
            return postAreaRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteAreaByMemberID(Long memberID) throws BaseException {
        try {
            int result = areaDao.deleteAreaByMemberID(memberID);
            if(result == 0) {
                throw new BaseException(DELETE_FAIL_AREA);
            }
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
