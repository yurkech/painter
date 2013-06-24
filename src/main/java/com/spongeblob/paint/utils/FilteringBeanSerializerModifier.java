package com.spongeblob.paint.utils;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.ser.BeanPropertyWriter;
import org.codehaus.jackson.map.ser.BeanSerializerModifier;

import java.util.List;

public final class FilteringBeanSerializerModifier
    extends BeanSerializerModifier
{
    @SuppressWarnings("rawtypes")
	private final ImmutableMultimap<Class, String> filters;

    @SuppressWarnings("rawtypes")
	static FilteringBeanSerializerModifier excluding(ImmutableMultimap<Class, String> filters)
    {
        return new FilteringBeanSerializerModifier(filters);
    }

    @SuppressWarnings("rawtypes")
	private FilteringBeanSerializerModifier(ImmutableMultimap<Class, String> filters)
    {
        this.filters = filters;
    }

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config,
                                                     BasicBeanDescription beanDesc,
                                                     List<BeanPropertyWriter> beanProperties)
    {
        if (filters == null || filters.size() == 0) return beanProperties;

        ImmutableCollection<String> filter = filters.get(beanDesc.getBeanClass());
        if (filter == null) return beanProperties;

        List<BeanPropertyWriter> included = Lists.newArrayList();
        for (BeanPropertyWriter property : beanProperties)
            if (!filter.contains(property.getName()))
                included.add(property);

        return included;
    }
}