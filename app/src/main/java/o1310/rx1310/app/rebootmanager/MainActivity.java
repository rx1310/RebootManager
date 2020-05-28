/*!
 * @author      rx1310 <rx1310@inbox.ru>
 * @copyright   Copyright (c) o1310, 2020
 * @license     MIT License
 */

package o1310.rx1310.app.rebootmanager;

// простите. я говнокодер.

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.Html;
import eu.chainfire.libsuperuser.Shell;
import o1310.rx1310.app.rebootmanager.R;
import o1310.rx1310.app.rebootmanager.RebootManager;
import android.content.Intent;

public class MainActivity extends PreferenceActivity {
	
	SharedPreferences s;
	
	protected void onCreate(Bundle b) {
		super.onCreate(b);
		
		// указываем заголовок активности
		setTitle(R.string.activity_main);
		
		// сохранение настроек
		s = PreferenceManager.getDefaultSharedPreferences(this);
		
		// булево значение настройки "Режим Pro"
		Boolean proMode = s.getBoolean("SETTING_PRO_MODE", false);
		
		// создаем экран
		PreferenceScreen p = getPreferenceManager().createPreferenceScreen(this);
		setPreferenceScreen(p);
		
		// пункт на случай отсутствия root на девайсе
		Preference rootNotAvailableMsg = new Preference(this);
		rootNotAvailableMsg.setEnabled(false);
		rootNotAvailableMsg.setSelectable(false);
		rootNotAvailableMsg.setSummary(R.string.msg_root_not_available);
		
		// пункт для перехода в "Настройки"
		Preference goToAdditionallyActivity = new Preference(this);
		goToAdditionallyActivity.setKey("goToAdditionallyActivity");
		goToAdditionallyActivity.setSummary(R.string.activity_additionally);
		
		// перезагрузка в режим recovery
		Preference rebootIntoRecovery = new Preference(this);
		rebootIntoRecovery.setKey("rebootIntoRecovery");
		rebootIntoRecovery.setTitle(R.string.mng_reboot_into_recovery);
		rebootIntoRecovery.setSummary(R.string.mng_reboot_into_recovery_desc);
		
		// перезагрузка в режим recovery (v2)
		Preference rebootIntoRecoveryV2 = new Preference(this);
		rebootIntoRecoveryV2.setKey("rebootIntoRecoveryV2");
		rebootIntoRecoveryV2.setSummary(R.string.mng_reboot_into_recovery_desc_v2);
		
		// перезагрузка в bootloader
		Preference rebootIntoBootloader = new Preference(this);
		rebootIntoBootloader.setKey("rebootIntoBootloader");
		rebootIntoBootloader.setTitle(R.string.mng_reboot_into_bootloader);
		rebootIntoBootloader.setSummary(R.string.mng_reboot_into_bootloader_desc);
		
		// перезагрузка в bootloader (v2)
		Preference rebootIntoBootloaderV2 = new Preference(this);
		rebootIntoBootloaderV2.setKey("rebootIntoBootloaderV2");
		rebootIntoBootloaderV2.setSummary(R.string.mng_reboot_into_bootloader_desc_v2);
		
		// перезагрузка системы
		Preference rebootSystem = new Preference(this);
		rebootSystem.setKey("rebootSystem");
		rebootSystem.setTitle(R.string.mng_reboot_system);
		rebootSystem.setSummary(R.string.mng_reboot_system_desc);
		
		// перезагрузка системы (v2)
		Preference rebootSystemV2 = new Preference(this);
		rebootSystemV2.setKey("rebootSystemV2");
		rebootSystemV2.setSummary(R.string.mng_reboot_system_desc_v2);
		
		// перезагрузка системы (без ядра)
		Preference rebootSystemSoft = new Preference(this);
		rebootSystemSoft.setKey("rebootSystemSoft");
		rebootSystemSoft.setTitle(R.string.mng_reboot_system_soft);
		rebootSystemSoft.setSummary(R.string.mng_reboot_system_soft_desc);
		
		// отключение системы
		Preference sysShutdown = new Preference(this);
		sysShutdown.setKey("sysShutdown");
		sysShutdown.setTitle(R.string.mng_shutdown_system);
		sysShutdown.setSummary(R.string.mng_shutdown_system_desc);
		
		// отключение системы (v2)
		Preference sysShutdownV2 = new Preference(this);
		sysShutdownV2.setKey("sysShutdownV2");
		sysShutdownV2.setSummary(R.string.mng_shutdown_system_desc_v2);
		
		// отключение системы
		Preference safeMode = new Preference(this);
		safeMode.setKey("safeMode");
		safeMode.setTitle(R.string.mng_safe_mode);
		safeMode.setSummary(R.string.mng_safe_mode_desc);
		
		// проверка наличия root
		if (!Shell.SU.available()) {
			// если рут доступа нет
			setTitle(R.string.app_name);
			p.addPreference(rootNotAvailableMsg);
		} else {
			// если рут доступ есть
			if (proMode) {
				// если включен режим "Pro"
				p.addPreference(rebootIntoRecovery);
				p.addPreference(rebootIntoRecoveryV2);
				p.addPreference(rebootIntoBootloader);
				p.addPreference(rebootIntoBootloaderV2);
				p.addPreference(rebootSystem);
				p.addPreference(rebootSystemV2);
				p.addPreference(rebootSystemSoft);
				p.addPreference(sysShutdown);
				p.addPreference(sysShutdownV2);
				p.addPreference(safeMode);
			} else {
				// если режим "Pro" отключен
				p.addPreference(rebootIntoRecovery);
				p.addPreference(rebootIntoBootloader);
			}
		}
		
		p.addPreference(goToAdditionallyActivity); // добавляем в конце пункт для перехода в меню "Дополнительно"
		
	}

