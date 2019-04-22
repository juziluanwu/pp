package app.pp.service;


import app.pp.entity.Saleman;

import java.util.List;
import java.util.Map;


/**
 * 销售人员
 */
public interface SalemanService {


    /**
     * 保存销售人员
     */
    void save(Saleman saleman);

    /**
     * 修改销售人员
     */
    void update(Saleman saleman);

    /**
     * 删除分销售人员
     */
    void delete(Integer id);

    /**
     * 销售人员
     *
     * @return
     */
    List<Saleman> selectall(Map<String,Object> param);
}
