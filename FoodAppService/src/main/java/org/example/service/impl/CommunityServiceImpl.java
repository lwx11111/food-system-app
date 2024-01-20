package org.example.service.impl;

import org.example.domain.Community;
import org.example.dao.CommunityMapper;
import org.example.service.ICommunityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 社区帖子表 服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2023-10-27
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements ICommunityService {

    @Override
    public void saveByParam(Community obj,Map<String, String> params){
        if (obj.getReleaseTime() == null) {
            obj.setReleaseTime(LocalDateTime.now());
        }
        this.save(obj);
    }

    @Override
    public void updateByParam(Community obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<Community> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<Community> selectBy(Map<String, String> params) {
        QueryWrapper<Community> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<Community> selectPage(Map<String, String> params) {
        Page<Community> page = PageUtils.pageHandler(params);
        QueryWrapper<Community> query = getQuery(params);
        IPage<Community> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<Community> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<Community> page = PageUtils.pageHandler(params);
        QueryWrapper<Community> query = getQuery(params);
        IPage<Community> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<Community> selpageCustomSqlByMap(Map<String, String> params) {
        Page<Community> page = PageUtils.pageHandler(params);
        IPage<Community> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<Community> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Community"), Community.class, data);
        String fileName = String.format("Community_%d.xls", System.currentTimeMillis());
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
        List<Community> dataList = new ExcelImportService().importExcelByIs(inputStream, Community.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<Community> query = new QueryWrapper<>();
        List<Community> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Community"),
        Community.class, data);
        String fileName = String.format("Community_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<Community> getQuery(Map<String, String> params){
        QueryWrapper<Community> query  = new QueryWrapper<>();
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
            if("content".equals(entry.getKey())){
                query.eq("content",entry.getValue());
            }
            if("releaseTime".equals(entry.getKey())){
                query.eq("release_time",entry.getValue());
            }
            if("likes".equals(entry.getKey())){
                query.eq("likes",entry.getValue());
            }
            if("title".equals(entry.getKey())){
                query.like("title",entry.getValue());
            }
            if("categoryId".equals(entry.getKey())){
                query.eq("category_id",entry.getValue());
            }
        }
        return  query;
    }
}
