package fr.christophelouer.commons.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.christophelouer.commons.tests.dao.BusinessBeanSpecializedDao;
import fr.christophelouer.commons.tests.model.BusinessBean;
import fr.christophelouer.commons.tests.model.BusinessBeanSpecialized;

public class BusinessBeanSpecializedDaoTest
{

	private static BusinessBeanSpecializedDao facadeDataBean;

	@BeforeClass
	public static void init()
	{
		facadeDataBean = new BusinessBeanSpecializedDao();
	}

	@Test
	public void testCreate()
	{
		BusinessBeanSpecialized bean = facadeDataBean.newInstance();
		bean.setName("ETRS_CREATE");
		assertNotNull(bean);
		facadeDataBean.create(bean);
		assertTrue(!facadeDataBean.isEmpty());
	}

	@Test
	public void testRead()
	{
		BusinessBeanSpecialized bean = facadeDataBean.newInstance();
		bean.setName("ETRS_READ");
		assertNotNull(bean);
		facadeDataBean.create(bean);
		BusinessBean other = facadeDataBean.read(bean.getId());
		assertEquals(bean, other);
	}

	@Test
	public void testMultiUpdateAndVersion()
	{
		BusinessBeanSpecialized bean = facadeDataBean.newInstance();
		bean.setName("ETRS_READ");
		facadeDataBean.create(bean);
		assertNotNull(bean);
		bean.setName("ETRS_UPDATE_1");
		facadeDataBean.update(bean);
		bean.setName("ETRS_UPDATE_2");
		facadeDataBean.update(bean);
		BusinessBean other = facadeDataBean.read(bean.getId());

		assertEquals(other.getName(), bean.getName());
		assertEquals(new Long(3L), other.getVersion());
	}

	@Test
	public void testCreateMany()
	{
		facadeDataBean.beginTransaction();
		for (int i = 0; i < 1000; i++)
		{
			BusinessBeanSpecialized bean = facadeDataBean.newInstance();
			bean.setName("ETRS_CREATE_" + i);
			assertNotNull(bean);
			facadeDataBean.create(bean);
		}
		facadeDataBean.commitTransaction();
		assertTrue(!facadeDataBean.isEmpty());
	}

}
