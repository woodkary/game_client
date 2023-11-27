package com.kary.hahaha3.controller.pagesAndIndexControllers;

import com.kary.hahaha3.controller.BaseController;
import com.kary.hahaha3.exceptions.expired.SessionExpireException;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.RecordVO;
import com.kary.hahaha3.service.RecordVOService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:123
 */
//TODO css样式表太丑啦！求一个好看的样式。
@RestController
@Tag(name = "首页")
public class IndexController extends BaseController {
    @Autowired
    @Qualifier("RecordVOService")
    private RecordVOService recordVOService;
    @GetMapping({"/","/index"})
    @Operation(summary = "返回首页",description = "return to index")
    public JsonResult index0(HttpSession session) throws Exception {
        User myAccount= (User) session.getAttribute("myAccount");
        if(myAccount==null){
            throw new SessionExpireException("你尚未登录");
        }
        List<RecordVO> recordVO=recordVOService.getGamesByIds(myAccount.getUsername(), 1,1);
        return JsonResult.ok(recordVO,"这是战绩");
    }
}
