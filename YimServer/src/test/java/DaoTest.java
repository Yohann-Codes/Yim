import dao.GroupDao;
import dao.OfflineMsgGroupDao;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yohann on 2017/1/19.
 */
public class DaoTest {
    @Test
    public void insertGroup() {
        GroupDao groupDao = null;
        try {
            groupDao = new GroupDao();
            int r = groupDao.insertGroup("小分队", "yohann");
            System.out.println(r);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            groupDao.close();
        }
    }

    @Test
    public void removeGroup() {
        GroupDao groupDao = null;
        try {
            groupDao = new GroupDao();
            int r = groupDao.removeGroup("小分队");
            System.out.println(r);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            groupDao.close();
        }
    }

    @Test
    public void queryMemberByGroupName() {
        GroupDao groupDao = null;
        try {
            groupDao = new GroupDao();
            List<String> members = groupDao.queryMemberByGroupName("小分队");
            System.out.println(members);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            groupDao.close();
        }
    }

    @Test
    public void queryNoMemColumn() {
        GroupDao groupDao = null;
        try {
            groupDao = new GroupDao();
            String column = groupDao.queryNoMemColumn("小分队");
            System.out.println(column);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            groupDao.close();
        }
    }

    @Test
    public void insertMember() {
        GroupDao groupDao = null;
        try {
            groupDao = new GroupDao();
            String column = groupDao.queryNoMemColumn("小分队");
            System.out.println(column);
            int r = groupDao.insertMember("小分队", "albert", column);
            System.out.println(r);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            groupDao.close();
        }
    }

    @Test
    public void reomoveMember() {
        GroupDao groupDao = null;
        try {
            groupDao = new GroupDao();
            String column = groupDao.queryColumnByMem("group1", "yanghuan");
            System.out.println(column);
            int r = groupDao.removeMember("group1", column);
            System.out.println(r);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            groupDao.close();
        }
    }

    @Test
    public void queryAll() {
        GroupDao groupDao = null;
        try {
            groupDao = new GroupDao();
            Map<String, List<String>> groups = groupDao.queryAllbyMember("yanghuan");
            Set<Map.Entry<String, List<String>>> entries = groups.entrySet();
            Iterator<Map.Entry<String, List<String>>> ite = entries.iterator();
            while (ite.hasNext()) {
                Map.Entry<String, List<String>> group = ite.next();
                String groupName = group.getKey();
                List<String> members = group.getValue();
                System.out.println(groupName + " " + members);
            }

            System.out.println(groups.size());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            groupDao.close();
        }
    }
}
