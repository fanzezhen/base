package com.github.fanzezhen.base.sysweb;

import com.github.fanzezhen.common.core.util.SysUtil;
import com.github.fanzezhen.base.sysbiz.facade.SysPermissionServiceFacade;
import com.github.fanzezhen.common.security.util.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class CommonController extends com.github.fanzezhen.base.sysweb.BaseController {
    @Resource
    private SysPermissionServiceFacade sysPermissionServiceFacade;

    @GetMapping("/")
    public String empty() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("permissionList", sysPermissionServiceFacade.listMenuPermissionByUserId(SecurityUtil.getLoginUserId()));
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome(ModelMap modelMap) {
        modelMap.addAttribute("javaVersion", System.getProperty("java.version") + " " + System.getProperty("java.vm.name"));
        modelMap.addAttribute("diskRating", SysUtil.getDiskRating());
        return "welcome";
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

}
