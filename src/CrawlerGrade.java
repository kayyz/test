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
 * �ɼ���ȡ�࣬���excel
 *
 */
public class CrawlerGrade {

	// �������ϵͳ����ȡ���Ŀγ̵���url
	public String url = "http://jwglxt.hbeutc.cn:20000/jwglxt/cjcx/cjcx_dcListByXs.html";

	public static void main(String[] args) throws Exception {
		new CrawlerGrade().exportGradeExcel("JSESSIONID=6B2FEECA20C58B64699C3E8B6B86A2EB; UM_distinctid=17d5275ce3d348-0d66af45882751-978183a-100200-17d5275ce3e283; CNZZDATA1276005150=1644594440-1637762591-%7C1637762591", "518300284144", "2020", "",
				"���", "D:/myCourse/");
	}

	/**
	 * ��ȡ�ɼ����excel������
	 * 
	 * @param cookie  ��¼���cookie
	 * @param account ѧ��
	 * @param xnm     ѧ�꣬�����2020-2021ѧ��ʹ�2020,�����2021-2022ѧ����2021���Դ�����
	 * @param xqm     ѧ�ڴ��룬��һѧ�ڴ�3���ڶ�ѧ�ڴ�12������ѧ�ڴ�16���������ѧ�ڲ�ѯ�ʹ�""
	 * @param xm      ����
	 * @param path	  excel���·�� ���ļ���·������D:/myCourse/
	 * @throws IOException
	 */
	public void exportGradeExcel(String cookie, String account, String xnm, String xqm, String xm, String path)
			throws IOException {
		// ����ͷ����������cookie
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", cookie);
		// �����������
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("gnmkdmKey", "N305005");
		params.put("sessionUserKey", account);
		params.put("xnm", xnm);
		params.put("xqm", xqm);
		params.put("dcclbh", "JW_N305005_XSCXCJ");
		params.put("queryModel.sortName", "");
		params.put("queryModel.sortOrder", "asc");
		params.put("fileName", "666");

		// ����������ȡ���ݣ����ص�����excel
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
		String sendBody = "exportModel.selectCol=xnmmc@ѧ��&exportModel.selectCol=xqmmc@ѧ��&exportModel.selectCol=kch@�γ̴���&exportModel.selectCol=kcmc@�γ�����&exportModel.selectCol=kcxzmc@�γ�����&exportModel.selectCol=xf@ѧ��&exportModel.selectCol=cj@�ɼ�&exportModel.selectCol=cjbz@�ɼ���ע&exportModel.selectCol=jd@����&exportModel.selectCol=ksxz@�ɼ�����&exportModel.selectCol=sfcjzf@�Ƿ�ɼ�����&exportModel.selectCol=sfxwkc@�Ƿ�ѧλ�γ�&exportModel.selectCol=kkbmmc@����ѧԺ&exportModel.selectCol=kcbj@�γ̱��&exportModel.selectCol=kclbmc@�γ����&exportModel.selectCol=kcgsmc@�γ̹���&exportModel.selectCol=jxbmc@��ѧ��&exportModel.selectCol=jsxm@�ον�ʦ&exportModel.selectCol=khfsmc@���˷�ʽ&exportModel.selectCol=xh@ѧ��&exportModel.selectCol=xm@����&exportModel.selectCol=xb@�Ա�&exportModel.selectCol=xslb@ѧ�����&exportModel.selectCol=jgmc@ѧԺ&exportModel.selectCol=zymc@רҵ&exportModel.selectCol=njmc@�꼶&exportModel.selectCol=bj@�༶&exportModel.selectCol=xsbjmc@ѧ�����&";
		RequestBody body = RequestBody.create(mediaType, sendBody + MapUtil.join(params, "&", "=", ""));
		Request request = new Request.Builder().url("http://jwglxt.hbeutc.cn:20000/jwglxt/cjcx/cjcx_dcListByXs.html")
				.method("POST", body).addHeader("Cookie", cookie)
				.addHeader("Content-Type", "application/x-www-form-urlencoded").build();

		Response response = client.newCall(request).execute();
		//��ȡ�ļ�������
		InputStream byteStream = response.body().byteStream();
		//�ļ�·��+����
		String filePath = path + xm + xnm + "�ɼ�" + ".xlsx";
		//���ļ����������
		FileUtil.writeFromStream(byteStream, filePath);
		System.out.println("�ɼ���ȡ�ɹ���·��Ϊ��" + filePath);
	}

}
