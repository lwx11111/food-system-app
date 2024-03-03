package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.example.domain.Dic;
import org.example.dao.DicMapper;
import org.example.service.IDicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.vo.FoodsVO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2024-01-22
 */
@Service
public class DicServiceImpl extends ServiceImpl<DicMapper, Dic> implements IDicService {

    @Override
    public void updateValueById(String id, String value) {
        LambdaUpdateWrapper<Dic> update = new LambdaUpdateWrapper<Dic>()
                .eq(Dic::getId, id)
                .set(Dic::getValue, value);
        this.update(update);
    }

    @Override
    public Dic getDicByKeyy(String keyy) {
        LambdaQueryWrapper<Dic> query = new LambdaQueryWrapper<Dic>()
                .eq(Dic::getKeyy, keyy);
        List<Dic> list = this.list(query);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Dic> listParentDic() {
        LambdaQueryWrapper<Dic> query = new LambdaQueryWrapper<>();
        query.isNull(Dic::getParentId);
        return this.list(query);
    }

    @Override
    public FoodsVO listFoods() {
        LambdaQueryWrapper<Dic> query = new LambdaQueryWrapper<>();
        query.isNull(Dic::getParentId);
        List<Dic> categoryNames = this.list(query);
        List<List<Dic>> foods = new ArrayList<>();
        for (Dic category : categoryNames) {
            LambdaQueryWrapper<Dic> query1 = new LambdaQueryWrapper<>();
            query1.eq(Dic::getParentId, category.getId());
            List<Dic> food = this.list(query1);
            foods.add(food);
        }
        FoodsVO foodsVO = new FoodsVO();
        foodsVO.setCategoryNames(categoryNames);
        foodsVO.setFoods(foods);
        return foodsVO;
    }

    @Override
    public void saveByParam(Dic obj,Map<String, String> params){
        this.save(obj);
    }

    @Override
    public void updateByParam(Dic obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<Dic> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<Dic> selectBy(Map<String, String> params) {
        QueryWrapper<Dic> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<Dic> selectPage(Map<String, String> params) {
        System.out.println(params);
        Page<Dic> page = PageUtils.pageHandler(params);
        QueryWrapper<Dic> query = getQuery(params);
        IPage<Dic> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<Dic> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<Dic> page = PageUtils.pageHandler(params);
        QueryWrapper<Dic> query = getQuery(params);
        IPage<Dic> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<Dic> selpageCustomSqlByMap(Map<String, String> params) {
        Page<Dic> page = PageUtils.pageHandler(params);
        IPage<Dic> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<Dic> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Dic"), Dic.class, data);
        String fileName = String.format("Dic_%d.xls", System.currentTimeMillis());
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
        List<Dic> dataList = new ExcelImportService().importExcelByIs(inputStream, Dic.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<Dic> query = new QueryWrapper<>();
        List<Dic> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Dic"),
        Dic.class, data);
        String fileName = String.format("Dic_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<Dic> getQuery(Map<String, String> params){
        QueryWrapper<Dic> query  = new QueryWrapper<>();
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
            if("keyy".equals(entry.getKey())){
                query.like("keyy",entry.getValue());
            }
            if("value".equals(entry.getKey())){
                query.eq("value",entry.getValue());
            }
            if("remark".equals(entry.getKey())){
                query.eq("remark",entry.getValue());
            }
            if("parentId".equals(entry.getKey())){
                query.eq("parent_id",entry.getValue());
            }
        }
        return  query;
    }
}
