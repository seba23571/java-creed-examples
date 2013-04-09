package test.javacreed.examples.db.tjt;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.javacreed.examples.db.tjt.TjtDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/META-INF/spring/test-context.xml" })
public class TjtDaoTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private TjtDao dao;

  @Before
  public void init() {
    jdbcTemplate.update("DELETE FROM t1");
    jdbcTemplate.update("DELETE FROM t2");
  }

  @Test
  public void test1() {
    dao.save("1");

    Assert.assertEquals("1", jdbcTemplate.queryForObject("SELECT * FROM t1", String.class));
    Assert.assertEquals(Integer.valueOf(1), jdbcTemplate.queryForObject("SELECT * FROM t2", Integer.class));
  }

  @Test
  public void test2() {
    try {
      dao.save("a");
      Assert.fail("This had to fail with a DataIntegrityViolationException");
    } catch (final DataIntegrityViolationException e) {
      // Ignore this error as this was meant to fail
    }
    Assert.assertEquals(Integer.valueOf(0), jdbcTemplate.queryForObject("SELECT COUNT(*) FROM t1", Integer.class));
    Assert.assertEquals(Integer.valueOf(0), jdbcTemplate.queryForObject("SELECT COUNT(*) FROM t2", Integer.class));
  }
}
