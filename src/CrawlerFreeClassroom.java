import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;

/**
 * ���н���������ȡ�࣬���excel
 *
 */
public class CrawlerFreeClassroom {

	// ��ȡ���н�������url
	public String url = "http://jwglxt.hbeutc.cn:20000/jwglxt/cdjy/cdjy_cxKxcdlb.html?doType=query&gnmkdm=N2155";

	public static void main(String[] args) throws Exception {
		new CrawlerFreeClassroom().exportFreeClassroomExcel("JSESSIONID=EB4DC2BD8A77D230F7437319A4992E36", "2021", "3", "12",
				"5", "7,8", "D:/myCourse/");
	}

	/**
	 * ��ȡ���н������excel������
	 * 
	 * @param cookie  ��¼���cookie
	 * @param xnm     ѧ�꣬����ϵͳ����Ŀǰֻ�ܴ�2021�������2020-2021ѧ��ʹ�2020,�����2021-2022ѧ����2021���Դ�����
	 * @param xqm     ѧ�ڴ��룬����ϵͳ���ƣ�Ŀǰֻ�ܴ�3����һѧ�ڴ�3���ڶ�ѧ�ڴ�12������ѧ�ڴ�16���������ѧ�ڲ�ѯ�ʹ�""
	 * @param week    �ڼ��ܣ���12�ܿ�ʼ��19�ܽ����������,����
	 * @param xqj     ���ڼ��������,��������1,2,3
	 * @param lesson  �ڴΣ�1-14֮�����ֵ�������,����
	 * @param path    excel���·�� ���ļ���·������D:/myCourse/
	 * @throws IOException
	 */
	public void exportFreeClassroomExcel(String cookie, String xnm, String xqm, String week, String xqj, String lesson, String path)
			throws IOException {
		// ����ͷ����������cookie
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);
		// �����������
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
		//����õ����н�������
		String result = HttpRequest.post(url).addHeaders(headers).form(params).execute().body();
		
		//����excel
		JSONObject jsonObject = JSONObject.parseObject(result);
		List<FreeClassroomBean> data = JSONObject.parseArray(JSON.toJSONString(jsonObject.get("items")), FreeClassroomBean.class);
		String[] titles = {"���ر��", "��������", "У��", "�������", "��λ��", "������λ��", "¥��", "���ؽ������ͺ�", "ʹ�ò���", "�йܲ���"};
		String filePath = path + "���н���.xlsx";
		ExportExcelUtil.exportExcel(data, titles, filePath);
		System.out.println("���н���������ȡ�ɹ���·��Ϊ��" + filePath);
	}

}
