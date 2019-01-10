package cn.mldn.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

public class BeanOperate {
	private Object currentObj; // ��ʾ��ǰ����ı������
	private String attribute; // Ҫ����������
	private String value; // Ҫ����������
	private String arrayValue[]; // Ҫ��������������
	private Field field; // ��ʾҪ�����ĳ�Ա����

	/**
	 * ���в������ݵĽ��գ����պ�ſ��Խ������ݵ����ò���
	 * 
	 * @param obj
	 *            ��ʾ��ǰҪ�����˹��ܵ������
	 * @param attribute
	 *            �����ˡ�����.����.����...���ַ���
	 * @param value
	 *            ��ʾ���Ե����ݡ�
	 */
	public BeanOperate(Object obj, String attribute, String value) {
		this.currentObj = obj; // ���浱ǰ�Ĳ�������
		this.attribute = attribute;
		this.value = value;
		this.handleParameter();
		this.setValue();
	}

	/**
	 * �����������ݵĲ���
	 * 
	 * @param obj
	 * @param attribute
	 * @param value
	 */
	public BeanOperate(Object obj, String attribute, String arrayValue[]) {
		this.currentObj = obj; // ���浱ǰ�Ĳ�������
		this.attribute = attribute;
		this.arrayValue = arrayValue;
		this.handleParameter();
		this.setValue();
	}

	private void handleParameter() { // ����ڴ������ݽ��д���
		try {
			String result[] = this.attribute.split("\\.");
			if (result.length == 2) { // ���ڱ�ʾ���ǵ�������
				// �������е�getter�������ǲ����ڲ����ģ����Բ�������Ϊ��
				Method getMet = this.currentObj.getClass().getMethod(
						"get" + StringUtils.initcap(result[0]));
				this.currentObj = getMet.invoke(this.currentObj); // ������getter()����
				this.field = this.currentObj.getClass().getDeclaredField(
						result[1]);// ȡ�ö����Ա
			} else { // ���ڱ�ʾ���Ƕ༶����
				for (int x = 0; x < result.length; x++) {
					// System.out.println("x = " + x + " = " + this.currentObj);
					// ����֪����ǰ�����ĳ�Ա������ΪֻҪ�ж�����ڲſ����ҵ��������ͣ��ſ��Ե���setter����
					this.field = this.currentObj.getClass().getDeclaredField(
							result[x]);
					// System.out.println(this.field.getName());
					if (x < result.length - 1) { // ���ڲ������һ����ɣ���������
						Method met = this.currentObj.getClass().getMethod(
								"get" + StringUtils.initcap(result[x]));
						this.currentObj = met.invoke(this.currentObj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setValue() { // ����һ��ר�������������ݵķ��������õ�setter����
		try {
			Method setMet = this.currentObj.getClass().getMethod(
					"set" + StringUtils.initcap(this.field.getName()),
					this.field.getType());
			String type = this.field.getType().getSimpleName(); // ȡ����������
			System.out.println(type + "  ***************");
			if ("int".equalsIgnoreCase(type)
					|| "integer".equalsIgnoreCase(type)) {
				if (this.value.matches("\\d+")) {
					setMet.invoke(this.currentObj, Integer.parseInt(this.value));
				}
			} else if ("double".equalsIgnoreCase(type)) {
				if (this.value.matches("\\d+(\\.\\d+)?")) {
					setMet.invoke(this.currentObj,
							Double.parseDouble(this.value));
				}
			} else if ("string".equalsIgnoreCase(type)) {
				setMet.invoke(this.currentObj, this.value);
			} else if ("date".equalsIgnoreCase(type)) {
				if (this.value.matches("\\d{4}-\\d{2}-\\d{2}")) {
					setMet.invoke(this.currentObj, new SimpleDateFormat(
							"yyyy-MM-dd").parse(this.value));
				}
				if (this.value
						.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
					setMet.invoke(this.currentObj, new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss").parse(this.value));
				}
			} else if ("string[]".equalsIgnoreCase(type)) { // �ַ�������
				setMet.invoke(this.currentObj, new Object[] { this.arrayValue });
			} else if ("int[]".equalsIgnoreCase(type)) {
				int temp[] = new int[this.arrayValue.length];
				for (int x = 0; x < temp.length; x++) {
					if (this.arrayValue[x].matches("\\d+")) {
						temp[x] = Integer.parseInt(this.arrayValue[x]);
					}
				}
				setMet.invoke(this.currentObj, new Object[] { temp });
			} else if ("integer[]".equalsIgnoreCase(type)) {
				Integer temp[] = new Integer[this.arrayValue.length];
				for (int x = 0; x < temp.length; x++) {
					if (this.arrayValue[x].matches("\\d+")) {
						temp[x] = Integer.parseInt(this.arrayValue[x]);
					}
				}
				setMet.invoke(this.currentObj, new Object[] { temp });
			} else if ("double[]".equals(type)) {
				double temp[] = new double[this.arrayValue.length];
				for (int x = 0; x < temp.length; x++) {
					if (this.arrayValue[x].matches("\\d+(\\.\\d+)?")) {
						temp[x] = Double.parseDouble(this.arrayValue[x]);
					}
				}
				setMet.invoke(this.currentObj, new Object[] { temp });
			} else if ("Double[]".equalsIgnoreCase(type)) {
				Double temp[] = new Double[this.arrayValue.length];
				for (int x = 0; x < temp.length; x++) {
					if (this.arrayValue[x].matches("\\d+(\\.\\d+)?")) {
						temp[x] = Double.parseDouble(this.arrayValue[x]);
					}
				}
				setMet.invoke(this.currentObj, new Object[] { temp });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
