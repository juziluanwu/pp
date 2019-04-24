package app.pp.service;


import app.pp.entity.SaleSlip;
import app.pp.entity.Saleman;
import app.pp.vo.RenewalVO;
import app.pp.vo.SaleSlipDelVO;

import java.util.List;
import java.util.Map;


/**
 * 销售单
 */
public interface SaleSlipService {


    /**
     * 保存销售单
     */
    void save(SaleSlip slip);

    /**
     * 修改销售单
     */
    void update(SaleSlip slip);

    /**
     * 删除销售单
     */
    void delete(SaleSlipDelVO vo);

    /**
     * 销售单列表
     *
     * @return
     */
    List<SaleSlip> selectall(Map<String, Object> param);

    /**
     * 销售单详情
     */

    SaleSlip info(Integer id);

    /**
     * 保单续费
     * @param vo
     */
    void renewal(RenewalVO vo);

    /**
     * 获取保单续期信息
     * @param id
     * @return
     */
    Map<String,Object> getRenewalInfo(Integer id);

}