	@Override
	protected void onPause() {
		super.onPause();
		// принудительно выгружаем процесс из памяти
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	// обработка нажатий на пункты
	public boolean onPreferenceTreeClick(PreferenceScreen s, Preference p) {
		
		switch (p.getKey()) {
			
			// переход в меню "Дополнительное"
			case "goToAdditionallyActivity": 
				startActivity(new Intent(this, AdditionallyActivity.class)); // вызов активности
				break;
			
			// переход в режим recovery
			case "rebootIntoRecovery":
				Shell.SU.run(RebootManager.CMD_REBOOT_RECOVERY); // выполнение команды
				break;

				// переход в режим recovery (v2)
			case "rebootIntoRecoveryV2":
				Shell.SU.run(RebootManager.CMD_REBOOT_RECOVERY_V2); // выполнение команды
				break;
				
			// переход в bootloader
			case "rebootIntoBootloader":
				Shell.SU.run(RebootManager.CMD_REBOOT_BOOTLOADER); // выполнение команды
				break;
				
			// переход в bootloader (v2)
			case "rebootIntoBootloaderV2":
				Shell.SU.run(RebootManager.CMD_REBOOT_BOOTLOADER_V2); // выполнение команды
				break;
			
			// перезапуск системы
			case "rebootSystem":
				Shell.SU.run(RebootManager.CMD_REBOOT_SYS); // выполнение команды
				break;
				
			// перезапуск системы (v2)
			case "rebootSystemV2":
				Shell.SU.run(RebootManager.CMD_REBOOT_SYS_V2); // выполнение команды
				break;
				
			// быстрая перезагрузка системы
			case "rebootSystemSoft":
				rebootSystemSoftDlg(); // вызов диалога
				break;
				
			// переход в безопасный режим
			case "safeMode":
				Shell.SU.run(RebootManager.CMD_SAFE_MODE); // выполнение команды
				break;
				
			// выключение устройства
			case "sysShutdown":
				Shell.SU.run(RebootManager.CMD_SHUTDOWN); // выполнение команды
				break;
				
			// выключение устройства (v2)
			case "sysShutdownV2":
				Shell.SU.run(RebootManager.CMD_SHUTDOWN_V2); // выполнение команды
				break;
				
		}
		
		return super.onPreferenceTreeClick(s, p);
		
	}
	
	// диалог для SoftReboot с предупреждением
	void rebootSystemSoftDlg(){

		// создаем билдер
		AlertDialog.Builder b = new AlertDialog.Builder(this);

		b.setTitle(R.string.app_name); // заголовок
		b.setMessage(Html.fromHtml(getString(R.string.msg_reboot_system_soft))); // сообщение (+ задействованы теги html)
		b.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() { // обработка нажатия кнопки "Да"
				public void onClick(DialogInterface d, int i) {
					Shell.SU.run(RebootManager.CMD_REBOOT_SYS_SOFT); // выполняем перезапуск
				}
			});
		b.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() { // обработка нажатия кнопки "Нет"
				public void onClick(DialogInterface d, int i) {
					d.cancel(); // закрывает диалог
				}
			});

		AlertDialog a = b.create(); // создаем диалог

		a.show(); // отображаем диалог

	}
	
}
