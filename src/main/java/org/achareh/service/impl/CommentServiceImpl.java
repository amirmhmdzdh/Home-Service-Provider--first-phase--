package org.achareh.service.impl;

import org.achareh.base.service.impl.BaseServiceImpl;
import org.achareh.model.comment.Comment;
import org.achareh.repository.CommentRepository;
import org.achareh.service.CommentService;
import org.hibernate.SessionFactory;

public class CommentServiceImpl
        extends BaseServiceImpl<Comment, Long, CommentRepository>
        implements CommentService {
    public CommentServiceImpl(CommentRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}
