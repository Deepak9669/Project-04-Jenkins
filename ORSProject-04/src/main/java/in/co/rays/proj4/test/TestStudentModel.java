package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.StudentModel;

public class TestStudentModel {
	public static void main(String[] args)  {

//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByEmail();
		testSearch();

		StudentModel model = new StudentModel();
		try {
			System.out.println(model.nextPk());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	public static void testAdd() {

		StudentBean bean = new StudentBean();
		StudentModel model = new StudentModel();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bean.setFirstName("dev");
			bean.setLastName("verma");
			bean.setDob(sdf.parse("2002-06-01"));
			bean.setGender("male");
			bean.setMobileNo("9669726800");
			bean.setEmail("dev123@gmail.com");
			bean.setCollegeId(1);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			model.add(bean);
			System.out.println("user added sucessfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testUpdate() {

		try {
			StudentBean bean = new StudentBean();
			StudentModel model = new StudentModel();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			bean.setId(2);
			bean.setFirstName("deepak");
			bean.setLastName("verma");
			bean.setDob(sdf.parse("2002-06-01"));
			bean.setGender("male");
			bean.setMobileNo("9669726800");
			bean.setEmail("dev@gmail.com");
			bean.setCollegeId(1);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			model.update(bean);
			System.out.println("user update sucessfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testDelete() {

		StudentBean bean = new StudentBean();
		StudentModel model = new StudentModel();

		bean.setId(2);

		try {
			model.delete(bean);
			System.out.println("student deleted sucessfully");
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testFindByPk() {

		StudentModel model = new StudentModel();

		try {
			StudentBean bean = model.findByPk(1);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getGender());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByEmail() {

		StudentModel model = new StudentModel();

		try {
			StudentBean bean = model.findByEmailId("dev@gmail.com");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getGender());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testSearch() {

		try {
			StudentModel model = new StudentModel();
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			bean.setFirstName("deepak");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getGender());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmail());
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
