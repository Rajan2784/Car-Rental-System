package CarRental;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private String carBrand;
    private String carModel;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String carBrand, String carModel, double basePricePerDay){
        this.carId = carId;
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String  getCarId(){
        return carId;
    }

    public String getCarBrand(){
        return carBrand;
    }

    public String getCarModel(){
        return  carModel;
    }

    public double calculatedPrice(int rentDay){
        return basePricePerDay*rentDay;
    }
    public boolean isAvailable(){
        return isAvailable;
    }

    public void rent(){
        isAvailable = false;
    }

    public void returnedCar(){
        isAvailable = true;
    }
}

class Customer{
    private String customerName;
    private String customerId;

    public Customer(String customerName,String customerId){
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public String getCustomerName(){
        return customerName;
    }

    public String getCustomerId(){
        return customerId;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int rentDays;

    public Rental(Car car, Customer customer, int rentDays){
        this.car = car;
        this.customer = customer;
        this.rentDays = rentDays;
    }

    public Car getCar(){
        return car;
    }

    public Customer getCustomer(){
        return customer;
    }

    public int getRentDays(){
        return rentDays;
    }
}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public void rentCar(Car car,Customer customer, int days){
        if (car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car,customer,days));
        }else {
            System.out.println("Car is not available for rent.");
        }
    }

    public void returnCar(Car car){

        Rental rentToRemove = null;
        for (Rental rental : rentals){
            if (rental.getCar()==car){
                rentToRemove = rental;
                break;
            }
        }
        if (rentToRemove!=null){
            rentals.remove(rentToRemove);
        }else {
            System.out.println("Car was not rented");
        }
        car.returnedCar();
    }

    public void menu(){
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("*** Car Rental System ***");
            System.out.println("1. Rent a car");
            System.out.println("2. Return a car");
            System.out.println("3. Exit");

            System.out.println("Enter your choice");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice==1){
                System.out.println("\n== Rent a car ==\n");
                System.out.println("Enter your name: ");
                String customerName = sc.nextLine();
                System.out.println("\n Available Cars: \n");

                for (Car car:cars){
                    if (car.isAvailable()){
                        System.out.println(car.getCarId()+ " - " + car.getCarBrand() + " - "+ car.getCarModel());

                    }
                }

                System.out.println("Enter the car you want to rent: ");
                String carId = sc.nextLine();

                System.out.println("Enter the number of days for rental: ");
                int rentalDays = sc.nextInt();
                sc.nextLine();

                Customer newCustomer = new Customer(customerName,"cus"+customers.size()+1);
                addCustomer(newCustomer);

                Car selectedCar = null;
                for (Car car:cars){
                    if (car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null){
                    double totalPrice = selectedCar.calculatedPrice(rentalDays);
                    System.out.println("\n== Rental Information ==");
                    System.out.println("Customer id: " + newCustomer.getCustomerId());
                    System.out.println("Customer name: "+ newCustomer.getCustomerName());
                    System.out.println("Car: "+ selectedCar.getCarBrand()+ " " + selectedCar.getCarModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n",totalPrice);
                    System.out.println("\nConfirm rental (Y/N)");
                    String confirm = sc.nextLine();

                    if (confirm.equalsIgnoreCase("Y")){
                        rentCar(selectedCar,newCustomer,rentalDays);
                        System.out.println("\nCar rented successfully");
                    }else {
                        System.out.println("\nRental cancelled.");
                    }

                }else {
                    System.out.println("\n Invalid car selection or car not available for rent.");

                }
            }else if(choice==2){
                System.out.println("Enter the car Id: ");
                String carId = sc.nextLine();

                Car carToReturn = null;
                for (Car car:cars){
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }

                if (carToReturn !=null){
                    Customer customer = null;
                    for (Rental rental:rentals){
                        if (rental.getCar() == carToReturn){
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null){
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by "+customer.getCustomerName());

                    }else {
                        System.out.println("Car was not rented or rental information is missing.");

                    }
                }else {
                    System.out.println("Invalid car id or car is not rented.");
                }
            } else if(choice==3){
                break;
            }
            else {
                System.out.println("Invalid Choice please enter a valid option");
            }
        }
    }



}

public class Main {
    public static void main(String[] args) {

        Car car = new Car("C001","Mahindra","Thar",150.00);
        Car car1 = new Car("C002","Mahindra","Scorpio",250.00);
        Car car2 = new Car("C003","Mahindra","Bolero",100.00);

        CarRentalSystem rentalSystem = new CarRentalSystem();
        rentalSystem.addCar(car);
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);

        rentalSystem.menu();

    }
}
