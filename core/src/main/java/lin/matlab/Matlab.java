//package lin.matlab;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.ObjectOutputStream;
//import java.lang.reflect.Array;
//import java.util.AbstractMap.SimpleEntry;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import com.mathworks.toolbox.javabuilder.MWArray;
//import com.mathworks.toolbox.javabuilder.MWCellArray;
//import com.mathworks.toolbox.javabuilder.MWCharArray;
//import com.mathworks.toolbox.javabuilder.MWClassID;
//import com.mathworks.toolbox.javabuilder.MWComplexity;
//import com.mathworks.toolbox.javabuilder.MWComponentOptions;
//import com.mathworks.toolbox.javabuilder.MWCtfExtractLocation;
//import com.mathworks.toolbox.javabuilder.MWCtfFileSource;
//import com.mathworks.toolbox.javabuilder.MWException;
//import com.mathworks.toolbox.javabuilder.MWFunctionHandle;
//import com.mathworks.toolbox.javabuilder.MWJavaObjectRef;
//import com.mathworks.toolbox.javabuilder.MWLogicalArray;
//import com.mathworks.toolbox.javabuilder.MWNumericArray;
//import com.mathworks.toolbox.javabuilder.MWStructArray;
//import com.mathworks.toolbox.javabuilder.internal.MWFunctionSignature;
//import com.mathworks.toolbox.javabuilder.internal.MWMCR;
//
///***
// * 2013-6-7
// * 
// * @author 李水云 
// * @version 2013-5-30
// * 该类主要是为了实现java与Matlab双方数据转换，使得java轻松调用Matlab生成的CTF文件，
// *         并使用java生成的Matlab实例调用CTF文件里的各种函数实现相应的计算功能
// */
//public class Matlab {
//	/***
//	 * 将java数据类型封装成matlab数据类型
//	 * 
//	 * @param obj
//	 * @return
//	 */
//	@SuppressWarnings("rawtypes")
//	private static MWArray wapper(Object obj) {
//		Class<?> type = obj.getClass();
//		Class<?> itemType = arrayType(type);
//		// 处理复数情况
//		// 传进来的参数如果是Object类型数组，并且元素个数是2则表示是一个复数
//		if (type.isArray() && type.getComponentType() == Object.class
//				&& Array.getLength(obj) == 2) {
//
//			Object[] tmpObj = (Object[]) obj;
//			return new MWNumericArray(tmpObj[0], tmpObj[1]);
//
//			// 传进来参数如果是Object类型数组，并且长度为3，则表示是一个稀疏矩阵，第一个元素存储行号，第二元素存储列号，第三元素存储数值
//		} else if (type.isArray() && type.getComponentType() == Object.class
//				&& Array.getLength(obj) == 3) {
//
//			Object[] tmpObj = (Object[]) obj;
//			return MWNumericArray.newSparse((int[]) tmpObj[0],
//					(int[]) tmpObj[1], tmpObj[2], MWClassID.DOUBLE);
//			// 传进来参数如果是Object类型数组，并且长度为3，则表示是一个复数稀疏矩阵，第一个元素存储行号，第二元素存储列号，第三元素存储数值的实部，第四元素存储数值虚部
//		} else if (type.isArray() && type.getComponentType() == Object.class
//				&& Array.getLength(obj) == 4) {
//
//			Object[] tmpObj = (Object[]) obj;
//			return MWNumericArray.newSparse((int[]) tmpObj[0],
//					(int[]) tmpObj[1], tmpObj[2], tmpObj[3], MWClassID.DOUBLE);
//			// 处理java源生类型数据、源生数据类型的封装类型数据
//		} else if (itemType == int.class || itemType == Integer.class
//				|| itemType == double.class || itemType == Double.class
//				|| itemType == byte.class || itemType == Byte.class
//				|| itemType == short.class || itemType == Short.class
//				|| itemType == float.class || itemType == Float.class
//				|| itemType == long.class || itemType == Long.class) {
//			// 若元素个数为0直接返回一个空数值数组
//			if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
//				return new MWNumericArray();
//			} else {
//				return new MWNumericArray(obj);
//			}
//			// 处理字符、字符串类型数据
//		} else if (itemType == String.class || itemType == char.class
//				|| itemType == Character.class) {
//
//			return new MWCharArray(obj);
//			// 处理逻辑(布尔)类型数据
//		} else if (itemType == boolean.class || itemType == Boolean.class) {
//
//			return new MWLogicalArray(obj);
//			// 处理List类型数据
//		} else if (List.class.isAssignableFrom(type)) {
//
//			return wapperMWStructArray((List) obj);
//
//		} /*
//		 * else if (List.class.isAssignableFrom(type)) {
//		 * 
//		 * return wapperMWCellArray(obj);
//		 * 
//		 * }
//		 */
//		return null;
//	}
//
//	/**
//	 * 获得数组元素类型
//	 * 
//	 * @param c
//	 * @return
//	 */
//	private static Class<?> arrayType(Class<?> c) {
//		// 获取数据类型，若为数组则返回数组元素类型，反之直接返回其类型
//		if (c.isArray()) {
//			return arrayType(c.getComponentType());
//		}
//		return c;
//	}
//
//	/**
//	 * 处理结构体类型 将java的List类型数据封装成Matlab结构类型数组MWStructArray
//	 * 
//	 * @param obj
//	 * @return
//	 */
//
//	@SuppressWarnings({ "rawtypes" })
//	public static MWStructArray wapperMWStructArray(List obj) {
//		String[] params = new String[obj.size()];
//		int[] fileds = new int[] { 1, 1 };
//
//		// List里面保存的数据类型是SimpleEntry，可以分别取出其key和value
//		for (int n = 0; n < obj.size(); n++) {
//			SimpleEntry sim = (SimpleEntry) obj.get(n);
//			params[n] = (String) sim.getKey();
//		}
//
//		MWStructArray mws2 = new MWStructArray(fileds, params);
//
//		for (int i = 0; i < obj.size(); i++) {
//			SimpleEntry sim = (SimpleEntry) obj.get(i);
//			mws2.set(i + 1, wapper(sim.getValue()));
//		}
//		return mws2;
//	}
//
//	/**
//	 * 处理细胞类型
//	 * 
//	 * @param obj
//	 * @return
//	 */
//	// private static MWCellArray wapperMWCellArray(Object obj) {
//	//
//	// return null;
//	// }
//
//	// =====================================================================================================
//	// 将 matlab 数据类型解析为 java 数据类型
//	/**
//	 * 将matlab数据类型解析为java的数据类型
//	 * 
//	 * @param mw
//	 * @return
//	 */
//	private static Object unWapper(MWArray mw) {
//		if (mw instanceof MWStructArray) {
//
//			return unWapperMWStructArray((MWStructArray) mw);
//
//		} else if (mw instanceof MWCellArray) {
//
//			return unWapperMWCellArray((MWCellArray) mw);
//
//		} else if (mw instanceof MWNumericArray) {
//
//			return unWapperMWNumericArray((MWNumericArray) mw);
//
//		} else if (mw instanceof MWLogicalArray) {
//
//			return unWapperMWLogicalArray((MWLogicalArray) mw);
//
//		} else if (mw instanceof MWCharArray) {
//
//			return unWapperMWCharArray((MWCharArray) mw);
//
//		} else if (mw instanceof MWFunctionHandle) {
//
//			return unWapperMWFunctionHandle((MWFunctionHandle) mw);
//
//		} else if (mw instanceof MWJavaObjectRef) {
//
//			return unWapperMWJavaObjectRef((MWJavaObjectRef) mw);
//
//		} else {
//			return serial(mw);
//		}
//	}
//
//	/***
//	 * 处理数值、数值数组
//	 * 
//	 * @param array
//	 * @return
//	 */
//	private static Object unWapperMWNumericArray(MWNumericArray array) {
//		if (array.numberOfElements() == 0) {
//			return new double[] {};
//		} else if (array.numberOfElements() == 1) {
//			// 判断大小为1，并且是复数，是则构建一个大小为2的数组分别存储其实部和虚部
//			if (array.complexity() == MWComplexity.COMPLEX) {
//				Object[] ob = new Object[2];
//				ob[0] = array.get(1);
//				ob[1] = array.getImag(1);
//				return ob;
//			} else {
//				return array.getData();
//			}
//			// 判断是否是稀疏矩阵
//		} else if (array.isSparse()) {
//			// 判断稀疏矩阵里的数值是否为复数，为复数则创建一个长度为4的数组分别存储行号、列号、数值的实部、数值的虚部
//			if (array.complexity() == MWComplexity.COMPLEX) {
//
//				int[] rows = array.rowIndex();// 行号
//				int[] column = array.columnIndex();// 列号
//
//				Object[] objs = new Object[4];
//				objs[0] = rows;
//				objs[1] = column;
//
//				Object real = Array.newInstance(double.class, rows.length);
//				Object imag = Array.newInstance(double.class, column.length);
//
//				for (int i = 0; i < rows.length; i++) {
//					Array.set(real, i,
//							array.get(new int[] { rows[i], column[i] }));
//					Array.set(imag, i,
//							array.get(new int[] { rows[i], column[i] }));
//				}
//				objs[2] = real;
//				objs[3] = imag;
//				return objs;
//				// 如果是稀疏矩阵，其数值不是复数，创建一个长度为3的数组，分别存储数值的行号、列号、数值
//			} else {
//				int[] rows = array.rowIndex();// 行号
//				int[] column = array.columnIndex();// 列号
//
//				Object[] objs = new Object[3];
//				objs[0] = rows;
//				objs[1] = column;
//
//				Object real = Array.newInstance(double.class, rows.length);
//
//				for (int i = 0; i < rows.length; i++) {
//					Array.set(real, i,
//							array.get(new int[] { rows[i], column[i] }));
//				}
//				objs[2] = real;
//				return objs;
//			}
//		} else {
//			Object obj = array.toArray();
//			return obj;
//		}
//	}
//
//	/***
//	 * 将逻辑数组解析为java的相应数组
//	 * 
//	 * @param array
//	 * @return
//	 */
//	private static Object unWapperMWLogicalArray(MWLogicalArray array) {
//		int[] index = new int[array.getDimensions().length];
//		// 如果该数组只有一个元素，则直接返回该值
//		if (array.numberOfElements() == 1) {
//			for (int n = 0; n < index.length; n++) {
//				index[n] = 1;
//			}
//			return array.get(index);
//		}
//		// 如果数组元素不只一个，将其转为相应数组
//		Object obj = array.toArray();
//		return obj;
//	}
//
//	/***
//	 * 将字符数组解析为java的相应数组
//	 * 
//	 * @param array
//	 * @return
//	 */
//	private static Object unWapperMWCharArray(MWCharArray array) {
//		int[] dims = array.getDimensions();
//		Object obj = null;
//		// 如果是单个数据直接返回一个字符
//		if (array.numberOfElements() == 1) {
//			return array.getData();
//
//			// 如果是行向量，则返回一个字符串
//		} else if (dims.length == 2 && dims[0] == 1) {
//			StringBuffer str = new StringBuffer();
//			str.append(array.toString());
//			return str.toString();
//
//			// 如果是列向量返回字符串数组
//		} else {
//			int[] newDims = new int[dims.length - 1];
//			for (int n = 0; n < newDims.length; n++) {
//				obj = Array.newInstance(String.class, newDims);
//				int[] index = new int[array.getDimensions().length];
//				charValueImpl(obj, array, index, array.getDimensions().length);
//			}
//			return obj;
//		}
//	}
//
//	// 处理字符数组内容
//	private static void charValueImpl(Object obj, MWCharArray array,
//			int[] index, int dim) {
//		if (dim == 2) {
//			StringBuffer buffer = null;
//			for (int k = 0; k < Array.getLength(obj); k++) {
//				buffer = new StringBuffer();
//				index[index.length - 2] = k + 1;
//				int length = array.getDimensions()[array.getDimensions().length - 1];
//				for (int n = 0; n < length; n++) {
//					index[index.length - 1] = n + 1;
//					buffer.append(array.getChar(index));
//				}
//				Array.set(obj, k, buffer.toString());
//			}
//		} else if (dim > 1) {
//			for (int n = 0; n < Array.getLength(obj); n++) {
//				index[index.length - dim] = n + 1;
//				charValueImpl(Array.get(obj, n), array, index, dim - 1);
//			}
//		}
//	}
//
//	/***
//	 * 处理结构数组 将结构数组解析为java的List集合，并且集合里的数据类型是SimpleEntry
//	 * 
//	 * @param struct
//	 * @return
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private static List unWapperMWStructArray(MWStructArray struct) {
//		List list = new ArrayList<>();
//		String[] names = struct.fieldNames();
//		for (String name : names) {
//			int index = struct.fieldIndex(name);
//			Object value = struct.getField(index + 1);
//			value = unWapper((MWArray) value);
//			SimpleEntry simple = new SimpleEntry(name, value);
//			list.add(simple);
//		}
//		return list;
//	}
//	/**
//	 * 处理细胞数组 暂不处理
//	 * 
//	 * @param mw
//	 * @return
//	 */
//	private static Object unWapperMWCellArray(MWCellArray mw) {
//		return null;
//	}
//
//	/***
//	 * 处理MWFunctionHandle 暂不处理
//	 * 
//	 * @param mw
//	 * @return
//	 */
//	private static Object unWapperMWFunctionHandle(MWFunctionHandle mw) {
//		return null;
//	}
//
//	/***
//	 * 处理unWapperMWJavaObjectRef 暂不处理
//	 * 
//	 * @param mw
//	 * @return
//	 */
//	private static Object unWapperMWJavaObjectRef(MWJavaObjectRef mw) {
//		return null;
//	}
//
//	/**
//	 * 处理剩余情况
//	 * 
//	 * @param mw
//	 * @return
//	 */
//	private static Object[] serial(MWArray mw) {
//		ByteArrayOutputStream _bout = new ByteArrayOutputStream();
//		ObjectOutputStream _out = null;
//		try {
//			_out = new ObjectOutputStream(_bout);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		try {
//			_out.writeObject(mw);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		Object[] r = new Object[2];
//		r[0] = "Serialize";
//		r[1] = _bout.toByteArray();
//		return r;
//	}
//
//	// =========================================================================================
//	// 实例化matlab
//
//	// mcrMap用于存储组件名和创建的matlab实例
//	private static Map<String, MWMCR> mcrMap = new HashMap<>();
//
//	/**
//	 * 获得matlab实例
//	 * 
//	 * @param file
//	 * @return
//	 * @throws MWException
//	 */
//	private static MWMCR getMCR(File file) throws MWException {
//		String sComponentName = file.getName();
//		if (!mcrMap.containsKey(sComponentName)) {
//			synchronized (mcrMap) {
//				if (!mcrMap.containsKey(sComponentName)) {
//					MWComponentOptions componentOptions = new MWComponentOptions(
//							MWCtfExtractLocation.EXTRACT_TO_CACHE,
//							new MWCtfFileSource(file));
//					MWMCR mcr = MWMCR.newInstance(componentOptions,
//							String.class, sComponentName, sComponentName,
//							supportVersion());
//					mcrMap.put(sComponentName, mcr);
//				}
//			}
//		}
//
//		return mcrMap.get(sComponentName);
//	}
//
//	// ===================================================================================
//	// matlab实例调用函数
//	/***
//	 * 该函数主要功能是调用给定CTF里的各种函数
//	 * 
//	 * @param component文件名称
//	 * @param fun函数名称
//	 * @param args参数
//	 * @return
//	 * @throws MWException
//	 */
//	public static Object fun(File component, String fun, Object... args)
//			throws Exception {
//		// 得到CTF文件
//		MWFunctionSignature sDataTypeChangeSignature = new MWFunctionSignature(
//				1, false, fun, args.length, false);
//
//		MWArray[] objs = new MWArray[1];
//
//		MWMCR mwmcr = getMCR(component);
//
//		MWArray[] rhs = new MWArray[args.length];
//		for (int n = 0; n < args.length; n++) {
//			rhs[n] = wapper(args[n]);
//		}
//		
//		mwmcr.invoke(Arrays.asList(objs), Arrays.asList(rhs),
//				sDataTypeChangeSignature);
//
//		return unWapper(objs[0]);
//
//	}
//
//	/***
//	 * @param count
//	 *            返回结果个数
//	 * @param component
//	 *            文件名称
//	 * @param fun
//	 *            函数名称
//	 * @param args参数
//	 * @return
//	 * @throws MWException
//	 */
//	public static Object[] fun(int count, File component, String fun,
//			Object... args) throws Exception {
//		// 得到CTF文件
//		try {
//			MWFunctionSignature sDataTypeChangeSignature = new MWFunctionSignature(
//					count, false, fun, args.length, false);
//
//			MWArray[] objs = new MWArray[count];
//
//			MWMCR mwmcr = getMCR(component);
//
//			MWArray[] rhs = new MWArray[args.length];
//			for (int n = 0; n < args.length; n++) {
//				rhs[n] = wapper(args[n]);
//			}
//
//			mwmcr.invoke(Arrays.asList(objs), Arrays.asList(rhs),
//					sDataTypeChangeSignature);
//
//			Object[] result = new Object[objs.length];
//
//			for (int n = 0; n < objs.length; n++) {
//				result[n] = unWapper(objs[n]);
//			}
//			return result;
//		} catch (Throwable e) {
//			e.printStackTrace();
//			throw new Exception(e.getMessage(),e.getCause());
//		}
//	}
//
//	// ============================================================================================================
//
//	/**
//	 * 注销掉不再使用的matlab实例
//	 * 
//	 * @param component
//	 */
//	public static void disposeCompoent(File component) {
//		MWMCR mwmcr = mcrMap.get(component);
//		mwmcr.dispose();
//		mcrMap.remove(component);
//	}
//
//	/***
//	 * 获得当前所支持的matlab的版本号
//	 * 
//	 * @return
//	 */
//	public static int[] supportVersion() {
//		int[] version = new int[3];
//		version[0] = 8;
//		version[1] = 0;
//		version[2] = 0;
//		return version;
//	}
//}
