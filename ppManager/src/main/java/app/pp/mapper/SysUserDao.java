package app.pp.mapper;


import app.pp.common.BaseMapper;
import app.pp.entity.SysUserEntity;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
@Mapper
@Component
public interface SysUserDao extends BaseMapper<SysUserEntity> {

	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);

	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);

	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);

	SysUserEntity selectById(Long userId);

	void updateById(SysUserEntity user);

	Integer deleteBatchIds(List<Long> array);

	List<SysUserEntity> selectByMap(Map params);

	int updatePassword(@Param("userId") Long userId, @Param("password") String password, @Param("newPassword") String newPassword);

	List<String> selectPermissByUserId(@Param("userId") Long userId);

	SysUserEntity selectByUsername(String username);
}
