package com.upd.common.basis.base;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.common.util.TokenUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class QueryForm {
	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	public HttpSession getSession() {
		return request.getSession();
	}

    private transient StringBuffer queryDescrition = new StringBuffer();
	protected ArrayList<Object> queryParam = new ArrayList<Object>();
	protected Map<String, Object> paraMap= new HashMap<String, Object>();
	private transient boolean parsed = false;
	protected abstract void doParseInternal();
	
//	public QueryForm(Map<String, String[]> paraMap) {
//		for(Map.Entry<String, String[]> entry:paraMap.entrySet()){
//			this.paraMap.put(entry.getKey(),entry.getValue()[0]);
//		}
//	}
//	public QueryForm(HashMap<String, Object> paraMap) {
//		this.paraMap=paraMap;
//	}
//	public QueryForm(String alias,Object id) {
//		queryDescrition.append(" " + alias + " = ?");
//		queryParam.add(id);
//	}
	public Object[] values(){
		return queryParam.toArray();
	}
	
	public void parse() {
		queryDescrition.append(" 1=1");
		Operator operator = (Operator) getSession().getAttribute("logedOperator");
		String token=request.getHeader("token");
		String unitCode = null;
		if(null!=operator&&operator.getRole().getId()!=1){
			 unitCode = operator.getOrg().getCode().substring(0, ORGType.SYS_DEFAULT.getCodeLength()
					+ORGType.UNIT.getCodeLength());
		}else if(null!=token){
			JWT jwt= TokenUtils.verify(token);
			unitCode= jwt.getHeaderClaim("unitCode").asString();
        }
		if(null!=unitCode&&!unitCode.equals("")) {
			String className=this.getClass().getName();
			if(className.indexOf("Article")>0||className.indexOf("Carousel")>0) {
                if (token != null){//APP用户查询系统管理员创建的 和本单位的文章
                    queryDescrition.append(" and (operator.org.code like '"+unitCode+"%' or operator.role.id  = 1)");
                }else {//后台不能查询管理员创建的文章
                    queryDescrition.append(" and (operator.org.code like '"+unitCode+"%')");
                }
			}else{
				likeR("operator.org.code", unitCode);
			}
		}
		doParseInternal();
		parsed=true;
	}
	public String toQueryDescription() {
		if (!parsed&&queryDescrition.length()==0) {
			parse();
		}
		return queryDescrition.toString();
	}
	
	protected void eq(String alias, Object value) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and " + alias + " = ?");
			queryParam.add(value);
		}
	}

	protected void or(String alias, Object value) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" or " + alias + " = ?");
			queryParam.add(value);
		}
	}
	protected void oreqA(String alias,String alias2, Object value) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and ("+alias+"=? or " + alias2 + " = ?)");
			queryParam.add(value);
			queryParam.add(value);
		}
	}
	protected void oreqV(String alias,Object value, Object value2) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and ("+alias+"=? or " + alias + " = ?)");
			queryParam.add(value);
			queryParam.add(value2);
		}
	}

	protected void oreq(String alias,String alias2,Object value, Object value2) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and ("+alias+"=? or " + alias2 + " = ?)");
			queryParam.add(value);
			queryParam.add(value2);
		}
	}

	protected void like(String alias, Object value) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and " + alias + " like ?");
			queryParam.add("%"+value+"%");
		}
	}

	/*
	右模糊匹配
	 */
	protected void likeR(String alias, Object value) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and " + alias + " like ?");
			queryParam.add(value+"%");
		}
	}

    protected void notLike(String alias, Object value) {
        if (value != null&&value.toString()!="") {
            queryDescrition.append(" and " + alias + " not like ?");
            queryParam.add("%"+value+"%");
        }
    }
	protected void orlike(String alias,String alias2, Object value) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and ("+alias+" like ? or " + alias2 + " like ?)");
			queryParam.add("%"+value+"%");
			queryParam.add("%"+value+"%");
		}
	}
	
	protected void notEq(String alias, Object value) {
		if (value != null&&value.toString()!="") {
			if (value instanceof String) {
				value = ((String) value).replace("'", "");
			}
			queryDescrition.append(" and " + alias + " !=?");
			queryParam.add(value);
		}
	}
	/*
	 * 小于等于
	 */
	protected void le(String alias, Object value) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and " + alias + " <=?");
			queryParam.add(value);
		}
	}
	/*
	 * 大于等于
	 */
	protected void ge(String alias, Object value) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and " + alias + " >=?");
			queryParam.add(value);
		}
	}
	/*
	 * 小于
	 */
	protected void lt(String alias, Object value) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and " + alias + " < ?");
			queryParam.add(value);
		}
	}
	/*
	 * 大于
	 */
	protected void gt(String alias,Object value) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and " + alias + " > ?");
			queryParam.add(value);
		}
	}
	protected void in(String alias,Object value) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and " + alias + " in ("+value+")");
		}
	}

	protected void notIn(String alias,Object value) {
		if (value != null&&value.toString()!="") {
			queryDescrition.append(" and " + alias + " not in ("+value+")");
		}
	}

	protected void notNull(String alias) {
		queryDescrition.append(" and " + alias + " is NOT NULL");
	}

	protected void isNull(String alias) {
		queryDescrition.append(" and " + alias + " is NULL");
	}

	public Map<String, Object> getParaMap() {
		return paraMap;
	}
	public void put(String key,Object val) {
		paraMap.put(key, val);
	}
	public void setParaMap(Map<String, Object> paraMap) {
		this.paraMap = paraMap;
	}
	
	public void orderBy(Object order){
		if(null!=order){
			queryDescrition.append(" order by "+order);
		}
	}
}
