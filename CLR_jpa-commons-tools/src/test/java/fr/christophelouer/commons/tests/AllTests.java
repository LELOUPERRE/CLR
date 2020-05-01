package fr.christophelouer.commons.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * suite de tests unitaires.
 * 
 * @author CDT RBN
 *
 */

@RunWith(Suite.class)
@SuiteClasses({ BusinessBeanDaoTest.class, BusinessBeanSpecializedDaoTest.class, EntityFactoryTest.class })
public class AllTests {

}
