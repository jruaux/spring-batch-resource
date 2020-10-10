package org.springframework.batch.item.redis.support;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class KeyValue<K> extends AbstractKeyValue<K, Object> {

	private DataType type;

}
