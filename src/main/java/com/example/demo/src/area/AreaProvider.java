package com.example.demo.src.area;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.area.model.GetAreaRes;
import com.example.demo.src.product.Model.GetProductRes;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class AreaProvider {
    AreaDao areaDao = new AreaDao();

    public AreaProvider(AreaDao areaDao) {
        this.areaDao = areaDao;
    }

    @Transactional
    public List<GetAreaRes> getAreas(int page) throws BaseException {
        try {
            List<GetAreaRes> getAreaRes = areaDao.getAreas(page);
            return getAreaRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetAreaRes> getAreaByMemberID (int page, Long memberID) throws BaseException{
        try {
            List<GetAreaRes> getAreaRes = areaDao.getAreaByMemberID(page, memberID);
            return getAreaRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
