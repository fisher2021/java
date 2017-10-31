package com.upd.business.controller.rest;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.upd.auth.service.LogService;
import com.upd.business.constant.BacklogType;
import com.upd.business.entity.PartyMembershipDues;
import com.upd.business.service.BacklogUserService;
import com.upd.business.service.DictService;
import com.upd.business.service.PartyMembershipDuesService;
import com.upd.common.basis.base.BaseController;
import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.entity.Dict;
import com.upd.common.basis.rest.RestErrorCode;
import com.upd.common.basis.rest.RestResult;
import com.upd.common.util.BigDecimalKit;
import com.upd.common.util.DateUtil;
import com.upd.common.util.TokenUtils;
import com.upd.common.util.pay.alipay.config.AlipayConfig;
import com.upd.common.util.pay.alipay.util.AlipayCore;
import com.upd.common.util.pay.alipay.util.AlipayNotify;
import com.upd.common.util.pay.wxpay.PaymentKit;
import com.upd.common.util.pay.wxpay.WxPayUtil;
import com.upd.common.util.pay.wxpay.util.XMLUtil;
import com.upd.common.util.prop.PropKit;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.InputSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付
 * Created by Administrator on 2017/6/6.
 */
@Controller
@RequestMapping("rest/pay")
public class PayController extends BaseController{

    @Autowired
    PartyMembershipDuesService partyMembershipDuesService;
    @Autowired
    BacklogUserService backlogUserService;
    @Autowired
    LogService logService;
    @Autowired
    DictService dictService;
    static {
        PropKit.use("pay.properties");
    }

    /**
     * 支付宝
     * @param id 缴费id
     */
    @RequestMapping("/alipay/{id}")
    @ResponseBody
    public String alipay(@PathVariable("id") Integer id, @RequestHeader("token") String token, Integer backlogId){
        PartyMembershipDues dues=partyMembershipDuesService.get(id);
        if(null==dues)throw  new BusinessException(RestErrorCode.PARAM,"缴费编号错误！");
        //计算金额
        BigDecimal trade_free=BigDecimalKit.sub(dues.getAmount().doubleValue(),dues.getFeeReceived().doubleValue());
        JWT jwt= TokenUtils.verify(token);
        int userId= jwt.getHeaderClaim("id").asInt();
        String body = "";
        if (backlogId != null){
            body = userId+","+backlogId;
        }else {
            body = userId+","+ "";
        }
        Map<String, String> map= AlipayConfig.getParameter(DateUtil.parseDateTimeToStrByDateFormat(new Date(),"yyyyMMddHHmmss")+dues.getId(), "党费缴纳", body, trade_free.toString());
//        Map<String, String> sign=new HashMap<String, String>();
//        sign.put("sign", AlipayCore.createLinkString(map,false));
        return  new RestResult("支付宝支付",AlipayCore.createLinkString(map,false)).toString();
    }

    /**
     *<p>Title: callback</p>
     *<p>Description:支付宝回调 生成支付订单</p>
     */
    @RequestMapping("/alipay/callback")
    @ResponseBody
    public String aliCallback(HttpServletRequest request, HttpServletResponse response){
        Map<String,String> params = AlipayConfig.getParams(request);
        String trade_status = params.get("trade_status");
        String out_trade_no = params.get("out_trade_no");
        System.out.println(JSON.toJSONString(params));
        String result="fail";
        if(AlipayNotify.verify(params)){//验证成功
            if ("TRADE_SUCCESS".equals(trade_status)){
                //更新订单状态
                PartyMembershipDues dues=partyMembershipDuesService.get(Integer.parseInt(out_trade_no.substring(14)));
                System.out.println("dues.id:"+dues.getId());
//                BigDecimal total=new BigDecimal(Double.parseDouble(params.get("total_amount"))/100);
                BigDecimal total = new BigDecimal(params.get("total_fee"));
                System.out.println("total:"+total);
                if(dues.getPayment().compareTo(total)==0 && !dues.getStatus()) {
                    System.out.println("out_trade_no:"+out_trade_no);
                    dues.setTrade_no(out_trade_no);
                    dues.setFeeReceived(BigDecimalKit.add(dues.getFeeReceived().doubleValue(),total.doubleValue()));
                    dues.setStatus(true);
                    dues.setPayType("支付宝");
                    partyMembershipDuesService.update(dues);
                    //待办和积分
                    String body = params.get("body");
                    System.out.println("body:"+body);
                    String[] arry = body.split(",");
                    Integer userId = Integer.valueOf(arry[0]);
                    Dict dict = dictService.getDictByDictId("zxjfjf");
                    logService.editPoint(userId, dues.getId(), dict);
                    if (arry.length > 1){
                        Integer backlogId = Integer.valueOf(arry[1]);
                        System.out.println(backlogId);
                        backlogUserService.finishByBacklog(userId, backlogId, BacklogType.ZXJF);
                    }
                    result = "success";
                }
            }
        }
        return result;
    }

