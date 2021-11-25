import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;

/**
 * 空闲教室数据爬取类，输出excel
 *
 */
public class CrawlerFreeClassroom {

	// 获取空闲教室数据url
	public String url = "http://jwglxt.hbeutc.cn:20000/jwglxt/cdjy/cdjy_cxKxcdlb.html?doType=query&gnmkdm=N2155";

	public static void main(String[] args) throws Exception {
		new CrawlerFreeClassroom().exportFreeClassroomExcel("JSESSIONID=EB4DC2BD8A77D230F7437319A4992E36", "2021", "3", "12",
				"5", "7,8", "D:/myCourse/");
	}

	/**
	 * 爬取空闲教室输出excel到本地
	 * 
	 * @param cookie  登录后的cookie
	 * @param xnm     学年，由于系统限制目前只能传2021，如果是2020-2021学年就传2020,如果是2021-2022学年则传2021，以此类推
	 * @param xqm     学期代码，由于系统限制，目前只能传3，第一学期传3，第二学期传12，第三学期传16，如果不分学期查询就传""
	 * @param week    第几周，从12周开始，19周结束，多个用,隔开
	 * @param xqj     星期几，多个用,隔开，如1,2,3
	 * @param lesson  节次，1-14之间的数值，多个用,隔开
	 * @param path    excel输出路径 传文件夹路径，如D:/myCourse/
	 * @throws IOException
	 */
	public void exportFreeClassroomExcel(String cookie, String xnm, String xqm, String week, String xqj, String lesson, String path)
			throws IOException {
		// 请求头，设置请求cookie
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);
		// 设置请求参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fwzt", "cx");
		params.put("xqh_id", "1");
		params.put("xnm", xnm);
		params.put("xqm", xqm);
		params.put("xqj", xqj);
		params.put("cdlb_id", "");
		params.put("cdejlb_id", "");
		params.put("qszws", "");
		params.put("jszws", "");
		params.put("cdmc", "");
		params.put("lh", "");
		params.put("jyfs", "0");
		params.put("cdjylx", "");
		if (StrUtil.isNotBlank(week)) {
			int w = 0;
			List<String> weekList = StrUtil.split(week, ",");
			for (String string : weekList) {
				w += Math.pow(2, Integer.parseInt(string) - 1);
			}
			params.put("zcd", w);
		}
		
		if (StrUtil.isNotBlank(lesson)) {
			int l = 0;
			List<String> lessonList = StrUtil.split(lesson, ",");
			for (String string : lessonList) {
				l += Math.pow(2, Integer.parseInt(string) - 1);
			}
			params.put("jcd", l);
		}
		
		params.put("_search", "false");
		params.put("queryModel.showCount", "15000");
		params.put("queryModel.currentPage", "1");
		params.put("queryModel.sortName", "cdbh");
		params.put("queryModel.sortOrder", "asc");
		//请求得到空闲教室数据
		String result = HttpRequest.post(url).addHeaders(headers).form(params).execute().body();
		
		//导出excel
		JSONObject jsonObject = JSONObject.parseObject(result);
		List<FreeClassroomBean> data = JSONObject.parseArray(JSON.toJSONString(jsonObject.get("items")), FreeClassroomBean.class);
		String[] titles = {"场地编号", "场地名称", "校区", "场地类别", "座位数", "考试座位数", "楼号", "场地借用类型号", "使用部门", "托管部门"};
		String filePath = path + "空闲教室.xlsx";
		ExportExcelUtil.exportExcel(data, titles, filePath);
		System.out.println("空闲教室数据爬取成功，路径为：" + filePath);
	}

}
