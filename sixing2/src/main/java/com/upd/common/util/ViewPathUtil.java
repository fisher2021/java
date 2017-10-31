package com.upd.common.util;

/**
 * Created by zhangshao on 2015/12/15.
 */
public class ViewPathUtil {
  public static final String prefixPath = "front/";
  public static final String authPrefixPath = "auth/";
  public static final String managerPrefixPath = "admin/";
  public static final String authloginPath = authPrefixPath + "managerLogin";
  public static final String managerbranchListPath = managerPrefixPath + "branch/list";
  public static final String managerbranchEditPath = managerPrefixPath + "branch/edit";

  //价格搜索
  public static final String managerPriceSearchEditPath = managerPrefixPath + "priceSearch/edit";
  public static final String managerPriceSearchListPath = managerPrefixPath + "priceSearch/list";

  //规格参数
  public static final String managerspecificationParameterPath = managerPrefixPath + "specificationParameter/index";
}

