package com.example.app.oneshot;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.gen.mapper.AddressMapper;
import com.example.gen.model.AddressExample;

@Component
@StepScope
public class DeleteAllAddressTasklet implements Tasklet {

	@Autowired
	private AddressMapper addressMapper;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		AddressExample example = new AddressExample();
		addressMapper.deleteByExample(example);

		return RepeatStatus.FINISHED;
	}
}
