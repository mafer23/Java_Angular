package co.com.softlond.api.Plantilla;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import co.com.softlond.model.PlantillaModel;
import co.com.softlond.usecase.Plantilla.PlantillaOperationsUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlantillaHandler {
    
    private final PlantillaOperationsUseCase plantillaOperationsUseCase;

    public PlantillaHandler(PlantillaOperationsUseCase plantillaOperationsUseCase) {
        this.plantillaOperationsUseCase = plantillaOperationsUseCase;
    }



    public Mono<ServerResponse> savePlantilla(ServerRequest request) {
        
        return request.bodyToMono(PlantillaModel.class)
                .flatMap(plantillaOperationsUseCase::savePlantilla)
                .flatMap(plantilla -> ServerResponse.ok().bodyValue(plantilla))
                .switchIfEmpty(ServerResponse.noContent().build())
                .onErrorResume(error -> ServerResponse.badRequest().bodyValue(error.getMessage()));
    }
    
    public Mono<ServerResponse> listarPlantilla(ServerRequest request){
    	Flux<PlantillaModel> plantillaList = plantillaOperationsUseCase.listarPlantilla();
       	
        return ServerResponse.ok().body(plantillaList, PlantillaModel.class);
    }
 
    

  public Mono<ServerResponse> findByIdH(ServerRequest request) {
    String id = request.pathVariable("id");

    return plantillaOperationsUseCase.findById(id)
        .flatMap(c -> ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(c))
        .switchIfEmpty(ServerResponse.notFound().build());
}

	
	


public Mono<ServerResponse> delete(ServerRequest request) {
    String id = request.pathVariable("id");

    return plantillaOperationsUseCase.findById(id)  // Busca la plantilla por su ID
        .flatMap(plantilla -> plantillaOperationsUseCase.delete(plantilla)  // Usa la plantilla encontrada para eliminarla
            .then(ServerResponse.noContent().build()))  // Retorna ServerResponse.noContent() en lugar de ResponseEntity<Void>
        .switchIfEmpty(ServerResponse.notFound().build());  // Si no se encuentra la plantilla, retorna 404
}
    
    
    


}
