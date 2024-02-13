package org.example.service.impl;

import org.example.domain.MenuCollection;
import org.example.dao.MenuCollectionMapper;
import org.example.service.IMenuCollectionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

import java.io.InputStream;
import org.example.utils.PageUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户点赞表 服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2024-01-08
 */
@Service
public class MenuCollectionServiceImpl extends ServiceImpl<MenuCollectionMapper, MenuCollection> implements IMenuCollectionService {
    @Override
    public void deleteMenuCollectionByParams(MenuCollection obj) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", obj.getUserId().toString());
        params.put("menu_id", obj.getMenuId().toString());
        this.removeByMap(params);
    }
    @Override
    public void saveByParam(MenuCollection obj,Map<String, String> params){
        this.save(obj);
    }

    @Override
    public void updateByParam(MenuCollection obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<MenuCollection> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<MenuCollection> selectBy(Map<String, String> params) {
        QueryWrapper<MenuCollection> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<MenuCollection> selectPage(Map<String, String> params) {
        Page<MenuCollection> page = PageUtils.pageHandler(params);
        QueryWrapper<MenuCollection> query = getQuery(params);
        IPage<MenuCollection> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<MenuCollection> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<MenuCollection> page = PageUtils.pageHandler(params);
        QueryWrapper<MenuCollection> query = getQuery(params);
        IPage<MenuCollection> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<MenuCollection> selpageCustomSqlByMap(Map<String, String> params) {
        Page<MenuCollection> page = PageUtils.pageHandler(params);
        IPage<MenuCollection> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<MenuCollection> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "MenuCollection"), MenuCollection.class, data);
        String fileName = String.format("MenuCollection_%d.xls", System.currentTimeMillis());
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
        List<MenuCollection> dataList = new ExcelImportService().importExcelByIs(inputStream, MenuCollection.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<MenuCollection> query = new QueryWrapper<>();
        List<MenuCollection> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "MenuCollection"),
        MenuCollection.class, data);
        String fileName = String.format("MenuCollection_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<MenuCollection> getQuery(Map<String, String> params){
        QueryWrapper<MenuCollection> query  = new QueryWrapper<>();
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
            if("userId".equals(entry.getKey())){
                query.eq("user_id",entry.getValue());
            }
            if("menuId".equals(entry.getKey())){
                query.eq("menu_id",entry.getValue());
            }
        }
        return  query;
    }
}
