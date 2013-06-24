package com.spongeblob.paint.utils;

import com.google.common.collect.ImmutableMultimap;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.Module;

public final class PropertyFilteringModule extends Module {
    private static final Version MODULE_VERSION = new Version(1, 0, 0, null);
    private final String moduleName;
    @SuppressWarnings("rawtypes")
	private final ImmutableMultimap<Class, String> filters;

    public static Builder builder(String moduleName) {
        return new Builder(moduleName);
    }

    public static final class Builder {
        private final String moduleName;
        @SuppressWarnings("rawtypes")
		private final ImmutableMultimap.Builder<Class, String> filterBuilder = new ImmutableMultimap.Builder<Class, String>();

        private Builder(String moduleName) {
            this.moduleName = moduleName;
        }

        @SuppressWarnings("rawtypes")
		public Builder exclude(Class beanClass, String name) {
            filterBuilder.put(beanClass, name);
            return this;
        }

        public PropertyFilteringModule build() {
            return new PropertyFilteringModule(this);
        }
    }

    private PropertyFilteringModule(Builder builder) {
        moduleName = builder.moduleName;
        filters = builder.filterBuilder.build();
    }

    @Override
    public String getModuleName() {
        return moduleName;
    }

    @Override
    public Version version() {
        return MODULE_VERSION;
    }

    @Override
    public void setupModule(Module.SetupContext context) {
        context.addBeanSerializerModifier(
                FilteringBeanSerializerModifier.excluding(filters));
    }
}
