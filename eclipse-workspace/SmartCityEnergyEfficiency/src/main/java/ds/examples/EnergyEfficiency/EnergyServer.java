package ds.examples.EnergyEfficiency;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.*;
import java.util.Random;
import ds.examples.EnergyEfficiency.EnergyServer;
import ds.examples.EnergyEfficiency.Service1ElectricityGrpc.Service1ElectricityImplBase;
import ds.examples.EnergyEfficiency.Service2RenewablesGrpc.Service2RenewablesImplBase;
import ds.examples.EnergyEfficiency.Service3MaintenanceGrpc.Service3MaintenanceImplBase;
import ds.examples.EnergyEfficiency.CalculateResponse;
import ds.examples.EnergyEfficiency.NumberMessage;
import ds.examples.EnergyEfficiency.lightRequest;
import ds.examples.EnergyEfficiency.lightResponse;
import ds.examples.EnergyEfficiency.remoteRequest;
import ds.examples.EnergyEfficiency.remoteResponse;
import ds.examples.EnergyEfficiency.maintenanceResponse;
import ds.examples.EnergyEfficiency.maintenanceRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;

//public class EnergyServer extends Service1ElectricityImplBase  {//used to text rpcs in Service1Electricity.proto
//public class EnergyServer extends Service2RenewablesImplBase  {//used to text rpcs in Service12Renewables.proto
public class EnergyServer extends Service3MaintenanceImplBase  {//used to text rpcs in Service3Maintenance.proto


	
	
