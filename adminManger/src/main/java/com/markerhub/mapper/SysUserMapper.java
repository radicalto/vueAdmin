package com.markerhub.mapper;

import com.markerhub.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 我的公众号：MarkerHub
 * @since 2023-03-04
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

     List<Long> getNavMenuIds(Long userId);

     List<SysUser> listByMenuId(Long menuId);
}
