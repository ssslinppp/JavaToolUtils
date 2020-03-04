package com.ssslinppp.test;

import com.google.common.reflect.TypeToken;
import com.ssslinppp.model.Person;

public class Test {
    class MyFactory<Q> {
        TypeToken<Q> type = new TypeToken<Q>(getClass()) {
        };

        protected TypeToken<Q> getTypeToken() {
            final TypeToken<Q> typeToken = new TypeToken<Q>(getClass()) {
            };
            return typeToken;
        }

        protected Class<Q> getParameterType() {
            final TypeToken<Q> typeToken = new TypeToken<Q>(getClass()) {
            };
            final Class<Q> type = (Class<Q>) typeToken.getRawType();
            return type;
        }
    }

    @org.junit.Test
    public void vvv() {
        MyFactory<Person> factory = new MyFactory<Person>() {        };
//        MyFactory<Person> factory = new MyFactory<Person>();
        System.out.println(factory.getParameterType());
        System.out.println(factory.getTypeToken());

    }



}