    /**
     * 微信支付，预下单
     * @param id
     * @return
     */
    @RequestMapping("/wxpay/{id}")
    @ResponseBody
    public String wxpay(@PathVariable("id") Integer id,@RequestHeader("token") String token, Integer backlogId){
        PartyMembershipDues dues=partyMembershipDuesService.get(id);
        if(null==dues)throw  new BusinessException(RestErrorCode.PARAM,"缴费编号错误！");
        if(dues.getStatus())throw  new BusinessException(RestErrorCode.ERROR,"已缴费");
        BigDecimal trade_free=BigDecimalKit.sub(dues.getAmount().doubleValue(),dues.getFeeReceived().doubleValue());
        int i=(int) (BigDecimalKit.mul(trade_free.doubleValue(),100).doubleValue());
        JWT jwt= TokenUtils.verify(token);
        int userId= jwt.getHeaderClaim("id").asInt();
        String attach = "";
        if (backlogId != null){
            attach = userId+","+backlogId;
        }else {
            attach = userId+","+ "";
        }
        String res=new WxPayUtil().unifiedOrder(DateUtil.parseDateTimeToStrByDateFormat(new Date(),"yyyyMMddHHmmss")+dues.getId(),i+"",attach);
         return new RestResult("微信支付", res).toString();
    }

    @RequestMapping("/wxpay/callback")
    @ResponseBody
    public String wxpayCallback(HttpServletRequest request, HttpServletResponse response){
        Document doc = null;
        try {
            InputStream inStream = request.getInputStream();
            ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
            outSteam.close();
            inStream.close();
            String resultStr  = new String(outSteam.toByteArray(),"utf-8");

            StringReader read = new StringReader(resultStr);
            InputSource inputSource = new InputSource(read);
            SAXReader sb = new SAXReader();
            doc = sb.read(inputSource);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Map<String, String> resultMap = XMLUtil.Dom2Map(doc);
//		String result_code = resultMap.get("result_code");
//		String is_subscribe = resultMap.get("is_subscribe");
        String out_trade_no = resultMap.get("out_trade_no");
        String transaction_id = resultMap.get("transaction_id");
//		String sign = resultMap.get("sign");
//		String time_end = resultMap.get("time_end");
//		String bank_type = resultMap.get("bank_type");
        String return_code = resultMap.get("return_code");
//		String attach = resultMap.get("attach");
        String total_fee = resultMap.get("total_fee");
        System.out.println("success:"+request.getQueryString());
        Map<String, String> params = new HashMap<>();
        params.put("return_code", "FAIL");
        if(return_code.equals("SUCCESS")){
            System.out.println("out_trade_no:"+out_trade_no);
            PartyMembershipDues dues=partyMembershipDuesService.get(Integer.parseInt(out_trade_no.substring(14)));
            System.out.println("dues.id:"+dues.getId());
//            BigDecimal total=new BigDecimal(Double.parseDouble(params.get("total_fee"))/100);
            BigDecimal total = BigDecimalKit.div(Double.parseDouble(total_fee),100,2);
            if(dues.getPayment().compareTo(total)==0&&!dues.getStatus()) {
                dues.setTrade_no(out_trade_no);
                dues.setFeeReceived(BigDecimalKit.add(dues.getFeeReceived().doubleValue(),total.doubleValue()));
                dues.setStatus(true);
                dues.setPayType("微信支付");
                partyMembershipDuesService.update(dues);
                //待办和积分
                String attach = resultMap.get("attach");
                String[] arry = attach.split(",");
                Integer userId = Integer.valueOf(arry[0]);
                Dict dict = dictService.getDictByDictId("zxjfjf");
                logService.editPoint(userId, dues.getId(), dict);
                if (arry.length > 1){
                    Integer backlogId = Integer.valueOf(arry[1]);
                    backlogUserService.finishByBacklog(userId, backlogId, BacklogType.ZXJF);
                }
                params.put("return_code", "SUCCESS");
                params.put("return_msg","OK");
            }
        }
        response.setContentType("text/xml");
        return PaymentKit.mapToXml(params);
    }



}
