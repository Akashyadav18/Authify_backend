package com.Security.Authify.io;

import java.util.List;

public record cursorPageResponse<T> (
    List<T> data,
    int pageSize,
    Long nextCursor,
    boolean hasNext
) {}
