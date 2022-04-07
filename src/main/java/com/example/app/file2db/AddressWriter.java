package com.example.app.file2db;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.gen.mapper.AddressMapper;
import com.example.gen.model.Address;

@Component
@StepScope
public class AddressWriter implements ItemWriter<Address> {

	@Autowired
	private AddressMapper addressMapper;

	@Override
	public void write(List<? extends Address> items) throws Exception {
		for (Address item : items) {
			addressMapper.insert(item);
		}
	}
}
