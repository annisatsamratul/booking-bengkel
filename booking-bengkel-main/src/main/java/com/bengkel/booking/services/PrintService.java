package com.bengkel.booking.services;

import java.util.List;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Car;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Vehicle;

public class PrintService {
	
	public static void printLogin(String[] listLogin, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";
		
		System.out.printf("%-25s %n", title);
		System.out.println(line);
		
		for (String data : listLogin) {
			if (number < listLogin.length) {
				System.out.printf(formatTable, number, data);
			}else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}
	
	public static void printMenu(String[] listMenu, String title) {
		String line = "+---------------------------------+";
		int number = 1;
		String formatTable = " %-2s. %-25s %n";
		
		System.out.printf("%-25s %n", title);
		System.out.println(line);
		
		for (String data : listMenu) {
			if (number < listMenu.length) {
				System.out.printf(formatTable, number, data);
			}else {
				System.out.printf(formatTable, 0, data);
			}
			number++;
		}
		System.out.println(line);
		System.out.println();
	}
	
	public static void printVechicle(List<Vehicle> listVehicle) {
		String formatTable = "| %-2s | %-15s | %-10s | %-15s | %-15s | %-5s | %-15s |%n";
		String line = "+----+-----------------+------------+-----------------+-----------------+-------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Vechicle Id", "Warna", "Brand", "Transmisi", "Tahun", "Tipe Kendaraan");
	    System.out.format(line);
	    int number = 1;
	    String vehicleType = "";
	    for (Vehicle vehicle : listVehicle) {
	    	if (vehicle instanceof Car) {
				vehicleType = "Mobil";
			}else {
				vehicleType = "Motor";
			}
	    	System.out.format(formatTable, number, vehicle.getVehiclesId(), vehicle.getColor(), vehicle.getBrand(), vehicle.getTransmisionType(), vehicle.getYearRelease(), vehicleType);
	    	number++;
	    }
	    System.out.printf(line);
	}
	
	public static void pritnItemService(List<ItemService> listAllItemService, Vehicle vehicle) {
		String formatTable = "| %-2s | %-15s | %-15s | %-15s | %-15s |%n";
		String line = "+----+-----------------+-----------------+-----------------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Service Id", "Nama Service", "Tipe Kendaraan", "Harga");
	    System.out.format(line);
	    int number = 1;
	    for (ItemService service : listAllItemService) {
	    	if(service.getVehicleType().equals(vehicle.getVehicleType())) {
	    		System.out.format("| %-2s | %-15s | %-15s | %-15s | %,15.2f |%n", number, service.getServiceId(), service.getServiceName(), service.getVehicleType(), service.getPrice());
		    	number++;
	    	}
	    	
	    }
	    System.out.printf(line);
	}
	
	public static void pritnInformasiBooking(List<BookingOrder> orderBooking) {
		String formatTable = "| %-2s | %-20s | %-15s | %-15s | %-15s | %-15s |%n";
		String line = "+----+----------------------+-----------------+-----------------+-----------------+-----------------+%n";
		System.out.format(line);
	    System.out.format(formatTable, "No", "Booking Id", "Nama Customer", "Payment Method", "Total Service", "Total Payment");
	    System.out.format(line);
	    int number = 1;
	    for (BookingOrder book : orderBooking) {
	    	System.out.format("| %-2s | %-20s | %-15s | %-15s | %,15.2f | %,15.2f |%n", number, book.getBookingId(), book.getCustomer().getName(), book.getPaymentMethod(), book.getTotalServicePrice(), book.getTotalPayment());
		    number++;
	    }
	    System.out.printf(line);
	}
	
	//Silahkan Tambahkan function print sesuai dengan kebutuhan.
	
}
