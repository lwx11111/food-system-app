package org.example.service;

import org.example.domain.ShopOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 *
 * @since 2024-01-09
 */
public interface IShopOrderService extends IService<ShopOrder> {

    List<getRadarDataVo> getRadarData() throws Exception;

    List<getHotItemDataVo> getHotItemData() throws Exception;

    List<getDataNearlySixMonthsVo> getDataNearlySixMonths() throws Exception;

    List<ShopOrderStatisticalDataVo> getStatisticalData() throws Exception;

    List<ShopOrderVO> listShopOrderByUserId(Map<String, String> params) throws Exception;
    /**
     * 根据参数保存
     * @param obj
     * @param params
     * @return: void
     */
    String saveByParam(ShopOrder obj,Map<String, String> params);

    /**
     * 根据参数更新
     * @param obj
     * @param params
     * @return: void
     */
    void updateByParam(ShopOrder obj,Map<String, String> params);
    /**
     * 根据条件删除
     *
     * @param params
     * @return: void
     */
    void deleteBy(Map<String, String> params);

    /**
     * 根据条件查询
     *
     * @param params
     * @return: List<ShopOrder>
     */
     List<ShopOrder> selectBy(Map<String, String> params);

    /**
     * 分页查询
     *
     * @param params
     * @return: IPage<ShopOrder>
    */
    IPage<ShopOrder> selectPage(Map<String, String> params);

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param params
     * @return: IPage<ShopOrder>
    */
    IPage<ShopOrder> selpageCustomSqlByWrapper(Map<String, String> params);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param params
     * @return: IPage<ShopOrder>
    */
    IPage<ShopOrder> selpageCustomSqlByMap(Map<String, String> params);

    /**
     * 下载excel模板
     *
     * @param response HttpServletResponse
     * @param request  HttpServletRequest
     * @return: void
    */
    void downloadExcelTemplate(HttpServletResponse response, HttpServletRequest request) throws Exception;

    /**
     * 导入数据
     * @param file
     * @throws Exception
     */
    void uploadExcel(MultipartFile file) throws Exception;

    /**
     * 导出数据
     *
     * @param response HttpServletResponse
     * @param request  HttpServletRequest
     * @param params
     * @return: void
    */
    void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception;
}
