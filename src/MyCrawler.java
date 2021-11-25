
public class MyCrawler {

	public static void main(String[] args) throws Exception {
		//学号
		String account = "518300284144";
		//密码
		String password = "dfrr1255688";
		
		String xm = "甄聪";
		
		String path = "D:/myCourse/";
		
		//登录系统，获取cookie
		Login login = new Login();
		String cookie = login.toLogin(account, password);
		
		//爬取课表
		CrawlerCourse crawlerCourse = new CrawlerCourse();
		crawlerCourse.exportCoursePdf(cookie, account, "2020", "3", "2020-2021", "1", xm, path);
		
		//爬取成绩
		CrawlerGrade crawlerGrade = new CrawlerGrade();
		crawlerGrade.exportGradeExcel(cookie, account, "2020", "3", xm, path);
		
		//爬取空闲教室
		CrawlerFreeClassroom crawlerFreeClassroom = new CrawlerFreeClassroom();
		crawlerFreeClassroom.exportFreeClassroomExcel(cookie, "2021", "3", "12", "3,4", "7,8,9", path);
	}
}
