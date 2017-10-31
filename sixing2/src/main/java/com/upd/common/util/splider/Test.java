package com.upd.common.util.splider;


import com.upd.common.util.splider.util.DateUtil;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

public class Test {
	public void getDatasByClass()
	{
		Rule rule = new Rule(
				"http://www1.sxcredit.gov.cn/public/infocomquery.do?method=publicIndexQuery",
		new String[] { "query.enterprisename","query.registationnumber" }, new String[] { "兴网","" },
				"cont_right", Rule.CLASS, Rule.POST);
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		printf(extracts);
	}

	public void getDatasByCssQuery()
	{
		Rule rule = new Rule("http://www.11315.com/search",
				new String[] { "name" }, new String[] { "兴网" },
				"div.g-mn div.con-model", Rule.SELECTION, Rule.GET);
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		printf(extracts);
	}

	public static void printf(List<LinkTypeData> datas)
	{
		for (LinkTypeData data : datas)
		{
			System.out.println(data.getLinkText());
			System.out.println(data.getLinkHref());
			System.out.println("***************2222222********************");
		}

	}

	public static String printfContent(List<LinkTypeData> datas) {
		String res = "";
		for (LinkTypeData data : datas)
		{
			res+=data.getContent();
		}
		return res;
	}

	/**
	 *
	 * @param title
	 * @param content
	 * @param type
	 *
	 *  56			jiuye	就业
		57			keji	科技
		58			yiliao	医疗
		59			jiaoyu	教育
		60			jingji	经济
		61			shenghuo	生活
		7			zyxw	中央新闻
	 * @param author
     * @throws Exception
     */
	public static void insertIntoDB(String title, String content, Integer type, String author) throws Exception{
		DBHelper dbHelper = new DBHelper();

		PreparedStatement preparedStatement =
				dbHelper.exeSql("INSERT INTO article (createTime, updateTime, author, content, count, imgUrl, title, operator, type, targetOut, targetUrl, org_id) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		preparedStatement.setString(1, DateUtil.parseDateTimeToSec(new Date()));
		preparedStatement.setString(2, DateUtil.parseDateTimeToSec(new Date()));
		preparedStatement.setString(3, author);
		preparedStatement.setString(4, content);
		preparedStatement.setInt(5, 0);
		preparedStatement.setString(6, "");
		preparedStatement.setString(7, title);
		preparedStatement.setString(8, "");
		preparedStatement.setInt(9, type);
		preparedStatement.setInt(10, 0);
		preparedStatement.setString(11, "");
		preparedStatement.setInt(12, 0);

		preparedStatement.executeUpdate();

//		ResultSet rs = preparedStatement.getGeneratedKeys();
//		if (rs.next()) {
//			Serializable ret = (Serializable) rs.getObject(1);
//			System.out.println(ret);
//		}

	}

	public static void main(String[] args) {
		String  url = "http://scitech.people.com.cn";
		Rule rule = new Rule(url,
				new String[] { "word" }, new String[] { "" },
				"news_box", 0, Rule.GET);
		List<LinkTypeData> extracts = ExtractService.extract(rule);
		int times = 0;
		for (LinkTypeData data : extracts) {
			Rule rule1 = new Rule(url+data.getLinkHref(),
					new String[] { "word" }, new String[] { "" },
					"rwb_zw", 1, Rule.GET);
			List<LinkTypeData> extracts1 = ExtractService.extractContent(rule1);
			try {
				insertIntoDB(data.getLinkText(), printfContent(extracts1),57,"文化网");
			} catch (Exception e) {
				e.printStackTrace();
			}
			times++;
			if(times>5){
				break;
			}
		}
	}

}
