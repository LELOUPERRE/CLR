package fr.christophelouer.commons.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.christophelouer.commons.tests.dao.BusinessBeanDao;
import fr.christophelouer.commons.tests.model.BusinessBean;

public class BusinessBeanDaoTest
{

	private static BusinessBeanDao facadeDataBean;

	@BeforeClass
	public static void init()
	{
		facadeDataBean = new BusinessBeanDao();
	}

	@Test
	public void testCreate()
	{
		BusinessBean bean = facadeDataBean.newInstance();
		bean.setName("ETRS_CREATE");
		assertNotNull(bean);
		facadeDataBean.create(bean);
		assertTrue(!facadeDataBean.isEmpty());
	}

	@Test
	public void testRead()
	{
		BusinessBean bean = facadeDataBean.newInstance();
		bean.setName("ETRS_READ");
		assertNotNull(bean);
		facadeDataBean.create(bean);
		BusinessBean other = facadeDataBean.read(bean.getId());
		assertEquals(bean, other);
	}

	@Test
	public void testMultiUpdateAndVersion()
	{
		BusinessBean bean = facadeDataBean.newInstance();
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
			BusinessBean bean = facadeDataBean.newInstance();
			bean.setName("ETRS_CREATE_" + i);
			assertNotNull(bean);
			facadeDataBean.create(bean);
		}
		facadeDataBean.commitTransaction();
		assertTrue(!facadeDataBean.isEmpty());
	}

}
