package com.gam0zing.newnew_origins.util;

import net.minecraft.nbt.CompoundTag;

public class TagProvider {

    private TagProvider() {
    }

    public static class Builder {

        private final CompoundTag tag;

        private Builder() {
            tag = new CompoundTag();
        }
        public Builder putInt(String key, int value) {
            tag.putInt(key, value);
            return this;
        }
        public Builder putString(String key, String value) {
            tag.putString(key, value);
            return this;
        }
        public Builder putIntArray(String key, int[] value) {
            tag.putIntArray(key, value);
            return this;
        }
        public Builder putArray(String key, float value) {
            tag.putFloat(key, value);
            return this;
        }
        public Builder putDouble(String key, double value) {
            tag.putDouble(key, value);
            return this;
        }
        public Builder putTag(String key, CompoundTag value) {
            tag.put(key, value);
            return this;
        }
        public Builder merge(CompoundTag other) {
            tag.merge(other);
            return this;
        }
        public CompoundTag build() {
            return tag;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
