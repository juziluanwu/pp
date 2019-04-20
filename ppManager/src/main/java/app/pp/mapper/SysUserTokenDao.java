package app.pp.mapper;

import app.pp.common.BaseMapper;
import app.pp.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 系统用户Token
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:07
 */
@Mapper
@Component
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);

    SysUserTokenEntity selectById(long userId);

    void updateById(SysUserTokenEntity tokenEntity);

}
