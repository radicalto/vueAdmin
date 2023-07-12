package com.markerhub.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.markerhub.entity.SysMenu;
import com.markerhub.entity.SysRole;
import com.markerhub.entity.SysUser;
import com.markerhub.mapper.SysUserMapper;
import com.markerhub.service.SysMenuService;
import com.markerhub.service.SysRoleService;
import com.markerhub.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.markerhub.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2023-03-04
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public SysUser getByUsername(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username",username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        String authority = "";
        SysUser sysUser = sysUserMapper.selectById(userId);

        // 判断redis内是否有权限信息
        if (redisUtil.hasKey("GrantedAuthority:" + sysUser.getUsername())) {
             authority = (String) redisUtil.get("GrantedAuthority:" + sysUser.getUsername());
        }
        else {
            // 获取角色
            List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>()
                    .inSql("id", "select role_id from sys_user_role where user_id = " + userId));
            if (roles.size() > 0) {
                roles.stream().map(r -> "ROLE_"+r.getCode()).collect(Collectors.joining(","));
            }

            // 获取菜单编码
            List<Long> menu_ids = sysUserMapper.getNavMenuIds(userId);
            if (menu_ids.size() > 0) {
                List<SysMenu> menus = sysMenuService.listByIds(menu_ids);
                String menuPerms = menus.stream().map(m ->m.getPerms()).collect(Collectors.joining(","));
                authority=authority.concat(",").concat(menuPerms);
                log.info("用户ID - {} ---拥有的权限：{}", userId, authority);
            }
            // 将权限信息持久化 下次访问就会直接对应key找value效率高
            redisUtil.set("GrantedAuthority:"+sysUser.getUsername(),authority,60 * 60);
        }

        return authority;
    }

    // 删除某个用户的权限信息
    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("GrantedAuthority:"+username);
    }

    // 删除所有与该角色关联的用户的权限信息
    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {
        List<SysUser> sysUsers = this.list(new QueryWrapper<SysUser>()
                .inSql("id", "select user_id from sys_user_role where role_id = " + roleId));
        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    // 删除所有与该菜单关联的所有用户的权限信息
    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        List<SysUser> sysUsers = sysUserMapper.listByMenuId(menuId);
        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

}
