package org.achareh.repository.impl;

import org.achareh.base.repository.impl.BaseRepositoryImpl;
import org.achareh.model.comment.Comment;
import org.achareh.repository.CommentRepository;
import org.hibernate.SessionFactory;

public class CommentRepositoryImpl extends BaseRepositoryImpl<Comment, Long>
        implements CommentRepository {
    public CommentRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }
}
