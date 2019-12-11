package Dynamic;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

public class DynamicComplie {
	public static void main(String[] args) throws Exception {
		//��̬����
		JavaCompiler compiler=ToolProvider.getSystemJavaCompiler();
		int result=compiler.run(null, null, null, "E:/javac/HelloWorld.java");
		System.out.println(result==0?"����ɹ�":"����ʧ��");
		
		//ͨ��IO�����������ַ����洢��һ����ʱ�ļ���Hi.java��,
		String data="public class Hi{\r\n" + 
				"	public static void main(String[] args){\r\n" + 
				"		System.out.println(\"hello world\");\r\n" + 
				"	}\r\n" + 
				"}";
		OutputStream os=new BufferedOutputStream(new FileOutputStream("E:/javac/Hi.java"));
		os.write(data.getBytes());
		os.flush();
		os.close();
		int Hires=compiler.run(null, new FileOutputStream("E:/javac/res.txt"), null, "E:/javac/Hi.java");
		System.out.println(Hires==0?"����ɹ�":"����ʧ��");
		//��̬����
		//ͨ��Runtime
		Runtime run=Runtime.getRuntime();
		Process process=run.exec("java -cp E:/javac Hi");
		
		InputStream is=process.getInputStream();
		BufferedReader reader=new BufferedReader(new InputStreamReader(is));
		String info="";
		while((info=reader.readLine())!=null) {
			System.out.println(info);
		}
		//ͨ������
		try {
		URL[] urls=new URL[] {new URL("file:/"+"E:/javac/")};
		URLClassLoader loader=new URLClassLoader(urls);
		Class c=loader.loadClass("Hi");
		//���ü������main����
		Method m=c.getMethod("main", String[].class);
		m.invoke(null, (Object)new String[] {"aa","bb"});
		//���ڿɱ����ʱJDK5.0֮����еģ������������ɣ�m.invoke��null,"aa","bb"��,�ͷ����˲���������ƥ�������
		//��˱�����ϣ�Object��ǿ��ת�͡�
		}catch(Exception e) {
			
		}
		
		
	}

}
