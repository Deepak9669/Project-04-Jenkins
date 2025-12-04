package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.model.CourseModel;

public class TestCourseModel {

	public static void main(String[] args)  {

//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByName();
		testSearch();

		CourseModel model = new CourseModel();
		try {
			System.out.println(model.nextPk());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	public static void testAdd() {

		CourseModel model = new CourseModel();
		CourseBean bean = new CourseBean();

		bean.setName("B.sc");
		bean.setDuration("45 m");
		bean.setDescription("sumray");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			model.add(bean);
			System.out.println("Course added sucessfully");
		} catch (Exception e) {
		}
	}

	public static void testUpdate() {

		CourseModel model = new CourseModel();
		CourseBean bean = new CourseBean();
		bean.setId(3);
		bean.setName("B.sc.n");
		bean.setDuration("45 m");
		bean.setDescription("sumray");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {
			model.update(bean);
			System.out.println("Course update sucessfully");
		} catch (Exception e) {
		}
	}
	public static void testDelete() {

		CourseBean bean = new CourseBean();
		CourseModel model = new CourseModel();

		bean.setId(2);

		try {
			model.delete(bean);
			System.out.println("course deleted sucessfully");
		} catch (ApplicationException e) {

			e.printStackTrace();
		}


	}
	public static void testFindByPk() {

		CourseModel model = new CourseModel();

		try {
			CourseBean bean = model.findByPk(1);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDuration());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
	public static void testFindByName() {

		CourseModel model = new CourseModel();

		try {
			CourseBean bean = model.findByName("B.A");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDuration());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}


public static void testSearch() {

	try {
		CourseModel model = new CourseModel();
		CourseBean bean = new CourseBean();
		List list = new ArrayList();
		bean.setId(1);
		list = model.search(bean, 0, 0);
		if (list.size() < 0) {
			System.out.println("Test Serach fail");
		}
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (CourseBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDuration());
			System.out.println(bean.getDescription());
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




