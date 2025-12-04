package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;

public class TestUserModel {

	public static void main(String[] args) {

//		testAdd();
//		testDelete();
//		testUpdate();
//		testFindByPk();
//		testAuthenticate();
		testSearch();

		UserModel model = new UserModel();

		try {
			System.out.println(model.nextPk());
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	public static void testAdd()  {

		UserBean bean = new UserBean();
		UserModel model = new UserModel();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {

		bean.setFirstName("dev");
		bean.setLastName("verma");
		bean.setLogin("dev@gmail.com");
		bean.setPassword("12345");
		bean.setDob(sdf.parse("2002-06-01"));
		bean.setMobileNo("9876543223");
		bean.setRoleId(1);
		bean.setGender("male");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

			model.add(bean);

			System.out.println("user added sucessfully");
		} catch (ApplicationException | DuplicateRecordException | ParseException e) {

			e.printStackTrace();
		}

	}

	public static void testDelete() {

		UserBean bean = new UserBean();
		UserModel model = new UserModel();

		bean.setId(1);

		try {
			model.delete(bean);

			System.out.println("record deleted sucessfully");
		} catch (ApplicationException e) {

			e.printStackTrace();
		}

	}

	public static void testUpdate()  {
		
		UserBean bean = new UserBean();
		UserModel model = new UserModel();
		try {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		bean.setId(2);
		bean.setFirstName("dev");
		bean.setLastName("verma");
		bean.setLogin("dev@gmail.com");
		bean.setPassword("12345");
		bean.setDob(sdf.parse("2002-06-01"));
		bean.setMobileNo("9876543223");
		bean.setRoleId(1);
		bean.setGender("male");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
					model.update(bean);

			System.out.println("record update sucessfully");
		} catch (DuplicateRecordException | ApplicationException |ParseException e) {

			e.printStackTrace();
		}
		
		}
		

	public static void testFindByPk() {

		UserModel model = new UserModel();

		try {
			UserBean bean = model.findByPk(2);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getGender());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			System.out.println(bean.getCreatedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByLogin() {

		UserModel model = new UserModel();

		try {
			UserBean bean = model.findByLogin("dev@gmail.com");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getGender());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			System.out.println(bean.getCreatedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testAuthenticate() {

		UserModel model = new UserModel();

		try {
			UserBean bean = model.authenticate("dev@gmail.com", "12345");
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}

			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getLogin());
			System.out.println(bean.getPassword());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getRoleId());
			System.out.println(bean.getGender());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());
			System.out.println(bean.getCreatedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testSearch() {
		UserModel model = new UserModel();
		try {
			UserBean bean = new UserBean();
			List list = new ArrayList();
			bean.setFirstName("dev");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (UserBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}
