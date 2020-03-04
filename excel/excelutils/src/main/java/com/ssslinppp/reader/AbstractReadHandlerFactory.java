package com.ssslinppp.reader;

import com.google.common.reflect.TypeToken;

public abstract class AbstractReadHandlerFactory<T> {

    public <T> IExcelReadHandler createExcelReadHandler() {
        return new ExcelReadHandler<T>((Class<T>) getParameterType());
    }

    /**
     * 使用泛型时，会出现类型擦除，通过TypeToken来获取泛型类型
     *
     * @return
     */
    private Class<T> getParameterType() {
        final TypeToken<T> typeToken = new TypeToken<T>(getClass()) {
        };
        final Class<T> type = (Class<T>) typeToken.getRawType();
        return type;
    }
}
