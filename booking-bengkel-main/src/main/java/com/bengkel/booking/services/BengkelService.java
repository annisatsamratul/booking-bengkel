package com.bengkel.booking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.models.MemberCustomer;
import com.bengkel.booking.models.Vehicle;

import jdk.internal.util.xml.impl.Input;

public class BengkelService {
	
	public static Customer getCustomer(String customerId, List<Customer> listAllCustomers) {
		Customer customer = null;
		for(Customer data : listAllCustomers) {
			if(data.getCustomerId().equals(customerId)) {
				customer = data;
			}
		}
		return customer;
	}
	
	public static void informasiCustomer(Customer customer) {
		System.out.println("         Customer Profile          ");
		System.out.println("+---------------------------------+");
		System.out.println("Customer Id     : " + customer.getCustomerId());
		System.out.println("Nama            : " + customer.getName());
		if(customer instanceof MemberCustomer) {
			System.out.println("Customer Status : Member" );
			System.out.println("Alamat          : " + customer.getAddress());
			System.out.printf ("Saldo Koin      : %,.2f%n", ((MemberCustomer)customer).getSaldoCoin());
		}else {
			System.out.println("Customer Status : Non Member" );
			System.out.println("Alamat          : " + customer.getAddress());
		}
		System.out.println("List Kendaraan");
		PrintService.printVechicle(customer.getVehicles());
	}
	
	public static void bookingBengkel(Scanner input, Customer customer, List<ItemService> listAllItemService, List<BookingOrder> orderBooking) {
		System.out.println("          Booking Bengkel          ");
		System.out.println("+---------------------------------+");
		System.out.println("Masukan Vehicle Id:");
		String vehicleId = input.nextLine();
		
		//Pilih Vehicle
		Vehicle vehicle = getVehicle(vehicleId, customer);
		if(vehicle == null) {
			System.out.println("Vehicle Id Tidak Ditemukan!");
			return;
		}
			
		
		//Pilih Service
		getItemService(listAllItemService, vehicle);
		List<ItemService> pilihService = addService(customer, input, listAllItemService);
		if(pilihService == null) {
			System.out.println("Anda belum memilih service!");
			return;
		}
		
		//Metode Bayar
		String metode = Validation.validasiInput("Silahkan Pilih Metode Pembayaran Saldo Coin atau Cash \n", "Metode Pembayaran Salah!", "[a-zA-Z ]*");
		BookingOrder newOrder = new BookingOrder();
		if(customer instanceof MemberCustomer) {
			if(metode.equalsIgnoreCase("Saldo Coin")) {
				newOrder.setBookingId(generateIdBooking(customer));
				newOrder.setCustomer(customer);
				newOrder.setServices(pilihService);
				newOrder.setPaymentMethod("Saldo Coin");
				newOrder.setTotalServicePrice(getTotalService(pilihService));
				newOrder.calculatePayment();

				((MemberCustomer) customer).setSaldoCoin(((MemberCustomer) customer).getSaldoCoin() - newOrder.getTotalPayment());
			}else {
				newOrder.setBookingId(generateIdBooking(customer));
				newOrder.setCustomer(customer);
				newOrder.setServices(pilihService);
				newOrder.setPaymentMethod("Cash");
				newOrder.setTotalServicePrice(getTotalService(pilihService));
				newOrder.calculatePayment();
			}
		}else {
			newOrder.setBookingId(generateIdBooking(customer));
			newOrder.setCustomer(customer);
			newOrder.setServices(pilihService);
			newOrder.setPaymentMethod("Cash");
			newOrder.setTotalServicePrice(getTotalService(pilihService));
			newOrder.calculatePayment();
		}
		
		System.out.println("Booking Berhasil!");
		System.out.printf("Total Harga Service : %,.2f%n", newOrder.getTotalServicePrice());
		System.out.printf("Total Pembayaran    : %,.2f%n", newOrder.getTotalPayment());
		orderBooking.add(newOrder);
		
	}
	
	public static Vehicle getVehicle(String vehicleId, Customer customer) {
		
		Vehicle vehicle = null;
		for(Vehicle data : customer.getVehicles()) {
			if(data.getVehiclesId().equals(vehicleId)) {
				vehicle = data;
			}
		}
		return vehicle;
	}
	
	public static void getItemService(List<ItemService> listAllItemService, Vehicle vehicle) {
		System.out.println("List Service yang Tersedia");
		PrintService.pritnItemService(listAllItemService, vehicle); 
	}
	
	public static ItemService getService(String serviceId, List<ItemService> listAllItemService) {
		ItemService service = null;
		for(ItemService data : listAllItemService) {
			if(data.getServiceId().equals(serviceId)) {
				service = data;
			}
		}
		return service;
	}
	
	public static List<ItemService> addService(Customer customer, Scanner input, List<ItemService> listAllItemService){
		List<ItemService> pilih = new ArrayList<>();
		int maxService = 0;
		if(customer instanceof MemberCustomer) {
			maxService = 2;
		}else {
			maxService = 1;
		}
		
		for(int i = 0; i < maxService; i++) {
			System.out.println("Silahkan Masukan Service Id : ");
			String serviceId = input.nextLine();
			ItemService itemService = getService(serviceId, listAllItemService);
			if(itemService == null) {
				System.out.println("Service Id Tidak Ditemukan!");
			}else {
				pilih.add(itemService);
				if(customer instanceof MemberCustomer) {
					String tambah = Validation.validasiInput("Apakah anda ingin menambahkan Service Lainnya (Y/T) ?\n", "Hanya menerima masukan Y (ya) atau T (tidak)!", "[yYtT]");
					if(tambah.equalsIgnoreCase("T")) {
						break;
					}
					
				}
			}
		}
		
		return pilih;
	}
	
	public static int getTotalService(List<ItemService> pilihService) {
		int res = 0;
		for(ItemService data : pilihService) {
			res += data.getPrice();
		}
		return res;
	}
	
	public static String generateIdBooking(Customer customer) {
		String id = "";
		int num = 0;
		id = "Book-" + customer.getCustomerId() + "-00" + (num+1);
		return id;
	}
	
	public static void informasiBooking(List<BookingOrder> orderBooking) {
		System.out.println("      Informasi Booking Bengkel    ");
		System.out.println("+---------------------------------+");
		PrintService.pritnInformasiBooking(orderBooking);
		if(orderBooking.isEmpty()) {
			System.out.println("Tidak ada Order Booking!");
		}
		
	}
	
	public static void topUpCoin(Customer customer) {
		System.out.println("          Top Up Saldo Coin        ");
		System.out.println("+---------------------------------+");
		if(customer instanceof MemberCustomer) {
			int topUp = Validation.validasiNumberWithRange("Masukan besaran Top Up : \n", "Hanya menerima angka", "^[0-9]+$", 10000000, 100000);
			((MemberCustomer) customer).setSaldoCoin(topUp + ((MemberCustomer) customer).getSaldoCoin());
			
			System.out.println("Top Up Saldo Coin Berhasil!");
		}else {
			System.out.println("Maaf fitur ini hanya untuk Member saja!");
		}
		
	}
	
}
