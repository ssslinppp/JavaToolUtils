package com.ssslinppp.demo2;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.ssslinppp.demo1.Item;

/**
 * 大写转换器，在MyBeanSerializerModifier.java中进行注册
 */
public class UpperCasingWriter extends BeanPropertyWriter {
    BeanPropertyWriter _writer;

    public UpperCasingWriter(BeanPropertyWriter w) {
        super(w);
        _writer = w;
    }

    /**
     * 将 Item.itemName属性值，转化成大写输出
     *
     * @param bean
     * @param gen
     * @param prov
     * @throws Exception
     */
    @Override
    public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider prov) throws Exception {
        String value = ((Item) bean).getItemName();
        value = (value == null) ? "" : value.toUpperCase();
        gen.writeStringField("itemName", value);
    }
}
