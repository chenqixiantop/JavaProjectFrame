package com.chenqixian.cloud.common.core.web.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.date.DateUtil;
import com.chenqixian.cloud.common.core.web.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class BaseController {

    public BaseController() {
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                this.setValue(DateUtil.parseLocalDateTime(text, "yyyy-MM-dd HH:mm:ss"));
            }
        });
    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, boolean isCreateHeader, HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass, String fileName, HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }

    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException var4) {
            throw new BusinessException(var4.getMessage());
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
        }

        downLoadExcel(fileName, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        } else {
            ImportParams params = new ImportParams();
            params.setTitleRows(titleRows);
            params.setHeadRows(headerRows);
            List list = null;

            try {
                list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
                return list;
            } catch (NoSuchElementException var7) {
                throw new BusinessException("模板不能为空");
            } catch (Exception var8) {
                var8.printStackTrace();
                throw new BusinessException(var8.getMessage());
            }
        }
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        } else {
            ImportParams params = new ImportParams();
            params.setTitleRows(titleRows);
            params.setHeadRows(headerRows);
            List list = null;

            try {
                list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
                return list;
            } catch (NoSuchElementException var7) {
                throw new BusinessException("excel文件不能为空");
            } catch (Exception var8) {
                throw new BusinessException(var8.getMessage());
            }
        }
    }

    public static <T> ExcelImportResult<T> importExcelMore(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass) {
        if (file == null) {
            return null;
        } else {
            ImportParams params = new ImportParams();
            params.setTitleRows(titleRows);
            params.setHeadRows(headerRows);
            ExcelImportResult excelImportResult = null;

            try {
                excelImportResult = ExcelImportUtil.importExcelMore(file.getInputStream(), pojoClass, params);
                return excelImportResult;
            } catch (NoSuchElementException var7) {
                throw new BusinessException("excel文件不能为空");
            } catch (Exception var8) {
                throw new BusinessException(var8.getMessage());
            }
        }
    }
}
