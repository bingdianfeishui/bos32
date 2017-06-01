package com.itheima.bos.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
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

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Autowired
	private IRegionService regionService;

	private static String[] EXTENSIONS = { "xls", "xlsx" };
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
	private File regionFile;

	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}

	private String regionFileFileName;

	public void setRegionFileFileName(String regionFileFileName) {
		this.regionFileFileName = regionFileFileName;
	}

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
				Integer id = (int) row.getCell(0).getNumericCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();

				Region region = new Region(id, province, city, district,
						postcode, null, null, null);
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

	private void generatePinYin(Region region) {
		String province = region.getProvince();
		String city = region.getCity();
		String district = region.getDistrict();
		if (REGION_ENDS.contains(province.substring(province.length() - 1)))
			province = province.substring(0, province.length() - 1);
		if (REGION_ENDS.contains(city.substring(city.length() - 1)))
			city = city.substring(0, city.length() - 1);
		if (REGION_ENDS.contains(district.substring(district.length() - 1)))
			district = district.substring(0, district.length() - 1);

		String[] headStrs = PinYin4jUtils.getHeadByString(province + city
				+ district);
		String shortcode = StringUtils.join(headStrs);

		String citycode = PinYin4jUtils.hanziToPinyin(city, "");

		region.setShortcode(shortcode);
		region.setCitycode(citycode);
	}
    
    @JsonIgnoreProperties("subareas")
    interface RegionMixIn{}
	@Action("pageQuery")
	public String pageQuery() throws IOException{
	    if(searchMode){
	        
	    }
	    
	    regionService.pageQuery(pageBean);
	    BOSUtils.getResponse().setContentType("text/json;charset=UTF-8");
	    String json = JacksonUtils.init(pageBean.getClass()).setIncludeProperties("total", "rows").SerializeObj(pageBean);
	    //System.out.println(json);
	    BOSUtils.getResponse().getWriter().write(json);
	    return NONE;
	}
	
	@Action("edit")
	public String edit() throws IOException{
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
	        BOSUtils.getResponse().getWriter().print(list.toString());
	    
	    
	    return NONE;
	}
	
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
	
	@Action("listAjax")
	public String listAjax(){
	    
	    return NONE;
	}
}
