package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.StudentModel;

public class TestMarksheetModel {

	public static void main(String[] args)  {

//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByRollNo();
		testSearch();

		MarksheetModel model = new MarksheetModel();

		try {
			System.out.println(model.nextPk());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}

	public static void testAdd() {

		MarksheetBean bean = new MarksheetBean();
		bean.setStudentId(2);
		bean.setRollNo("23457");
		bean.setChemistry(67);
		bean.setPhysics(68);
		bean.setMaths(75);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		MarksheetModel model = new MarksheetModel();
		try {
			model.add(bean);
			System.out.println("Marksheet added successfully");

		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testUpdate() {

		MarksheetBean bean = new MarksheetBean();
		bean.setId(1);
		bean.setStudentId(2);
		bean.setRollNo("23451");
		bean.setChemistry(67);
		bean.setPhysics(68);
		bean.setMaths(75);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		MarksheetModel model = new MarksheetModel();
		try {
			model.update(bean);
			System.out.println("Marksheet Update successfully");

		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		MarksheetBean bean = new MarksheetBean();
		MarksheetModel model = new MarksheetModel();

		bean.setId(2);

		try {
			model.delete(bean);
			System.out.println("marksheet deteted sucessfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByPk() {

		MarksheetModel model = new MarksheetModel();

		try {
			MarksheetBean bean = model.findByPk(1);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getStudentId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByRollNo() {

		MarksheetModel model = new MarksheetModel();

		try {
			MarksheetBean bean = model.findByRollNo("23451");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getStudentId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getMaths());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}
public static void testSearch() {
		
		try {
			MarksheetModel model = new MarksheetModel ();
			MarksheetBean bean = new MarksheetBean ();
			List list= new ArrayList();
			bean.setName("dev");
			list =  model.search(bean, 0, 0);
			if (list.size() <0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}


