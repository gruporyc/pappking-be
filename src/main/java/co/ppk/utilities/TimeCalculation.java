package co.ppk.utilities;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class TimeCalculation {
	
	 public long TimeCalculation(String[] args) {
	         	
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	    	
		  	String temporalStart = args[0];
	    	TemporalAccessor temporalI = formatter.parse(temporalStart);
	    	LocalDateTime tiempolocali = LocalDateTime.from(temporalI);
	    	ZonedDateTime zonatiempoi = ZonedDateTime.of(tiempolocali, ZoneId.systemDefault());
	    	Instant horai = Instant.from(zonatiempoi);
	    	
	    	String temporalEnd = args[1];
	    	TemporalAccessor temporalF = formatter.parse(temporalEnd);
	    	LocalDateTime tiempolocalf = LocalDateTime.from(temporalF);
	    	ZonedDateTime zonatiempof = ZonedDateTime.of(tiempolocalf, ZoneId.systemDefault());
	    	Instant horaf = Instant.from(zonatiempof);
	    	
	    	Duration dur = Duration.between( horai, horaf);
	    	long hours = dur.toHours();
	    	long minutes = dur.toMinutes();
	    	//String TimeCalculation = "tu tiempo Pappking es de: "+minutes+ " minutos su monto a cancelar es de: "+minutes*15;
	    	long calculoTiempo = minutes;
	    	return calculoTiempo;
	        
	    }
}
