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
	
	//获取登录所需要的公钥，获取到后进行登录密码的加密
	public  String get_public_key_url = "http://jwglxt.hbeutc.cn:20000/jwglxt/xtgl/login_getPublicKey.html?time=";

	//登录url，用账号密码进行登录，登录成功后返回cookie，用于请求其他接口
	public String login_url = "http://jwglxt.hbeutc.cn:20000/jwglxt/xtgl/login_slogin.html?time=";

	public static void main(String[] args) throws Exception {
		new Login().toLogin("518300284144", "dfrr1255688");
	}
	
	/**
	 * 登录系统获取cookie，分为三步，1、获取公钥，2、拿公钥加密登录密码，3、去登录
	 * @param account	账号
	 * @param password	密码
	 * @return	cookie
	 * @throws Exception
	 */
	public String toLogin(String account, String password) throws Exception {
		System.out.println("登录中...");
		//给这个链接后面加上时间戳
		get_public_key_url += (System.currentTimeMillis() / 1000);
		//get请求接口获取公钥
		String result = HttpUtil.get(get_public_key_url);
		//将结果转换成json对象
		JSONObject jsonObject = new JSONObject(result);
		//获取公钥
		String modulus = jsonObject.getStr("modulus");
		String exponent = jsonObject.getStr("exponent");
		
		//System.out.println("获取到的公钥为：" + modulus);
		//拿到公钥后加密密码，去登录获取cookie
		//设置登录参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("language", "zh_CN");
		params.put("yhm", account);
		
		//读取js文件
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
		FileReader scriptFile = new FileReader("./js/login.js");
		engine.eval(scriptFile);
		Invocable in = (Invocable) engine;
		//调用js中的加密方法加密密码
		String mm = in.invokeFunction("encryptionPwd", modulus, exponent, password).toString();
		//System.out.println("加密后的密码为：" + mm);
		
		params.put("mm", mm);
		
		//给登录链接后面加上时间戳
		login_url += (System.currentTimeMillis() / 1000);
		//发送post请求登录
		HttpResponse httpResponse = HttpRequest.post(login_url).form(params).execute();
		//获取响应头中的cookie
		//System.out.println(httpResponse.getCookieStr());
		String cookie = httpResponse.getCookieStr().split(";")[0];
		System.out.println("登录成功获取到的cookie为：" + cookie);
		return cookie;
	}
}
