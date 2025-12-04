package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.model.SubjectModel;

public class testSubjectModel {

	public static void main(String[] args) {
//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByName();
		testSearch();

		SubjectModel model = new SubjectModel();

		try {
			System.out.println(model.nextPk());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public static void testAdd() {

		SubjectBean bean = new SubjectBean();
		SubjectModel model = new SubjectModel();

		try {
			bean.setName("devil");
			bean.setCourseId(3);
			bean.setDescription("summary");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			model.add(bean);
			System.out.println("subject added sucessfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testUpdate() {

		SubjectBean bean = new SubjectBean();
		SubjectModel model = new SubjectModel();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bean.setName("deepak");
			bean.setCourseId(3);
			bean.setDescription("summary");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			model.update(bean);
			System.out.println("user update sucessfully");
		} catch (ApplicationException | DuplicateRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testDelete() {

		SubjectBean bean = new SubjectBean();
		SubjectModel model = new SubjectModel();

		bean.setId(3);

		try {
			model.delete(bean);
			System.out.println("subject deleted sucessfully");
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testFindByPk() {

		SubjectModel model = new SubjectModel();

		try {
			SubjectBean bean = model.findByPk(1);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
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

		SubjectModel model = new SubjectModel();

		try {
			SubjectBean bean = model.findByName("dev");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
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
			SubjectModel model = new SubjectModel();
			SubjectBean bean = new SubjectBean();
			List list = new ArrayList();
			bean.setId(1);
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (SubjectBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
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




