package app.pp.mapper;

import app.pp.common.BaseMapper;
import app.pp.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:33
 */
@Mapper
@Component
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);

    void updateById(SysRoleEntity role);

	void deleteBatchIds(List<Long> longs);

	List<SysRoleEntity> selectByMap(Map<String, Object> map);

	SysRoleEntity selectById(Long roleId);

	List<SysRoleEntity> selectWaibuByMap(Map<String, Object> map);
}
