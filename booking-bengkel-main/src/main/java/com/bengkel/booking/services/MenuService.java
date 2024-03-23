package com.bengkel.booking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bengkel.booking.models.BookingOrder;
import com.bengkel.booking.models.Customer;
import com.bengkel.booking.models.ItemService;
import com.bengkel.booking.repositories.CustomerRepository;
import com.bengkel.booking.repositories.ItemServiceRepository;

public class MenuService {
	private static List<Customer> listAllCustomers = CustomerRepository.getAllCustomer();
	private static List<ItemService> listAllItemService = ItemServiceRepository.getAllItemService();
	private static Scanner input = new Scanner(System.in);
	private static List<BookingOrder> orderBooking = new ArrayList<>();
	
	public static void run() {
		String[] listLogin = {"Login", "Exit"};
		int menuChoice = 0;
		boolean isLooping = true;
		
		do {
			PrintService.printMenu(listLogin, "Aplikasi Booking Bengkel");
			menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu: ", "Input Harus Berupa Angka!", "^[0-1]+$", listLogin.length-1, 0);
			System.out.println(menuChoice);
			
			switch (menuChoice) {
			case 1:
				login();
				break;
			default:
				System.out.println("Aplikasi Telah Berhenti!");
				isLooping = false;
				break;
			}
		} while (isLooping);
		
	}
	
	public static void login() {
		System.out.println("                Login              ");
		System.out.println("+---------------------------------+");
		
		int attempts = 3;
		boolean isValid = false;
		while(isValid == false) {
			System.out.println("Masukan Customer Id: ");
			String customerId = input.nextLine();
			
			System.out.println("Masukan Password: ");
			String password = input.nextLine();
			
			
			if(attempts != 0) {
				Customer customer = getCustomer(customerId, listAllCustomers);
				if(customer != null) {
					if(customer.getPassword().equals(password)) {
						System.out.println("Login Berhasil!");
						System.out.println();
						mainMenu(customer);
						isValid = true; 
					}else {
						System.out.println("Password yang Anda masukan salah!");
						isValid = false; attempts--;
					}
				}else {
					System.out.println("Customer Id Tidak Ditemukan!");
					isValid = false; attempts--;
				}
			}else {
				System.out.println("Aplikasi Berhenti!");
				System.exit(0);
				break;
			}
			
		}
	}
	
	public static Customer getCustomer(String customerId, List<Customer> listAllCustomers) {
		Customer customer = null;
		for(Customer data : listAllCustomers) {
			if(data.getCustomerId().equals(customerId)) {
				customer = data;
			}
		}
		return customer;
	}
	
	public static void mainMenu(Customer customer) {
		
		String[] listMenu = {"Informasi Customer", "Booking Bengkel", "Top Up Bengkel Coin", "Informasi Booking", "Logout"};
		int menuChoice = 0;
		boolean isLooping = true;
		
		do {
			System.out.println();
			PrintService.printMenu(listMenu, "Booking Bengkel Menu");
			menuChoice = Validation.validasiNumberWithRange("Masukan Pilihan Menu: ", "Input Harus Berupa Angka!", "^[0-9]+$", listMenu.length-1, 0);
			System.out.println(menuChoice);
			
			switch (menuChoice) {
			case 1:
				//panggil fitur Informasi Customer
				BengkelService.informasiCustomer(customer);
				break;
			case 2:
				//panggil fitur Booking Bengkel
				BengkelService.bookingBengkel(input, customer, listAllItemService, orderBooking);
				break;
			case 3:
				//panggil fitur Top Up Saldo Coin
				BengkelService.topUpCoin(customer);
				break;
			case 4:
				//panggil fitur Informasi Booking Order
				BengkelService.informasiBooking(orderBooking);
				break;
			default:
				System.out.println("Logout");
				System.out.println();
				isLooping = false;
				break;
			}
		} while (isLooping);
		
		
	}
	
	//Silahkan tambahkan kodingan untuk keperluan Menu Aplikasi
}
