package com.markerhub.service;

import com.markerhub.common.dto.SysMenuDto;
import com.markerhub.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2023-03-04
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenuDto> getCurrentUserMenu();

    List<SysMenu> tree();
}
