package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.User1Bean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.model.User1Model;
import in.co.rays.proj4.model.UserModel;

public class TestUser1model {

	public static void main(String[] args) {

//	testAdd();
//		testUpdate();
//		testDelete();
//		testFindByPk();
//		testFindByLogin();
	testSearch();

		User1Model model = new User1Model();

		try {
			System.out.println(model.nextPk());
			System.out.println("Primary key generated sucessfully");
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

	}

	public static void testAdd() {

		User1Model model = new User1Model();
		User1Bean bean = new User1Bean();

		bean.setName("devil");
		bean.setEmail("dev1234@gmail.com");
		bean.setPhoneno("9876543212");
		bean.setAddress("indore");
		bean.setStatus(1);
		bean.setGender("male");
		bean.setCreatedBy("user");
		bean.setModifiedBy("user");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		try {
			model.add(bean);
			System.out.println("user added sucesssfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testUpdate() {

		User1Model model = new User1Model();
		User1Bean bean = new User1Bean();

		bean.setId(1);
		bean.setName("dev verma");
		bean.setEmail("dev@gmail.com");
		bean.setPhoneno("9876543212");
		bean.setAddress("indore");
		bean.setStatus(1);
		bean.setGender("male");
		bean.setCreatedBy("user");
		bean.setModifiedBy("user");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));

		try {
			model.update(bean);
			System.out.println("user update sucesssfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testDelete() {

		User1Model model = new User1Model();
		User1Bean bean = new User1Bean();

		bean.setId(1);

		try {
			model.delete(bean);
			System.out.println("users deleted sucessfully");
		} catch (ApplicationException e) {
			e.printStackTrace();
		}

	}

	public static void testFindByPk() {

		User1Model model = new User1Model();

		try {
			User1Bean bean = model.findByPk(2);
			
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
            System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getPhoneno());
			System.out.println(bean.getAddress());
			System.out.println(bean.getStatus());
			System.out.println(bean.getGender());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	public static void testFindByLogin() {

		User1Model model = new User1Model();

		try {
			User1Bean bean = model.findByLogin("dev1@gmail.com");
			
			if (bean == null) {
				System.out.println("Test Find By login fail");
			}
            System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getPhoneno());
			System.out.println(bean.getAddress());
			System.out.println(bean.getStatus());
			System.out.println(bean.getGender());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getCreatedDatetime());
			System.out.println(bean.getModifiedDatetime());

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	public static void testSearch() {
		User1Model model = new User1Model();
		try {
			User1Bean bean = new User1Bean();
			List list = new ArrayList();
			bean.setName("dev");
			list = model.search(bean, 0, 0);
			if (list.size() < 0) {
				System.out.println("Test Serach fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (User1Bean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}


