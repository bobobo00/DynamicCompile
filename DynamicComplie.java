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
		//动态编译
		JavaCompiler compiler=ToolProvider.getSystemJavaCompiler();
		int result=compiler.run(null, null, null, "E:/javac/HelloWorld.java");
		System.out.println(result==0?"编译成功":"编译失败");
		
		//通过IO流操作，将字符串存储成一个临时文件（Hi.java）,
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
		System.out.println(Hires==0?"编译成功":"编译失败");
		//动态运行
		//通过Runtime
		Runtime run=Runtime.getRuntime();
		Process process=run.exec("java -cp E:/javac Hi");
		
		InputStream is=process.getInputStream();
		BufferedReader reader=new BufferedReader(new InputStreamReader(is));
		String info="";
		while((info=reader.readLine())!=null) {
			System.out.println(info);
		}
		//通过反射
		try {
		URL[] urls=new URL[] {new URL("file:/"+"E:/javac/")};
		URLClassLoader loader=new URLClassLoader(urls);
		Class c=loader.loadClass("Hi");
		//调用加载类的main方法
		Method m=c.getMethod("main", String[].class);
		m.invoke(null, (Object)new String[] {"aa","bb"});
		//由于可变参数时JDK5.0之后才有的，上面代码会编译成：m.invoke（null,"aa","bb"）,就发生了参数个数不匹配的问题
		//因此必须加上（Object）强制转型。
		}catch(Exception e) {
			
		}
		
		
	}

}
