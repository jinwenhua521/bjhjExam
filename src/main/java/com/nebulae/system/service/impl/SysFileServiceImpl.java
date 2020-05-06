package com.nebulae.system.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nebulae.system.entity.SysFile;
import com.nebulae.system.mapper.SysFileMapper;
import com.nebulae.system.service.SysFileService;
import com.nebulae.utils.ExampleUtils;

@Service
public class SysFileServiceImpl implements SysFileService {
	
	@Autowired
	SysFileMapper sysFileMapper;

	@Override
	public List<SysFile> getFileList(SysFile sysFile) {
		 List<SysFile> list=null;
		if(sysFile!=null) {
			ExampleUtils example=new ExampleUtils();
			ExampleUtils.Criteria criteria=example.createCriteria();
			if(sysFile.getDocType()!=null) {
				criteria.andFieldEqualTo("DOC_TYPE", sysFile.getDocType().toString());
			}
			if(!StringUtils.isEmpty(sysFile.getCreateid())) {
				criteria.andFieldEqualTo("CREATEID", sysFile.getCreateid());
			}
			if(!StringUtils.isEmpty(sysFile.getFilename())) {
				criteria.andFieldEqualTo("FILENAME", sysFile.getFilename());
			}
			if(!StringUtils.isEmpty(sysFile.getFileAllname())) {
				criteria.andFieldLike("FILE_ALLNAME", "%"+sysFile.getFileAllname()+"%");
			}
			if(!StringUtils.isEmpty(sysFile.getFiletype())) {
				criteria.andFieldEqualTo("FILETYPE", sysFile.getFiletype());
			}
			if(!StringUtils.isEmpty(sysFile.getSuffix())) {
				criteria.andFieldEqualTo("SUFFIX", sysFile.getSuffix());
			}
			if(!StringUtils.isEmpty(sysFile.getFileLabel())) {
				criteria.andFieldEqualTo("FILE_LABEL", sysFile.getFileLabel());
			}
			list=sysFileMapper.selectByExample(example);
		}
		return list;
	}

	@Override
	public List<SysFile> getLabel(String userId) {
		List<SysFile> list=null;
		if(!StringUtils.isEmpty(userId)) {
			list=sysFileMapper.selectLabel(userId);
		}
		return list;
	}

	@Override
	public int save(SysFile sysFile) {
		int count=0;
		if(sysFile!=null) {
			count=sysFileMapper.insertSelective(sysFile);
		}
		return count;
	}

	@Override
	public int update(SysFile sysFile) {
		int count=0;
		if(sysFile!=null) {
			count=sysFileMapper.updateByPrimaryKeySelective(sysFile);
		}
		return count;
	}

	@Override
	public int delete(String id) {
		int count=0;
		if(!StringUtils.isEmpty(id)) {
			count=sysFileMapper.deleteByPrimaryKey(id);
		}
		return count;
	}

	@Override
	public SysFile getById(String id) {
		SysFile sysFile=null;
		if(!StringUtils.isEmpty(id)) {
			sysFile=sysFileMapper.selectByPrimaryKey(id);
		}
		return sysFile;
	}

}
