
public class MyCrawler {

	public static void main(String[] args) throws Exception {
		//ѧ��
		String account = "518300284144";
		//����
		String password = "dfrr1255688";
		
		String xm = "���";
		
		String path = "D:/myCourse/";
		
		//��¼ϵͳ����ȡcookie
		Login login = new Login();
		String cookie = login.toLogin(account, password);
		
		//��ȡ�α�
		CrawlerCourse crawlerCourse = new CrawlerCourse();
		crawlerCourse.exportCoursePdf(cookie, account, "2020", "3", "2020-2021", "1", xm, path);
		
		//��ȡ�ɼ�
		CrawlerGrade crawlerGrade = new CrawlerGrade();
		crawlerGrade.exportGradeExcel(cookie, account, "2020", "3", xm, path);
		
		//��ȡ���н���
		CrawlerFreeClassroom crawlerFreeClassroom = new CrawlerFreeClassroom();
		crawlerFreeClassroom.exportFreeClassroomExcel(cookie, "2021", "3", "12", "3,4", "7,8,9", path);
	}
}
