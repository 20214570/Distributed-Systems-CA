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


	}//closes lightSensor method
	
	
	public StreamObserver<bridgeMessage> bridgeLights(StreamObserver<bridgeResponse> responseObserver) {
		
		return new StreamObserver<bridgeMessage> () {

			@Override
			public void onNext(bridgeMessage msg) {
				System.out.println("receiving bridgeLights pedestrian count: "+ msg.getPedestrianCount());
				
				//int count =  msg.getPedestrianCount();
				
				bridgeResponse.Builder response = bridgeResponse.newBuilder();
				
				if(msg.getPedestrianCount() > 30) {
					//System.out.println("Pedestrian count is less than 30, using mains generator to power bridge lights.");	
				
					response.setEnergyStatus("Pedestrian count is less than 30, using mains generator to power bridge lights.");
					
				}else {
					response.setEnergyStatus("Pedestrian count is greater than 30, using foot traffic electricity generator for bridge lights.");	
				}
				
				//bridgeResponse reply = bridgeResponse.newBuilder().setPedestrianCount(count).build();

				bridgeResponse reply = bridgeResponse.newBuilder().setEnergyStatus(response).build();
				
				//bridgeResponse reply = bridgeResponse.newBuilder().build();
				
				responseObserver.onNext(response);
				
			}

			@Override
			public void onError(Throwable t) {
				
				t.printStackTrace();
				
			}

			@Override
			public void onCompleted() {
				System.out.println("receiving pedestrian count completed ");
				
				//completed too
				responseObserver.onCompleted();
			}
			
		};
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
