package com.upd.business.controller.back;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.*;
import com.upd.business.form.JobForm;
import com.upd.business.service.*;
import com.upd.business.utils.OtherConfigBundle;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.FileDownLoadUtil;
import com.upd.common.util.FileUtils;
import com.upd.common.util.page.Pagination;
import com.upd.common.util.word.WordUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作展示后台管理控制器
 * @author cao.xin
 * 2017年1月5日
 */
@Controller("backJobBackController")
@RequestMapping("/back/job")
public class JobBackController extends BaseController {
	@Autowired 
	private JobBackService jobBackService;
    @Autowired
    FileSaveService fileSaveService;
	@Autowired
    ORGService orgService;
	/**
	 * 分页条件查询工作展示列表
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Pagination page, Model model, JobForm form, HttpSession session){
        //党支部的管理员只能查看本支部的用户工作展示
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
        jobBackService.page(page, form);
        List<ORG> orgList = orgService.findAll();
        model.addAttribute("orgList", orgList);
		model.addAttribute("page", page);
		model.addAttribute("form", form);
		return "jobback/list";
	}
	/**
	 * 跳转到工作展示详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/details")
	public String details(Integer id, Model model){
		JobBack jobBack = jobBackService.get(id);
		model.addAttribute("job", jobBack);
		return "jobback/details";
	}

	/**
	 * 删除工作展示
	 * @param jobBack
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(JobBack jobBack){
		jobBackService.delete(jobBack);
		return new RestResult("删除工作展示",null).toString();
	}

	/**
	 * 批量删除工作展示
	 * @param ids
	 * @return
	 */
	@RequestMapping("/ajax/deleteBatch")
	@ResponseBody
	public String deleteBatch(String ids, HttpSession session){
		if(StringUtils.isNotBlank(ids)){
			jobBackService.deleteBatch(ids);
		}
		return new RestResult("批量删除工作展示",null).toString();
	}
    /**
     * 跳转到工作展示添加或修改页面
     * @param id
     * @return
     */
    @RequestMapping("/addOrEdit")
    public String addOrEdit(Integer id, Model model){
        if(id != null){
            JobBack jobBack = jobBackService.get(id);
            model.addAttribute("jobBack", jobBack);
        }
        return "jobback/addOrEdit";
    }
    /**
     * 添加或修改工作展示
     * @param jobBack
     * @return
     */
    @RequestMapping("/ajax/addOrEdit")
    @ResponseBody
    public String addOrEdit(MultipartHttpServletRequest request, JobBack jobBack, HttpSession session) throws IOException {
        //图片上传处理
        MultipartFile file1 = request.getFile("file1");
        MultipartFile file2 = request.getFile("file2");
        String folder = "/jobBack/";
        if (file1 != null){
            String image1 = fileSaveService.add(file1,folder).getUrl();
            jobBack.setImage1(image1);
        }
        if (file2 != null){
            String image2 = fileSaveService.add(file2,folder).getUrl();
            jobBack.setImage2(image2);
        }
        if (jobBack.getId() == null){
            Operator operator = (Operator) session.getAttribute("logedOperator");
            jobBack.setOrgId(operator.getOrg().getId());
            jobBack.setOperator(operator);
            jobBackService.add(jobBack);
        }else {
            jobBackService.edit(jobBack);
        }
        return new RestResult("添加或修改工作展示",null).toString();
    }

    /**
     * 导出工作展示Word
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/exportword")
    @ResponseBody
    public String export(HttpServletResponse response, Integer id) throws UnsupportedEncodingException {
        //获取导出数据
        JobBack jobBack = jobBackService.get(id);
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title",jobBack.getSubject());
        dataMap.put("desc1",jobBack.getDesc1());
        dataMap.put("content",jobBack.getContent());
        //从下载路径获取图片的真实路径
        String img = jobBack.getImage1();
        int fileId = Integer.valueOf(img.substring(img.lastIndexOf("=")+1));
        FileSave file = fileSaveService.get(fileId);
        dataMap.put("img1",file.getPath());
        img = jobBack.getImage2();
        if (img != null && !img.equals("")){
            fileId = Integer.valueOf(img.substring(img.lastIndexOf("=")+1));
            file = fileSaveService.get(fileId);
            dataMap.put("img2",file.getPath());
            dataMap.put("desc2",jobBack.getDesc2());
        }else {
            dataMap.put("img2","");
            dataMap.put("desc2","");
        }
        //导出文件
        String fileName="工作展示.doc";
        WordUtil wordUtil = new WordUtil();
        String path = OtherConfigBundle.getConfig("upload_url")+"/doc/"+fileName;
        wordUtil.createDoc(dataMap,path,response,"job.ftl");
        return new RestResult("导出工作展示",null).toString();
    }

}
