package ds.examples.EnergyEfficiency;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import ds.examples.EnergyEfficiency.Service3MaintenanceGrpc.Service3MaintenanceImplBase;
import io.grpc.Context;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

public class EnergyServer3 extends Service3MaintenanceImplBase {

	//remoteDiagnostics method
	public void remoteDiagnostics(remoteRequest request, StreamObserver<remoteResponse> responseObserver) {
		try {
		//Get content of message from client
		System.out.println("\nQuery: " + request.getRemote() + "\n");

		//if statement checks if the client still needs a response
		if (Context.current().isCancelled()) {
			responseObserver.onError(Status.CANCELLED.withDescription("remoteDiagnostics method cancelled by client.").asRuntimeException());
			return;
		}
				
		//Build response
		remoteResponse.Builder responseBuilder = remoteResponse.newBuilder();
				
		responseBuilder.setRemoteChillerStatus("Chiller ID: 12345\nChiller Brand Name: York 2000 Centrifugal\nCooling capacity: 300 kW\nCompressor Status: ACTIVE\nAlarm Status: NONE");
			
		responseObserver.onNext(responseBuilder.build());
				
		System.out.println("Server response sent to client.\n");

		responseObserver.onCompleted();//server tells the client that there are no more messages
		
		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
	}//closes remoteDiagnostics method
		
			
	//predictiveMaintenance method
	public void predictiveMaintenance(maintenanceRequest request, StreamObserver<maintenanceResponse> responseObserver) {
				
		try {
					
		//Get content of message from client
		System.out.println("Query: " + request.getMaintenance() + "\n");

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MM yyyy");
		String lastAppointment = "23 01 2022";
		String nextAppointment = "27 04 2023";
		LocalDate date1 = LocalDate.parse(lastAppointment, dtf);
		LocalDate date2 = LocalDate.parse(nextAppointment, dtf);
		long daysBetween = ChronoUnit.DAYS.between(date1, date2);
	
		//if statement checks if the client still needs a response
		if (Context.current().isCancelled()) {
			responseObserver.onError(Status.CANCELLED.withDescription("predictiveMaintenance method cancelled by client.").asRuntimeException());
			return;
		}
				
		//Build response
		maintenanceResponse.Builder responseBuilder = maintenanceResponse.newBuilder();
				
		responseBuilder.setMaintenanceStatus("The last scheduled energy efficiency appointment was " + lastAppointment + ".\nThe next scheduled energy efficiency appointment is " + nextAppointment + ".\nThere are " + daysBetween + " days between the energy efficiency appointments.");
			
		responseObserver.onNext(responseBuilder.build());
				
		System.out.println("Server response sent to client.\n");

		responseObserver.onCompleted();//server tells the client that there are no more messages
				
		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
	}//closes predictiveMaintenance method
			
	
	
	
}//closes EnergyService 3
