package org.example.service;

import org.example.domain.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.vo.MenuVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜谱信息表 服务类
 * </p>
 *
 * 
 * @since 2023-10-27
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> listMenuByNames(Map<String, String> params);
    IPage<MenuVO> getDailyRecommendation(Map<String, String> params);

    IPage<MenuVO> getMenuCollectionByUserId(Map<String, String> params);
    /**
     * 根据参数保存
     * @param obj
     * @return: void
     */
    void saveByParam(MenuVO obj);

    /**
     * 根据参数更新
     * @param obj
     * @param params
     * @return: void
     */
    void updateByParam(Menu obj,Map<String, String> params);
    /**
     * 根据条件删除
     *
     * @param params
     * @return: void
     */
    void deleteBy(Map<String, String> params);


    MenuVO getMenuVOById(String id);

    /**
     * 根据条件查询
     *
     * @param params
     * @return: List<Menu>
     */
     List<Menu> selectBy(Map<String, String> params);

    /**
     * 分页查询
     *
     * @param params
     * @return: IPage<Menu>
    */
    IPage<MenuVO> selectPage(Map<String, String> params);

    /**
     * 分页查询-自定义sql-Wrapper
     *
     * @param params
     * @return: IPage<Menu>
    */
    IPage<Menu> selpageCustomSqlByWrapper(Map<String, String> params);

    /**
     * 分页查询-自定义sql-Map
     *
     * @param params
     * @return: IPage<Menu>
    */
    IPage<Menu> selpageCustomSqlByMap(Map<String, String> params);

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
