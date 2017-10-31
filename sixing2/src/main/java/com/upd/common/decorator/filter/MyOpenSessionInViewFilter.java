package com.upd.common.decorator.filter;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;

public class MyOpenSessionInViewFilter extends OpenSessionInViewFilter {
    @Override
    protected Session openSession(SessionFactory arg0)
            throws DataAccessResourceFailureException {
        Session session=super.openSession(arg0);
        session.setFlushMode(FlushMode.COMMIT);
        return session;
    }
}