/*!
 * @author      rx1310 <rx1310@inbox.ru>
 * @copyright   Copyright (c) o1310, 2020
 * @license     MIT License
 */

package o1310.rx1310.app.rebootmanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

public class RebootManager {
	
	public static String CMD_SHUTDOWN = "su -c svc power shutdown"; // команда для выключения устройства
	public static String CMD_SHUTDOWN_V2 = "su -c reboot -p"; // команда для выключения устройства (v2)
	public static String CMD_REBOOT_RECOVERY = "su -c svc power reboot recovery"; // команда для перехода в recovery
	public static String CMD_REBOOT_RECOVERY_V2 = "su -c reboot recovery"; // команда для перехода в recovery (v2)
	public static String CMD_REBOOT_BOOTLOADER = "su -c svc power reboot bootloader"; // команда для перехода в bootloader
	public static String CMD_REBOOT_BOOTLOADER_V2 = "su -c reboot bootloader"; // команда для перехода в bootloader (v2)
	public static String CMD_REBOOT_DOWNLOAD = "su reboot download"; // команда для перехода в download mode
	public static String CMD_REBOOT_SYS = "su -c svc power reboot"; // команда для перезапуска системы
	public static String CMD_REBOOT_SYS_V2 = "su -c reboot system"; // команда для перезапуска системы (v2)
	public static String CMD_REBOOT_SYS_SOFT = "setprop ctl.restart zygote"; // команда для soft-reboot
	public static String CMD_SAFE_MODE = "setprop persist.sys.safemode 1 && setprop ctl.restart zygote"; // команда для перехода в безопасный режим
	//public static String TEST_CMD_FAKE_BATTERY_LOW = "am broadcast -a android.intent.action.BATTERY_LOW";
	
	// версия приложения
	public static String appVersion(Context c) {
		
		// переменные
		String s, a;
		int v;
		
		// регистрируем PackageManager
		PackageManager m = c.getPackageManager();
		
		try {
			// регистрируем PackageInfo
			PackageInfo i = m.getPackageInfo(c.getPackageName(), 0);
			s = i.versionName; // Получаем название версии
			v = i.versionCode; // Получаем код версии
			a = s + "." + v;   // Объединяем название и код версии для "вида"
		} catch(PackageManager.NameNotFoundException e) {
			// в случае ошибки вернем "error(String.appVersion)"
			e.printStackTrace();
			a = "error(String.appVersion)";
		}
		
		return a; // вернем версию в формате НАЗВАНИЕ.КОД (напр.: 1.200915)
		
	}
	
	// вызов Toast
	public static void showToast(String s, Context c) {
		Toast.makeText(c, s, Toast.LENGTH_LONG).show();
	}
	
	// переход по ссылкам
	public static void openUrl(String url, Context c) {
		c.startActivity(new Intent (Intent.ACTION_VIEW, Uri.parse(url)));
	}
	
}
