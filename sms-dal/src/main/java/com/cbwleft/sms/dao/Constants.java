package com.cbwleft.sms.dao;

public abstract class Constants {
	
	public static abstract class SendStatus{
		public final static byte FAILURE = 0;
		public final static byte SENDING = 1;
		public final static byte SUCCESS = 2;
	}
	
	public static abstract class ValidateStatus{
		public final static byte NO = 0;
		public final static byte YES = 1;
	}
	
}
