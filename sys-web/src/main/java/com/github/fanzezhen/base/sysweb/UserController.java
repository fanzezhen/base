package com.github.fanzezhen.base.sysweb;

import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.github.fanzezhen.common.core.annotion.BizLog;
import com.github.fanzezhen.common.core.constant.CommonConstant;
import com.github.fanzezhen.common.core.enums.auth.PermissionTypeEnum;
import com.github.fanzezhen.common.core.model.dto.PageDto;
import com.github.fanzezhen.common.core.struct.SysUserBizLogStruct;
import com.github.fanzezhen.base.sysbiz.facade.*;
import com.github.fanzezhen.base.sysbiz.foundation.entity.SysPermission;
import com.github.fanzezhen.base.sysbiz.foundation.entity.SysRole;
import com.github.fanzezhen.base.sysbiz.foundation.entity.SysUser;
import com.github.fanzezhen.base.sysbiz.model.dto.SysPermissionDto;
import com.github.fanzezhen.base.sysbiz.model.dto.SysRoleDto;
import com.github.fanzezhen.base.sysbiz.model.dto.SysUserDto;
import com.github.fanzezhen.common.security.util.SecurityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zezhen.fan
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private SysUserServiceFacade sysUserServiceFacade;
    @Resource
    private SysRoleServiceFacade sysRoleServiceFacade;
    @Resource
    private SysUserRoleServiceFacade sysUserRoleServiceFacade;
    @Resource
    private SysPermissionServiceFacade sysPermissionServiceFacade;

    @GetMapping("/list")
    public String list() {
        return "user/user-list";
    }

    @PostMapping("/list/page")
    @ResponseBody
    public ResponseData listPage(@RequestBody PageDto<SysUserDto, SysUser> page) {
        return ResponseData.success(sysUserServiceFacade.page(page));
    }

    @GetMapping("/edit")
    public String edit(@RequestParam(required = false) String userId, ModelMap modelMap) {
        modelMap.addAttribute("sysUser", userId == null ? new SysUser() : sysUserServiceFacade.getById(userId));
        modelMap.addAttribute("title", userId == null ? "添加" : "修改");
        modelMap.addAttribute("sysRoleList", sysRoleServiceFacade.listValid());
        modelMap.addAttribute("sysRoleIdList", sysRoleServiceFacade.listIdObjByUserId(userId));
        return "user/user-edit";
    }


    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.addAttribute("sysUser", new SysUser());
        modelMap.addAttribute("title", "添加");
        modelMap.addAttribute("sysRoleList", sysRoleServiceFacade.listValid());
        modelMap.addAttribute("sysRoleIdList", new ArrayList<>());
        return "user/user-edit";
    }

    @PostMapping("/save")
    @ResponseBody
    @BizLog(value = "保存用户", struct = SysUserBizLogStruct.class)
    public ResponseData save(@RequestBody SysUserDto sysUserDto) {
        return ResponseData.success(sysUserServiceFacade.save(sysUserDto));
    }

    @PostMapping("/save/batch")
    @ResponseBody
    @BizLog(value = "保存用户", struct = SysUserBizLogStruct.class)
    public ResponseData saveBatch(@RequestBody List<SysUserDto> sysUserDtoList) {
        return ResponseData.success(sysUserServiceFacade.saveBatch(sysUserDtoList));
    }

    @PostMapping("/add")
    @ResponseBody
    @BizLog(value = "新增用户", struct = SysUserBizLogStruct.class)
    public ResponseData add(@RequestBody SysUserDto sysUserDto) {
        sysUserDto.setId(null);
        sysUserDto.setPassword(SecurityUtil.encrypt(CommonConstant.DEFAULT_USER_PASSWORD));
        return ResponseData.success(sysUserServiceFacade.save(sysUserDto));
    }

    @GetMapping("/role")
    public String role() {
        return "user/user-role";
    }

    @PostMapping("/role/list/page")
    @ResponseBody
    public ResponseData roleListPage(@RequestBody PageDto<SysRoleDto, SysRole> page) {
        return ResponseData.success(sysRoleServiceFacade.page(page));
    }

    @GetMapping("/role/add")
    public String roleAdd(ModelMap modelMap) {
        modelMap.addAttribute("sysRole", new SysRole());
        modelMap.addAttribute("title", "添加角色");
        modelMap.addAttribute("permissionIdList", new ArrayList<>());
        modelMap.addAttribute("permissionTree", sysPermissionServiceFacade.listMenuPermissionByUserId(SecurityUtil.getLoginUserId()));
        return "user/role-edit";
    }

    @GetMapping("/role/edit")
    public String roleEdit(@RequestParam() String roleId, ModelMap modelMap) {
        modelMap.addAttribute("sysRole", sysRoleServiceFacade.getById(roleId));
        modelMap.addAttribute("title", "修改角色");
        modelMap.addAttribute("permissionIdList", sysPermissionServiceFacade.listPermissionByRoleId(roleId));
        modelMap.addAttribute("permissionTree", sysPermissionServiceFacade.listMenuPermissionByUserId(SecurityUtil.getLoginUserId()));
        return "user/role-edit";
    }

    @GetMapping("/role/edit-button")
    public String roleEditButton(@RequestParam String roleId, ModelMap modelMap) {
        modelMap.addAttribute("sysRole", sysRoleServiceFacade.getById(roleId));
        modelMap.addAttribute("title", "修改角色");
        modelMap.addAttribute("permissionIdList", sysPermissionServiceFacade.listPermissionByRoleId(roleId));
        modelMap.addAttribute("permissionTree", sysPermissionServiceFacade.listMenuPermissionByUserId(SecurityUtil.getLoginUserId()));
        return "user/role-edit-button";
    }

    @PostMapping("/role/save-button")
    @ResponseBody
    public ResponseData roleSaveButton(@RequestBody SysRoleDto sysRoleDto) {
        return ResponseData.success(sysRoleDto);
    }


    @PostMapping("/role/save")
    @ResponseBody
    public ResponseData roleSave(@RequestBody SysRoleDto sysRoleDto) {
        return ResponseData.success(sysRoleDto);
    }

    @PostMapping("/role/del/batch")
    @ResponseBody
    public ResponseData roleDelBatch(@RequestParam(value = "idList") List<String> idList) {
        return ResponseData.success();
    }

    @GetMapping("/rule")
    public String ruleList(ModelMap modelMap) {
        modelMap.addAttribute("typeList", PermissionTypeEnum.values());
        return "user/user-rule";
    }

    @PostMapping("/rule/page")
    @ResponseBody
    public ResponseData rulePage(@RequestBody PageDto<SysPermissionDto, SysPermission> page) {
        return ResponseData.success(page);
    }

    @GetMapping("/rule/add")
    public String ruleEdit(ModelMap modelMap) {
        modelMap.addAttribute("sysRule", new SysPermission());
        modelMap.addAttribute("sysRuleList", new ArrayList<>());
        modelMap.addAttribute("typeList", PermissionTypeEnum.values());
        modelMap.addAttribute("title", "添加");
        return "user/rule-edit";
    }

    @PostMapping("/rule/save")
    @ResponseBody
    public ResponseData ruleSave(@RequestBody SysPermissionDto sysPermissionDto) {
        return ResponseData.success(sysPermissionDto);
    }
}
