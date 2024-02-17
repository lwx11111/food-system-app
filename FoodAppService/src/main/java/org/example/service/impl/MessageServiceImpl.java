package org.example.service.impl;

import org.example.domain.Message;
import org.example.dao.MessageMapper;
import org.example.service.IMessageService;
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
 *  服务实现类
 * </p>
 *
 * @author lwx20
 * @since 2024-02-16
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Override
    public void saveByParam(Message obj,Map<String, String> params){
        obj.setSendTime(LocalDateTime.now());
        this.save(obj);
    }

    @Override
    public void updateByParam(Message obj,Map<String, String> params){
        this.updateById(obj);
    }

    @Override
    public void deleteBy(Map<String, String> params) {
        QueryWrapper<Message> query = new QueryWrapper<>();
        if(!query.isEmptyOfWhere()) {
            remove(query);
        }
    }

    @Override
    public List<Message> selectBy(Map<String, String> params) {
        QueryWrapper<Message> query = new QueryWrapper<>();
        return list(query);
    }

    @Override
    public IPage<Message> selectPage(Map<String, String> params) {
        Page<Message> page = PageUtils.pageHandler(params);
        QueryWrapper<Message> query = getQuery(params);
        IPage<Message> result = this.page(page, query);
        return result;
    }

    @Override
    public IPage<Message> selpageCustomSqlByWrapper(Map<String, String> params) {
        Page<Message> page = PageUtils.pageHandler(params);
        QueryWrapper<Message> query = getQuery(params);
        IPage<Message> result = this.baseMapper.selpageCustomSqlByWrapper(page, query);
        return result;
    }

    @Override
    public IPage<Message> selpageCustomSqlByMap(Map<String, String> params) {
        Page<Message> page = PageUtils.pageHandler(params);
        IPage<Message> result = this.baseMapper.selpageCustomSqlByMap(page, params);
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
        List<Message> data = Lists.newArrayList();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Message"), Message.class, data);
        String fileName = String.format("Message_%d.xls", System.currentTimeMillis());
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
        List<Message> dataList = new ExcelImportService().importExcelByIs(inputStream, Message.class, params, false).getList();
        this.saveBatch(dataList);
        // map 导入
        // List<Map<String, Object>> maps = ExcelImportUtil.importExcel(inputStream, Map.class, params);
        // System.out.println("maps = " + maps);
    }

    @Override
    public void excel(HttpServletResponse response, HttpServletRequest request, Map<String, String> params) throws Exception{
        QueryWrapper<Message> query = new QueryWrapper<>();
        List<Message> data = list(query);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "Message"),
        Message.class, data);
        String fileName = String.format("Message_%d.xls", System.currentTimeMillis());
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
    private  QueryWrapper<Message> getQuery(Map<String, String> params){
        QueryWrapper<Message> query  = new QueryWrapper<>();
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
            if("sendUserId".equals(entry.getKey())){
                query.eq("send_user_id",entry.getValue());
            }
            if("receiveUserId".equals(entry.getKey())){
                query.eq("receive_user_id",entry.getValue());
            }
            if("content".equals(entry.getKey())){
                query.eq("content",entry.getValue());
            }
            if("sendTime".equals(entry.getKey())){
                query.eq("send_time",entry.getValue());
            }
            if("parentId".equals(entry.getKey())){
                query.eq("parent_id",entry.getValue());
            }
        }
        return  query;
    }
}
