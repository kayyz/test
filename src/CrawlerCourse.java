import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

/**
 * �γ���ȡ�࣬���pdf
 *
 */
public class CrawlerCourse {
	
	//�������ϵͳ����ȡ���Ŀγ̵���url
	public String url = "http://jwglxt.hbeutc.cn:20000/jwglxt/kbcx/xskbcx_cxXsShcPdf.html?doType=table";

	public static void main(String[] args) throws Exception {
		new CrawlerCourse().exportCoursePdf("JSESSIONID=8F8B87644C9B8BFCB98F0EFAB7F64F35", "518300284144", "2020", "12", "2020-2021", "2", "���", "D:/myCourse/");
	}
	
	/**
	 * ��ȡ�γ����pdf������
	 * @param cookie	��¼���cookie
	 * @param account	ѧ��
	 * @param xnm		ѧ�꣬�����2020-2021ѧ��ʹ�2020,�����2021-2022ѧ����2021���Դ�����
	 * @param xqm		ѧ�ڴ��룬��һѧ�ڴ�3���ڶ�ѧ�ڴ�12������ѧ�ڴ�16
	 * @param xnmc		ѧ�����ƣ���2019-2020����2020-2021����2021-2022���Դ�����
	 * @param xqmmc		ѧ�ڣ��ɴ�1,2,3 ��һѧ�ھʹ�1��
	 * @param xm		����
	 * @param pdf���·��	���ļ���·������D:/myCourse/
	 */
	public void exportCoursePdf(String cookie, String account, String xnm, String xqm, String xnmc, 
			String xqmmc, String xm, String path) {
			//����ͷ����������cookie
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("Cookie", cookie);
			//�����������
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
			System.out.println("�α���ȡ�ɹ���·��Ϊ��" + filePath);
		
	}
	
}
