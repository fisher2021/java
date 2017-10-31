package com.upd.business.controller.rest;


import com.upd.business.service.DictService;
import com.upd.business.service.DictTypeService;
import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.entity.DictType;
import com.upd.common.basis.vo.JsonReturn;
import com.upd.common.util.FileUtils;
import com.upd.common.util.ZTree;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 词典控制器
 * Created by ljw on 2017/5/3.
 */
@Controller
@RequestMapping("rest/dictionary")
public class DictController {
	@Autowired
	private DictService dictService;
	@Autowired
	private DictTypeService dictTypeService;
	@RequestMapping(value = "getDictListByTypeId", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<Dict> getDictListByTypeId( @RequestParam String dictType)
	{
		List<Dict> dicts= dictService.getDictListByTypeId(dictType);
		return dicts;
	}
	//通过父数据字典查询子数据字典
	@RequestMapping(value="getSubDictList")
	@ResponseBody
	public List<Dict> getSubDictList(String parentDictId,String dictTypeid ){
		List<Dict> dictList=  this.dictService.getSubDictList(parentDictId,dictTypeid);
		return  dictList;
	}
	@RequestMapping("/toDict")
	public String  toDict(){return "admin/dict/dict";}

	@RequestMapping(value="/getDate")
	@ResponseBody
	public String getDate(HttpServletRequest request){
		String id =  request.getParameter("id");
		String sort =  request.getParameter("sort");
		System.out.println("id:"+id);
		System.out.println("sort:"+sort);
		String json="";
		if(id==null){
			json = getDictTypeDate();
		}else{
			json = getDictDate(Integer.parseInt(id), Integer.parseInt(sort) );
		}
		return json;
	}
	//添加类型
	@RequestMapping("/addDictType")
	@ResponseBody
	public JsonReturn addDictType(DictType dictTpye){
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn = dictTypeService.addDictType(dictTpye);
		return jsonReturn;
	}

	//获取类型数据
	@RequestMapping("/getDictType")
	@ResponseBody
	public DictType getDictType(Integer id){
		return dictTypeService.get(id);
	}

	//获取dict数据
	@RequestMapping("/getDict")
	@ResponseBody
	public Dict getDict(Integer id){
		return dictService.get(id);
	}

	//获取dict数据
	@RequestMapping("/getSelectDictType")
	@ResponseBody
	public JsonReturn getSelectDictType(Integer id){
		JsonReturn json = new JsonReturn();
		json.setData(dictTypeService.getDictTypeList());
		return json;
	}

	//添加dict
	@RequestMapping("/addDict")
	@ResponseBody
	public JsonReturn addDict(Dict dict,HttpServletRequest request){
		JsonReturn jsonReturn = new JsonReturn();
		String parentId = request.getParameter("parentId");
		String sort = request.getParameter("sort");
		String dictTypeId = request.getParameter("papa");
		jsonReturn = dictService.addDict(dict, Integer.parseInt(parentId),Integer.parseInt(sort),Integer.parseInt(dictTypeId));
		return jsonReturn;
	}
	public String getDictTypeDate(){
		List<DictType> dictTypeList = dictTypeService.getDictTypeList();
		List<ZTree> zTreeList = new ArrayList<ZTree>();
		for (int i = 0; i <dictTypeList.size() ; i++) {
			ZTree z = new ZTree();
			z.setId(dictTypeList.get(i).getId());
			z.setName(dictTypeList.get(i).getDictTypeName());
			z.setpId(0);
			z.setIsParent(true);
			z.setOpen(true);
			z.setSort(0);
			zTreeList.add(z);
		}
		JSONArray jsonarray = JSONArray.fromObject(zTreeList);
		String json = jsonarray.toString();
		return json;
	}

	public String getDictDate(Integer id, Integer sort){
		List<ZTree> zTreeList = new ArrayList<ZTree>();
		List<Dict> dictList=null;
		if(sort==0){
			dictList = dictService.getDictList(id);
		}else{
			dictList=dictService.getSubDictList(id);
		}

		for (int i = 0; i <dictList.size() ; i++) {
			ZTree z = new ZTree();
			Dict dict = dictList.get(i);
			z.setId(dict.getId());
			z.setName(dict.getDictName());
			z.setpId(id);
			z.setOpen(true);
			List<Dict> sonList = dictService.getSubDictList(dict.getId());
			if (sonList.size()!=0){
				z.setIsParent(true);
			}else{
				z.setIsParent(false);
			}
			z.setSort(dict.getSort());
			zTreeList.add(z);
		}
		JSONArray jsonarray = JSONArray.fromObject(zTreeList);
		String json = jsonarray.toString();
		return json;
	}

	/**
	 * 添加类别
	 * @param dict
	 * @param file
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addType")
	public Object addType(Dict dict , MultipartFile file, HttpSession session,int parentId ){
		JsonReturn jsonReturn = new JsonReturn();
		int sort = 0;
		int dictTypeId = parentId;
		jsonReturn = dictService.addDict(dict, parentId,sort,dictTypeId);

		return jsonReturn;
	}

	/**
	 * 删除类别
	 * @param id
	 * @param map
     * @return
     */
	@ResponseBody
	@RequestMapping("delType")
	public Object delNewsType(int id,ModelMap map){
		JsonReturn jsonReturn = new JsonReturn();
		Dict dict = this.dictService.get(id);
		if (dict!=null){
			//删除类别
			this.dictService.delete(dict);
			jsonReturn.setFlag(true);
		}
		return jsonReturn;
	}

	/**
	 * 编辑类别
	 * @param dict
	 * @param file
	 * @param session
	 * @return
	 */
	@RequestMapping("editType")
	public Object editType(Dict dict , MultipartFile file, HttpSession session,int parentId){
		JsonReturn jsonReturn = new JsonReturn();
		int sort = 0;
		int dictTypeId = parentId;
		dictService.saveOrUpdate(dict);
		jsonReturn.setFlag(true);

		return jsonReturn;
	}
}
