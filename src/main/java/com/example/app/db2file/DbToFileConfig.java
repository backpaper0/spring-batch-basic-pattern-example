package com.example.app.db2file;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import com.example.gen.mapper.AddressMapper;
import com.example.gen.model.Address;

@Configuration
public class DbToFileConfig {

	@Autowired
	private JobBuilderFactory jobs;
	@Autowired
	private StepBuilderFactory steps;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;

	@Bean
	@StepScope
	public MyBatisCursorItemReader<Address> addressDbReader() {
		return new MyBatisCursorItemReaderBuilder<Address>()
				.sqlSessionFactory(sqlSessionFactory)
				.queryId(AddressMapper.class.getName() + ".selectByExample")
				.parameterValues(Map.of("oredCriteria", List.of()))
				.build();
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<Address> addressFileWriter(@Value("#{jobParameters['input.file']}") String file) {

		//TODO ファイルパスのバリデーション、ファイルの存在チェックなど

		PathResource resource = new PathResource(file);

		return new FlatFileItemWriterBuilder<Address>()
				.name("fileWriter")
				.resource(resource)
				.encoding("UTF-8")
				.delimited()
				.names(
						"code",
						"oldZip",
						"zip",
						"kanaAddress1",
						"kanaAddress2",
						"kanaAddress3",
						"address1",
						"address2",
						"address3",
						"flag1",
						"flag2",
						"flag3",
						"flag4",
						"class1",
						"class2")
				.build();
	}

	@Bean
	public Step dbToFileStep() {
		return steps.get("DbToFile")
				.<Address, Address> chunk(1000) //TODO チャンクサイズをプロパティで設定できるようにする
				.reader(addressDbReader())
				.writer(addressFileWriter(null))
				.build();
	}

	@Bean
	public Job dbToFileJob() {
		return jobs.get("DbToFile")
				.start(dbToFileStep())
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