	public static void main(String[] args) {
		
		//creates energyServer object.
		EnergyServer energyServer = new EnergyServer();

		//conventional port for gRPC.		
		int port = 50051;
		

		try {//try/catch to catch errors and throw exceptions.
			
			System.out.println("Starting gRPC Server...");
			
			//uses builder pattern and calls methods to specify the port, add the service, then build and start the server.
			Server server = ServerBuilder.forPort(port).addService(energyServer).build().start();

			System.out.println("Smart City (energy efficiency) server started, listening on " + port);

			server.awaitTermination();


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}//closes main method
	
	//lightSensor
	//Illuminance is the measure of the amount of light received on the surface. It is typically expressed in lux (lm/m2).
	//Method below takes in lightRequest as a parameter and returns the generic lightResponse as type StreamObserver
	public void lightSensor(lightRequest request,StreamObserver<lightResponse> responseObserver) {

		//Finds out the content of the message sent by the client using getNumbers, getMin, getMax. 
		System.out.printf("receiving lux data: %d from: %d to: %d \n",
				request.getNumbers(), request.getMin(), request.getMax()  );
		//this method is part of the Print Stream class. "d" formats decimal integers.

		Random rand = new Random();

		for(int i=0; i<request.getNumbers(); i++) {
			
			//generates random numbers for lux values using max and min values set in client.
			int random_value = rand.nextInt(request.getMax() - request.getMin()) + request.getMin() + 1;
			
			//builds a response with the builder method and sets the Number as random_value.
			lightResponse reply = lightResponse.newBuilder().setNumbers(random_value).build();

			responseObserver.onNext(reply);

			try {
				//wait for a second
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		responseObserver.onCompleted();//server tells the client that there are no more messages


	}//closes lightSensor method
	
	//the power for the bridge lights switches between mains electricity and the foot fall kinetic generator depending on the level of foot traffic on the bridge.
	// see https://futurism.com/new-flooring-tech-generates-electricity-through-your-footsteps
	public StreamObserver<bridgeMessage> bridgeLights(StreamObserver<bridgeResponse> responseObserver) {
		
		return new StreamObserver<bridgeMessage> () {

			@Override
			public void onNext(bridgeMessage msg) {//when a message is sent to server from client
				
				//gets pedestrian count from client
				System.out.println("receiving bridgeLights pedestrian count: "+ msg.getPedestrianCount());
				
				//builds response
				bridgeResponse.Builder responseBuilder = bridgeResponse.newBuilder();
				
				if(msg.getPedestrianCount() <= 30) {
					//if pedestrian count is less than or equal to 30, the power for the bridge lights switches to the mains generator.
					responseBuilder.setEnergyStatus("Pedestrian count is less than or equal to 30 - using mains generator to power bridge lights.");	
				}else {
					//if pedestrian count is greater than 30, the power for the bridge lights switches to the the kinetic foot traffic generator.
					responseBuilder.setEnergyStatus("Pedestrian count is greater than 30 - using foot traffic electricity generator for bridge lights.");	
				}
				
				//sends response to client
				responseObserver.onNext(responseBuilder.build());
				
			}

			@Override
			public void onError(Throwable t) {
				
				t.printStackTrace();
				
			}

			@Override
			public void onCompleted() {
				System.out.println("receiving pedestrian count completed ");
				
				//completed too
				responseObserver.onCompleted();//server tells the client that there are no more messages
			}
			
		};
	}
	
	
	//turbineStatus method
		public void turbineStatus(turbineRequest request, StreamObserver<turbineResponse> responseObserver) {
			
			//Get content of message from client
			System.out.println("Query: " + request.getTurbine());

			//Build response
			turbineResponse.Builder responseBuilder = turbineResponse.newBuilder();
			
			responseBuilder.setTurbineStatus("Turbine ID: 4\nTurbine Brand Name: Windmaster 300\nNominal Power: 300 kW\nWind Speed: 29m/sec");
		
			responseObserver.onNext(responseBuilder.build());

			responseObserver.onCompleted();//server tells the client that there are no more messages
	}//closes turbineStatus method

	
	//hydroAverageValues method
	public StreamObserver<NumberMessage> hydroAverageValues(
			StreamObserver<CalculateResponse> responseObserver) {


		return new StreamObserver<NumberMessage>() {

			ArrayList<Float> list = new ArrayList();

			@Override
			public void onNext(NumberMessage request) {

				System.out.println("Streaming water-flow figures from client: "+ request.getNumber() + " gallons per second."  );

				list.add(request.getNumber());		           

			}

			@Override
			public void onError(Throwable t) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompleted() {
				System.out.printf("hydroAverageValues method complete.\n" );

				double temp = 0;	
				for(float v:  list) {
					temp = temp + v;
				}
				float mean = (float) (temp/list.size());

				CalculateResponse reply = CalculateResponse.newBuilder().setResult(mean).build();

				responseObserver.onNext(reply);

				responseObserver.onCompleted();

			}

		};

	}//closes hydroAverageValues method

	//remoteDiagnostics method
		public void remoteDiagnostics(remoteRequest request, StreamObserver<remoteResponse> responseObserver) {
			
			//Get content of message from client
			System.out.println("Query: " + request.getRemote());

			//Build response
			remoteResponse.Builder responseBuilder = remoteResponse.newBuilder();
			
			responseBuilder.setRemoteChillerStatus("Chiller ID: 12345\nChiller Brand Name: York 2000 Centrifugal\nCooling capacity: 300 kW\nCompressor Status: ACTIVE\nAlarm Status: NONE");
		
			responseObserver.onNext(responseBuilder.build());

			responseObserver.onCompleted();//server tells the client that there are no more messages
		}//closes remoteDiagnostics method
	
		
		//predictiveMaintenance method
		public void predictiveMaintenance(maintenanceRequest request, StreamObserver<maintenanceResponse> responseObserver) {
			
			//Get content of message from client
			System.out.println("Query: " + request.getMaintenance());

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
			String lastAppointment = "23 01 2022";
			String nextAppointment = "27 04 2023";

			LocalDate date1 = LocalDate.parse(lastAppointment, dtf);
			LocalDate date2 = LocalDate.parse(nextAppointment, dtf);
			//long daysBetween = Duration.between(date1, date2).toDays();
			long daysBetween = ChronoUnit.DAYS.between(date1, date2);
			//System.out.println ("Days: " + daysBetween);
			
			
			//Build response
			maintenanceResponse.Builder responseBuilder = maintenanceResponse.newBuilder();
			
			responseBuilder.setMaintenanceStatus("The last scheduled energy efficiency appointment was " + lastAppointment + ".\nThe next scheduled energy efficiency appointment is " + nextAppointment + ".\nThere are " + daysBetween + " days between the energy efficiency appointments.");
		
			responseObserver.onNext(responseBuilder.build());

			responseObserver.onCompleted();//server tells the client that there are no more messages
		}//closes predictiveMaintenance method
		
	
}//closes EnergyEfficiencyServer
