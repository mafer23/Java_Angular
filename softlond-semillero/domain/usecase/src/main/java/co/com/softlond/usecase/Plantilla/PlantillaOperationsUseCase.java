package co.com.softlond.usecase.Plantilla;

import co.com.softlond.model.gateways.*;

import java.sql.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import co.com.softlond.model.HistorialModel;
import co.com.softlond.model.PlantillaModel;
import co.com.softlond.model.gateways.PlantillaGateways;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class PlantillaOperationsUseCase  {
    
    private final PlantillaGateways plantillaGateways;
    private final HistorialOperationsUseCase historialOperationsUseCase;

    public PlantillaOperationsUseCase(PlantillaGateways plantillaGateways, HistorialOperationsUseCase historialOperationsUseCase) {
        this.plantillaGateways = plantillaGateways;
        this.historialOperationsUseCase = historialOperationsUseCase;
    }

    /* public Mono<PlantillaModel> savePlantilla(PlantillaModel plantilla) {
        
        plantilla.setFechaActualizacion(new Date(System.currentTimeMillis()));

        return plantillaGateways.savePlantilla(plantilla)
                .flatMap(savedPlantilla -> historialOperationsUseCase.getHistorial()
                        .defaultIfEmpty(new HistorialModel())
                        .flatMap(history -> {
                            history.setContador(null == history.getContador() ? 1 : history.getContador() + 1);
                            history.setDescripcion(savedPlantilla.getDescripcion());
                            return historialOperationsUseCase.saveHistorial(history);
                        })
                        .thenReturn(savedPlantilla));    
    } */

    public Mono<PlantillaModel> savePlantilla(PlantillaModel plantilla) {
        return plantillaGateways.savePlantilla(plantilla)
                .doOnSuccess(savedPlantilla -> 
                saveHistorialAsync(savedPlantilla.getDescripcion())
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe());   
    }
    
    public Flux<PlantillaModel> listarPlantilla(){
    	return plantillaGateways.listarPlantilla();
    }

    
   public Mono<PlantillaModel> findById(String id){
       return plantillaGateways.findById(id);
   }

  


    
   public Mono<Void> delete(PlantillaModel plantilla){
        return plantillaGateways.delete(plantilla);
   }
    
    private Mono<Void> saveHistorialAsync(String descripcion){
        return historialOperationsUseCase.getHistorial()
                        .defaultIfEmpty(new HistorialModel())
                        .flatMap(history -> {
                            history.setContador(null == history.getContador() ? 1 : history.getContador() + 1);
                            history.setDescripcion(descripcion);
                            return historialOperationsUseCase.saveHistorial(history);
                        })
                        .then();
    }

    
}
