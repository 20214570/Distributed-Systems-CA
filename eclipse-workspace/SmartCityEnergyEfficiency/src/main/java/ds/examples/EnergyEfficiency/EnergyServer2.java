package ds.examples.EnergyEfficiency;

import java.util.ArrayList;

import ds.examples.EnergyEfficiency.Service2RenewablesGrpc.Service2RenewablesImplBase;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

public class EnergyServer2 extends Service2RenewablesImplBase {

	//turbineStatus method
			public void turbineStatus(turbineRequest request, StreamObserver<turbineResponse> responseObserver) {
				
				try {
				//Get content of message from client
				System.out.println("Query: " + request.getTurbine() + "\n");

				//Build response
				turbineResponse.Builder responseBuilder = turbineResponse.newBuilder();
				
				responseBuilder.setTurbineStatus("Turbine ID: 4\nTurbine Brand Name: Windmaster 300\nNominal Power: 300 kW\nWind Speed: 29m/sec");
			
				responseObserver.onNext(responseBuilder.build());

				responseObserver.onCompleted();//server tells the client that there are no more messages
				
				} catch (StatusRuntimeException e) {
					e.printStackTrace();
				}
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
					System.out.println("WARNING: Error in hydroAverageValues() method.");
				}

				@Override
				public void onCompleted() {
					try {
					System.out.printf("hydroAverageValues method complete." );

					double temp = 0;	
					for(float v:  list) {
						temp = temp + v;
					}
					float mean = (float) (temp/list.size());

					CalculateResponse reply = CalculateResponse.newBuilder().setResult(mean).build();

					responseObserver.onNext(reply);

					responseObserver.onCompleted();
					
					} catch (StatusRuntimeException e) {
						e.printStackTrace();
					}

				}

			};

		}//closes hydroAverageValues method
	
	
	
	
}
