package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.protobuf.TextFormat.ParseException;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;

public class TestRoleModel {

	public static void main(String[] args) {
//	testAdd();
//		testDelete();
		testUpdate();
//		testFindByPk();
//		testFindByName();
//			testSearch();
//		
		RoleModel model = new RoleModel();
		try {
			System.out.println(model.nextPk());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	public static void testAdd() {

		RoleBean bean = new RoleBean();
		bean.setName("kios");
		bean.setDescription("kiosk");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		RoleModel model = new RoleModel();
		try {
			model.add(bean);
			System.out.println("Role added successfully");

		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
	}

	public static void testDelete() {
		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();
		try {
			bean.setId(5);

			model.delete(bean);

			System.out.println("record deleted sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void testUpdate() {

		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		bean.setId(1);
		bean.setName("admin");
		bean.setDescription("kiosk");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		try {
			model.update(bean);
		} catch (ApplicationException | DuplicateRecordException e) {
			e.printStackTrace();
		}
		System.out.println("Recorded updated sucessfully");

	}

	public static void testFindByPk() {

		RoleModel model = new RoleModel();

		try {
			RoleBean bean = model.findByPk(2);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByName() {

		RoleModel model = new RoleModel();

		try {
			RoleBean bean = model.findByName("hr");
			if (bean == null) {
				System.out.println("Test Find By name fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getDescription());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testSearch() {

		try {
			RoleModel model = new RoleModel();
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			bean.setName("student");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}