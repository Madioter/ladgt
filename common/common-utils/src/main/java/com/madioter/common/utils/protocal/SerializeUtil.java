package com.madioter.common.utils.protocal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 对象序列化和反序列化工具 Created by Yi.Wang2 on 2016/8/28.
 */
public class SerializeUtil {

	/**
	 * 对象序列化方法
	 *
	 * @param object
	 *          待序列化对象
	 * @return 比特数组
	 * @throws IOException
	 *           IO异常
	 */
	public static byte[] serialize(Serializable object) throws IOException {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		// 序列化
		baos = new ByteArrayOutputStream();
		oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		byte[] bytes = baos.toByteArray();
		return bytes;

	}

	/**
	 * 对象反序列化方法
	 *
	 * @param bytes
	 *          比特数组
	 * @return 序列化对象
	 * @throws IOException
	 *           IO异常
	 * @throws ClassNotFoundException
	 *           对象类缺失异常
	 */
	public static Serializable unserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bais = null;
		// 反序列化
		bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return (Serializable) ois.readObject();
	}

}
