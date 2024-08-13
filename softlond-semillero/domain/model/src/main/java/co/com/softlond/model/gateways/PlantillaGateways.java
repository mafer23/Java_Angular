package co.com.softlond.model.gateways;

import co.com.softlond.model.PlantillaModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlantillaGateways {
     
    Mono<PlantillaModel> savePlantilla(PlantillaModel plantilla);
    
    Flux<PlantillaModel> listarPlantilla();

    Mono<PlantillaModel> findById(String id);
    

     Mono<Void> delete(PlantillaModel plantilla);
   
    
    
    
}
