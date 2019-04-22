package app.pp.service;


import app.pp.entity.SaleSlip;
import app.pp.entity.Saleman;

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
    void delete(Integer id);

    /**
     * 销售单列表
     *
     * @return
     */
    List<SaleSlip> selectall(Map<String, Object> param);
}
