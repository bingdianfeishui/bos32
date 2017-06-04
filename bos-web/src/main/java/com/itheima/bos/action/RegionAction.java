package com.itheima.bos.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.itheima.bos.action.base.BaseAction;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.IRegionService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.bos.utils.JacksonUtils;
import com.itheima.bos.utils.PinYin4jUtils;

@Controller
@Scope("prototype")
@Namespace("/region")
@ParentPackage("basicStruts")
// @Result(name="input", location="/WEB-INF/pages/base/region.jsp")
public class RegionAction extends BaseAction<Region> {
    private static final long serialVersionUID = 1L;

    @Autowired
    private IRegionService regionService;

    // region actions
    @Action("importXls")
    public String importFile() {
        String flag = "0";
        Workbook workbook = null;
        try {
            if (regionFile == null || StringUtils.isBlank(regionFileFileName)) {
                BOSUtils.writeToResponse(flag);
                return NONE;
            }
            if (!(regionFileFileName.endsWith(RegionAction.EXTENSIONS[0]) || regionFileFileName
                    .endsWith(RegionAction.EXTENSIONS[1]))) {
                BOSUtils.writeToResponse(flag);
                return NONE;
            }

            workbook = regionFileFileName.endsWith(RegionAction.EXTENSIONS[0]) ? new HSSFWorkbook(
                    new FileInputStream(regionFile)) : new XSSFWorkbook(
                    new FileInputStream(regionFile));
            Sheet sheet = workbook.getSheetAt(0);
            List<Region> regionList = new ArrayList<>();
            for (Row row : sheet) {
                if (row.getRowNum() == 0)
                    continue;
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                Integer id = Integer.valueOf(row.getCell(0)
                        .getStringCellValue());
                String province = row.getCell(1).getStringCellValue();
                String city = row.getCell(2).getStringCellValue();
                String district = row.getCell(3).getStringCellValue();
                String postcode = row.getCell(4).getStringCellValue();
                String shortcode = row.getCell(5).getStringCellValue();
                String citycode = row.getCell(6).getStringCellValue();

                Region region = new Region(id, province, city, district,
                        postcode, shortcode, citycode, null);
                generatePinYin(region); // 获取简码和城市编码

                regionList.add(region);
            }

            regionService.saveBatch(regionList);
            flag = "1";
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null)
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        try {
            BOSUtils.writeToResponse(flag);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    @Action("findByQ")
    @Override
    public String findByQ() throws IOException {
        if (StringUtils.isBlank(q)) {
            // 查询关键字为空时，返回空json字符串（若查询所有太费时间）
            BOSUtils.getResponse().getWriter().print("{}");
        } else {
            pageBean.getDetachedCriteria().add(generateSearchRestrictions(q));
            List<Region> list = regionService.findListByCriteria(pageBean
                    .getDetachedCriteria());

            JacksonUtils.init(Region.class).setIncludeProperties("id", "name")
                    .serializeObj(BOSUtils.getResponse(), list);
        }
        return NONE;
    }

    // @Action("add")
    @Override
    public String add() throws IOException {
        // TODO Auto-generated method stub
        return NONE;
    }

    // @Action("delete")
    @Override
    public String delete() throws IOException {
        // TODO Auto-generated method stub
        return NONE;
    }

    @Action("edit")
    @Override
    public String edit() throws IOException {
        List<String> list = validateModel();

        if (list.size() == 0) {
            try {
                Region region = regionService.findById(model.getId());
                // 更新数据
                region.setProvince(model.getProvince());
                region.setCity(model.getCity());
                region.setDistrict(model.getDistrict());
                region.setPostcode(model.getPostcode());
                region.setShortcode(model.getShortcode());
                region.setCitycode(model.getCitycode());
                regionService.update(region);
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

    @Action("pageQuery")
    @Override
    public String pageQuery() throws IOException {
        if (searchMode)
            pageBean.getDetachedCriteria().add(generateSearchRestrictions(q));

        regionService.pageQuery(pageBean);
        BOSUtils.getResponse().setContentType("text/json;charset=UTF-8");
        String json = JacksonUtils.init(pageBean.getClass())
                .setIncludeProperties("total", "rows")
                .addMixIn(Region.class, RegionMixIn.class)
                .serializeObj(pageBean);
        // System.out.println(json);
        BOSUtils.getResponse().getWriter().write(json);
        return NONE;
    }

    @Action("findById")
    public String findById() throws IOException {
        Region region = regionService.findById(model.getId());
        String json = JacksonUtils.init(Region.class)
                .setIncludeProperties("id", "name").serializeObj(region);
        System.out.println(json);
        BOSUtils.getResponse().getWriter().write(json);
        return NONE;
    }

    // endregion actions

    // region private fiedlds and methods
    /**
     * 上传文件允许的后缀
     */
    private static String[] EXTENSIONS = { "xls", "xlsx" };
    /**
     * 用于生成简码时，过滤省市区的最后一个字
     */
    @SuppressWarnings("serial")
    private final static List<String> REGION_ENDS = new ArrayList<String>() {
        {
            add("省");
            add("市");
            add("区");
            add("县");
            add("乡");
            add("旗");
        }
    };

    /**
     * 属性驱动，导入的文件
     */
    private File regionFile;

    public void setRegionFile(File regionFile) {
        this.regionFile = regionFile;
    }

    /**
     * 属性驱动，上传的文件名
     */
    private String regionFileFileName;

    public void setRegionFileFileName(String regionFileFileName) {
        this.regionFileFileName = regionFileFileName;
    }

    /**
     * Region类混入注解，用JackSon序列化时的字段控制
     * 
     * @author Lee
     *
     */
    @JsonIgnoreProperties("subareas")
    interface RegionMixIn {
    }

    /**
     * 根据Region的省市区字段生成简码shotcode和城市编码citycode
     * 
     * @param region
     */
    private void generatePinYin(Region region) {
        String province = region.getProvince();
        String city = region.getCity();
        String district = region.getDistrict();
        if (province.endsWith("省") || province.endsWith("市"))
            province = province.substring(0, province.length() - 1);
        if (REGION_ENDS.contains(city.substring(city.length() - 1)))
            city = city.substring(0, city.length() - 1);
        if (REGION_ENDS.contains(district.substring(district.length() - 1)))
            district = district.substring(0, district.length() - 1);

        if (StringUtils.isBlank(region.getShortcode())) {
            String[] headStrs = PinYin4jUtils.getHeadByString(province + city
                    + district);
            String shortcode = StringUtils.join(headStrs);
            region.setShortcode(shortcode);
        }
        if (StringUtils.isBlank(region.getCitycode())) {
            String citycode = PinYin4jUtils.hanziToPinyin(city, "");
            region.setCitycode(citycode);
        }
    }

    /**
     * 构造Region模糊查询条件，逻辑关系OR，查询方式 like
     * 
     * @param keyword
     *            查询关键字，不能为空
     * @return 构造完成的查询条件
     */
    private Criterion generateSearchRestrictions(String keyword) {
        Disjunction dis = Restrictions.disjunction();
        if (StringUtils.isBlank(keyword))
            return dis;
        dis.add(Restrictions.like("province", "%" + keyword + "%"));
        dis.add(Restrictions.like("city", "%" + keyword + "%"));
        dis.add(Restrictions.like("district", "%" + keyword + "%"));
        dis.add(Restrictions.like("postcode", "%" + keyword + "%"));
        dis.add(Restrictions.like("shortcode", "%" + keyword + "%"));
        dis.add(Restrictions.like("citycode", "%" + keyword + "%"));
        return dis;
    }

    /**
     * 验证RegionModel，并返回错误信息列表
     * 
     * @return
     */
    private List<String> validateModel() {
        List<String> list = new ArrayList<>();
        if (StringUtils.isBlank(model.getProvince()))
            list.add("省份不能为空");
        if (StringUtils.isBlank(model.getCity()))
            list.add("城市不能为空");
        if (StringUtils.isBlank(model.getDistrict()))
            list.add("区域不能为空");
        if (StringUtils.isBlank(model.getPostcode()))
            list.add("邮政编码不能为空");
        if (StringUtils.isBlank(model.getCitycode()))
            list.add("城市编码不能为空");
        if (StringUtils.isBlank(model.getShortcode()))
            list.add("简码不能为空");

        return list;
    }
    // endregion private fiedlds and methods
}
