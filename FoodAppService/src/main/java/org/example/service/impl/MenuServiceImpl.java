package org.example.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.example.domain.Dic;
import org.example.domain.Menu;
import org.example.dao.MenuMapper;
import org.example.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.vo.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
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
 * 菜谱信息表 服务实现类
 * </p>
 *
 *
 * @since 2023-10-27
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private DicServiceImpl dicService;

    @Override
    public List<Menu> listMenuByNames(Map<String, String> params) {
        System.out.println(params.get("name"));
        JSONArray array = JSONArray.parseArray(params.get("name"));
        List<Menu> list = new ArrayList<>();
        for (Object obj : array) {
            String name = (String) obj;
            System.out.println(name);
            LambdaQueryWrapper<Menu> query = new LambdaQueryWrapper<Menu>()
                    .like(Menu::getName, name);
            Page<Menu> page = PageUtils.pageHandler(params);
            list.addAll(this.list(query));
        }
        return list;
    }

    @Override
    public IPage<MenuVO> getDailyRecommendation(Map<String, String> params) {
        Dic dic = dicService.getDicByKeyy("每日推荐id");
        String randomId = dic.getValue();
        if ("".equals(dic.getValue())) {
            List<String> randomIds = menuMapper.getRandomIds();
            String value = "(";
            for (int i = 0; i < randomIds.size(); i++) {
                if (i == randomIds.size() - 1) {
                    value += "'" + randomIds.get(i) + "')";
                } else {
                    value += "'" + randomIds.get(i) + "',";
                }
            }
            dic.setValue(value);
            dicService.updateValueById(dic.getId(), value);
        }
        String value = dic.getValue();
        Page<Menu> page = PageUtils.pageHandler(params);
        IPage<Menu> result = menuMapper.getDailyRecommendation(page, value);
        return this.MenuToMenuVO(result);
    }

    @Override
    public IPage<MenuVO> getMenuCollectionByUserId(Map<String, String> params) {
        Page<Menu> page = PageUtils.pageHandler(params);
        String userId = params.get("userId");
        IPage<Menu> result = menuMapper.getMenuCollectionByUserId(page, userId);
        return this.MenuToMenuVO(result);
    }

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
            if("categoryId".equals(entry.getKey())){
                query.eq("category_id",entry.getValue());
            }
            if("userId".equals(entry.getKey())){
                query.eq("user_id",entry.getValue());
            }
        }
        return query;
    }
}
