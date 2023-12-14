package com.example.demo.src.area;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.area.model.GetAreaRes;
import com.example.demo.src.area.model.PatchAreaReq;
import com.example.demo.src.area.model.PostAreaReq;
import com.example.demo.src.area.model.PostAreaRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.AREA_EMPTY_AREAID;
import static com.example.demo.config.BaseResponseStatus.POST_AREA_EMPTY_AREA;

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

    // memberID == null, 전체 지역 조회 아니면 memberID로 조회
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

    // 지역 변경
    @ResponseBody
    @PatchMapping("/{memberID}")
    public BaseResponse<String> modifyArea(@PathVariable("memberID") Long memberID, @RequestParam Long areaID) {
        if(areaID == null)
            return new BaseResponse<>(AREA_EMPTY_AREAID);

        try {
            PatchAreaReq patchAreaReq = new PatchAreaReq(memberID, areaID);
            areaService.modifyArea(patchAreaReq);

            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    // 회원 가입 후 내 지역 선택
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostAreaRes> addArea(@RequestBody PostAreaReq postAreaReq) {
        if(postAreaReq.getAreaID() == null) {
            return new BaseResponse<>(POST_AREA_EMPTY_AREA);
        }
        try {
            PostAreaRes postAreaRes = areaService.addArea(postAreaReq);
            return new BaseResponse<>(postAreaRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
