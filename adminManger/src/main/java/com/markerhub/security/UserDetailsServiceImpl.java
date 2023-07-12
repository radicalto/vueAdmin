package com.markerhub.security;

import com.markerhub.entity.SysUser;
import com.markerhub.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    SysUserService sysUserService;
    // 重新定义这个查用户数据的过程,重写UserDetailsService接口
    // 让我们登录过程系统是从我们数据库中获取数据的
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser s = sysUserService.getByUsername(username);
        if (s == null) {
            throw  new UsernameNotFoundException("用户或密码不正确");
        }
        return new AccountUser(s.getId(),s.getUsername(),s.getPassword(),getUserAuthority(s.getId()));
    }

    /**
     * 获取用户权限信息（角色，菜单权限）
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Long userId){
        // 获取权限字符串
        String authority = sysUserService.getUserAuthorityInfo(userId);
        // 通过内置的工具类，把权限字符串封装成GrantedAuthority列表
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
