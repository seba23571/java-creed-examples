package com.javacreed.examples.spring.hiwj;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/test-ceh-context.xml" })
public class ThreadInterruptedTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Before
  public void dropAndCreateTable() {
    dropTable();
    jdbcTemplate.execute("CREATE TABLE `test_table` (`name` VARCHAR(128) NOT NULL)");
  }

  @After
  public void dropTable() {
    jdbcTemplate.execute("DROP TABLE IF EXISTS `test_table`");
  }

  @Test
  public void test() {
    Thread.currentThread().interrupt();
    try {
      jdbcTemplate.update("INSERT INTO `test_table` (`name`) VALUES (?)", "Hello World.");
      Assert.fail("The operation should have failed as the current thread was interrupted");
    } catch (final Exception e) {
      Assert.assertTrue(Thread.currentThread().isInterrupted());
    }
  }
}
