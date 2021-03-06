package app.pp.mapper;

import app.pp.entity.Policy;
import app.pp.entity.PolicyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
@Component
@Mapper
public interface PolicyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(List<Policy> record);

    int insertSelective(Policy record);

    Policy selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Policy record);

    //通过前缀查询该前缀是否已经生成过保单号

    int selectByPrefix(String prefix);

    //查询上次升到的数
    int selectNumber(String prefix);

    //删除保单号
    int del(Integer policyid);

    //获取车行分组对应未关联的保单号
    Policy selectByGroupid(Integer groupid);


    Policy  selectByCondition(@Param("groupid")Integer groupid, @Param("amount")BigDecimal amount,@Param("yearlimit")Integer yearlimit);


    //保单号查询
    List<PolicyEntity> list(PolicyEntity policyEntity);
}