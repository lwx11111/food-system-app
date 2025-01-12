package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.example.domain.Cart;
import org.example.dao.CartMapper;
import org.example.service.ICartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.vo.CartVO;
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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    @Autowired
    ShopItemServiceImpl shopItemService;

    @Override
    public List<CartVO> listCartByUserId(Map<String, String> params) throws Exception {
        System.out.println("12333333333333333333333333333333");
        Page<Cart> page = PageUtils.pageHandler(params);
        QueryWrapper<Cart> query = getQuery(params);
        IPage<Cart> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        List<CartVO> list = new ArrayList<>();
        System.out.println(result.getRecords().size());
        for (Cart cart : result.getRecords()) {
            CartVO vo = new CartVO();
            vo.setId(cart.getId());
            vo.setUserId(cart.getUserId());
            // shopItem
            vo.setShopItem(shopItemService.getById(cart.getShopItemId()));
            vo.setAmount(cart.getAmount());
            System.out.println(vo);
            list.add(vo);
        }
        return list;
    }

    @Override
    public void saveByParam(Cart obj,Map<String, String> params){
        if (this.isHave(obj)){
            LambdaUpdateWrapper<Cart> updateWrapper = new LambdaUpdateWrapper<Cart>()
                    .eq(Cart::getUserId, obj.getUserId())
                    .eq(Cart::getShopItemId, obj.getShopItemId())
                    .setSql("amount = amount + " + obj.getAmount());
            this.update(updateWrapper);
        } else {
            this.save(obj);
        }
    }

    Boolean isHave(Cart obj){
        QueryWrapper<Cart> query = new QueryWrapper<>();
        query.eq("user_id", obj.getUserId());
        query.eq("shop_item_id", obj.getShopItemId());
        Cart cart = this.getOne(query);
        return cart != null;
    }
    @Override
    public void updateByParam(Cart obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<Cart> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<Cart> selectBy(Map<String, String> params) {
        QueryWrapper<Cart> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<Cart> selectPage(Map<String, String> params) {
        Page<Cart> page = PageUtils.pageHandler(params);
        QueryWrapper<Cart> query = getQuery(params);
        IPage<Cart> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<Cart> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<Cart> page = PageUtils.pageHandler(params);
        QueryWrapper<Cart> query = getQuery(params);
        IPage<Cart> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<Cart> selpageCustomSqlByMap(Map<String, String> params) {
        Page<Cart> page = PageUtils.pageHandler(params);
        IPage<Cart> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<Cart> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Cart"), Cart.class, data);
        String fileName = String.format("Cart_%d.xls", System.currentTimeMillis());
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
        List<Cart> dataList = new ExcelImportService().importExcelByIs(inputStream, Cart.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<Cart> query = new QueryWrapper<>();
        List<Cart> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Cart"),
        Cart.class, data);
        String fileName = String.format("Cart_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<Cart> getQuery(Map<String, String> params){
        QueryWrapper<Cart> query  = new QueryWrapper<>();
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
