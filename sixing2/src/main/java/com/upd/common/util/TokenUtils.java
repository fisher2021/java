package com.upd.common.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.interfaces.Claim;
import com.upd.common.basis.base.BusinessException;
import com.upd.common.basis.rest.RestErrorCode;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hui on 2017/3/10.
 */
public class TokenUtils {
    private static final String key="test";
    /**
     * @param headerClaims 其他参数，例如 姓名 角色 等等
     * @param expires 有效时间参数
     * @return
     */
    public static String create(Map<String, Object> headerClaims,Date expires){
        try {
            return JWT.create()
                    .withExpiresAt(expires)
                    .withIssuedAt(new Date())
                    .withIssuer("auth0").withHeader(headerClaims)
                    .sign(Algorithm.HMAC256(key));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JWT verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(key))
                    .acceptLeeway(0).acceptExpiresAt(0)
                    .withIssuer("auth0")
                    .build();
            JWT jwt = (JWT) verifier.verify(token);
            jwt = JWT.decode(token);
            return jwt;//信息
        } catch (InvalidClaimException e) {
            throw new BusinessException(RestErrorCode.TOKEN_EXPIRED);
        } catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(RestErrorCode.TOKEN);
        }
    }

    public static Claim get(String token,String key) {
        return TokenUtils.verify(token).getHeaderClaim(key);
    }

    public static void main(String[] args) {
        Map< String,Object > headerClaims =  new HashMap();
        headerClaims.put("user","zh");
        headerClaims.put("password","919cfbfefe2bc5b356fe49ec4f61ef23");
        String token=create(headerClaims,DateUtil.addDate(new Date(),15));
        System.out.println(token);
        JWT jwt= verify(token);
        Map < String,Claim > claims = jwt.getClaims();    // Key is the Claim name
        System.out.println(JSON.toJSONString(claims));
        Claim claim1= jwt.getHeaderClaim("user");
        System.out.println(claim1.asString());
    }
}