package com.example.demo.src.area;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.area.model.GetAreaRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Profile("prod")
@RequestMapping("/app/areas")
public class AreaController {

    @Autowired
    private final AreaService areaService;
    @Autowired
    private final AreaProvider areaProvider;

    public AreaController(AreaService areaService, AreaProvider areaProvider) {
        this.areaService = areaService;
        this.areaProvider = areaProvider;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetAreaRes>> getAreas(@RequestParam(value="page", defaultValue = "0") int page, @RequestParam(required = false) Long memberID) {
        try {
            if(memberID == null) {
                System.out.println(true);
                List<GetAreaRes> getAreaRes = areaProvider.getAreas(page);
                return new BaseResponse<>(getAreaRes);
            }

            List<GetAreaRes> getAreaRes = areaProvider.getAreaByMemberID(page, memberID);
            return new BaseResponse<>(getAreaRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
