package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.model.TimetableModel;
import in.co.rays.proj4.model.UserModel;

public class TestTimetableModel {

	public static void main(String[] args)  {
		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
		testcheckByCourseName();
//		testcheckBySubjectName();
//		testcheckBySemester();
//		testcheckByExamTime();
		testSearch();

//		TimetableModel model=new TimetableModel();
//		try {
//			System.out.println(model.nextPk());
//		} catch (DatabaseException e) {
//			e.printStackTrace();
//		}
	}

	public static void testAdd()  {

		TimetableBean bean = new TimetableBean();
		TimetableModel model = new TimetableModel();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {

			bean.setSemester("second");
			bean.setDescription("summary");
			bean.setExamDate(sdf.parse("2025-10-12"));
			bean.setExamTime("9Am to 1PM");
			bean.setSubjectId(2);
			bean.setCourseId(1);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			model.add(bean);
			System.out.println("TimeTable update sucessfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testUpdate()  {

		TimetableBean bean = new TimetableBean();
		TimetableModel model = new TimetableModel();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			bean.setId(3);
			bean.setSemester("second");
			bean.setDescription("summary");
			bean.setExamDate(sdf.parse("2025-10-14"));
			bean.setExamTime("9Am to 1PM");
			bean.setSubjectId(2);
			;
			bean.setCourseId(1);
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
			bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			model.update(bean);
			System.out.println("TimeTable added sucessfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testDelete() {

		TimetableBean bean = new TimetableBean();
		TimetableModel model = new TimetableModel();

		bean.setId(2);

		try {
			model.delete(bean);

			System.out.println("record deleted sucessfully");
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testFindByPk() {

		try {
			TimetableModel model = new TimetableModel();
			TimetableBean bean = model.findByPk(3);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getSemester());
			System.out.println(bean.getDescription());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			System.out.println(bean.getCreatedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testcheckByCourseName() {

		try {
			TimetableModel model = new TimetableModel();
	TimetableBean bean = new TimetableBean ();
			java.sql.Date examDate = java.sql.Date.valueOf("2025-10-12");
			 bean = model.checkByCourseName(1L, examDate);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getSemester());
			System.out.println(bean.getDescription());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			System.out.println(bean.getCreatedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testcheckBySubjectName() {

		try {
			TimetableModel model = new TimetableModel();
			java.sql.Date examDate = java.sql.Date.valueOf("2025-10-14");
			TimetableBean bean = model.checkBySubjectName(1L, 2L, examDate);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getSemester());
			System.out.println(bean.getDescription());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			System.out.println(bean.getCreatedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testcheckBySemester() {

		try {
			TimetableModel model = new TimetableModel();
			java.sql.Date examDate = java.sql.Date.valueOf("2025-10-14");
			TimetableBean bean = model.checkBySemester(1L, 2L, "second", examDate);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getSemester());
			System.out.println(bean.getDescription());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			System.out.println(bean.getCreatedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testcheckByExamTime() {

		try {
			TimetableModel model = new TimetableModel();
			java.sql.Date examDate = java.sql.Date.valueOf("2025-10-14");
			String examTime = "9AM to 1PM";

			TimetableBean bean = model.checkByExamTime(1L, 2L, "second", examDate, examTime, "summary");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getSemester());
			System.out.println(bean.getDescription());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			System.out.println(bean.getCreatedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	public static void testSearch() {

		try {
			TimetableModel model = new TimetableModel();
			TimetableBean bean = new TimetableBean();
			List list = new ArrayList();
			bean.setId(1);
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (TimetableBean) it.next();

				System.out.println(bean.getId());
				System.out.println(bean.getSemester());
				System.out.println(bean.getDescription());
				System.out.println(bean.getExamTime());
				System.out.println(bean.getExamDate());
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
