package org.example.service;

import org.example.domain.MenuLike;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户点赞表 服务类
 * </p>
 *
 * 
 * @since 2023-10-27
 */
public interface IMenuLikeService extends IService<MenuLike> {
    void deleteMenuLikeByParams(MenuLike obj);
    /**
     * 根据参数保存
     * @param obj
     * @param params
     * @return: void
     */
    void saveByParam(MenuLike obj,Map<String, String> params);

    /**
     * 根据参数更新
     * @param obj
     * @param params
     * @return: void
     */
    void updateByParam(MenuLike obj,Map<String, String> params);
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
     * @return: List<MenuLike>
     */
     List<MenuLike> selectBy(Map<String, String> params);

    /**
     * 分页查询
     *
     * @param params
     * @return: IPage<MenuLike>
    */
    IPage<MenuLike> selectPage(Map<String, String> params);

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param params
     * @return: IPage<MenuLike>
    */
    IPage<MenuLike> selpageCustomSqlByWrapper(Map<String, String> params);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param params
     * @return: IPage<MenuLike>
    */
    IPage<MenuLike> selpageCustomSqlByMap(Map<String, String> params);

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
