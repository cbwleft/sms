package com.cbwleft.sms.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.cbwleft.sms.config.SMSHealthConfig;
import com.cbwleft.sms.dao.constant.Columns;
import com.cbwleft.sms.dao.model.Message;

@Component
public class SMSHealthIndicator implements HealthIndicator {

	private final boolean[] samples;// 统计样本

	private long count = 0;// 统计发送总量

	private final float threshold;// 短信发送成功率临界值

	private String lastFailCode = null;// 上一次发送失败原因

	public SMSHealthIndicator(SMSHealthConfig config) {
		samples = new boolean[config.getSamples()];
		threshold = config.getThreshold();
	}

	@Override
	public synchronized Health health() {
		if (count < samples.length) {
			return Health.unknown().withDetail("count", count).build();
		} else {
			int success = 0;
			for (boolean sample : samples) {
				if (sample) {
					success++;
				}
			}
			float ratio = (success * 100 / samples.length) / 100f;
			if (ratio < threshold) {
				return Health.down().withDetail("count", count).withDetail("ratio", ratio)
						.withDetail("lastFailCode", lastFailCode).build();
			} else {
				return Health.up().withDetail("count", count).withDetail("ratio", ratio).build();
			}
		}
	}

	public synchronized void addSample(Message message) {
		if (message.getSendStatus() == Columns.SendStatus.SUCCESS) {
			int index = (int) (count++ % samples.length);
			samples[index] = true;
		} else if (message.getSendStatus() == Columns.SendStatus.FAILURE) {
			lastFailCode = message.getFailCode();
			int index = (int) (count++ % samples.length);
			samples[index] = false;
		}
	}

}
