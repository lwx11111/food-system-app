package org.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.example.web.SimpleResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.domain.MenuComment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.service.IMenuCommentService;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 菜谱评论表 前端控制器
 * </p>
 *
 * 
 * @since 2023-10-27
 */
@RestController
@Tag(name = "菜谱评论表服务")
@RequestMapping("/menucomment")
@CrossOrigin(origins = "*",maxAge = 3600)
public class MenuCommentController {
    @Autowired
    private IMenuCommentService service;

    @PostMapping
    @ResponseBody
    @Operation(description = "创建菜谱评论表")
    public SimpleResponse save(@RequestBody MenuComment obj){
        SimpleResponse response = new SimpleResponse();
        try {
            service.saveByParam(obj,obj.getParams());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PutMapping("/{id}")
    @ResponseBody
    @Operation(description = "更新菜谱评论表")
    public SimpleResponse update(@PathVariable(name = "id") String id,@RequestBody MenuComment obj){
        SimpleResponse response = new SimpleResponse();
        try {
            service.updateByParam(obj,obj.getParams());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @Operation(description = "按ID删除菜谱评论表")
    public SimpleResponse remove(@PathVariable(name = "id") String id){
            SimpleResponse response = new SimpleResponse();
        try {
        service.removeById(id);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @GetMapping("/{id}")
    @Operation(description = "按ID查询菜谱评论表")
    @ResponseBody
    public SimpleResponse select(@PathVariable(name = "id") String id) {
        SimpleResponse response = new SimpleResponse();
        try {
            response.setData(service.getById(id));
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/dels")
    @ResponseBody
    @Operation(description = "按ID删除多个菜谱评论表")
    public SimpleResponse removes(@RequestBody List<String> ids){
        SimpleResponse response = new SimpleResponse();
        try {
            service.removeByIds(ids);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }


    @PostMapping("/delby")
    @Operation(description = "条件删除菜谱评论表")
    public void deleteBy(@RequestBody(required = false) Map<String, String> params) {
        service.deleteBy(params);
    }

    @PostMapping("/selby")
    @ResponseBody
    public List<MenuComment> selectBy(@RequestBody(required = false) Map<String, String> params) {
        return  service.selectBy(params);
    }

    @PostMapping("/selpage")
    @Operation(description = "分页查询菜谱评论表")
    @ResponseBody
    public SimpleResponse selectPage(@RequestBody Map<String, String> params) {
        SimpleResponse response = new SimpleResponse();
        try {
            IPage<MenuComment> page = service.selectPage(params);
            response.setData(page);
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @PostMapping("/selpageCustomSqlByWrapper")
    @ResponseBody
    public IPage<MenuComment> selpageCustomSqlByWrapper(@RequestBody Map<String, String> params) {
        return service.selpageCustomSqlByWrapper(params);
    }

    @PostMapping("/selpageCustomSqlByMap")
    @Operation(description = "分页查询-自定义sql-Map菜谱评论表")
    @ResponseBody
    public IPage<MenuComment> selpageCustomSqlByMap(@RequestBody Map<String, String> params) {
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
    public void excel(HttpServletResponse response, HttpServletRequest request,
            @RequestBody Map<String, String> params) throws Exception {
        service.excel(response, request, params);
    }
}

