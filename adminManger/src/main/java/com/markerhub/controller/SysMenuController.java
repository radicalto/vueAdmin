package com.markerhub.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.common.dto.SysMenuDto;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.SysMenu;
import com.markerhub.entity.SysRoleMenu;
import com.markerhub.entity.SysUser;
import com.markerhub.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Action;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2023-03-04
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

//    获取当前用户导航和权限信息
    @GetMapping("/nav")
    public Result nav(Principal principal){
        SysUser sysUser = sysUserService.getByUsername(principal.getName());
        // 获取权限信息
        String authorityInfo = sysUserService.getUserAuthorityInfo(sysUser.getId());
        String[] authorityArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");
        // 获取导航栏信息
        List<SysMenuDto> navs = sysMenuService.getCurrentUserMenu();
        return Result.succ(
                MapUtil.builder()
                        .put("nav",navs)
                        .put("authoritys",authorityArray).map()
        );
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable(name = "id") Long id){

        return Result.succ(sysMenuService.getById(id));
    }

    @GetMapping("/list")
    public Result list(){
        List<SysMenu> menus = sysMenuService.tree();
        return Result.succ(menus);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody SysMenu sysMenu){
        sysMenu.setCreated(LocalDateTime.now());
        sysMenuService.save(sysMenu);
        return Result.succ(sysMenu);
    }

    @PostMapping("/update")
    public Result update(@Validated @RequestBody SysMenu sysMenu){
        sysMenu.setCreated(LocalDateTime.now());
        sysMenuService.updateById(sysMenu);
        // 清除与菜单有关的所有缓存
        sysUserService.clearUserAuthorityInfoByMenuId(sysMenu.getId());
        return Result.succ(sysMenu);
    }

    @PostMapping("/delete/{id}")
    public Result delete(@PathVariable(name = "id") Long id){
        int count = sysMenuService.count(new QueryWrapper<SysMenu>().eq("parent_id", id));
        if (count > 0){
            return Result.fail("请先删除子菜单");
        }
        // 清除与菜单有关的所有缓存
        sysUserService.clearUserAuthorityInfoByMenuId(id);
        sysMenuService.removeById(id);

        // 同步删除相关中间表的相关信息
        sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id", id));
        return Result.succ("");


    }
}
