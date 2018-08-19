package powerlessri.anotsturdymod.library.utils;

import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;

//TODO modular crash report factory
//INCOMPLETE
public class CrashReportUtils {
	
	private CrashReportUtils() {
	}
	
	public static CrashReport makeSimpleReport(Throwable exception, String description) {
		return CrashReport.makeCrashReport(exception, description);
	}
	
	
	
	public static void simpleCrash(Throwable exception, String description) {
		throw new ReportedException(makeSimpleReport(exception, description));
	}
	
}
