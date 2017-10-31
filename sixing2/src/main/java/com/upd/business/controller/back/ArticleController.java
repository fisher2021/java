package com.upd.business.controller.back;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;

import com.alibaba.fastjson.JSONObject;
import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.business.entity.Article;
import com.upd.business.entity.FileSave;
import com.upd.business.form.ArticleForm;
import com.upd.business.service.ArticleService;
import com.upd.business.service.DictService;
import com.upd.business.service.FileSaveService;
import com.upd.business.utils.OtherConfigBundle;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.FileUtils;
import com.upd.common.util.StringUtil;
import com.upd.common.util.page.Pagination;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * 文章管理后台管理控制器
 * @author cao.xin
 * 2017年1月5日
 */
@Controller("backArticleController")
@RequestMapping("/back/article")
public class ArticleController extends BaseController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private DictService dictService;
	@Autowired
	FileSaveService fileSaveService;

	/**
	 * 分页条件查询文章列表
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Pagination page, Model model, ArticleForm form,String dictId, HttpSession session){
        //获取登录信息,支部管理员只能查看本支部的通知公告，三会一课，民主生活会
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
		List<Dict> firstDicts = dictService.getDictListByParentId(dictService.getDictByDictId(dictId).getId());//获取第一频道列表
		if (firstDicts.size() != 0){
			String channelDictId = form.getChannelDictId1();//首次进入文章列表页面时查询其下所有文章
			String channelDictId2 = form.getChannelDictId2();
			if (channelDictId == null || channelDictId == "" || channelDictId2 == "") {
				StringBuilder ids = new StringBuilder();
				for (Dict dict:firstDicts) {
					ids.append(dict.getId()).append(",");
				}
				form.setIds(ids.substring(0,ids.lastIndexOf(",")).toString());
			}
			if (dictId.equals("shyk")){
				List<Dict> secondDicts = new ArrayList<Dict>();//获取第二频道列表
				for (Dict dict:firstDicts) {
					List<Dict> dicts = dictService.getDictListByParentId(dict.getId());
					for (Dict dict1:dicts) {
						secondDicts.add(dict1);
					}
				}
				StringBuilder ids = new StringBuilder();//有二级频道的查询其下所有文章
				for (Dict dict:secondDicts) {
					ids.append(dict.getId()).append(",");
				}
				form.setIds(ids.substring(0,ids.lastIndexOf(",")).toString());
				model.addAttribute("secondDicts",secondDicts);
			}
		}else {
			form.setIds(dictService.getDictByDictId(dictId).getId().toString());
		}
		articleService.page(page, form);
		model.addAttribute("firstDicts",firstDicts);
		model.addAttribute("dictId",dictId);
		model.addAttribute("page", page);
		model.addAttribute("form", form);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		return "article/list";
	}
	/**
	 * 跳转到文章详情页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/details")
	public String details(Integer id, Model model){
		Article article = articleService.get(id);
		model.addAttribute("article", article);
		return "article/details";
	}

	/**
	 * 跳转到文章添加或修改页面
	 * @param id
	 * @return
	 */
	@RequestMapping("/addOrEdit")
	public String addOrEdit(Integer id, Model model,String dictId){
		if(id != null){
			Article article = articleService.get(id);//编辑内容
			model.addAttribute("article", article);
		}
        List<Dict> kind = dictService.getDictListByTypeId("articleKind");//所属分类
        List<Dict> firstDicts = new ArrayList<>();
        for (Dict dict:kind){
            firstDicts.addAll(dictService.getDictListByParentId(dict.getId()));//获取第一频道列表
        }
        List<Dict> secondDicts = new ArrayList<Dict>();
        Dict shyk = dictService.getDictByDictId("shyk");//获取第二频道列表
        for (Dict dict1:dictService.getDictListByParentId(shyk.getId())) {
            List<Dict> dicts = dictService.getDictListByParentId(dict1.getId());
            for (Dict dict2:dicts) {
                secondDicts.add(dict2);
            }
        }
//        List<Dict> firstDicts = dictService.getDictListByParentId(dictService.getDictByDictId(dictId).getId());//获取第一频道列表
//		if (firstDicts.size() != 0){
//			if (dictId.equals("shyk")){
//				List<Dict> secondDicts = new ArrayList<Dict>();//获取第二频道列表
//				for (Dict dict:firstDicts) {
//					List<Dict> dicts = dictService.getDictListByParentId(dict.getId());
//					for (Dict dict1:dicts) {
//						secondDicts.add(dict1);
//					}
//				}
//				model.addAttribute("secondDicts",secondDicts);
//			}
//		}
		if (dictId.equals("gzgf")){//工作规范
			Integer gzgf  = dictService.getDictByDictId(dictId).getId();
			model.addAttribute("gzgf",gzgf);
		}
        if (dictId.equals("lzjs")){//廉政建设
            Integer lzjs  = dictService.getDictByDictId(dictId).getId();
            model.addAttribute("lzjs",lzjs);
        }
        if (dictId.equals("tzgg")){//通知公告
            Integer dzb  = dictService.getDictByDictId("dzb").getId();
            model.addAttribute("dzb",dzb);
        }
        model.addAttribute("kind",kind);
        model.addAttribute("shyk",shyk);
		model.addAttribute("firstDicts",firstDicts);
        model.addAttribute("secondDicts",secondDicts);
		model.addAttribute("dictId",dictId);
        model.addAttribute("branch", ORGType.PARTY_BRANCH);
		return "article/addOrEdit";
	}

	/**
	 * 添加或修改文章
	 * @param article
	 * @return
	 */
	@RequestMapping("/ajax/addOrEdit")
	@ResponseBody
	public String addOrEdit(Article article,Integer typeId, MultipartHttpServletRequest request, HttpSession session) throws IOException {
        Operator operator = (Operator) session.getAttribute("logedOperator");
		//图片上传处理
		MultipartFile file = request.getFile("image");
        if(file != null){
            String folder = "/article/";
            String image = fileSaveService.add(file,folder).getUrl();
            article.setImgUrl(image);
        }
        if (typeId != null){
            Dict type = dictService.get(typeId);
            article.setType(type);
        }
        if (article.getId() == null){
            //如果不是党支部添加的文章，将组织ID都设置为0，方便展示给所有用户
            if (operator.getOrg().getLevel() != ORGType.PARTY_BRANCH){
                article.setOrgId(0);
            }else {
                article.setOrgId(operator.getOrg().getId());
            }
            article.setOperator(operator);//创建人
            articleService.add(article);
        }else {
            articleService.edit(article);
        }
		return new RestResult("添加或修改文章",null).toString();
	}

	/**
	 * 删除文章
	 * @param article
	 * @return
	 */
	@RequestMapping("/ajax/delete")
	@ResponseBody
	public String delete(Article article){
		articleService.delete(article);
		return new RestResult("删除文章",null).toString();
	}

	/**
	 * 批量删除文章
	 * @param ids
	 * @return
	 */
	@RequestMapping("/ajax/deleteBatch")
	@ResponseBody
    public String deleteBatch(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            articleService.deleteBatch(ids);
        }
        return new RestResult("批量删除文章", null).toString();
    }
    /**
     * 排序
     * @return
     */
    @RequestMapping("/rank")
    @ResponseBody
    public String upRank(Integer id,Integer type,String channel1,String channel2,String dictId, HttpSession session){
        ArticleForm form = new ArticleForm();
        //按当前条件重新查询文章
        Operator operator = (Operator) session.getAttribute("logedOperator");
        if (operator.getOrg().getLevel() == ORGType.PARTY_BRANCH){
            form.setOrgId(operator.getOrg().getId());
        }
        if (channel2 != null && !channel2.equals("")){
            form.setChannelDictId2(channel2);
        }else if (channel1 != null && !channel1.equals("")){
            form.setChannelDictId1(channel1);
        }else {
            form.setType(dictId);
        }
        List<Article> articles = articleService.articleList(form);
        //根据type找出相邻文章的ID
        Integer otherId = null;
        for (int i=0;i<articles.size();i++){
            if (articles.get(i).getId().equals(id)){
                if (type.equals(1)){
                    if (i-1 < 0){
                        throw new BusinessException(RestErrorCode.PARAM,"文章已是头条！");
                    }
                    otherId = articles.get(i-1).getId();
                }else if (type.equals(2)){
                    if ((i+1) > (articles.size()-1)){
                        throw new BusinessException(RestErrorCode.PARAM,"文章已是最后一条！");
                    }
                    otherId = articles.get(i+1).getId();
                }
                break;
            }
        }
        articleService.rank(id,otherId);
        return new RestResult("排序",null).toString();
    }

    //富文本上传文件
    @RequestMapping(value ="/uploadUEditorFile", method = RequestMethod.POST)
    @ResponseBody
    public String uploadUEditorFile(HttpServletRequest request, HttpServletResponse response, MultipartFile upfile) {
//        String webpath = request.getSession().getServletContext().getRealPath("/");
       /* SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        Date nowdate = new Date();*/
//        String path = OtherConfigBundle.getConfig("upload_url");
//        String targetDir = "/static/res/upload/ueditor/"+StringUtil.DateToString(new Date(),"yyyy")+"/"+StringUtil.DateToString(new Date(),"MM-dd");
//        String tmpPath = FileUtils.doUpload(path, upfile, targetDir);//上传文件
//        String url = request.getScheme()+"://"; //请求协议 http 或 https
//        url+=request.getHeader("host");  // 请求服务器
        String url = request.getContextPath();
        try {
            url += fileSaveService.add(upfile,"/ueditor/").getUrl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject();
        obj.put("state", "SUCCESS");
//        obj.put("url", url+"/"+tmpPath);
        obj.put("url", url);
        obj.put("title", upfile.getOriginalFilename());
        obj.put("original", upfile.getOriginalFilename());
        return obj.toString();
    }

}
