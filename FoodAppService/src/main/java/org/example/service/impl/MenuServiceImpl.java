package org.example.service.impl;

import org.example.domain.Menu;
import org.example.dao.MenuMapper;
import org.example.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.apache.poi.ss.usermodel.Workbook;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import com.google.common.collect.Lists;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.example.utils.PageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 菜谱信息表 服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2023-10-27
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public void saveByParam(MenuVO obj) {
        System.out.println(obj);
        Menu menu = new Menu(obj);
        this.save(menu);
    }

    @Override
    public void updateByParam(Menu obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<Menu> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public MenuVO getMenuVOById(String id) {
        Menu menu = baseMapper.selectById(id);
        System.out.println(menu);
        return new MenuVO(menu);
    }

    @Override
    public List<Menu> selectBy(Map<String, String> params) {
        QueryWrapper<Menu> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<MenuVO> selectPage(Map<String, String> params) {
        Page<Menu> page = PageUtils.pageHandler(params);
        QueryWrapper<Menu> query = getQuery(params);
        IPage<Menu> result = this.page(page, query);
        return this.MenuToMenuVO(result);
    }

    IPage<MenuVO> MenuToMenuVO(IPage<Menu> page) {
        IPage<MenuVO> result = new Page<>();
        result.setCurrent(page.getCurrent());
        result.setSize(page.getSize());
        result.setPages(page.getPages());
        result.setTotal(page.getTotal());

        System.out.println(page.getRecords().size());
        List<Menu> menuList = page.getRecords();
        List<MenuVO> menuVOList = new ArrayList<>();
        for(Menu menu : menuList){
            System.out.println("=============================");
            System.out.println(menu);
            MenuVO menuVO = new MenuVO(menu);
            System.out.println(menuVO);
            menuVOList.add(menuVO);
        }
        result.setRecords(menuVOList);
        System.out.println(result);
        return result;
    }

    @Override
    public IPage<Menu> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<Menu> page = PageUtils.pageHandler(params);
        QueryWrapper<Menu> query = getQuery(params);
        IPage<Menu> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<Menu> selpageCustomSqlByMap(Map<String, String> params) {
        Page<Menu> page = PageUtils.pageHandler(params);
        IPage<Menu> result = this.baseMapper.selpageCustomSqlByMap(page, params);
        return result;
    }

    /**
     * 下载excel模板
     *
     * @param response HttpServletResponse
     * @param request  HttpServletRequest
     * @return: void
    */
    @Override
    public void downloadExcelTemplate(HttpServletResponse response, HttpServletRequest request) throws Exception{
        List<Menu> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Menu"), Menu.class, data);
        String fileName = String.format("Menu_%d.xls", System.currentTimeMillis());
        response.setHeader("Content-Disposition", "attachment;Filename="+ fileName);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    /**
     * 导入数据
     * @param file
     * @throws Exception
     */
    @Override
    public void uploadExcel(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ImportParams params = new ImportParams();
        // bean 导入
        List<Menu> dataList = new ExcelImportService().importExcelByIs(inputStream, Menu.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<Menu> query = new QueryWrapper<>();
        List<Menu> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Menu"),
        Menu.class, data);
        String fileName = String.format("Menu_%d.xls", System.currentTimeMillis());
        response.setHeader("Content-Disposition", "attachment;Filename="+ fileName);
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    /**
     * 定义数据查询条件
     * @param params
     * @return
     */
    private  QueryWrapper<Menu> getQuery(Map<String, String> params){
        QueryWrapper<Menu> query  = new QueryWrapper<>();
        if(params==null||params.size()<1) {
            return  query;
        }
        for (Map.Entry<String, String> entry:params.entrySet()){
            if(StringUtils.isBlank(entry.getValue())){
                continue;
            }
            if("id".equals(entry.getKey())){
                query.eq("id",entry.getValue());
            }
            if("name".equals(entry.getKey())){
                query.like("name",entry.getValue());
            }
            if("description".equals(entry.getKey())){
                query.eq("description",entry.getValue());
            }
            if("Ingredients".equals(entry.getKey())){
                query.eq("Ingredients",entry.getValue());
            }
            if("steps".equals(entry.getKey())){
                query.eq("steps",entry.getValue());
            }
            if("likes".equals(entry.getKey())){
                query.eq("likes",entry.getValue());
            }
            if("type".equals(entry.getKey())){
                query.eq("type",entry.getValue());
            }
        }
        return  query;
    }
}
