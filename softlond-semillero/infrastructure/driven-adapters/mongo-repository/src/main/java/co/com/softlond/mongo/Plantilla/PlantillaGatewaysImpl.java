package co.com.softlond.mongo.Plantilla;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.com.softlond.model.PlantillaModel;
import co.com.softlond.model.gateways.PlantillaGateways;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PlantillaGatewaysImpl implements PlantillaGateways {    

    @Autowired
    private ReactivePlantillaMongoRepository reactivePlantillaMongoRepository;

    @Override
    public Mono<PlantillaModel> savePlantilla(PlantillaModel plantilla) {
        return reactivePlantillaMongoRepository.save(PlantillaMapper.toCollection(plantilla))
                .map(plantillaEntity -> PlantillaMapper.toModel(plantillaEntity));
    }
    
    
    @Override
    public Flux<PlantillaModel> listarPlantilla(){
    	return reactivePlantillaMongoRepository.findAll()
    			.map(plantillaEntity -> PlantillaMapper.toModel(plantillaEntity));
    }
    
   
    
    


    @Override
    public Mono<PlantillaModel> findById(String id) {
        return reactivePlantillaMongoRepository.findById(id)
        .map(plantillaEntity -> PlantillaMapper.toModel(plantillaEntity));
    }
    
    

    @Override
    public Mono<Void> delete(PlantillaModel plantilla){
        return reactivePlantillaMongoRepository.delete(PlantillaMapper.toCollection(plantilla))
            .then(Mono.empty()); // `Mono.empty()` es retornado cuando la operación de eliminación ha terminado
    }


    
    
}
