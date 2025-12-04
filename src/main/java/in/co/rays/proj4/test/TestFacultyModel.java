package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.FacultyModel;
import in.co.rays.proj4.model.TimetableModel;

public class TestFacultyModel {

	public static void main(String[] args) {

//		testAdd();
		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByEmail();
//		testSearch();

		FacultyModel model = new FacultyModel();

		try {
			System.out.println(model.nextPk());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public static void testAdd() {

		FacultyBean bean = new FacultyBean();
		FacultyModel model = new FacultyModel();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {

			bean.setFirstName("Virat");
			bean.setLastName("yadav");
			bean.setDob(sdf.parse("2002-06-16"));
			bean.setGender("male");
			bean.setMobileNo("9669726800");
			bean.setEmail("ravi123@gmail.com");
			bean.setCollegeId(1);
			bean.setSubjectId(1);
			bean.setCourseId(1);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			model.add(bean);
			System.out.println("Faculty added sucessfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			e.printStackTrace();
		}

	}

	public static void testUpdate() {

		FacultyModel model = new FacultyModel();
		FacultyBean bean = new FacultyBean();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			bean.setId(3L);
			bean.setFirstName("rohit");
			bean.setLastName("yadav");
			bean.setDob(sdf.parse("2002-06-16"));
			bean.setGender("male");
			bean.setMobileNo("9669726800");
			bean.setEmail("ravi123@gmail.com");
			bean.setCollegeId(1);
			bean.setSubjectId(1);
			bean.setCourseId(1);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			model.update(bean);
			System.out.println("Faculty update sucessfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testDelete() {

		FacultyBean bean = new FacultyBean();
		FacultyModel model = new FacultyModel();

		bean.setId(2);

		try {
			model.delete(bean);
			System.out.println("faculty deleted sucessFully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByPk() {

		try {
			FacultyModel model = new FacultyModel();

			FacultyBean bean = model.findByPk(1);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());

		} catch (ApplicationException e) {

			e.printStackTrace();

		}
	}
	public static void testFindByEmail() {

		try {
			FacultyModel model = new FacultyModel();

			FacultyBean bean = model.findByEmail("raj@gmail.com");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());

		} catch (ApplicationException e) {

			e.printStackTrace();

		}
	}
	public static void testSearch() {

		try {
			FacultyModel model = new FacultyModel();
			FacultyBean bean = new FacultyBean();
			List list = new ArrayList();
			bean.setId(1);
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (FacultyBean) it.next();

				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getCreatedBy());
				System.out.println(bean.getModifiedBy());
				System.out.println(bean.getCreatedDatetime());
				System.out.println(bean.getModifiedDatetime());

			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

}



