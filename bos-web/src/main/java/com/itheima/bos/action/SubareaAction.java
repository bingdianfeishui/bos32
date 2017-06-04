package com.itheima.bos.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.service.ISubareaService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.JacksonUtils;
import com.itheima.bos.utils.PageBean;

/**
 * @author Lee
 *
 */
@Controller
@Scope("prototype")
@Namespace("/subarea")
@ParentPackage("basicStruts")
@Results({ @Result(name = "list", location = "/WEB-INF/pages/base/subarea.jsp") })
public class SubareaAction extends BaseAction<Subarea> {
    private static final long serialVersionUID = 1L;

    // region actions
    @Action("add")
    public String add() {
        // TODO:验证model
        subareaService.add(model);
        return LIST;
    }

    @Action("edit")
    public String edit() throws IOException {
        // TODO:验证model
        List<String> list = new ArrayList<>();

        if (list.size() == 0) {
            try {
                Subarea sa = subareaService.findById(model.getId());
                sa.setAddresskey(model.getAddresskey());
                sa.setStartnum(model.getStartnum());
                sa.setEndnum(model.getEndnum());
                sa.setSingle(model.getSingle());
                sa.setPosition(model.getPosition());
                Region r = regionService.findById(model.getRegion().getId());
                if (r != null)
                    sa.setRegion(r);

                subareaService.update(sa);
            } catch (Exception e) {
                e.printStackTrace();
                list.add("服务器忙，请稍后重试！");
            }
        }

        BOSUtils.getResponse().setContentType("text/html;charset=UTF-8");
        BOSUtils.getResponse().getWriter()
                .print(list.size() == 0 ? "" : list.toString());
        return NONE;
    }

    @Action("delete")
    public String delete() {
        // TODO Auto-generated method stub
        return LIST;
    }

    /**
     * 条件分页查询
     */
    @Action("pageQuery")
    public String pageQuery() throws IOException {
        DetachedCriteria criteria = pageBean.getDetachedCriteria();
        if (StringUtils.isNotBlank(model.getAddresskey())) {
            criteria.add(Restrictions.like("addressKey",
                    "%" + model.getAddresskey() + "%"));
        }
        Region r = model.getRegion();
        if (r != null) {
            // 使用别名来进行jion查询
            criteria.createAlias("region", "r", JoinType.INNER_JOIN);
            if (StringUtils.isNotBlank(r.getProvince()))
                criteria.add(Restrictions.like("r.province",
                        "%" + r.getProvince() + "%"));
            if (StringUtils.isNotBlank(r.getCity()))
                criteria.add(Restrictions.like("r.city", "%" + r.getCity()
                        + "%"));
            if (StringUtils.isNotBlank(r.getDistrict()))
                criteria.add(Restrictions.like("r.district",
                        "%" + r.getDistrict() + "%"));
        }

        subareaService.pageQuery(pageBean);
        JacksonUtils.init(PageBean.class).setIncludeProperties("total", "rows")
                .addMixIn(Region.class, RegionMixIn.class)
                .addMixIn(Subarea.class, SubareaMixIn.class)
                .serializeObj(BOSUtils.getResponse(), pageBean);
        return NONE;
    }

    @Action("findByQ")
    public String findByQ() {
        subareaService.findListByCriteria(pageBean.getDetachedCriteria());
        return NONE;
    }

    @Action("exportXls")
    public String exportXls() throws IOException {
        // 1. 查询所有分区
        List<Subarea> list = subareaService.findAll();
        // 2. 创建xls workbook
        @SuppressWarnings("resource")
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row headRow = sheet.createRow(0);
        headRow.createCell(0).setCellValue("分区ID");
        headRow.createCell(1).setCellValue("关键字");
        headRow.createCell(2).setCellValue("开始编号");
        headRow.createCell(3).setCellValue("结束编号");
        headRow.createCell(4).setCellValue("单双号");
        headRow.createCell(5).setCellValue("位置信息");
        headRow.createCell(6).setCellValue("省市区");

        sheet.setColumnWidth(5,20*2*256);
        sheet.setColumnWidth(6,20*2*256);
        
        for (Subarea sb : list) {
            Row row = sheet.createRow(sheet.getLastRowNum()+1);
            row.createCell(0).setCellValue(sb.getId());
            row.createCell(1).setCellValue(sb.getAddresskey());
            row.createCell(2).setCellValue(sb.getStartnum());
            row.createCell(3).setCellValue(sb.getEndnum());
            row.createCell(4).setCellValue(sb.getSingle());
            row.createCell(5).setCellValue(sb.getPosition());
            row.createCell(6).setCellValue(sb.getRegion().getName());
        }
        
        // 3. 下载
        String filename = "subareas.xls";   //默认文件名
        try {
            filename = URLEncoder.encode(DOWNLOAD_FILENAME, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        BOSUtils.getResponse().setContentType(
                ServletActionContext.getServletContext().getMimeType(
                        DOWNLOAD_FILENAME));
        BOSUtils.getResponse().setCharacterEncoding("utf-8"); 
        BOSUtils.getResponse().setHeader("content-disposition",
                "attachment; filename=" + filename);
        workbook.write(BOSUtils.getResponse().getOutputStream());
        return NONE;
    }

    // endregion actions

    // region private fields and methods
    @Resource
    private ISubareaService subareaService;

    @Autowired
    private IRegionService regionService;

    private static final String DOWNLOAD_FILENAME = "分区数据.xls";

    @JsonIgnoreProperties("subareas")
    interface RegionMixIn {
    }

    @JsonIgnoreProperties("decidedZone")
    interface SubareaMixIn {
    }
    // endregion private fields and methods
}
