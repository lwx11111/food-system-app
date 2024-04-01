package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.example.web.SimpleResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.ShopOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.IShopOrderService;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 * @since 2024-01-09
 */
@RestController
@Tag(name = "服务")
@RequestMapping("/shoporder")
@CrossOrigin(origins = "*",maxAge = 3600)
public class ShopOrderController {
    @Autowired
    private IShopOrderService service;

    @PostMapping("/getRadarData")
    @ResponseBody
    public SimpleResponse getRadarData(){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getRadarData());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping("/getHotItemData")
    @ResponseBody
    public SimpleResponse getHotItemData(){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getHotItemData());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }


    @PostMapping("/getDataNearlySixMonths")
    @ResponseBody
    public SimpleResponse getDataNearlySixMonths(){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getDataNearlySixMonths());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping("/getStatisticalData")
    @ResponseBody
    @Operation(description = "获取统计数据")
    public SimpleResponse getStatisticalData(){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getStatisticalData());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping("/listShopOrderByUserId")
    @ResponseBody
    @Operation(description = "创建")
    public SimpleResponse listShopOrderByUserId(@RequestBody Map<String, String> params){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.listShopOrderByUserId(params));
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping
    @ResponseBody
    @Operation(description = "创建")
    public SimpleResponse save(@RequestBody ShopOrder obj){
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.saveByParam(obj,obj.getParams()));
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(description = "更新")
    public SimpleResponse update(@PathVariable(name = "id") String id,@RequestBody ShopOrder obj){
        SimpleResponse response = new SimpleResponse();
        try {
            service.updateByParam(obj,obj.getParams());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(description = "按ID删除")
    public SimpleResponse remove(@PathVariable(name = "id") String id){
        SimpleResponse response = new SimpleResponse();
        try {
        service.removeById(id);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping("/{id}")
    @Operation(description = "按ID查询")
    @ResponseBody
    public SimpleResponse select(@PathVariable(name = "id") String id) {
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getById(id));
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping("/dels")
    @ResponseBody
    @Operation(description = "按ID删除多个")
    public SimpleResponse removes(@RequestBody List<String> ids){
        SimpleResponse response = new SimpleResponse();
        try {
            service.removeByIds(ids);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }


    @PostMapping("/delby")
    @Operation(description = "条件删除")
    public void deleteBy(@RequestBody(required = false) Map<String, String> params) {
        service.deleteBy(params);
    }

    @PostMapping("/selby")
    @ResponseBody
    public List<ShopOrder> selectBy(@RequestBody(required = false) Map<String, String> params) {
        return  service.selectBy(params);
    }

    @PostMapping("/selpage")
    @Operation(description = "分页查询")
    @ResponseBody
    public SimpleResponse selectPage(@RequestBody Map<String, String> params) {
        SimpleResponse response = new SimpleResponse();
        try {
            IPage<ShopOrder> page = service.selectPage(params);
            response.setData(page);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    @PostMapping("/selpageCustomSqlByWrapper")
    @ResponseBody
    public IPage<ShopOrder> selpageCustomSqlByWrapper(@RequestBody Map<String, String> params) {
        return service.selpageCustomSqlByWrapper(params);
    }

    @PostMapping("/selpageCustomSqlByMap")
    @Operation(description = "分页查询-自定义sql-Map")
    @ResponseBody
    public IPage<ShopOrder> selpageCustomSqlByMap(@RequestBody Map<String, String> params) {
        return service.selpageCustomSqlByMap(params);
    }

    /**
     * 下载excel模板
     * @param response
     * @param request
     * @throws Exception
     */
    @PostMapping("/downloadExcelTemplate")
    @ResponseBody
    public void downloadExcelTemplate(HttpServletResponse response, HttpServletRequest request) throws Exception {
        service.downloadExcelTemplate(response, request);
    }

    /**
     * excel导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/uploadExcel")
    @ResponseBody
    public void uploadExcel(@RequestParam MultipartFile file) throws Exception {
        service.uploadExcel(file);
    }

    @PostMapping("/excel")
    @ResponseBody
    public void excel(HttpServletResponse response, HttpServletRequest request, @RequestBody Map<String, String> params) throws Exception {
        service.excel(response, request, params);
    }
}

