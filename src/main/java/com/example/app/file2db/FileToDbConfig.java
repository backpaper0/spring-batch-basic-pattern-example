package com.example.app.file2db;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import com.example.gen.model.Address;

@Configuration(proxyBeanMethods = false)
public class FileToDbConfig {

	@Autowired
	private JobBuilderFactory jobs;
	@Autowired
	private StepBuilderFactory steps;
	@Autowired
	private AddressWriter addressWriter;

	@Bean
	@JobScope
	public ItemStreamReader<Address> fileReader(@Value("#{jobParameters['file']}") String file) {

		//TODO ファイルパスのバリデーション、ファイルの存在チェックなど

		PathResource resource = new PathResource(file);

		return new FlatFileItemReaderBuilder<Address>()
				.name("fileReader")
				.resource(resource)
				.encoding("Windows-31J")
				.targetType(Address.class)
				.delimited()
				.names(
						"code",
						"old_zip",
						"zip",
						"kana_address1",
						"kana_address2",
						"kana_address3",
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
	public Step fileToDbStep(ItemStreamReader<Address> fileReader) {
		return steps.get("fileToDbStep")
				.<Address, Address> chunk(1000) //TODO チャンクサイズをプロパティで設定できるようにする
				.reader(fileReader)
				//TODO ファイル内容のバリデーションをItemProcessorで行う
				.writer(addressWriter)
				.build();
	}

	@Bean
	public Job fileToDbJob(Step fileToDbStep) {
		return jobs.get("fileToDbJob")
				.start(fileToDbStep)
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
