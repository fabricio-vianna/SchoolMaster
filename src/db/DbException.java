package db;

import java.io.Serial;

public class DbException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5944821997618492515L;

    public DbException(String msg) {
        super(msg);
    }
}
