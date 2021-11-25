import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

/**
 * 课程爬取类，输出pdf
 *
 */
public class CrawlerCourse {
	
	//这个是在系统中提取到的课程导出url
	public String url = "http://jwglxt.hbeutc.cn:20000/jwglxt/kbcx/xskbcx_cxXsShcPdf.html?doType=table";

	public static void main(String[] args) throws Exception {
		new CrawlerCourse().exportCoursePdf("JSESSIONID=8F8B87644C9B8BFCB98F0EFAB7F64F35", "518300284144", "2020", "12", "2020-2021", "2", "甄聪", "D:/myCourse/");
	}
	
	/**
	 * 爬取课程输出pdf到本地
	 * @param cookie	登录后的cookie
	 * @param account	学号
	 * @param xnm		学年，如果是2020-2021学年就传2020,如果是2021-2022学年则传2021，以此类推
	 * @param xqm		学期代码，第一学期传3，第二学期传12，第三学期传16
	 * @param xnmc		学年名称，传2019-2020或者2020-2021或者2021-2022，以此类推
	 * @param xqmmc		学期，可传1,2,3 第一学期就传1，
	 * @param xm		姓名
	 * @param pdf输出路径	传文件夹路径，如D:/myCourse/
	 */
	public void exportCoursePdf(String cookie, String account, String xnm, String xqm, String xnmc, 
			String xqmmc, String xm, String path) {
			//请求头，设置请求cookie
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Cookie", cookie);
			//设置请求参数
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("gnmkdmKey", "N2151");
			params.put("sessionUserKey", account);
			params.put("xnm", xnm);
			params.put("xqm", xqm);
			params.put("xnmc", xnmc);
			params.put("xqmmc", xqmmc);
			params.put("jgmc", "");
			params.put("xm", xm);
			params.put("xxdm", "");
			params.put("xszd.sj", true);
			params.put("xszd.cd", true);
			params.put("xszd.js", true);
			params.put("xszd.jszc", false);
			params.put("xszd.jxb", true);
			params.put("xszd.xkbz", true);
			params.put("xszd.kcxszc", true);
			params.put("xszd.zhxs", true);
			params.put("xszd.zxs", true);
			params.put("xszd.khfs", true);
			params.put("xszd.xf", true);
			params.put("xszd.skfsmc", false);
			params.put("kzlx", "dy");
			HttpResponse httpResponse = HttpRequest.post(url).addHeaders(headers).form(params).execute();
			InputStream inputStream = httpResponse.bodyStream();
			String filePath = path + xm + xnmc + "-" +  xqmmc + ".pdf";
			FileUtil.writeFromStream(inputStream, filePath);
			System.out.println("课表爬取成功，路径为：" + filePath);
		
	}
	
}
