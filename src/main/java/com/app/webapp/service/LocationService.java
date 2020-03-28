package com.app.webapp.service;

import com.app.webapp.model.Location;
import com.app.webapp.repository.LocationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService implements ILocationService {
    private final LocationRepository locationRepository;
    private static final int pageSize = 11;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Page<Location> findAllLocations(Integer page) {
        return locationRepository.findAll(PageRequest.of(page - 1, LocationService.pageSize));
    }

    @Override
    public Location findLocationById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public void createLocation(Location location) {
        locationRepository.save(location);
    }

    @Override
    public void editLocation(Location location) {
        locationRepository.save(location);
    }




    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public void deleteById(Long id) {
        locationRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        locationRepository.deleteAll();
    }

    @Override
    public boolean existsById(Long id) {
        return locationRepository.existsById(id);
    }
}
