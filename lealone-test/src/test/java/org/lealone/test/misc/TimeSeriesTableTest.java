/*
 * Copyright Lealone Database Group.
 * Licensed under the Server Side Public License, v 1.
 * Initial Developer: zhh
 */
package org.lealone.test.misc;

import org.junit.Test;
import org.lealone.db.RunMode;
import org.lealone.test.sql.SqlTestBase;

public class TimeSeriesTableTest extends SqlTestBase {

    private static final String TS_DB_NAME = "TimeSeriesTableTestDB";

    public TimeSeriesTableTest() {
        super(TS_DB_NAME, RunMode.SHARDING);
    }

    @Test
    public void run() throws Exception {
        stmt.executeUpdate("DROP TABLE IF EXISTS TimeSeriesTableTest");
        sql = "CREATE TABLE IF NOT EXISTS TimeSeriesTableTest "
                + "(id long AUTO_INCREMENT PRIMARY KEY, dt datetime, INDEX (dt), f int)";
        stmt.executeUpdate(sql);

        Thread t1 = new Thread(new CrudTest());
        Thread t2 = new Thread(new CrudTest());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private static class CrudTest extends SqlTestBase implements Runnable {
        public CrudTest() {
            super(TS_DB_NAME);
        }

        @Override
        protected void test() throws Exception {
            for (int i = 0; i < 100; i++) {
                stmt.executeUpdate("insert into TimeSeriesTableTest(dt, f) values(CURRENT_TIMESTAMP()," + i + ")");
            }
        }

        @Override
        public void run() {
            runTest();
        }
    }

}
