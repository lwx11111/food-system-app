package org.example.service.impl;

import org.example.domain.ShopOrder;
import org.example.dao.ShopOrderMapper;
import org.example.service.IShopOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 *
 * @since 2024-01-09
 */
@Service
public class ShopOrderServiceImpl extends ServiceImpl<ShopOrderMapper, ShopOrder> implements IShopOrderService {
    @Autowired
    ShopItemServiceImpl shopItemService;

    @Override
    public List<getRadarDataVo> getRadarData() throws Exception {
        return baseMapper.getRadarData();
    }

    @Override
    public List<getHotItemDataVo> getHotItemData() throws Exception {
        return baseMapper.getHotItemData();
    }

    @Override
    public List<getDataNearlySixMonthsVo> getDataNearlySixMonths() throws Exception {
        return baseMapper.getDataNearlySixMonths();
    }

    @Override
    public List<ShopOrderStatisticalDataVo> getStatisticalData() throws Exception {
        return baseMapper.getStatisticalData();

    }

    @Override
    public List<ShopOrderVO> listShopOrderByUserId(Map<String, String> params) throws Exception {
        Page<ShopOrder> page = PageUtils.pageHandler(params);
        QueryWrapper<ShopOrder> query = getQuery(params);
        IPage<ShopOrder> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        List<ShopOrderVO> list = new ArrayList<>();
        System.out.println(result.getRecords().size());
        for (ShopOrder shopOrder : result.getRecords()) {
            ShopOrderVO vo = new ShopOrderVO();
            vo.setId(shopOrder.getId());
            vo.setUserId(shopOrder.getUserId());
            vo.setPrice(shopOrder.getPrice());
            // shopItem
            vo.setShopItem(shopItemService.getById(shopOrder.getShopItemId()));
            vo.setAmount(shopOrder.getAmount());
            System.out.println(vo);
            list.add(vo);
        }
        return list;
    }
    @Override
    public String saveByParam(ShopOrder obj,Map<String, String> params){
        obj.setTime(LocalDateTime.now());
        this.save(obj);
        return obj.getId();
    }

    @Override
    public void updateByParam(ShopOrder obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<ShopOrder> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<ShopOrder> selectBy(Map<String, String> params) {
        QueryWrapper<ShopOrder> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<ShopOrder> selectPage(Map<String, String> params) {
        Page<ShopOrder> page = PageUtils.pageHandler(params);
        QueryWrapper<ShopOrder> query = getQuery(params);
        IPage<ShopOrder> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<ShopOrder> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<ShopOrder> page = PageUtils.pageHandler(params);
        QueryWrapper<ShopOrder> query = getQuery(params);
        IPage<ShopOrder> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<ShopOrder> selpageCustomSqlByMap(Map<String, String> params) {
        Page<ShopOrder> page = PageUtils.pageHandler(params);
        IPage<ShopOrder> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<ShopOrder> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "ShopOrder"), ShopOrder.class, data);
        String fileName = String.format("ShopOrder_%d.xls", System.currentTimeMillis());
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
        List<ShopOrder> dataList = new ExcelImportService().importExcelByIs(inputStream, ShopOrder.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<ShopOrder> query = new QueryWrapper<>();
        List<ShopOrder> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "ShopOrder"),
        ShopOrder.class, data);
        String fileName = String.format("ShopOrder_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<ShopOrder> getQuery(Map<String, String> params){
        QueryWrapper<ShopOrder> query  = new QueryWrapper<>();
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
            if("shopItemId".equals(entry.getKey())){
                query.eq("shop_item_id",entry.getValue());
            }
            if("amount".equals(entry.getKey())){
                query.eq("amount",entry.getValue());
            }
        }
        return  query;
    }
}
