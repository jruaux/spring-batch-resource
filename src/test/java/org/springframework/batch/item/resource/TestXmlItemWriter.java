package org.springframework.batch.item.resource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.redis.support.DataType;
import org.springframework.batch.item.redis.support.KeyValue;
import org.springframework.batch.item.xml.XmlResourceItemWriter;
import org.springframework.batch.item.xml.support.XmlResourceItemWriterBuilder;
import org.springframework.core.io.FileSystemResource;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class TestXmlItemWriter {

	@Test
	void test() throws Exception {
		Path directory = Files.createTempDirectory(TestXmlItemWriter.class.getName());
		Path file = directory.resolve("redis.xml");
		XmlMapper mapper = new XmlMapper();
		mapper.setConfig(mapper.getSerializationConfig().withRootName("record"));
		JacksonJsonObjectMarshaller<KeyValue<String>> marshaller = new JacksonJsonObjectMarshaller<>();
		marshaller.setObjectMapper(mapper);
		XmlResourceItemWriter<KeyValue<String>> writer = new XmlResourceItemWriterBuilder<KeyValue<String>>()
				.name("xml-resource-writer").resource(new FileSystemResource(file)).rootName("root")
				.xmlObjectMarshaller(marshaller).build();
		writer.afterPropertiesSet();
		writer.open(new ExecutionContext());
		KeyValue<String> item1 = new KeyValue<>();
		item1.setKey("key1");
		item1.setTtl(123);
		item1.setType(DataType.HASH);
		Map<String, String> hash1 = Map.of("field1", "value1", "field2", "value2");
		item1.setValue(hash1);
		KeyValue<String> item2 = new KeyValue<>();
		item2.setKey("key2");
		item2.setTtl(456);
		item2.setType(DataType.STREAM);
		Map<String, String> hash2 = Map.of("field1", "value1", "field2", "value2");
		item2.setValue(hash2);
		writer.write(Arrays.asList(item1, item2));
		writer.close();
		ObjectReader reader = mapper.readerFor(KeyValue.class);
		List<KeyValue<String>> keyValues = reader.<KeyValue<String>>readValues(file.toFile()).readAll();
		Assertions.assertEquals(2,  keyValues.size());
		Assertions.assertEquals(item1.getKey(), keyValues.get(0).getKey());
		Assertions.assertEquals(item2.getKey(), keyValues.get(1).getKey());
		Assertions.assertEquals(item1.getTtl(), keyValues.get(0).getTtl());
		Assertions.assertEquals(item2.getTtl(), keyValues.get(1).getTtl());
		Assertions.assertEquals(item1.getValue(), keyValues.get(0).getValue());
		Assertions.assertEquals(item2.getValue(), keyValues.get(1).getValue());
		
	}

}
