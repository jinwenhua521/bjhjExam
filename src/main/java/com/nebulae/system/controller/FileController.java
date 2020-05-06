package com.nebulae.system.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nebulae.annotation.Log;
import com.nebulae.config.UpLoadFileConfig;
import com.nebulae.system.entity.SysFile;
import com.nebulae.system.service.SysFileService;
import com.nebulae.utils.FileType;
import com.nebulae.utils.FileUtil;
import com.nebulae.utils.GridTable;
import com.nebulae.utils.JsonUtils;
import com.nebulae.utils.Query;
import com.nebulae.utils.R;
import com.nebulae.utils.Tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("system/file")
public class FileController extends BaseController{
	
	@Autowired
	SysFileService sysFileService;
	
	@Autowired
	private UpLoadFileConfig UpLoadFileConfig;
	
	@GetMapping()
	@RequiresPermissions("system:file:file")
	public String getFile(Model model) {
		SysFile file=new SysFile();
		file.setDocType(new BigDecimal(0));
		List<SysFile> list= sysFileService.getFileList(file);
		model.addAttribute("Catalog", list);
		List<SysFile> label=sysFileService.getLabel(getUserId());
		model.addAttribute("Label", label);
		return "system/file/file";
	}
	
	
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("system:file:file")
	public GridTable list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		SysFile sysFile=JsonUtils.map2pojo(params, SysFile.class);
		sysFile.setDocType(new BigDecimal(1));
		sysFile.setCreateid(getUserId());
		// 查询列表数据
		PageHelper.startPage(Integer.parseInt(query.get("page").toString()), Integer.parseInt(query.get("limit").toString()));
		PageHelper.orderBy("UP_DATE desc");
		PageInfo<SysFile>  pageInfo=new PageInfo<SysFile>(sysFileService.getFileList(sysFile));
		GridTable grid=new GridTable(pageInfo.getList(),pageInfo.getTotal());
		return grid;
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	public R remove(String id, HttpServletRequest request) {
		SysFile sysFile=sysFileService.getById(id);
		String fileName = UpLoadFileConfig.getUploadPath() + sysFile.getFileurl().replace("/files/", "");
		if (sysFileService.delete(id) > 0) {
			boolean b = FileUtil.deleteFile(fileName);
			if (!b) {
				return R.error("数据库记录删除成功，文件删除失败");
			}
			return R.ok();
		} else {
			return R.error();
		}
	}
	
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") String id, Model model) {
		SysFile sysFile = sysFileService.getById(id);
		model.addAttribute("SysFile", sysFile);
		return "system/file/file_lable";
	}
	
	/**
	 * 修改
	 */
	@Log("编辑数据字典")
	@ResponseBody
	@RequestMapping("/update")
	public R update(SysFile sysFile) {
		if(sysFileService.update(sysFile)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	@ResponseBody
	@PostMapping("/upload")
	R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		if ("test".equals(getUsername())) {
			return R.error(1, "演示系统不允许修改,完整体验请部署程序");
		}
		SysFile sysFile = new SysFile();
		String fileName = file.getOriginalFilename();
		sysFile.setFilename(fileName);
		String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
		fileName = FileUtil.RenameToUUID(fileName);
		sysFile.setSuffix(prefix);
		sysFile.setFiletype(FileType.fileType(fileName));
		sysFile.setFileurl("/files/" + fileName);
		sysFile.setUpDate(new Date());
		sysFile.setSid(Tools.getID());
		sysFile.setCreateid(getUserId());
		sysFile.setFileAllname(fileName);
		sysFile.setDocType(new BigDecimal(1));
		sysFile.setFileSize(new BigDecimal(file.getSize()));
		try {
			FileUtil.uploadFile(file.getBytes(), UpLoadFileConfig.getUploadPath(), fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return R.error();
		}

		if (sysFileService.save(sysFile) > 0) {
			return R.ok().put("fileName",sysFile.getFileurl());
		}
		return R.error();
	}

}
