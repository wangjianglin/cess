//package lin.matlab;
//
//import java.io.File;
//
//import org.junit.Test;
//
//import com.mathworks.toolbox.javabuilder.MWException;
//
//public class MatlabTestUnit {
//	/***
//	 * 读取音频文件 测试已经通过
//	 */
//	// @Test
//	public void testWavread() {
//		File file = new File("test/ad/matlab/ultrasonic.ctf");
//		String fileName = "test/ad/matlab/1/1.wav";
//		try {
//			// 调用
//			Object[] objs = Matlab.fun(4, file, "wavread", fileName);
//
//			System.out.println(objs);
//
//		} catch (MWException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	/***
//	 * 提取特性值 测试已经通过
//	 */
//	// @Test
//	public void testTestF() {
//		File file = new File("test/ad/matlab/ultrasonic.ctf");
//		String fileName = "test/ad/matlab/1/1.wav";
//
//		try {
//			// 测试调用
//			Object[] objs = Matlab.fun(4, file, "wavread", fileName);
//
//			Object objs2 = Matlab.fun(file, "testF", objs[0], objs[1]);
//
//			System.out.println(objs2);
//
//		} catch (MWException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/***
//	 * 测试有效音频 测试已经通过
//	 */
//	// @Test
//	public void testVADtest() {
//		File file = new File("test/ad/matlab/ultrasonic.ctf");
//		String fileName = "test/ad/matlab/1/1.wav";
//
//		try {
//			// 测试调用
//			Object[] objs = Matlab.fun(4, file, "wavread", fileName);
//
//			Object objs2 = Matlab.fun(file, "VADtest", objs[0]);
//
//			System.out.println(objs2);
//		} catch (MWException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/***
//	 * 训练模型，由已知数据样本，训练出一个模型
//	 */
//	 //@Test
//	public void testTestT() {
//		File file = new File("c:/ctf/ultrasonic.ctf");
//		String fileName = "test/ad/matlab/1/1.wav";
//		String fileName2 = "test/ad/matlab/2/1.wav";
//		try {
//			// 测试调用
//			// 读取音频文件
//			Object[] objs1 = new Object[4];
//			try {
//				objs1 = Matlab.fun(4, file, "wavread", fileName);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			Object[] objs = new Object[4];
//			try {
//				objs = Matlab.fun(4, file, "wavread", fileName2);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			// 提取特征性值
//			Object objs2 = Matlab.fun(file, "testF", objs1[0], objs1[1]);
//			Object objs4 = Matlab.fun(file, "testF", objs[0], objs[1]);
//			double[][] f1 = (double[][]) objs2;
//			double[][] f2 = (double[][]) objs4;
//
//			double[][] label = new double[f1.length + f2.length][1];
//
//			for (int i = 0; i < f1.length; i++) {
//				label[i][0] = 1.0;
//			}
//			for (int j = f1.length; j < f2.length; j++) {
//				label[j][0] = 0.0;
//			}
//
//			double[][] result = new double[f1.length + f2.length][27];
//
//			for (int n = 0; n < f1.length; n++) {
//				for (int k = 0; k < 25; k++) {
//					result[n][k] = f1[n][k];
//				}
//			}
//			for (int n = f1.length; n < label.length; n++) {
//				for (int k = 0; k < 25; k++) {
//					result[n][k] = f2[n - f1.length][k];
//				}
//			}
//
//			// 训练返回模型
//			System.out.println("label:"+label.length);
//
//			String cmd = "";
//			Object objs3 = Matlab.fun(file, "testT", label, result, cmd);
//
//			System.out.println(objs3);
//
//		} catch (MWException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	/***
//	 * 检测是否存在放电
//	 */
//	@Test
//	public void testTestPL() {
//		File file = new File("c:/ctf/ultrasonic.ctf");
//		String fileName = "test/ad/matlab/1/1.wav";
//		String fileName2 = "test/ad/matlab/2/1.wav";
//		try {
//			// 测试调用
//			// 读取音频文件
//			Object[] objs1 = new Object[4];
//			try {
//				objs1 = Matlab.fun(4, file, "wavread", fileName);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			Object[] objs = new Object[4];
//			try {
//				objs = Matlab.fun(4, file, "wavread", fileName2);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			// 提取特征性值
//			Object objs2 = Matlab.fun(file, "testF", objs1[0], objs1[1]);
//			Object objs4 = Matlab.fun(file, "testF", objs[0], objs[1]);
//			double[][] f1 = (double[][]) objs2;
//			double[][] f2 = (double[][]) objs4;
//
//			double[][] label = new double[f1.length + f2.length][1];
//
//			for (int i = 0; i < f1.length; i++) {
//				label[i][0] = 1.0;
//			}
//			for (int j = f1.length; j < f2.length; j++) {
//				label[j][0] = 0.0;
//			}
//
//			double[][] result = new double[f1.length + f2.length][27];
//
//			for (int n = 0; n < f1.length; n++) {
//				for (int k = 0; k < 25; k++) {
//					result[n][k] = f1[n][k];
//				}
//			}
//			for (int n = f1.length; n < label.length; n++) {
//				for (int k = 0; k < 25; k++) {
//					result[n][k] = f2[n - f1.length][k];
//				}
//			}
//
//			// 训练返回模型
//
//			// String cmd = "-b 1 -t 3 -g 1 -r 0";
//			String cmd = "";
//			Object objs3 = Matlab.fun(file, "testT", label, result, cmd);
//
//			Object objs5 = Matlab.fun(file, "testPL", f1, objs3);
//
//			System.out.println(objs5);
//
//			// 验证得到结果
//
//		} catch (MWException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
