package com.gam0zing.newnew_origins.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Map;

/// 这个类存储了所有json的数据格式
/// 用于Provider中进行对象创建
public class DataSources {

    public record TranslateData(
            String key,
            String value
    ) {}

    public record ID_Holder<T>(
            String id,
            T object
    ) {}

    /// 每个记录类的CODEC，用于读写JSON
    public static class Codecs {

        public static final Codec<TranslateData> CODEC_TRANSLATE = Codec.unboundedMap(Codec.STRING, Codec.STRING)
                .comapFlatMap(
                        map -> {
                            if (map.size() == 1) {
                                Map.Entry<String, String> entry = map.entrySet().iterator().next();
                                TranslateData data = new TranslateData(entry.getKey(), entry.getValue());
                                return DataResult.success(data);
                            } else {
                                return DataResult.error(() -> "Expected exactly one key-value pair");
                            }
                        },
                        translateData -> Map.of(translateData.key(), translateData.value())
                );
    }
}