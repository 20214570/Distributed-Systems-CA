package ds.examples.EnergyEfficiency;

import java.io.IOException;
import java.util.Random;
import ds.examples.EnergyEfficiency.EnergyServer;
import ds.examples.EnergyEfficiency.Service1ElectricityGrpc.Service1ElectricityImplBase;
import ds.examples.EnergyEfficiency.lightRequest;
import ds.examples.EnergyEfficiency.lightResponse;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;

public class EnergyServer extends Service1ElectricityImplBase {

	
	
	public static void main(String[] args) {
		EnergyServer energyServer = new EnergyServer();

		int port = 50051;
		
		System.out.println("Starting gRPC Server...");

		try {

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
	public void lightSensor(lightRequest request,StreamObserver<lightResponse> responseObserver) {


		System.out.printf("receiving lux data: %d from: %d to: %d \n",
				request.getNumbers(), request.getMin(), request.getMax()  );//this method is part of the Print Stream class. "d" formats decimal integers.

		Random rand = new Random();

		for(int i=0; i<request.getNumbers(); i++) {

			int random_value = rand.nextInt(request.getMax() - request.getMin()) + request.getMin() + 1;

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


		responseObserver.onCompleted();


	}
	/*
	public void turbineStatus(turbineRequest request, StreamObserver<turbineResponse> responseObserver) {

		System.out.println("receiving turbineStatus method " + request.getturbine());

		float value = Float.NaN;
		String msg= "";

		//could use this section to change temperature in a  room. Replace ADDITION etc. with ROOM 1 etc.
		
		if(	request.getOperation()==Operation.ADDITION)
			value = request.getNumber1() + request.getNumber2();
		else if(	request.getOperation()==Operation.SUBTRACTION)
			value = request.getNumber1() - request.getNumber2();
		else if(	request.getOperation()==Operation.MULTIPLICATION)
			value = request.getNumber1() * request.getNumber2();
		else if(	request.getOperation()==Operation.DIVISION)
			value = request.getNumber1() / request.getNumber2();
		else {
			value = Float.NaN;
			msg = "no supported/implemented operation";
		}		

		turbineResponse reply = turbineResponse.newBuilder().setResult(value).setMessage(msg).build();

		responseObserver.onNext(reply);

		responseObserver.onCompleted();
	}//closes turbineStatus method
	*/
	
	
	
	
}//closes EnergyEfficiencyServer
