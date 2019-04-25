package app.pp.service.impl;

import app.pp.common.Result;
import app.pp.entity.*;
import app.pp.enums.ErrorEnum;
import app.pp.mapper.PolicyMapper;
import app.pp.service.GroupService;
import app.pp.service.PolicyService;
import app.pp.utils.GlobleUtils;
import app.pp.utils.ResultUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.Pipe;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    PolicyMapper policyMapper;
    @Autowired
    GroupService groupService;

    //批量生成保单号
    @Override
    public Result verb(Policy policy) {
        //首先先查询前缀是否已经生成过保单号
        int count = policyMapper.selectByPrefix(policy.getPrefix());

        List<Policy> list = new ArrayList<Policy>();

        //大于0就证明该前缀已经被生成过 如果被生成过 接下来查询上次生成到的数
        if(count>0){
            //查询上一次生成到的数
            int num = policyMapper.selectNumber(policy.getPrefix());

            if(num==99999)

                return ResultUtils.fail("以到最大生成数,请联系管理员");

            NumberFormat f=new DecimalFormat("00000");

            for(int i=num+1;i<=num+policy.getVerb();i++){
                //若果到了最大生成数就跳出循环进行插入操作
                if(i>99999)

                    break;

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(policy.getPrefix());

                stringBuilder.append(f.format(i));

                list.add(set(policy.getPrefix(),i,stringBuilder.toString(),policy.getGroupid()));

            }

        }else{
            //如果该前缀是第一次生成那么就从1开始
            NumberFormat f=new DecimalFormat("00000");

            for(int i=1;i<=policy.getVerb();i++){
                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(policy.getPrefix());

                stringBuilder.append(f.format(i));
                list.add(set(policy.getPrefix(),i,stringBuilder.toString(),policy.getGroupid()));

            }

        }

        //生成之后存入库里
        int insert = policyMapper.insert(list);

        if(insert>0){

            return ResultUtils.result(ErrorEnum.SUCCESS,"生成保单号成功");

        }else{

            return ResultUtils.fail("生成保单号失败");

        }


    }

    //删除保单号
    @Override
    public Result del(Integer policyid) {

        int del = policyMapper.del(policyid);
        if(del>0){
            return ResultUtils.result(ErrorEnum.SUCCESS,"删除成功");
        }else{
            return ResultUtils.fail("删除失败");
        }

    }
    //保单号转移车行
    @Override
    public Result transfer(TransferCarEntity transferCarEntity) {
            int num = 0;
        for (int i = 0;i<transferCarEntity.getPolicys().size();i++){
            Policy policy = new Policy();
            policy.setId(transferCarEntity.getPolicys().get(i));
            policy.setGroupid(transferCarEntity.getGroupid());
           num =  policyMapper.updateByPrimaryKeySelective(policy);
        }
        if(num>0){
            return ResultUtils.result(ErrorEnum.SUCCESS,"车行转移成功");
        }else{
            return ResultUtils.fail("车行转移失败");
        }

    }

    @Override
    //保单号查询
    public Result list(PolicyEntity policy) {
        if(policy.getGroupid()==null){
            policy.setGroup(groupService.selectall());
        }

        PageHelper.startPage(null == policy.getPage() ? 1 : policy.getPage(), GlobleUtils.DEFAULT_PAGE_SIZE);
        PageInfo<PolicyEntity> pageInfo = new PageInfo<PolicyEntity>(policyMapper.list(policy));
        return ResultUtils.result(ErrorEnum.SUCCESS,pageInfo);
    }


    public Policy set(String prefix,Integer number,String pnum,Integer groupid){

        SysUserEntity s = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();

        Policy policys = new Policy();

        policys.setCreatedtime(new Date());

        policys.setCreator(s.getUserId());

        policys.setNumber(number);

        policys.setPrefix(prefix);

        policys.setGroupid(groupid);

        policys.setIsdel(0);

        policys.setPnum(pnum);

        policys.setState(1);

        return  policys;
    }
}
