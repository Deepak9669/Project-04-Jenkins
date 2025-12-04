package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;

public class TestCollegeModel {

	public static void main(String[] args)  {

//		testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByName();
		testSearch();

//		CollegeModel model = new CollegeModel();
//
//		try {
//			System.out.println(model.nextPk());
//		} catch (DatabaseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	public static void testAdd() {

		CollegeModel model = new CollegeModel();
		CollegeBean bean = new CollegeBean();

		bean.setName("IPS");
		bean.setAddress("M.Y");
		bean.setState("MP");
		bean.setCity("Indore");
		bean.setPhoneNo("987654323");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {

			model.add(bean);
			System.out.println("Role added sucessfully");
		} catch (ApplicationException | DuplicateRecordException e) {

			e.printStackTrace();

		}

	}

	public static void testUpdate() {

		CollegeModel model = new CollegeModel();
		CollegeBean bean = new CollegeBean();
		bean.setId(1);
		bean.setName("dev");
		bean.setAddress("M.Y");
		bean.setState("MP");
		bean.setCity("Indore");
		bean.setPhoneNo("987654323");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		try {

			model.update(bean);
			System.out.println("Role Updated sucessfully");
		} catch (ApplicationException | DuplicateRecordException e) {

			e.printStackTrace();

		}

	}

	public static void testDelete() {
		CollegeBean bean = new CollegeBean();
		CollegeModel model = new CollegeModel();
		try {
			bean.setId(2);

			model.delete(bean);

			System.out.println("record deleted sucessfully");
		} catch (Exception e) {

		}

	}

	public static void testFindByPk() {

		CollegeModel model = new CollegeModel();

		try {
			CollegeBean bean = model.findByPk(1);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			System.out.println(bean.getCreatedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByName() {

		CollegeModel model = new CollegeModel();

		try {
			CollegeBean bean = model.findByName("Deepak");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getState());
			System.out.println(bean.getCity());
			System.out.println(bean.getPhoneNo());
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
			CollegeModel model = new CollegeModel();
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();

			bean.setName("dev");

			list = model.search(bean, 0, 0);

			if (list.size() < 0) {
				System.out.println("Test search fail");

			}
			Iterator it = list.iterator();

			while (it.hasNext()) {
				bean = (CollegeBean) it.next();

				System.out.println(bean.getId());
				System.out.println(bean.getCity());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("record search sucessFully");

		}

	}

}
