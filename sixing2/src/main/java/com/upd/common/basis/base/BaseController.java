package com.upd.common.basis.base;

import com.upd.auth.entity.Operator;
import com.upd.business.constant.ORGType;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.basis.rest.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 包含Controller公用的方法，其他的controller通过继承来使用这些公用的方法.
 */
public abstract class BaseController {

    public Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 
     * 获取用户IP
     */
    public static String getRemoteIP(HttpServletRequest request) {
        String ip = null;
        if ((request.getHeader("x-forwarded-for") != null) && (!"unknown".equalsIgnoreCase(request.getHeader("x-forwarded-for")))) {
            ip = request.getHeader("x-forwarded-for");
        } else {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 接口异常
     * @param e
     * @return
     */
    @ExceptionHandler({BusinessException.class})
    @ResponseBody
    public String businessException(BusinessException e) {
        logger.error("BusinessException，errorCode=" + e.getMessage(), e);
        return new RestResult(null,e.getCode(),e.getMessage(),null).toString();
    }

    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public String allException(Exception e) {
        logger.error("系统异常", e);
        return new RestResult(null, RestErrorCode.SYSTEM_ERROR.getCode(),RestErrorCode.SYSTEM_ERROR.getMsg(),null).toString();
    }

    /**
     * 单位code
     * @return
     */
    public static String getOperatorUnitCode(Operator operator){
        if(operator==null){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            operator = (Operator)request.getSession().getAttribute("logedOperator");
        }

        String code = "";
        if(operator.getOrg()!=null && operator.getOrg().getCode()!=null){
            String orgCode = operator.getOrg().getCode();
            if(orgCode.length()==3){
                code = orgCode.substring(0, ORGType.SYS_DEFAULT.getCodeLength());
            }else{
                code = orgCode.substring(0,ORGType.SYS_DEFAULT.getCodeLength() +ORGType.UNIT.getCodeLength());
            }
        }
        return code;
    }

    /**
     * 单位code 不包含单位
     * @return
     */
    public static String getOperatorUnitCodeUnder(Operator operator){
        if(operator==null){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            operator = (Operator)request.getSession().getAttribute("logedOperator");
        }
        String code = "";
        if(operator.getOrg()!=null && operator.getOrg().getCode()!=null){
            String orgCode = operator.getOrg().getCode();
            if(orgCode.length()==3){
                code = orgCode.substring(0, ORGType.SYS_DEFAULT.getCodeLength()) + "0";
            }else{
                code = orgCode.substring(0,ORGType.SYS_DEFAULT.getCodeLength() +ORGType.UNIT.getCodeLength()) + "0";
            }
        }
        return code;
    }
}
