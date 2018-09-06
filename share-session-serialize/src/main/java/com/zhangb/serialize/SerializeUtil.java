package com.zhangb.serialize;

import java.io.IOException;

import org.nustaq.serialization.FSTConfiguration;

import com.zhangb.proxy.session.ProxySession;


/**
 * fst序列化工具
 * https://github.com/RuedigerMoeller/fast-serialization
 *
 */
public class SerializeUtil{
	
	static FSTConfiguration conf = FSTConfiguration.createDefaultConfiguration();
	/**
	 * 请将常用的序列化/反序列化 对象注册在这里，可以加快速度
	 */
	static {
		conf.registerClass(ProxySession.class, String.class);
		//不实现Serializable接口也能序列化
		conf.setForceSerializable(true);
	}
	/**
	 * 对象转换成byte[]
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public static byte[] objToBytes(Object obj){
		if (obj == null) {
			return null;
		}
		return conf.asByteArray(obj);
	}
	
	/**
	 * byte[]转换成对象
	 * @param bytes
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static Object bytesToObj(byte[] bytes) {
		if (bytes == null || bytes.length < 1) {
			return null;
		}
		return conf.asObject(bytes);
	}
}
