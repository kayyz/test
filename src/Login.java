import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;

public class Login {
	
	//��ȡ��¼����Ҫ�Ĺ�Կ����ȡ������е�¼����ļ���
	public  String get_public_key_url = "http://jwglxt.hbeutc.cn:20000/jwglxt/xtgl/login_getPublicKey.html?time=";

	//��¼url�����˺�������е�¼����¼�ɹ��󷵻�cookie���������������ӿ�
	public String login_url = "http://jwglxt.hbeutc.cn:20000/jwglxt/xtgl/login_slogin.html?time=";

	public static void main(String[] args) throws Exception {
		new Login().toLogin("518300284144", "dfrr1255688");
	}
	
	/**
	 * ��¼ϵͳ��ȡcookie����Ϊ������1����ȡ��Կ��2���ù�Կ���ܵ�¼���룬3��ȥ��¼
	 * @param account	�˺�
	 * @param password	����
	 * @return	cookie
	 * @throws Exception
	 */
	public String toLogin(String account, String password) throws Exception {
		System.out.println("��¼��...");
		//��������Ӻ������ʱ���
		get_public_key_url += (System.currentTimeMillis() / 1000);
		//get����ӿڻ�ȡ��Կ
		String result = HttpUtil.get(get_public_key_url);
		//�����ת����json����
		JSONObject jsonObject = new JSONObject(result);
		//��ȡ��Կ
		String modulus = jsonObject.getStr("modulus");
		String exponent = jsonObject.getStr("exponent");
		
		//System.out.println("��ȡ���Ĺ�ԿΪ��" + modulus);
		//�õ���Կ��������룬ȥ��¼��ȡcookie
		//���õ�¼����
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("language", "zh_CN");
		params.put("yhm", account);
		
		//��ȡjs�ļ�
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
		FileReader scriptFile = new FileReader("./js/login.js");
		engine.eval(scriptFile);
		Invocable in = (Invocable) engine;
		//����js�еļ��ܷ�����������
		String mm = in.invokeFunction("encryptionPwd", modulus, exponent, password).toString();
		//System.out.println("���ܺ������Ϊ��" + mm);
		
		params.put("mm", mm);
		
		//����¼���Ӻ������ʱ���
		login_url += (System.currentTimeMillis() / 1000);
		//����post�����¼
		HttpResponse httpResponse = HttpRequest.post(login_url).form(params).execute();
		//��ȡ��Ӧͷ�е�cookie
		//System.out.println(httpResponse.getCookieStr());
		String cookie = httpResponse.getCookieStr().split(";")[0];
		System.out.println("��¼�ɹ���ȡ����cookieΪ��" + cookie);
		return cookie;
	}
}
