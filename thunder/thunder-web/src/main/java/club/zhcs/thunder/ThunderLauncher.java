package club.zhcs.thunder;

import org.nutz.web.WebLauncher;

public class ThunderLauncher {

	public static void main(String[] args) {
		String path = System.getProperty("java.library.path");
		System.setProperty("java.library.path", path + ":src/main/webapp/WEB-INF/lib");
		WebLauncher.start(args);
	}
}
