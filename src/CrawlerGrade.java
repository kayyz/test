import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 成绩爬取类，输出excel
 *
 */
public class CrawlerGrade {

	// 这个是在系统中提取到的课程导出url
	public String url = "http://jwglxt.hbeutc.cn:20000/jwglxt/cjcx/cjcx_dcListByXs.html";

	public static void main(String[] args) throws Exception {
		new CrawlerGrade().exportGradeExcel("JSESSIONID=6B2FEECA20C58B64699C3E8B6B86A2EB; UM_distinctid=17d5275ce3d348-0d66af45882751-978183a-100200-17d5275ce3e283; CNZZDATA1276005150=1644594440-1637762591-%7C1637762591", "518300284144", "2020", "",
				"甄聪", "D:/myCourse/");
	}

	/**
	 * 爬取成绩输出excel到本地
	 * 
	 * @param cookie  登录后的cookie
	 * @param account 学号
	 * @param xnm     学年，如果是2020-2021学年就传2020,如果是2021-2022学年则传2021，以此类推
	 * @param xqm     学期代码，第一学期传3，第二学期传12，第三学期传16，如果不分学期查询就传""
	 * @param xm      姓名
	 * @param path	  excel输出路径 传文件夹路径，如D:/myCourse/
	 * @throws IOException
	 */
	public void exportGradeExcel(String cookie, String account, String xnm, String xqm, String xm, String path)
			throws IOException {
		// 请求头，设置请求cookie
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);
		// 设置请求参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gnmkdmKey", "N305005");
		params.put("sessionUserKey", account);
		params.put("xnm", xnm);
		params.put("xqm", xqm);
		params.put("dcclbh", "JW_N305005_XSCXCJ");
		params.put("queryModel.sortName", "");
		params.put("queryModel.sortOrder", "asc");
		params.put("fileName", "666");

		// 发送请求爬取数据，下载到本地excel
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		String sendBody = "exportModel.selectCol=xnmmc@学年&exportModel.selectCol=xqmmc@学期&exportModel.selectCol=kch@课程代码&exportModel.selectCol=kcmc@课程名称&exportModel.selectCol=kcxzmc@课程性质&exportModel.selectCol=xf@学分&exportModel.selectCol=cj@成绩&exportModel.selectCol=cjbz@成绩备注&exportModel.selectCol=jd@绩点&exportModel.selectCol=ksxz@成绩性质&exportModel.selectCol=sfcjzf@是否成绩作废&exportModel.selectCol=sfxwkc@是否学位课程&exportModel.selectCol=kkbmmc@开课学院&exportModel.selectCol=kcbj@课程标记&exportModel.selectCol=kclbmc@课程类别&exportModel.selectCol=kcgsmc@课程归属&exportModel.selectCol=jxbmc@教学班&exportModel.selectCol=jsxm@任课教师&exportModel.selectCol=khfsmc@考核方式&exportModel.selectCol=xh@学号&exportModel.selectCol=xm@姓名&exportModel.selectCol=xb@性别&exportModel.selectCol=xslb@学生类别&exportModel.selectCol=jgmc@学院&exportModel.selectCol=zymc@专业&exportModel.selectCol=njmc@年级&exportModel.selectCol=bj@班级&exportModel.selectCol=xsbjmc@学生标记&";
		RequestBody body = RequestBody.create(mediaType, sendBody + MapUtil.join(params, "&", "=", ""));
		Request request = new Request.Builder().url("http://jwglxt.hbeutc.cn:20000/jwglxt/cjcx/cjcx_dcListByXs.html")
				.method("POST", body).addHeader("Cookie", cookie)
				.addHeader("Content-Type", "application/x-www-form-urlencoded").build();

		Response response = client.newCall(request).execute();
		//获取文件输入流
		InputStream byteStream = response.body().byteStream();
		//文件路径+名称
		String filePath = path + xm + xnm + "成绩" + ".xlsx";
		//将文件输出到本地
		FileUtil.writeFromStream(byteStream, filePath);
		System.out.println("成绩爬取成功，路径为：" + filePath);
	}

}
