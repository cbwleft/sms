package com.cbwleft.sms.service.impl;

import com.cbwleft.sms.config.ChannelConfig;
import com.cbwleft.sms.service.IChannelSMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class ChannelSMSServices implements Iterable<IChannelSMSService> {

	@Autowired
	private ChannelConfig channelConfig;

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public Iterator<IChannelSMSService> iterator() {
		return new Itr();
	}

	public IChannelSMSService getBatchSendable() {
		return (IChannelSMSService) applicationContext.getBean(channelConfig.getBatchSend());
	}

	private class Itr implements Iterator<IChannelSMSService> {

		int i;

		@Override
		public boolean hasNext() {
			return i < channelConfig.getAvailable().size();
		}

		@Override
		public IChannelSMSService next() {
			return (IChannelSMSService) applicationContext.getBean(channelConfig.getAvailable().get(i++));
		}
	}

}
