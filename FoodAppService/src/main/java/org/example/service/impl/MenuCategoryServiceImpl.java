package org.example.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.example.dao.MenuCategoryMapper;
import org.example.domain.MenuCategory;
import org.example.service.IMenuCategoryService;
import org.example.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 * @since 2024-01-20
 */
@Service
public class MenuCategoryServiceImpl extends ServiceImpl<MenuCategoryMapper, MenuCategory> implements IMenuCategoryService {

    @Override
    public void saveByParam(MenuCategory obj,Map<String, String> params){
        this.save(obj);
    }

    @Override
    public void updateByParam(MenuCategory obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<MenuCategory> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<MenuCategory> selectBy(Map<String, String> params) {
        QueryWrapper<MenuCategory> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<MenuCategory> selectPage(Map<String, String> params) {
        Page<MenuCategory> page = PageUtils.pageHandler(params);
        QueryWrapper<MenuCategory> query = getQuery(params);
        IPage<MenuCategory> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<MenuCategory> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<MenuCategory> page = PageUtils.pageHandler(params);
        QueryWrapper<MenuCategory> query = getQuery(params);
        IPage<MenuCategory> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<MenuCategory> selpageCustomSqlByMap(Map<String, String> params) {
        Page<MenuCategory> page = PageUtils.pageHandler(params);
        IPage<MenuCategory> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<MenuCategory> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "MenuCategory"), MenuCategory.class, data);
        String fileName = String.format("Category_%d.xls", System.currentTimeMillis());
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
        List<MenuCategory> dataList = new ExcelImportService().importExcelByIs(inputStream, MenuCategory.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<MenuCategory> query = new QueryWrapper<>();
        List<MenuCategory> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "MenuCategory"),
                MenuCategory.class, data);
        String fileName = String.format("MenuCategory%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<MenuCategory> getQuery(Map<String, String> params){
        QueryWrapper<MenuCategory> query  = new QueryWrapper<>();
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
        }
        return  query;
    }
}
